package com.jetpeter.android.openreddit.models;

import java.util.List;

/**
 * Created by Jefferey on 12/15/13.
 */
public class ImgurAlbumResponse {

    boolean success;
    List<ImgurImage> data;

    public boolean getSuccess() {
        return success;
    }

    public List<ImgurImage> getImages() {
        return data;
    }

}
