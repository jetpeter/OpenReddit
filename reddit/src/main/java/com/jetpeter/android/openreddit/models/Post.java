package com.jetpeter.android.openreddit.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jefferey on 12/14/13.
 *
 * This data model represents the T3 object in the reddit api
 *
 */
public class Post extends RedditData implements Serializable {

    private String domain;
    private String banned_by;
    private String subreddit;
    private String selftext_html;
    private String selftext;
    private String likes;
    private String link_flair_text;
    private String author;
    private String thumbnail;
    private String subreddit_id;
    private String link_flair_css_class;
    private String author_flair_css_class;
    private String permalink;
    private String url;
    private String author_flair_text;
    private String title;
    private String distinguished;
    private boolean visited;
    private boolean clicked;
    private boolean stickied;
    private boolean saved;
    private boolean is_self;
    private boolean over_18;
    private boolean hidden;
    private long created;
    private long created_utc;
    private long score;
    private long num_comments;
    private long ups;
    private long downs;
    private long num_reports;

    public Post() {
    }

    public String getDomain() {
        return domain;
    }

    public String getBannedBy() {
        return banned_by;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getSelftextHtml() {
        return selftext_html;
    }

    public String getSelftext() {
        return selftext;
    }

    public String getLikes() {
        return likes;
    }

    public String getLinkFlairText() {
        return link_flair_text;
    }

    public String getAuthor() {
        return author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getSubredditId() {
        return subreddit_id;
    }

    public String getLinkFlairCssClass() {
        return link_flair_css_class;
    }

    public String getAuthorFlairCssClass() {
        return author_flair_css_class;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthorFlairText() {
        return author_flair_text;
    }

    public String getTitle() {
        return title;
    }

    public String getDistinguished() {
        return distinguished;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isStickied() {
        return stickied;
    }

    public boolean isSaved() {
        return saved;
    }

    public boolean isSelf() {
        return is_self;
    }

    public boolean isOver18() {
        return over_18;
    }

    public boolean isHidden() {
        return hidden;
    }

    public long getCreated() {
        return created;
    }

    public long getCreatedUtc() {
        return created_utc;
    }

    public long getScore() {
        return score;
    }

    public long getNumComments() {
        return num_comments;
    }

    public long getUps() {
        return ups;
    }

    public long getDowns() {
        return downs;
    }

    public long getNumReports() {
        return num_reports;
    }

    public String getShortTimeSinceCreated() {
        Calendar now = Calendar.getInstance();
        long diffInMillisec = now.getTimeInMillis() - (getCreatedUtc() * 1000);
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
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

    public boolean hasThumbnail() {
        if (thumbnail != null && thumbnail.startsWith("http://")) {
            return true;
        }
        return false;
    }
}
