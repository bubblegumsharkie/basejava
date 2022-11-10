package org.resumebase.utils;

import com.google.gson.*;

import java.lang.reflect.Type;

public class AbstractGSONAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = jsonPrimitive.getAsString();

        try {
            Class<?> tClass = Class.forName(className);
            return jsonDeserializationContext.deserialize(jsonObject.get(INSTANCE), tClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject returnValue = new JsonObject();
        returnValue.addProperty(CLASSNAME, section.getClass().getName());
        JsonElement jsonElement = jsonSerializationContext.serialize(section);
        returnValue.add(INSTANCE, jsonElement);
        return returnValue;
    }
}
