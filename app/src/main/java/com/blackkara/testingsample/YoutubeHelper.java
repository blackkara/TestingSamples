package com.blackkara.testingsample;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mustafa.kara on 9.2.2018.
 */

public class YoutubeHelper {
    private static final String REGEX_YOUTUBE_ID = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";
    private static final String REGEX_YOUTUBE_MOBILE_LINK = "http(?:s?):\\/\\/(?:m\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?[\\w\\?=]*)?";
    private static final String REGEX_YOUTUBE_WEB_LINK = "http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?[\\\\w\\?=]*)?";

    public String getYouTubeId(String link) {
        if (link == null || link.trim().length() <= 0)
            return null;

        Pattern pattern = Pattern.compile(REGEX_YOUTUBE_ID, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(link);

        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    public List<String> getMobileLinks(String text) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(REGEX_YOUTUBE_MOBILE_LINK)
                .matcher(text);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }

    public List<String> getWebLinks(String text) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(REGEX_YOUTUBE_WEB_LINK)
                .matcher(text);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }
}
