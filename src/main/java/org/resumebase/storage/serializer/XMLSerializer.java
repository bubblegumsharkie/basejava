package org.resumebase.storage.serializer;

import org.resumebase.model.*;
import org.resumebase.utils.XMLParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XMLSerializer implements Serializer {
    private final XMLParser xmlParser;

    public XMLSerializer() {
        xmlParser = new XMLParser(
                Resume.class,
                Organization.class,
                OrganizationSection.class,
                ListSection.class,
                TextSection.class,
                Organization.Period.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshal(resume, writer);
        }

    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(inputStreamReader);
        }
    }
}
