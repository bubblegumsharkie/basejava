package org.resumebase.model;

public class TextSection extends AbstractSection {
    private final String text;

    public TextSection(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
