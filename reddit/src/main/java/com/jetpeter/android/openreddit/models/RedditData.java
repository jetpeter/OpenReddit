package com.jetpeter.android.openreddit.models;

/**
 * Created by jpetersen on 1/26/14.
 *
 * Base class that reprosents the reddit data  types:
 * t1_	Comment
 * t2_	Account
 * t3_	Link
 * t4_	Message
 * t5_	Subreddit
 * t6_	Award
 * t8_	PromoCampaign
 */
public abstract class RedditData {

    protected String name;
    protected String id;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


}
