package br.com.mm.adcertproj.poplposters.model;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * This class is used to deserialize json responses from "The Movie Database", which usually embeds
 * the useful content in a nested attribute.
 * Just use it with GsonBuilder.registerTypeAdapter.
 * @param <T> Class type resulted after parsing the nested content.
 */
public class MDBDeserializer<T> implements JsonDeserializer<T> {

    private final String nestedAttrName;

    /**
     * Constructor receiving the nested attribute's name to deserialize.
     * @param nestedAttrName Name of the nested attribute to be deserialized.
     */
    public MDBDeserializer(String nestedAttrName) {
        this.nestedAttrName = nestedAttrName;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonElement content = json.getAsJsonObject().get(nestedAttrName);
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .fromJson(content, typeOfT);
    }
}