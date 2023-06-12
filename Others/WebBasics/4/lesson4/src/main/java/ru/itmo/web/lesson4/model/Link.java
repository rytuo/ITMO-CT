package ru.itmo.web.lesson4.model;

public class Link {
    private final String text;
    private final String link;

    public Link(String text, String link) {
        this.text = text;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }
}
