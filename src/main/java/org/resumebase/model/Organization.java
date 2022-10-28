package org.resumebase.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Organization {

    private final String website;
    private final String name;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String website, String name, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.website = website;
        this.name = name;
        periods.add(new Period(startDate, endDate, title, description));
    }

    @Override
    public String toString() {
        return "Organization{" +
                "website='" + website + '\'' +
                ", name='" + name + '\'' +
                ", periods=" + periods +
                '}';
    }
}
