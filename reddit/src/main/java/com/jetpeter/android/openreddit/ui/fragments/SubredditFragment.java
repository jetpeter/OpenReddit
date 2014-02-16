package com.jetpeter.android.openreddit.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.Activity;

import com.android.volley.Response;
import com.jetpeter.android.openreddit.R;
import com.jetpeter.android.openreddit.models.Listing;
import com.jetpeter.android.openreddit.models.ListingWrapper;
import com.jetpeter.android.openreddit.network.ApiEndpoints;
import com.jetpeter.android.openreddit.ui.ListSwipeInteraction;
import com.jetpeter.android.openreddit.ui.RedditActivity;
import com.jetpeter.android.openreddit.ui.adapters.SubredditAdapter;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;


/**
 * Created by Jefferey on 11/22/13.
 *
 * Fragment that downloads and shows the list of posts for the given subreddit in the newInstance
 * Method.  Failing to use the newInstance method of this fragment will cause problems
 */
public class SubredditFragment extends RedditFragment implements ListView.OnItemClickListener, OnRefreshListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SUBREDDIT = "subreddit_name";

    private SubredditAdapter mAdapter;
    private PullToRefreshLayout mPullToRefreshLayout;
    private boolean isFetchingMorePosts;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SubredditFragment newInstance(String subreddit) {
        SubredditFragment fragment = new SubredditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUBREDDIT, subreddit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isFetchingMorePosts = false;
        mAdapter = new SubredditAdapter(getActivity().getLayoutInflater());
        updateSubreddit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subreddit, container, false);
        mPullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.ptr_layout);
        ListView postList = (ListView) rootView.findViewById(R.id.Subreddit_post_list);
        postList.setOnScrollListener(new PostsScrollListener());
        postList.setAdapter(mAdapter);
        postList.setOnItemClickListener(this);
        postList.setOnTouchListener(new ListSwipeInteraction(postList, null));

        // Now setup the PullToRefreshLayout
        ActionBarPullToRefresh.from(getActivity())
                // Mark All Children as pullable
                .allChildrenArePullable()
                        // Set the OnRefreshListener
                .listener(this)
                        // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((RedditActivity) activity).setTitle(getSubredditTitle());
    }

    public String getSubredditTitle() {
        String title = getSubreddit();
        return title.length() > 0 ? title : "Front Page";
    }

    public String getSubreddit() {
        Bundle args = getArguments();
        return args.getString(ARG_SUBREDDIT);
    }

    private void updateSubreddit() {
        ApiEndpoints.getSubReddit(this, getSubreddit(), 0, "", new SubredditListener(), new DefaultErrorHandler());
    }

    @Override
    public void onRefreshStarted(View view) {
        mAdapter.clear();
        updateSubreddit();
    }

    public void fetchMorePosts(int count, String after) {
        isFetchingMorePosts = true;
        ApiEndpoints.getSubReddit(this, getSubreddit(), count, after, new MorePostsListener(), new DefaultErrorHandler());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RedditActivity activity = (RedditActivity) getActivity();
        PostFragment frag =  PostFragment.newInstance(mAdapter.getItem(position));
        activity.addContentFragment(frag, RedditActivity.POST_FRAGMENT_TAG, this);
    }

    public class SubredditListener implements Response.Listener<ListingWrapper> {
        @Override
        public void onResponse(ListingWrapper getListingResponse) {
            Listing listing = getListingResponse.getListing();
            mAdapter.replacePosts(listing.getChildren());
            if (mPullToRefreshLayout != null) {
                mPullToRefreshLayout.setRefreshComplete();
            }
        }
    }

    public class MorePostsListener implements Response.Listener<ListingWrapper> {
        @Override
        public void onResponse(ListingWrapper getListingResponse) {
            isFetchingMorePosts = false;
            Listing listing = getListingResponse.getListing();
            mAdapter.appendPosts(listing.getChildren());
        }
    }

    public class PostsScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int lastVisibleIndex = visibleItemCount + firstVisibleItem;
            if (mAdapter == null || mAdapter.getCount() == 0 || totalItemCount == 0) {
                return;
            }
            if (!isFetchingMorePosts && lastVisibleIndex >= (totalItemCount - 5)) {
                fetchMorePosts(totalItemCount, mAdapter.getItem(totalItemCount - 1).getName());
            }
        }
    }
}