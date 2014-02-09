package com.jetpeter.android.openreddit.utils;

import android.util.Log;

import com.jetpeter.android.openreddit.interfaces.UrlParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jefferey on 12/17/13.
 */
public class ImgurUrlParser extends UrlParser {

    public static final String LARGE_THUMB_FORMAT = "http://i.imgur.com/%sb.jpg";
    public static final String SMALL_THUMB_FORMAT = "http://i.imgur.com/%ss.jpg";
    public static final String LARGE_FORMAT = "http://i.imgur.com/%sl.jpg";
    public static final String MEDIUM_FORMAT = "http://i.imgur.com/%sm.jpg";
    public static final String FULL_FORMAT = "http://i.imgur.com/%s.jpg";


    public static final String IMGUR_IMAGE_ID_REGEX = "(?:imgur\\.com/gallery/|imgur\\.com/(?!a/))([\\w]+)";
    public static final String IMGUR_ALBUM_ID_REGEX = "(?:imgur\\.com/a/)([\\w]+)";

    public final char mType;
    public final String mId;

    public ImgurUrlParser(String url) {
        super(url);
        if (url.contains("/a/")) {
            mType = ALBUM;
            mId = parseAlbumId();
        } else {
            mType = IMAGE;
            mId = parseImageId();
        }
        Log.v("ImgurParser", "ParsedImgur, type: " + mType + ", id: " + mId);

    }

    @Override
    public char getType() {
        return mType;
    }

    @Override
    public String getCleanUrl() {
        return getFormattedUrl(FULL_FORMAT);
    }

    @Override
    public String getAlbumId() {
        return mId;
    }

    @Override
    public String getThumbnail() {
        return getFormattedUrl(LARGE_THUMB_FORMAT);
    }

    public String getFormattedUrl(String format) {
        return String.format(format, mId);
    }

    public String parseAlbumId() {
        Pattern compiledPattern = Pattern.compile(IMGUR_ALBUM_ID_REGEX);
        Matcher matcher = compiledPattern.matcher(getUrl());
        if(matcher.find()){
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public String parseImageId() {
        Pattern compiledPattern = Pattern.compile(IMGUR_IMAGE_ID_REGEX);
        Matcher matcher = compiledPattern.matcher(getUrl());
        if(matcher.find()){
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
