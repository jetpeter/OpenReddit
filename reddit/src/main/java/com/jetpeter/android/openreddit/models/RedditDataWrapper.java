package com.jetpeter.android.openreddit.models;

/**
 * Created by jpetersen on 1/26/14.
 */
public class RedditDataWrapper {

    public static final String KIND_COMMENT = "t1";
    public static final String KIND_ACCOUNT = "t2";
    public static final String KIND_POST = "t3";
    public static final String KIND_MESSAGE = "t4";
    public static final String KIND_SUBREDDIT = "t5";
    public static final String KIND_AWARD = "t6";
    public static final String KIND_PROMO_CAMPAIGN = "t8";

    public RedditDataWrapper(String kind, RedditData data) {
        this.kind = kind;
        this.data = data;
    }

    private String kind;
    private RedditData data;

    public boolean isKind(String kindCheck) {
        return kind.equals(kindCheck);
    }

    public String getKind() {
        return kind;
    }

    public RedditData getData() {
        return data;
    }

}
