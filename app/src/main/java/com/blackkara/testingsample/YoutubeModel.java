package com.blackkara.testingsample;

/**
 * Created by mustafa.kara on 9.2.2018.
 */

public class YoutubeModel {
    private String mId;
    private String mUrl;

    public YoutubeModel(String id, String url){
        mId = id;
        mUrl = url;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setUrl(String url){
        mUrl = url;
    }

    public String getUrl(){
        return mUrl;
    }

}
