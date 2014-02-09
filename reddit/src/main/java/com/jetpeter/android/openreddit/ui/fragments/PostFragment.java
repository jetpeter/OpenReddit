package com.jetpeter.android.openreddit.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;
import com.jetpeter.android.openreddit.R;
import com.jetpeter.android.openreddit.interfaces.UrlParser;
import com.jetpeter.android.openreddit.models.Comment;
import com.jetpeter.android.openreddit.models.ListingWrapper;
import com.jetpeter.android.openreddit.models.Post;
import com.jetpeter.android.openreddit.models.RedditDataWrapper;
import com.jetpeter.android.openreddit.network.ApiEndpoints;
import com.jetpeter.android.openreddit.network.VolleyManager;
import com.jetpeter.android.openreddit.ui.adapters.CommentsAdapter;
import com.jetpeter.android.openreddit.utils.SuperLinkParser;

import java.util.List;

/**
 * Created by Jefferey on 11/23/13.
 */
public class PostFragment extends RedditFragment {

    private static final String[] IMAGE_EXTENSIONS = {".jpg", ".png", ".jpeg"};

    private static final String POST_KEY = "post_key";

    private CommentsAdapter mCommentsAdapter;

    public static PostFragment newInstance(Post post) {
        PostFragment frag = new PostFragment();
        Bundle args = new Bundle();
        args.putSerializable(POST_KEY, post);
        frag.setArguments(args);
        return frag;
    }

    protected Post getPost() {
        Bundle args = getArguments();
        return (Post) args.getSerializable(POST_KEY);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommentsAdapter = new CommentsAdapter(getActivity().getLayoutInflater());
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup content = (ViewGroup) inflater.inflate(R.layout.fragment_post, container, false);
        ListView commentsView = (ListView) content.findViewById(R.id.PostView_comments);
        Post post = getPost();
        View postHeader = initPostHeader(post, inflater, commentsView);
        commentsView.addHeaderView(postHeader);
        View contentHeader = getContentView(post, inflater, commentsView);
        if (contentHeader != null) {
            commentsView.addHeaderView(contentHeader);
        }
        commentsView.setAdapter(mCommentsAdapter);
        fetchComments(post);
        return content;
    }

    public View initPostHeader(Post post, LayoutInflater inflater, ViewGroup parent) {
        ViewGroup postHeader = (ViewGroup) inflater.inflate(R.layout.post_header, parent, false);
        setTextView(postHeader, R.id.PostHeader_title, post.getTitle());
        setTextView(postHeader, R.id.PostHeader_num_comments, post.getNumComments() + " comments");
        if (post.hasThumbnail()) {
            NetworkImageView thumbnail = (NetworkImageView) postHeader.findViewById(R.id.PostHeader_thumbnail);
            thumbnail.setImageUrl(post.getThumbnail(), VolleyManager.getImageLoader());
        }
        return postHeader;
    }

    public void fetchComments(Post post) {
        ApiEndpoints.getComments(this, post.getSubreddit(), post.getId(), new CommentsListener(), new DefaultErrorHandler());
    }

    private class CommentsListener implements Response.Listener<ListingWrapper[]> {
        @Override
        public void onResponse(ListingWrapper[] listingWrappers) {
            if (listingWrappers.length > 1) {
                ListingWrapper commentsWrapper = listingWrappers[1];
                List<RedditDataWrapper> comments = commentsWrapper.getListing().getChildren();
                mCommentsAdapter.replaceComments(comments);
            }
        }
    }

    public View getContentView(Post post, LayoutInflater inflater, ViewGroup parent) {
        View contentView = null;
        if(post.isSelf()) {
            contentView = getSelfContent(post, inflater, parent);
        } else {
            SuperLinkParser linkParser = new SuperLinkParser(post.getDomain(), post.getUrl());
            switch (linkParser.getType()) {
                case UrlParser.IMAGE:
                    contentView = addImage(linkParser.getCleanUrl(), inflater, parent);
                    break;
                case UrlParser.ALBUM:
                    contentView = addAlbum(linkParser.getAlbumId(), inflater, parent);
                    break;
            }
        }
        return contentView;
    }

    public boolean hasImageExtension(String url) {
        for (String ext : IMAGE_EXTENSIONS) {
            if (url.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    public View getSelfContent(Post post, LayoutInflater inflater, ViewGroup parent) {
        ViewGroup contentHeader = (ViewGroup) inflater.inflate(R.layout.self_text_header, parent, false);
        setTextView(contentHeader, R.id.SelfText_text, post.getSelftext());
        return contentHeader;
    }

    public View addAlbum(String album, LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    public View addImage(String url, LayoutInflater inflater, ViewGroup parent) {
        ViewGroup contentHeader = (ViewGroup) inflater.inflate(R.layout.image_header, parent, false);
        NetworkImageView image = (NetworkImageView) contentHeader.findViewById(R.id.PostHeader_image);
        image.setImageUrl(url, VolleyManager.getImageLoader());
        return contentHeader;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentManager fm = getFragmentManager();
            fm.popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}