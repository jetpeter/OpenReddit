package com.jetpeter.android.openreddit.models;

/**
 * Created by Jefferey on 12/15/13.
 */
public class ImgurImage extends ImgurTools {

    String link;
    String id;
    String title;
    String description;
    String type;
    int width;
    int height;
    int views;
    boolean favorite;
    boolean nsfw;
    boolean animated;
    long datetime;

    public String getFormattedUrl(String format) {
        return getFormattedUrl(format, id);
    }

    public String getLink() {
        return link;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getViews() {
        return views;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public boolean isAnimated() {
        return animated;
    }

    public long getDatetime() {
        return datetime;
    }
}
