package org.resumebase.storage.serializer;

import org.resumebase.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class DataSerializer implements Serializer {
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            Set<Map.Entry<ContactType, String>> contacts = resume.getContacts().entrySet();
            Set<Map.Entry<SectionType, AbstractSection>> sections = resume.getSections().entrySet();
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            writeCollection(dataOutputStream, contacts, entry -> {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            });

            writeCollection(dataOutputStream, sections, entry -> {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                dataOutputStream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(((TextSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dataOutputStream, ((ListSection) section).getItems(), dataOutputStream::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dataOutputStream, ((OrganizationSection) section).getOrganizations(), organization -> {
                            dataOutputStream.writeUTF(organization.getName());
                            dataOutputStream.writeUTF(organization.getWebsite());
                            writeCollection(dataOutputStream, organization.getPeriods(), period -> {
                                dataOutputStream.writeUTF(period.getTitle());
                                dataOutputStream.writeUTF(period.getDescription());
                                writeLocalDate(dataOutputStream, period.getStartDate());
                                writeLocalDate(dataOutputStream, period.getEndDate());
                            });
                        });
                        break;
                }
            });
        }
    }

    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());
            return resume;
        }
    }
    
    private void writeLocalDate(DataOutputStream dataOutputStream, LocalDate localDate) throws IOException {
        dataOutputStream.writeInt(localDate.getYear());
        dataOutputStream.writeInt(localDate.getMonth().getValue());
    }



    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ElementReader {
        void read() throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dataOutputStream, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T item: collection) {
            writer.write(item);
        }
    }

}
