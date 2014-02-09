package com.jetpeter.android.openreddit.utils;

import com.jetpeter.android.openreddit.interfaces.UrlParser;

/**
 * Created by Jefferey on 12/17/13.
 */
public class GenericUrlParser extends UrlParser {

    private static final String[] IMAGE_EXTENSIONS = {".jpg", ".png", ".jpeg", ".gif"};

    public final char mType;


    public GenericUrlParser(String url) {
        super(url);
        if (extensionInTypes(IMAGE_EXTENSIONS)) {
            mType = UrlParser.IMAGE;
        } else {
            mType = UrlParser.NONE;
        }
    }

    @Override
    public char getType() {
        return mType;
    }

    @Override
    public String getCleanUrl() {
        return getUrl();
    }

    @Override
    public String getAlbumId() {
        return "";
    }

    @Override
    public String getThumbnail() {
        return null;
    }
}
