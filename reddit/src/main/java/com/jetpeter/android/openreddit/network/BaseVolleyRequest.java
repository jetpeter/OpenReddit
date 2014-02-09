package com.jetpeter.android.openreddit.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseVolleyRequest <T> extends Request<T> {

    /** The default socket timeout in milliseconds */
    public static final int DEFAULT_TIMEOUT_MS = 20000;

    /** The default number of retries */
    public static final int DEFAULT_MAX_RETRIES = 0;

    /** The default backoff multiplier */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

    protected List<NameValuePair> mParams;
    private final Map<String, String> mHeaders =  new HashMap<String, String>();

    public BaseVolleyRequest(int method, String url, ErrorListener listener) {
        super(method, url, listener);
        init();
    }

    private void init() {
        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));
    }

    public void addPostParams(List<NameValuePair> params) {
        mParams = params;
    }

    public void addHeader(String name, String value) {
        mHeaders.put(name, value);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        super.getHeaders();
        return mHeaders;
    }

    /**
     * We want to encode our own parameters because we don't want to use a Map
     * object to store our params. That way we can have duplicate keys which is
     * required for a couple of our post requests.
     *
     * @param params
     *            List<NameValuePair> params;
     * @param paramsEncoding
     *            whatever is returned from getParamsEncoding();
     * @return byte[] of encoded data.
     */
    protected byte[] encodeParameters(List<NameValuePair> params, String paramsEncoding) {
            return URLEncodedUtils.format(params, paramsEncoding).getBytes();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mParams != null && mParams.size() > 0) {
            return encodeParameters(mParams, getParamsEncoding());
        }
        return null;
    }
}