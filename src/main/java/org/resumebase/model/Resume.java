package org.resumebase.model;

import java.time.Period;
import java.util.*;

/**
 * Initial resume class
 */
public class Resume {
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final List<Period> periods = new ArrayList<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "UUID can't be null");
        Objects.requireNonNull(fullName, "fullName can't be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UUID: " + uuid + ", Full Name: " + fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContactByType(ContactType type) {
        return contacts.get(type);
    }

    public AbstractSection getSectionByType(SectionType type) {
        return sections.get(type);
    }
}
