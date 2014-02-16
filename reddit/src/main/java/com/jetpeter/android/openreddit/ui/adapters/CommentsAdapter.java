package com.jetpeter.android.openreddit.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jetpeter.android.openreddit.R;
import com.jetpeter.android.openreddit.models.Comment;
import com.jetpeter.android.openreddit.models.ListingWrapper;
import com.jetpeter.android.openreddit.models.RedditDataWrapper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jefferey on 12/15/13.
 *
 * Adapter to add comment threads to a list view.  This adapter handles expanding and collapsing
 * of threads be removing the comments from the working set.  The comment thread order is preserved
 * inside of of the comments children.
 */
public class CommentsAdapter extends BaseAdapter {


    // Use a linked list because we are going to be removing and adding a lot of entries
    // when we hide and show threads.
    private final List<RedditDataWrapper> mComments = new LinkedList<RedditDataWrapper>();
    private final LayoutInflater mInflater;

    public CommentsAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    /**
     * Remove the current set of comments and add all the new comments
     * Child elements of comments are added recursively to the list
     * and their depth is set in the Comment object.  The comment child
     * structure is not effected by this process so if we remove comments
     * from mComments they can be re-added easily with a given index.
     * @param newPosts List of RedditDataWrapper objects to replace in the adapter
     */
    public void replaceComments(List<RedditDataWrapper> newPosts) {
        mComments.clear();
        flattenAndAddComments(newPosts, 0);
        notifyDataSetChanged();
    }

    /**
     * Add a list of comment threads to the end of the current comment thread,
     * @param newPosts List of RedditDataWrapper objects to add to adapter
     */
    public void appendComments(List<RedditDataWrapper> newPosts) {
        flattenAndAddComments(newPosts, 0);
        notifyDataSetChanged();
    }

    /**
     * Recursively flatten a list of comment threads. For each comment in the list
     * the comment is first added to the list then this method is called recursively
     * on the list of children comments so comment order will be preserved. Each time
     * this method is called depth is incremented so when we add the children to the
     * ListView we can properly add indentation.
     * @param commentData List of RedditDataWrapper objects that represents a comment thread
     * @param depth The current depth of this method.  Depth should be the current depth of
     *              comment tread so children of the comment will be indented properly.
     */
    private void flattenAndAddComments(List<RedditDataWrapper> commentData, int depth) {
        for (RedditDataWrapper redditData : commentData) {
            if (redditData.isKind(RedditDataWrapper.KIND_COMMENT)) {
                Comment comment = (Comment) redditData.getData();
                comment.setIndent(depth);
                mComments.add(redditData);
                ListingWrapper listingWrapper = comment.getReplies();
                if (listingWrapper != null) {
                    flattenAndAddComments(listingWrapper.getListing().getChildren(), depth + 1);
                }
            }
        }
    }

    /**
     * Removes a comment thread from mComments so we can hide threads of comments.
     * The comment structure is preserved in the children of the comment so comments
     * can be re-added later.  This method is only for removing comments from the view
     * not for actually removing comments from the data structure.
     * @param parentComment Parent comment of the thread we are removing.
     * @return The number of comments hidden
     */
    private int removeChildren(Comment parentComment) {
        int commentsRemoved = 0;
        ListingWrapper listingWrapper = parentComment.getReplies();
        parentComment.setHidden(true);
        if (listingWrapper != null) {
            for (RedditDataWrapper redditData : listingWrapper.getListing().getChildren()) {
                if (redditData.isKind(RedditDataWrapper.KIND_COMMENT)) {
                    Comment comment = (Comment) redditData.getData();
                    // First remove the children of the comment
                    commentsRemoved += removeChildren(comment);
                    mComments.remove(redditData);
                    commentsRemoved++;
                }
            }
        }
        return commentsRemoved;
    }

