package com.jetpeter.android.openreddit.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jetpeter.android.openreddit.models.Comment;
import com.jetpeter.android.openreddit.models.Post;
import com.jetpeter.android.openreddit.models.RedditDataWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jefferey on 12/15/13.
 */
public class CommentsAdapter extends BaseAdapter {


    private final List<RedditDataWrapper> mComments = new ArrayList<RedditDataWrapper>();
    private final LayoutInflater mInflater;

    public CommentsAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    public void replaceComments(List<RedditDataWrapper> newPosts) {
        mComments.clear();
        mComments.addAll(newPosts);
        notifyDataSetChanged();
    }

    public void appendComments(List<RedditDataWrapper> newPosts) {
        mComments.addAll(newPosts);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Comment getItem(int position) {
        RedditDataWrapper dataWrapper = mComments.get(position);
        if (dataWrapper.isKind(RedditDataWrapper.KIND_COMMENT)) {
            return (Comment) dataWrapper.getData();
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        Comment comment = getItem(position);
        if (comment != null) {
            TextView text = (TextView) convertView;
            text.setText(comment.getBody());
        }
        return convertView;
    }
}
