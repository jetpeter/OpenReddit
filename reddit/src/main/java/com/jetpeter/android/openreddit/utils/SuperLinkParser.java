package com.jetpeter.android.openreddit.utils;

import android.util.Log;

import com.jetpeter.android.openreddit.interfaces.UrlParser;

/**
 * Extends UrlParser but really just interfaces other Extensions
 * of UrlParser by checking the host url.
 * Created by Jefferey on 12/17/13.
 */
public class SuperLinkParser extends UrlParser {

    public static final String HOST_IMGUR = "imgur.com";
    public static final String HOST_LIVEMEME = "livememe.com";

    private final String TAG = "URL_UTILS";

    private final String mHost;
    private final UrlParser mParser;


    public SuperLinkParser(String host, String url) {
        super(url);
        Log.v("SuperParser", url);
        mHost = host;
        if (host.contains(HOST_IMGUR)) {
            mParser = new ImgurUrlParser(getUrl());
        } else if (host.contains(HOST_LIVEMEME)) {
            mParser = new LiveMemeUrlParser(getUrl());
        } else  {
            mParser = new GenericUrlParser(getUrl());
        }
    }

    @Override
    public char getType() {
        return mParser.getType();
    }

    @Override
    public String getCleanUrl() {
        return mParser.getCleanUrl();
    }

    @Override
    public String getAlbumId() {
        return mParser.getAlbumId();
    }

    @Override
    public String getThumbnail() {
        return mParser.getThumbnail();
    }
}