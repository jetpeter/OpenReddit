package com.jetpeter.android.openreddit.models;

import org.apache.http.NameValuePair;

/**
 * Created by Jefferey on 12/15/13.
 */
public class RNameValuePair implements NameValuePair {

    String mName;
    Object mValue;

    public RNameValuePair(String name, Object value) {
        mName = name;
        mValue = value;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getValue() {
        return String.valueOf(mValue);
    }
}
