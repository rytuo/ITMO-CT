package ru.itmo.web.lesson4.model;

public class Post {
    private final long id;
    private final String title;
    private final String text;
    private final long user_id;

    public Post(long id, String title, String text, long user_id) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Long getUserId() {
        return user_id;
    }
}
