package com.jetpeter.android.openreddit.models;

/**
 * Created by jpetersen on 1/26/14.
 */
public class ListingWrapper {

    public ListingWrapper(String kind, Listing data) {
        this.kind = kind;
        this.data = data;
    }

    private String kind;
    private Listing data;

    public String getKind() {
        return kind;
    }

    public Listing getListing() {
        return data;
    }
}
