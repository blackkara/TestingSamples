package com.blackkara.testingsample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_youtube_sharing.*

class YoutubeSharingActivity : AppCompatActivity() {

    private lateinit var mYoutubeHelper: YoutubeHelper
    private lateinit var mYoutubeModels: MutableList<YoutubeModel>
    private lateinit var mYoutubePreviewAdapter: YoutubePreviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_sharing)

        mYoutubeHelper = YoutubeHelper()
        mYoutubeModels = mutableListOf<YoutubeModel>()
        mYoutubePreviewAdapter = YoutubePreviewAdapter(this, mYoutubeModels)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val youtubeLink = intent?.getStringExtra(Intent.EXTRA_TEXT)
        val youtubeId = mYoutubeHelper.getYouTubeId(youtubeLink)
        val youtubeModel = YoutubeModel(youtubeId, youtubeLink)
        mYoutubeModels.add(youtubeModel)
        mYoutubePreviewAdapter.setData(mYoutubeModels)
        recyclerViewPreview.adapter = mYoutubePreviewAdapter

    }
}
