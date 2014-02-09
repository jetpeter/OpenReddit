package com.jetpeter.android.openreddit.models;

/**
 * Created by Jefferey on 1/26/14.
 *
 * This data model represents the T1 object in the reddit api
 */
public class Comment extends RedditData {

    private String subreddit_id;
    private String banned_by;
    private String likes;
    private ListingWrapper replies;
    private boolean saved;
    private int gilded;
    private String author;
    private String parent_id;
    private String approved_by;
    private String body;
    //private long edited;
    private String author_flair_css_class;
    private int downs;
    private String body_html;
    private String link_id;
    private long created;
    private String author_flair_text;
    private long created_utc;
    private boolean distinguished;
    private int num_reports;
    private int ups;

    public String getSubredditId() {
        return subreddit_id;
    }

    public String getBannedBy() {
        return banned_by;
    }

    public String getLikes() {
        return likes;
    }

    public ListingWrapper getReplies() {
        return replies;
    }

    public boolean isSaved() {
        return saved;
    }

    public int getGilded() {
        return gilded;
    }

    public String getAuthor() {
        return author;
    }

    public String getParentId() {
        return parent_id;
    }

    public String getApprovedBy() {
        return approved_by;
    }

    public String getBody() {
        return body;
    }

//    public long getEdited() {
//        return edited;
//    }

    public String getAuthorFlairCssClass() {
        return author_flair_css_class;
    }

    public int getDowns() {
        return downs;
    }

    public String getBodyHtml() {
        return body_html;
    }

    public String getLinkId() {
        return link_id;
    }

    public long getCreated() {
        return created;
    }

    public String getAuthorFlairText() {
        return author_flair_text;
    }

    public long getCreatedUtc() {
        return created_utc;
    }

    public boolean isDistinguished() {
        return distinguished;
    }

    public int getNumReports() {
        return num_reports;
    }

    public int getUps() {
        return ups;
    }
}