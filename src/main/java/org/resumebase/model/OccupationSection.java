package org.resumebase.model;

import java.util.List;

public class OccupationSection extends Section {
    public final List<Occupation> occupations;

    public OccupationSection(List<Occupation> occupations) {
        this.occupations = occupations;
    }

    public List<Occupation> getOccupations() {
        return occupations;
    }

}
