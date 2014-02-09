package com.jetpeter.android.openreddit.network;

import com.android.volley.Request;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.jetpeter.android.openreddit.models.ImgurAlbumResponse;
import com.jetpeter.android.openreddit.models.ListingWrapper;
import com.jetpeter.android.openreddit.models.RNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jefferey on 12/14/13.
 */
public class ApiEndpoints {

    public static final String REDDIT_HOST = "http://api.reddit.com/";
    public static final String IMGUR_HOST = "https://api.imgur.com/3/";
    public static final String IMGUR_CLIENT_ID = "Client-ID 4c4472c97a6f953";
    public static final String IMGUR_AUTH_HEADER = "Authorization";



    public static void getSubReddit(Object tag, String subreddit, int count, String after ,Listener<ListingWrapper> listener, ErrorListener errorListener) {
        String url = REDDIT_HOST + subreddit + ".json";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new RNameValuePair("count", count));
        params.add(new RNameValuePair("after", after));
        url = String.format("%s?%s", url, URLEncodedUtils.format(params, "utf-8"));
        GsonRequest<ListingWrapper> request = new GsonRequest<ListingWrapper>(Request.Method.GET, url, listener, errorListener, ListingWrapper.class);
        request.setTag(tag);
        VolleyManager.addRequest(request);
    }

    public static void getComments(Object tag, String subreddit, String postId, Listener<ListingWrapper[]> listener, ErrorListener errorListener) {
        String url = String.format(Locale.US, "%sr/%s/comments/%s/.json", REDDIT_HOST, subreddit, postId);
        GsonRequest<ListingWrapper[]> request = new GsonRequest<ListingWrapper[]>(Request.Method.GET, url, listener, errorListener, ListingWrapper[].class);
        request.setTag(tag);
        VolleyManager.addRequest(request);
    }

    public static void getImgurAlbum(Object tag, String albumName, Listener<ImgurAlbumResponse> listener, ErrorListener errorListener) {
        String url = IMGUR_HOST + "album/" + albumName + "/images";
        GsonRequest<ImgurAlbumResponse> request = new GsonRequest<ImgurAlbumResponse>(Request.Method.GET, url, listener, errorListener, ImgurAlbumResponse.class);
        request.addHeader(IMGUR_AUTH_HEADER , IMGUR_CLIENT_ID);
        request.setTag(tag);
        VolleyManager.addRequest(request);
    }
}
