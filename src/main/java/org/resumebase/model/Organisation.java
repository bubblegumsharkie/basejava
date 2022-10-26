package org.resumebase.model;

import java.time.LocalDate;

public class Organisation {

    private final String url;
    private final String organisationName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String positionTitle;
    private final String description;

    public Organisation(String url, String organisationName, LocalDate startDate, LocalDate endDate, String positionTitle, String description) {
        this.url = url;
        this.organisationName = organisationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.positionTitle = positionTitle;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Occupation{" +
                "url='" + url + '\'' +
                ", organisationsName='" + organisationName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", positionTitle='" + positionTitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
