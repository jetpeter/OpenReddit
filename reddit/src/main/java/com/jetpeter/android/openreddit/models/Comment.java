package com.jetpeter.android.openreddit.models;

import android.text.Html;
import android.text.Spanned;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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

    // Non reddit variables.  These keep track of the state of the view
    // for the adapter
    private int indent;
    private boolean hidden;
    private int currentChildCount;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

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

    public void setCurrentChildCount(int currentChildCount) {
        this.currentChildCount = currentChildCount;
    }

    public int getCurrentChildCount() {
        return currentChildCount;
    }

    public String getShortTimeSinceCreated() {
        Calendar now = Calendar.getInstance();
        long diffInMilliseconds = now.getTimeInMillis() - (getCreatedUtc() * 1000);
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMilliseconds);
        long seconds = diffInSec % 60;
        diffInSec/= 60;
        long minutes = diffInSec % 60;
        diffInSec /= 60;
        long hours = diffInSec % 24;
        diffInSec /= 24;
        long days = diffInSec;
        if (days > 0) {
            return days + "d";
        }
        if (hours > 0) {
            return hours + "h";
        }
        if (minutes > 0) {
            return minutes + "m";
        }
        return seconds + "s";
    }

    public Spanned getSpannedHtmlBody() {
        String htmlBody = Html.fromHtml(getBodyHtml()).toString();
        // Fix Html tags not supported by TextView
        // Supported tags are listed here: http://commonsware.com/blog/Android/2010/05/26/html-tags-supported-by-textview.html
        htmlBody = htmlBody.replaceAll("<code>", "<tt>").replaceAll("</code>", "</tt>");
        // Remove trailing newlines
        if (htmlBody.length() > 2) {
            htmlBody = htmlBody.substring(0, htmlBody.length() - 2);
        } else {
            htmlBody = "";
        }
        return Html.fromHtml(htmlBody);
    }
}