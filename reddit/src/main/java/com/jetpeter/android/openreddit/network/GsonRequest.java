package com.jetpeter.android.openreddit.network;


import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.jetpeter.android.openreddit.utils.GsonManager;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jefferey on 12/14/13.
 */
public class GsonRequest<T> extends BaseVolleyRequest<T> {

    private final Response.Listener<T> mListener;
    private final Class<T> mClazz;

    public GsonRequest(int method, String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Class clazz) {
        super(method, url, errorListener);
        mListener = listener;
        mClazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        String parsed;
        try {
            parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(networkResponse.data);
        }
        Gson gson = GsonManager.getInstance();
        T responseModel = gson.fromJson(parsed, mClazz);
        return Response.success(responseModel, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}
