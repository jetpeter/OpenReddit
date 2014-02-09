package com.jetpeter.android.openreddit.interfaces;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jefferey on 12/17/13.
 */
public abstract class UrlParser {

    public static final String URL_EXTENSION_REGEX = "(?:\\.)([\\w]+)";

    public static final char IMAGE = 'i';
    public static final char VIDEO = 'v';
    public static final char ALBUM = 'a';
    public static final char NONE = 'n';

    private final String mUrl;

    public UrlParser(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public abstract char getType();

    /**
     * Returns a cleaned representation of url for the type of the url parser.
     * @return String url for the given type
     */
    public abstract String getCleanUrl();

    /**
     * Returns list of BaseAlbumImage which can be displayed in a pager
     * @return String url for the given type
     */
    public abstract String getAlbumId();


    /**
     * Returns a thumbnail for the given url if one can be parsed.
     * @return String url for the given type
     */
    public abstract String getThumbnail();

    public String getExtension() {
        String extension = "";
        try {
            URL uRL = new URL(mUrl);
            String file = uRL.getFile();
            if (file.contains(".")) {
                extension = file.substring(file.lastIndexOf('.'));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return extension;
    }


    public boolean extensionInTypes(String[] types) {
        return inType(getExtension(), types);
    }

    public boolean inType(String val, String[] types) {
        for (String type : types) {
            if (val.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
