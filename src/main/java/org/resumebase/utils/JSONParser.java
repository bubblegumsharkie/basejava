package org.resumebase.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.resumebase.model.AbstractSection;

import java.io.Reader;
import java.io.Writer;

public class JSONParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new AbstractGSONAdapter<AbstractSection>())
            .create();

    public static <T> T read(Reader reader, Class<T> tClass) {
        return GSON.fromJson(reader, tClass);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

}
