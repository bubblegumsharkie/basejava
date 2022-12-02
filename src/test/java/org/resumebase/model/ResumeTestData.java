package org.resumebase.model;

import org.resumebase.utils.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

//        resume.addContact(ContactType.LINK, "https://stub.stub");
//        resume.addContact(ContactType.GITHUB, "https://github.com/stub");
//        resume.addContact(ContactType.MAIL, "stub@basejava.com");
//        resume.addContact(ContactType.MOBILE, "+79991234567");
//        resume.addContact(ContactType.SKYPE, "@StubSkype");
//        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/stup");
//        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(
//                new ArrayList<>(Arrays.asList(
//                        "stub qualification 1",
//                        "stub qualification 2",
//                        "stub qualification 3"
//                ))));
//        resume.addSection(SectionType.PERSONAL, new TextSection("Stub personal"));
//        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(
//                new ArrayList<>(Arrays.asList(
//                        "stub achievement 1",
//                        "stub achievement 2",
//                        "stub achievement 3"
//                ))
//        ));
//        resume.addSection(
//                SectionType.EDUCATION, new OrganizationSection(
//                        new ArrayList<>(Arrays.asList(
//                                new Organization(
//                                        "https://stubsiteEdu.com",
//                                        "Stub name",
//                                        DateUtil.of(2000, Month.JANUARY),
//                                        DateUtil.of(2002, Month.DECEMBER),
//                                        "stub position title",
//                                        "stub description"),
//                                new Organization(
//                                        "https://stubsiteEdu.com",
//                                        "Stub name",
//                                        DateUtil.of(2003, Month.JANUARY),
//                                        DateUtil.of(2005, Month.DECEMBER),
//                                        "stub position title",
//                                        "stub description"
//                                )))));
//        resume.addSection(SectionType.OBJECTIVE, new TextSection("Stub objective"));
//        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(
//                new ArrayList<>(Arrays.asList(
//                        new Organization(
//                                "https://stubsiteWork.com",
//                                "Stub name",
//                                DateUtil.of(2006, Month.JANUARY),
//                                DateUtil.of(2010, Month.DECEMBER),
//                                "stub position title",
//                                "stub description"),
//                        new Organization("https://stubsWork.com",
//                                "Stub Name Multiple Pos",
//                                new ArrayList<>(Arrays.asList(
//                                        new Organization.Period(
//                                                DateUtil.of(2011, Month.JANUARY),
//                                                DateUtil.of(2015, Month.MARCH),
//                                                "Stub position 1",
//                                                "Stub description 1"),
//                                        new Organization.Period(
//                                                DateUtil.of(2015, Month.APRIL),
//                                                DateUtil.of(2021, Month.DECEMBER),
//                                                "Stub position 2",
//                                                "Stub description 2")
//                                )))))));
        return resume;
    }
}