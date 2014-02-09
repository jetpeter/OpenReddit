package com.jetpeter.android.openreddit.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jetpeter.android.openreddit.models.Comment;
import com.jetpeter.android.openreddit.models.ListingWrapper;
import com.jetpeter.android.openreddit.models.Post;
import com.jetpeter.android.openreddit.models.RedditData;
import com.jetpeter.android.openreddit.models.RedditDataWrapper;

import java.lang.reflect.Type;

/**
 * Created by Jefferey on 12/14/13.
 */
public class GsonManager {

    private static final Gson gson = getGson();

    public static Gson getInstance() {
        return gson;
    }

    private static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(RedditDataWrapper.class, new RedditDataParser());
        builder.registerTypeAdapter(ListingWrapper.class, new ListingWrapperParser());
        return builder.create();
    }
}
