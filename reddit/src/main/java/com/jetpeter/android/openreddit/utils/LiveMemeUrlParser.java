package com.jetpeter.android.openreddit.utils;

import com.jetpeter.android.openreddit.interfaces.UrlParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jefferey on 12/18/13.
 */
public class LiveMemeUrlParser extends UrlParser {

    public static final String FULL_FORMAT = "http://i.lvme.me/%s.jpg";
    public static final String LIVEMEME_IMAGE_ID_REGEX = "(?:livememe.com/)([\\w]+)";

    private final String mImageId;

    public LiveMemeUrlParser(String url) {
        super(url);
        mImageId = parseImageId();
    }

    public String parseImageId() {
        Pattern compiledPattern = Pattern.compile(LIVEMEME_IMAGE_ID_REGEX);
        Matcher matcher = compiledPattern.matcher(getUrl());
        if(matcher.find()){
            return matcher.group(1);
        } else {
            return "";
        }
    }

    @Override
    public char getType() {
        return IMAGE;
    }

    @Override
    public String getCleanUrl() {
        return getFormattedUrl(FULL_FORMAT);
    }

    @Override
    public String getAlbumId() {
        return null;
    }

    @Override
    public String getThumbnail() {
        return null;
    }

    public String getFormattedUrl(String format) {
        return String.format(format, mImageId);
    }
}
