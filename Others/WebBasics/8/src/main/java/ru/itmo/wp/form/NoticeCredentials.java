package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoticeCredentials {
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content.trim();
    }
}
