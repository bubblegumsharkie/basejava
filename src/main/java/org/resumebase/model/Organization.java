package org.resumebase.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(website, that.website) && Objects.equals(name, that.name) && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(website, name, periods);
    }
}
