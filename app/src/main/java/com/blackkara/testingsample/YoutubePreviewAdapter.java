package com.blackkara.testingsample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa.kara on 9.2.2018.
 */

public class YoutubePreviewAdapter extends RecyclerView.Adapter<YoutubePreviewAdapter.ViewHolder> {
    private static final int STATE_UNINITIALIZED = 1;
    private static final int STATE_INITIALIZING = 2;
    private static final int STATE_INITIALIZED = 3;

    private Context mContext;
    private List<YoutubeModel> mMedias = new ArrayList<>();
    private int mColorBlack = Color.parseColor("#FF000000");
    private int mColorTransparent = Color.parseColor("#00000000");

    public YoutubePreviewAdapter(Context context, @NonNull List<YoutubeModel> medias) {
        mContext = context;
        mMedias = medias;
    }

    public void setData(List<YoutubeModel> medias) {
        mMedias = medias;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_youtube_preview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final YoutubeModel mediaModel = mMedias.get(position);

        holder.mImageRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMedias.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mMedias.size());
            }
        });

        holder.mYouTubeThumbnailView.setVisibility(View.VISIBLE);
        holder.mImageViewPlayIcon.setVisibility(View.VISIBLE);
        holder.mImageViewPhoto.setVisibility(View.GONE);

        holder.mYouTubeThumbnailView.setTag(R.id.youtube_video_id, mediaModel.getId());
        int state = (int) holder.mYouTubeThumbnailView.getTag(R.id.youtube_initialize);

        if (state == STATE_UNINITIALIZED) {
            holder.initialize();
        } else if (state == STATE_INITIALIZED) {
            YouTubeThumbnailLoader loader = (YouTubeThumbnailLoader) holder.mYouTubeThumbnailView.getTag(R.id.youtube_thumbnail_loader);
            loader.setVideo(mediaModel.getId());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(mContext, YoutubeFullscreenActivity.class);
                // intent.putExtra(YoutubeFullscreenActivity.PARAM_VIDEO, mediaModel.getId());
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMedias.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private void initialize() {
            mYouTubeThumbnailView.setBackgroundColor(mColorBlack);
            mYouTubeThumbnailView.setTag(R.id.youtube_initialize, STATE_INITIALIZING);
            mYouTubeThumbnailView.setTag(R.id.youtube_thumbnail_loader, null);
            mYouTubeThumbnailView.setTag(R.id.youtube_video_id, "");
            mYouTubeThumbnailView.initialize("AIzaSyDhqI1iDLZAzH8ZWc9z5IpR7O8qS6lXaH4", new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    mYouTubeThumbnailView.setTag(R.id.youtube_initialize, STATE_INITIALIZED);
                    mYouTubeThumbnailView.setTag(R.id.youtube_thumbnail_loader, youTubeThumbnailLoader);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String loadedVideoId) {
                            String currentVideoId = (String) mYouTubeThumbnailView.getTag(R.id.youtube_video_id);
                            if (currentVideoId.equals(loadedVideoId)) {
                                mYouTubeThumbnailView.setBackgroundColor(mColorTransparent);
                            } else {
                                mYouTubeThumbnailView.setBackgroundColor(mColorBlack);
                            }
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                            mYouTubeThumbnailView.setBackgroundColor(mColorBlack);
                        }
                    });

                    String videoId = (String) mYouTubeThumbnailView.getTag(R.id.youtube_video_id);
                    if (videoId != null && !videoId.isEmpty()) {
                        youTubeThumbnailLoader.setVideo(videoId);

                    }
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    youTubeThumbnailView.setTag(R.id.youtube_initialize, STATE_UNINITIALIZED);
                    mYouTubeThumbnailView.setBackgroundColor(mColorBlack);
                }
            });
        }

        private ImageView mImageViewPhoto;
        private ImageButton mImageRemoveButton;
        private YouTubeThumbnailView mYouTubeThumbnailView;
        private ImageView mImageViewPlayIcon;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
            mImageRemoveButton = (ImageButton) itemView.findViewById(R.id.imageRemoveButton);
            mYouTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youTubeThumbnailView);
            mImageViewPlayIcon = (ImageView) itemView.findViewById(R.id.imageViewPlayIcon);
            initialize();
        }
    }
}
