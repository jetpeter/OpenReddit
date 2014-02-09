package com.jetpeter.android.openreddit.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jetpeter.android.openreddit.models.Comment;
import com.jetpeter.android.openreddit.models.Post;
import com.jetpeter.android.openreddit.models.RedditData;
import com.jetpeter.android.openreddit.models.RedditDataWrapper;

import java.lang.reflect.Type;

/**
 * Created by jpetersen on 1/26/14.
 *
 * Parse reddit objects:
 * t1_	Comment
 * t2_	Account
 * t3_	Link
 * t4_	Message
 * t5_	Subreddit
 * t6_	Award
 * t8_	PromoCampaign
 *
 * The Reddit Data wrapper class has a kind and data field.  The data field will be different
 * implementations of Reddit data depending on the kind field.
 */
public class RedditDataParser implements JsonDeserializer<RedditDataWrapper> {

    private static final String KIND_KEY = "kind";
    private static final String DATA_KEY = "data";

    @Override
    public RedditDataWrapper deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject listingObject = jsonElement.getAsJsonObject();
        JsonElement kindObject = listingObject.get(KIND_KEY);
        String kind = kindObject.getAsString();
        JsonElement dataElement = listingObject.get(DATA_KEY);
        RedditData data = null;
        if (kind.equals(RedditDataWrapper.KIND_COMMENT)) {
            data = jsonDeserializationContext.deserialize(dataElement, Comment.class);
        } else if (kind.equals(RedditDataWrapper.KIND_ACCOUNT)) {

        } else if (kind.equals(RedditDataWrapper.KIND_POST)) {
            data = jsonDeserializationContext.deserialize(dataElement, Post.class);
        } else if (kind.equals(RedditDataWrapper.KIND_MESSAGE)) {

        } else if (kind.equals(RedditDataWrapper.KIND_SUBREDDIT)) {

        } else if (kind.equals(RedditDataWrapper.KIND_AWARD)) {

        } else if (kind.equals(RedditDataWrapper.KIND_PROMO_CAMPAIGN)) {

        }
        return new RedditDataWrapper(kind, data);
    }
}