    /**
     * Add the children of the given comment into the mComments linked list
     * Thread order will be preserved and children indentation is set to
     * one more than the parent comment so this method can be used to insert
     * a thread into the comment structure not just show hidden comments.
     * When calling this method make sure the depth of the parent comment is
     * properly set.
     * @param parentComment The comment we want to add the children for, The comment itself
     *                      is not be added
     * @param index The index of the given comment
     */
    private void addChildren(Comment parentComment, int index) {
        ListingWrapper listingWrapper = parentComment.getReplies();
        parentComment.setHidden(false);
        if (listingWrapper != null) {
            List<RedditDataWrapper> commentData = listingWrapper.getListing().getChildren();
            for (int i = commentData.size() - 1; i >= 0; i--) {
                RedditDataWrapper redditData = commentData.get(i);
                if (redditData.isKind(RedditDataWrapper.KIND_COMMENT)) {
                    Comment comment = (Comment) redditData.getData();
                    comment.setIndent(parentComment.getIndent() + 1);
                    addChildren(comment, index);
                    mComments.add(index ,redditData);
                }
            }
        }
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
            convertView = mInflater.inflate(R.layout.comment_row, parent, false);
            convertView.setOnClickListener(new CommentClickListener());
            CommentViewHolder holder = new CommentViewHolder(convertView);
            convertView.setTag(holder);
        }
        Comment comment = getItem(position);
        if (comment != null) {
            CommentViewHolder holder = (CommentViewHolder) convertView.getTag();
            holder.setComment(comment, position);
            int padding = comment.getIndent() * convertView.getResources().getDimensionPixelOffset(R.dimen.padding_large);
            convertView.setPadding(padding, 0, 0, 0);
        }
        return convertView;
    }

    private class CommentViewHolder {

        private final TextView mAuthor;
        private final TextView mPoints;
        private final TextView mText;
        private final TextView mMoreCount;

        private int mIndex;

        public CommentViewHolder(View commentRow) {
            mAuthor = (TextView) commentRow.findViewById(R.id.CommentRow_author);
            mPoints = (TextView) commentRow.findViewById(R.id.CommentRow_points);
            mText = (TextView) commentRow.findViewById(R.id.CommentRow_text);
            mMoreCount = (TextView) commentRow.findViewById(R.id.CommentRow_more);
        }

        public void setComment(Comment comment, int index) {
            mAuthor.setText(comment.getAuthor());
            mPoints.setText(comment.getUps() - comment.getDowns() + "pts");
            mText.setText(comment.getBody());
            mText.setVisibility(comment.isHidden() ? View.GONE : View.VISIBLE);
            mMoreCount.setVisibility(comment.isHidden() ? View.VISIBLE : View.GONE);
            mIndex = index;
        }

        public int getIndex() {
            return mIndex;
        }

        public void collapseText(int moreCount) {
            // TODO animate this
            mText.setVisibility(View.GONE);
            mMoreCount.setText(moreCount + " more");
            mMoreCount.setVisibility(View.VISIBLE);
        }

        public void expandText() {
            // TODO animate this
            mText.setVisibility(View.VISIBLE);
            mMoreCount.setVisibility(View.GONE);
        }
    }

    /**
     * Click listener that hide or shows the list of comments.
     * The index of the comment is retrieved from the tag of the view
     * So this click listener does not have to be re-instantiated every time
     * the view is recycled.  If the tag is not an instance of CommentViewHolder of this view
     * an exception will be thrown and caught and nothing will happen.
     */
    private class CommentClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                CommentViewHolder holder = (CommentViewHolder) view.getTag();
                toggleComments(holder);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }

        private void toggleComments(CommentViewHolder holder) {
            int position = holder.getIndex();
            Comment comment = getItem(position);
            if (comment.isHidden()) {
                addChildren(comment, ++position);
                showCommentsHidden(true, holder, 0);
            } else {
                int numRemoved = removeChildren(comment);
                showCommentsHidden(false, holder, numRemoved);
            }

            notifyDataSetChanged();
        }

        private void showCommentsHidden(boolean show, CommentViewHolder holder, int numberRemoved) {
            if (show) {
                holder.expandText();
            } else {
                holder.collapseText(numberRemoved);
            }
        }

    }
}