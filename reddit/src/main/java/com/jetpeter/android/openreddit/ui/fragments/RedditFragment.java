package com.jetpeter.android.openreddit.ui.fragments;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Jefferey on 12/14/13.
 */
public class RedditFragment extends Fragment {


    public class DefaultErrorHandler implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            volleyError.printStackTrace();
        }
    }

    protected void setTextView(ViewGroup parent, int id, Object text) {
        try {
            TextView textView = (TextView) parent.findViewById(id);
            setTextView(textView, text);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    protected void setTextView(TextView textView, Object text) {
        textView.setText(text.toString());
    }

}
