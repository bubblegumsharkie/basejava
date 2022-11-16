package org.resumebase.storage.serializer;

import org.resumebase.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataSerializer implements Serializer {
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            Set<Map.Entry<ContactType, String>> contacts = resume.getContacts().entrySet();
            Set<Map.Entry<SectionType, AbstractSection>> sections = resume.getSections().entrySet();
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            writeCollectionToFile(dataOutputStream, contacts, entry -> {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            });

            writeCollectionToFile(dataOutputStream, sections, entry -> {
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
                        writeCollectionToFile(dataOutputStream, ((ListSection) section).getItems(), dataOutputStream::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollectionToFile(dataOutputStream, ((OrganizationSection) section).getOrganizations(), organization -> {
                            dataOutputStream.writeUTF(organization.getWebsite());
                            dataOutputStream.writeUTF(organization.getName());
                            writeCollectionToFile(dataOutputStream, organization.getPeriods(), period -> {
                                writeLocalDate(dataOutputStream, period.getStartDate());
                                writeLocalDate(dataOutputStream, period.getEndDate());
                                dataOutputStream.writeUTF(period.getTitle());
                                dataOutputStream.writeUTF(period.getDescription());
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
            readNextFromFile(dataInputStream, () ->
                    resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF()));
            readNextFromFile(dataInputStream, () -> {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                resume.addSection(sectionType, readSection(dataInputStream, sectionType));
            });
            return resume;
        }
    }

    private AbstractSection readSection(DataInputStream dataInputStream, SectionType sectionType) throws IOException {
         switch (sectionType) {
             case PERSONAL:
             case OBJECTIVE:
                 return new TextSection(dataInputStream.readUTF());
             case ACHIEVEMENT:
             case QUALIFICATIONS:
                 return new ListSection(readList(dataInputStream, dataInputStream::readUTF));
             case EXPERIENCE:
             case EDUCATION:
                 return new OrganizationSection(readList(dataInputStream, () ->
                         new Organization(dataInputStream.readUTF(),
                                 dataInputStream.readUTF(),
                                 readList(dataInputStream, () -> new Organization.Period(
                                         readLocalDate(dataInputStream),
                                         readLocalDate(dataInputStream),
                                         dataInputStream.readUTF(),
                                         dataInputStream.readUTF()
                                 )))));
             default:
                 throw new IllegalStateException("No such ENUM");
         }
    }

    private <T> List<T> readList(DataInputStream dataInputStream, ElementReader<T> reader) throws IOException {
        int size = dataInputStream.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private void writeLocalDate(DataOutputStream dataOutputStream, LocalDate localDate) throws IOException {
        dataOutputStream.writeInt(localDate.getYear());
        dataOutputStream.writeInt(localDate.getMonth().getValue());
    }

    private LocalDate readLocalDate(DataInputStream dataInputStream) throws IOException {
        return LocalDate.of(dataInputStream.readInt(), dataInputStream.readInt(), 1);
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }

    private <T> void writeCollectionToFile(DataOutputStream dataOutputStream, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T item: collection) {
            writer.write(item);
        }
    }

    private void readNextFromFile(DataInputStream dataInputStream, ElementProcessor reader) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            reader.process();
        }
    }

}
