package org.resumebase.model;

import java.time.LocalDate;

public class Occupation {

    private final String url;
    private final String companyName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String positionTitle;
    private final String description;

    public Occupation(String url, String companyName, LocalDate startDate, LocalDate endDate, String positionTitle, String description) {
        this.url = url;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.positionTitle = positionTitle;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Occupation{" +
                "url='" + url + '\'' +
                ", companyName='" + companyName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", positionTitle='" + positionTitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
