package org.resumebase.model;

import java.util.List;

public class OrganizationSection extends AbstractSection {
    public final List<Organization> organizations;

    public OrganizationSection(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

}
