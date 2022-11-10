package org.resumebase.storage.serializer;

import org.resumebase.model.Resume;
import org.resumebase.utils.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JSONSerializer implements Serializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            JSONParser.write(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return JSONParser.read(reader, Resume.class);
        }
    }
}
