package com.blackkara.testingsample;

import android.content.Context;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by mustafa.kara on 9.2.2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class YoutubeHelperMockitoTest {


    @Mock Context mContext;
    private YoutubeHelper mYoutubeHelper;

    @Before
    public void setup() {
        //Test başlamadan önce gerekli nesneleri oluştur
        mYoutubeHelper = new YoutubeHelper();
        when(mContext.getString(R.string.link1)).thenReturn("https://www.youtube.com/watch?v=FfW2yyxBHpg");
        when(mContext.getString(R.string.link2)).thenReturn("https://www.youtube.com/watch?v=NTG7NZgQals");
    }

    @After
    public void teardown() {
        // Test bittikten sonra nesneleri boşa çıkart
    }

    @Test
    public void shouldReturnSingleLink() {
        String someSentence = "A youtube link around some words "
                + mContext.getString(R.string.link1) + " to be caught from the words";

        List<String> caughtLinks = mYoutubeHelper.getWebLinks(someSentence);
        Assert.assertEquals(mContext.getString(R.string.link1), caughtLinks.get(0));
    }

    @Test
    public void shouldReturnMultipleLink() {
        String someSentence = "A youtube link around some words "
                + mContext.getString(R.string.link1) + " to be caught from the words."
                + " And also whole sentence has more than one a youtube link"
                + " like that " + mContext.getString(R.string.link2);

        List<String> caughtLinks = mYoutubeHelper.getWebLinks(someSentence);
        Assert.assertEquals(caughtLinks.size() > 1, true);
    }
}
