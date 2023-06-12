package ru.itmo.web.lesson4.model;

public class AsideText {
    private final String header;
    private final String body;

    public AsideText(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
