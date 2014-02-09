package com.jetpeter.android.openreddit.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.jetpeter.android.openreddit.R;
import com.jetpeter.android.openreddit.ui.fragments.NavigationDrawerFragment;
import com.jetpeter.android.openreddit.ui.fragments.SubredditFragment;
import com.jetpeter.android.openreddit.ui.views.BackSwipeLayout;

public class RedditActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TITLE_KEY = "title";

    public static final String POST_FRAGMENT_TAG = "post_fragment";
    public static final String SUBREDDIT_FRAGMENT_TAG = "subreddit_fragment";

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private BackSwipeLayout mBackSwipeLayout;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddit);
        final FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(new BackStackChangeListener());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment = (NavigationDrawerFragment) fm.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawer);
        mBackSwipeLayout = (BackSwipeLayout) findViewById(R.id.container);
        mBackSwipeLayout.setBackSwipeInterface(new RedditBackSwipeInterface(fm));
        updateNavigationMode(fm);
        if (savedInstanceState != null) {
            mTitle = savedInstanceState.getString(TITLE_KEY);
        } else {
            mTitle = getTitle().toString();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_KEY, mTitle);
    }

    @Override
    public void onBackPressed() {
        mBackSwipeLayout.cancelBackSwipe();
        super.onBackPressed();
    }

    /**
     * Based on how many items are in the fragment back stack enable back swipe
     * or the drawer menu.
     * @param fm Instance of FragmentManager
     */
    private void updateNavigationMode(FragmentManager fm) {
        enableDrawer(fm.getBackStackEntryCount() == 0);
        enableBackSwipe(fm.getBackStackEntryCount() > 0);
    }

    private void enableDrawer(boolean enable) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int drawerMode = enable ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(drawerMode);
        mNavigationDrawerFragment.enableDrawer(enable);
    }

    private void enableBackSwipe(boolean enable) {
        BackSwipeLayout backSwipe = (BackSwipeLayout) findViewById(R.id.container);
        backSwipe.setBackSwipeEnabled(enable);
    }

    @Override
    public void onSubredditSelected(String name) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        SubredditFragment frag = SubredditFragment.newInstance(name);
        ft.replace(R.id.container, frag, SUBREDDIT_FRAGMENT_TAG);
        ft.commit();
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.reddit, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    /**
     * Special add fragment method that replaces the content fragment without using
     * FragmentTransaction.replace.  Instead of using replace this method hides the
     * old fragment which is cheaper than detaching it.
     *
     * @param newContent The new fragment being added to the content frame
     * @param newContentTag The Tag for the new fragment
     * @param oldContent The current content fragment that we are replacing.
     */
    public void addContentFragment(Fragment newContent, String newContentTag, Fragment oldContent) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        ft.hide(oldContent);
        ft.add(R.id.container, newContent, newContentTag);
        ft.addToBackStack(oldContent.getTag());
        ft.commit();
    }

    private class RedditBackSwipeInterface implements BackSwipeLayout.BackSwipeInterface {

        private final FragmentManager mFragmentManager;
        public RedditBackSwipeInterface(FragmentManager fm) {
            mFragmentManager = fm;
        }

        @Override
        public void onBackSwipeStarted() {
            Fragment frag = getLatestFragmentFromBackStack();
            mFragmentManager.beginTransaction().show(frag).commit();
        }

        @Override
        public void onBackSwipeEnd(boolean didSwipeBack) {
            if (didSwipeBack) {
                mFragmentManager.popBackStack();
            } else {
                Fragment frag = getLatestFragmentFromBackStack();
                mFragmentManager.beginTransaction().hide(frag).commit();
            }
        }

        public Fragment getLatestFragmentFromBackStack() {
            int entryCount = mFragmentManager.getBackStackEntryCount();
            if (entryCount > 0) {
                FragmentManager.BackStackEntry entry = mFragmentManager.getBackStackEntryAt(entryCount - 1);
                return mFragmentManager.findFragmentByTag(entry.getName());
            } else {
                return null;
            }
        }
    }

    private class BackStackChangeListener implements FragmentManager.OnBackStackChangedListener {
        @Override
        public void onBackStackChanged() {
            FragmentManager fm = getSupportFragmentManager();
            updateNavigationMode(fm);
        }
    }
}
