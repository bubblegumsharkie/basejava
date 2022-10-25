package org.resumebase.model;

public enum ContactType {
    MOBILE("Мобильный"),
    SKYPE("Skype"),
    MAIL("Почта"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    LINK("Сайт");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}