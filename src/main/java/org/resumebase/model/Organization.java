package org.resumebase.model;

import org.resumebase.utils.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private String website;
    private String name;
    private List<Period> periods = new ArrayList<>();

    public Organization() {
    }

    public Organization(String website, String name, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.website = website;
        this.name = name;
        periods.add(new Period(startDate, endDate, title, description));
    }

    public Organization(String website, String name, List<Period> periods) {
        this.website = website;
        this.name = name;
        this.periods = periods;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Period() {
        }

        public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return Objects.equals(startDate, period.startDate) && Objects.equals(endDate, period.endDate) && Objects.equals(title, period.title) && Objects.equals(description, period.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }
    }
}
