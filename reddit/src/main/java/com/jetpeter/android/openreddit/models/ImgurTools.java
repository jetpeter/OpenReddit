package com.jetpeter.android.openreddit.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jefferey on 12/15/13.
 */
public class ImgurTools {

    public static final String IMGUR_IMAGE_ID_REGEX = "(?:imgur\\.com\\/gallery\\/|imgur\\.com\\/(?!a\\/))([\\w]+)";
    public static final String IMGUR_ALBUM_ID_REGEX = "(?:imgur\\.com\\/a\\/)([\\w]+)";


    public static final String IMGUR_URL = "imgur.com";
    public static final String IMGUR_ALBUM_PATH = "/a/";

    public static final String LARGE_THUMB_FORMAT = "http://i.imgur.com/%sb.jpg";
    public static final String SMALL_THUMB_FORMAT = "http://i.imgur.com/%ss.jpg";
    public static final String LARGE_FORMAT = "http://i.imgur.com/%sl.jpg";
    public static final String MEDIUM_FORMAT = "http://i.imgur.com/%sm.jpg";
    public static final String FULL_FORMAT = "http://i.imgur.com/%s.jpg";


    public static String parseImageId(String url) {
        Pattern compiledPattern = Pattern.compile(IMGUR_IMAGE_ID_REGEX);
        Matcher matcher = compiledPattern.matcher(url);
        if(matcher.find()){
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public static boolean isImgur(String url) {
        return url.contains(IMGUR_URL);
    }

    public static boolean isImage(String url) {
        return url.matches(IMGUR_IMAGE_ID_REGEX);
    }

    public static boolean isAlbum(String url) {
        return url.matches(IMGUR_ALBUM_ID_REGEX);
    }

    public static String parseAlbumId(String url) {
        Pattern compiledPattern = Pattern.compile(IMGUR_ALBUM_ID_REGEX);
        Matcher matcher = compiledPattern.matcher(url);
        if(matcher.find()){
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public static String getFormattedUrl(String format, String id) {
        return String.format(format, id);
    }
}
