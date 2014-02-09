package com.jetpeter.android.openreddit.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jetpeter.android.openreddit.models.Listing;
import com.jetpeter.android.openreddit.models.ListingWrapper;

import java.lang.reflect.Type;

/**
 * Created by jpetersen on 1/27/14.
 */
public class ListingWrapperParser implements JsonDeserializer<ListingWrapper> {

    private static final String KIND_KEY = "kind";
    private static final String DATA_KEY = "data";

    @Override
    public ListingWrapper deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.isJsonNull() || jsonElement.isJsonPrimitive() || jsonElement.isJsonArray()) {
            return null;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement kindElement = jsonObject.get(KIND_KEY);
        String kind = kindElement.getAsString();
        JsonElement dataElement = jsonObject.get(DATA_KEY);
        Listing listing = jsonDeserializationContext.deserialize(dataElement, Listing.class);
        return new ListingWrapper(kind, listing);
    }
}
