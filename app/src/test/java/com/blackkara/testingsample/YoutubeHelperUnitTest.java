package com.blackkara.testingsample;

import android.app.Service;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by mustafa.kara on 9.2.2018.
 */

public class YoutubeHelperUnitTest {

    private YoutubeHelper mYoutubeHelper;
    private String mLink1 = "https://www.youtube.com/watch?v=FfW2yyxBHpg";
    private String mLink2 = "https://www.youtube.com/watch?v=NTG7NZgQals";
    private String mLink3 = "https://www.youtube.com/watch?v=hQXLrPlcbeo";
    private String mLink4 = "https://m.youtube.com/watch?v=tUiP0_B6pP4";

    @Before
    public void setup() {
        //Test başlamadan önce gerekli nesneleri oluştur
        mYoutubeHelper = new YoutubeHelper();
    }

    @After
    public void teardown() {
        // Test bittikten sonra nesneleri boşa çıkart
    }

    @Test
    public void shouldReturnSingleLinkAsync() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception ec) {
                        ec.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });

            String someSentence = "A youtube link around some words "
                    + mLink1 + " to be caught from the words";

            List<String> caughtLinks = mYoutubeHelper.getWebLinks(someSentence);
            Assert.assertEquals(mLink1, caughtLinks.get(0));
            thread.start();
            countDownLatch.await();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void shouldReturnSingleLink() {
        String someSentence = "A youtube link around some words "
                + mLink1 + " to be caught from the words";

        List<String> caughtLinks = mYoutubeHelper.getWebLinks(someSentence);
        Assert.assertEquals(mLink1, caughtLinks.get(0));
    }

    @Test
    public void shouldReturnMultipleLink() {
        String someSentence = "A youtube link around some words "
                + mLink1 + " to be caught from the words."
                + " And also whole sentence has more than one a youtube link"
                + " like that " + mLink2;

        List<String> caughtLinks = mYoutubeHelper.getWebLinks(someSentence);
        Assert.assertEquals(caughtLinks.size() > 1, true);
    }

    @Test
    public void shouldNotReturnUniquely() {
        String someSentence = "A youtube link around some words "
                + mLink1 + " to be caught from the words."
                + " And also whole sentence has more than one a youtube link"
                + " like that " + mLink2
                + " BTW, this sentence includes duplicated youtube links"
                + " which are shown here : " + mLink1 + " " + mLink2 + " " + mLink3;

        List<String> caughtLinks = mYoutubeHelper.getWebLinks(someSentence);
        Assert.assertEquals(caughtLinks.size(), 5);


    }

    @Test
    public void shouldReturnWebLink() {
        String someSentence = "A youtube link around some words "
                + mLink1 + " to be caught from the words";

        List<String> caughtLinks = mYoutubeHelper.getWebLinks(someSentence);
        Assert.assertEquals(mLink1, caughtLinks.get(0));
    }

    @Test
    public void shouldReturnMobileLink() {
        String someSentence = "A youtube link around some words "
                + mLink4 + " to be caught from the words";

        List<String> caughtLinks = mYoutubeHelper.getMobileLinks(someSentence);
        Assert.assertEquals(mLink4, caughtLinks.get(0));
    }

    @Test
    public void shouldReturnWebAndMobileLinks() {
        String someSentence = "This sentence includes both web and mobile"
                + " youtube links. Web : " + mLink1 + " mobile : " + mLink4;

        Assert.assertEquals(mLink1, mYoutubeHelper.getWebLinks(someSentence).get(0));
        Assert.assertEquals(mLink4, mYoutubeHelper.getMobileLinks(someSentence).get(0));
    }
}
