package org.resumebase.model;

import java.util.List;

public class OrganisationSection extends Section {
    public final List<Organisation> organisations;

    public OrganisationSection(List<Organisation> organisations) {
        this.organisations = organisations;
    }

    public List<Organisation> getCompanies() {
        return organisations;
    }

}
