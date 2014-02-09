package com.jetpeter.android.openreddit.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.jetpeter.android.openreddit.R;
import com.jetpeter.android.openreddit.models.Post;
import com.jetpeter.android.openreddit.models.RedditDataWrapper;
import com.jetpeter.android.openreddit.network.VolleyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jefferey on 12/14/13.
 */
public class SubredditAdapter extends BaseAdapter {

    private final List<RedditDataWrapper> mPosts = new ArrayList<RedditDataWrapper>();
    private final LayoutInflater mInflater;

    public SubredditAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    public void replacePosts(List<RedditDataWrapper> newPosts) {
        mPosts.clear();
        mPosts.addAll(newPosts);
        notifyDataSetChanged();
    }

    public void appendPosts(List<RedditDataWrapper> newPosts) {
        mPosts.addAll(newPosts);
        notifyDataSetChanged();
    }

    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Override
    public Post getItem(int position) {
        RedditDataWrapper dataWrapper = mPosts.get(position);
        return (Post) dataWrapper.getData();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.post_row, parent, false);
            convertView.setTag(new PostViewHolder(convertView));
        }
        PostViewHolder postViewHolder = (PostViewHolder) convertView.getTag();
        postViewHolder.updateView(getItem(position));
        return convertView;
    }

    private class PostViewHolder {
        private final TextView mTitleView;
        private final TextView mSubredditView;
        private final TextView mSourceView;
        private final TextView mAuthorView;
        private final TextView mVotesTime;
        private final TextView mComments;
        private final NetworkImageView mThumbnail;

        public PostViewHolder(View row) {
            mTitleView = (TextView) row.findViewById(R.id.PostRow_title);
            mSubredditView = (TextView) row.findViewById(R.id.PostRow_subreddit);
            mSourceView = (TextView) row.findViewById(R.id.PostRow_source);
            mAuthorView = (TextView) row.findViewById(R.id.PostRow_author);
            mVotesTime = (TextView) row.findViewById(R.id.PostRow_votes_time);
            mComments = (TextView) row.findViewById(R.id.PostRow_comments);
            mThumbnail = (NetworkImageView) row.findViewById(R.id.PostRow_thumbnail);
        }

        public void updateView(Post post) {
            mTitleView.setText(post.getTitle());
            mSubredditView.setText(post.getSubreddit());
            mSourceView.setText(post.getDomain());
            mAuthorView.setText(post.getAuthor());
            mVotesTime.setText(post.getScore() + " • " + post.getShortTimeSinceCreated());
            mComments.setText(String.valueOf(post.getNumComments()));
            String thumbnailUrl = post.getThumbnail();
            if (post.hasThumbnail()) {
                mThumbnail.setImageUrl(thumbnailUrl, VolleyManager.getImageLoader());
                mThumbnail.setVisibility(View.VISIBLE);
            } else {
                mThumbnail.setVisibility(View.GONE);
            }
        }
    }
}
