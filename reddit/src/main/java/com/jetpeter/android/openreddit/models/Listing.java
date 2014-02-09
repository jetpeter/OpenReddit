package com.jetpeter.android.openreddit.models;

import java.util.List;

/**
 * Created by jpetersen on 1/26/14.
 */
public class Listing {

    private String modhash;
    private List<RedditDataWrapper> children;
    private String after;
    private String before;

    public String getModhash() {
        return modhash;
    }

    public List<RedditDataWrapper> getChildren() {
        return children;
    }

}
