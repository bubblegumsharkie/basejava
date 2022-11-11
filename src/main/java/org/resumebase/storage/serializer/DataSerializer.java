package org.resumebase.storage.serializer;

import org.resumebase.model.AbstractSection;
import org.resumebase.model.ContactType;
import org.resumebase.model.Resume;
import org.resumebase.model.SectionType;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class DataSerializer implements Serializer {
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            Set<Map.Entry<ContactType, String>> contacts = resume.getContacts().entrySet();
            Set<Map.Entry<SectionType, AbstractSection>> sections = resume.getSections().entrySet();
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts) {
                dataOutputStream.writeUTF(contact.getKey().name());
                dataOutputStream.writeUTF(contact.getValue());
            }

            dataOutputStream.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections) {
                dataOutputStream.writeUTF(section.getKey().name());
            }
        }
    }

    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());
            for (int i = 0; i < dataInputStream.readInt(); i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }

            for (int i = 0; i < dataInputStream.readInt(); i++) {

            }

            return resume;
        }
    }
}
