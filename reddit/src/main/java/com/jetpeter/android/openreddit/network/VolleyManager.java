package com.jetpeter.android.openreddit.network;

import java.net.HttpURLConnection;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.support.v4.util.LruCache;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NoCache;

public class VolleyManager {

    private static volatile RequestQueue mRequestQueue;
    private static volatile ImageLoader mImageLoader;

    public static void init(Context context) {
        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }
        HttpStack stack = null;
        if (Build.VERSION.SDK_INT >= 9) {
            HttpURLConnection.setFollowRedirects(false);
            stack = new HurlStack();
        } else {
            // Prior to Gingerbread, HttpUrlConnection was unreliable.
            // See:
            // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
            stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
        }
        Network network = new BasicNetwork(stack);
        mRequestQueue = new RequestQueue(new DiskBasedCache(context.getCacheDir()), network);
        mRequestQueue.start();
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache());
    }

    public static void stopRequests(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    public static void addRequest(Request<?> request) {
        mRequestQueue.add(request);
    }

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
        public static int getDefaultLruCacheSize() {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            return cacheSize;
        }

        public BitmapLruCache() {
            this(getDefaultLruCacheSize());
        }

        public BitmapLruCache(int sizeInKiloBytes) {
            super(sizeInKiloBytes);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }
}
