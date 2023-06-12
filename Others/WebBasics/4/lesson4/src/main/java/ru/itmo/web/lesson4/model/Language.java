package ru.itmo.web.lesson4.model;

public class Language {
    private final String text;
    private final String src;

    public Language(String text, String src) {
        this.text = text;
        this.src = src;
    }

    public String getText() {
        return text;
    }

    public String getSrc() {
        return src;
    }
}
