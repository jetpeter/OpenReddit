package com.jetpeter.android.openreddit;

import android.app.Application;

import com.jetpeter.android.openreddit.network.VolleyManager;

/**
 * Created by Jefferey on 12/14/13.
 */
public class RedditApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.init(this);
    }
}
