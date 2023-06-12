package ru.akirakozov.sd.refactoring.view;

/**
 * Класс для создания html тегов.
 * Более мощным аналогом была бы иерархия htmlTagBuilder классов,
 * но в данном проекте сейчас это будет оверхед
 */
public class HtmlTagBuilder {

    public String document(String... content) {
        return wrapTag("html", wrapTag("body", String.join("", content)));
    }

    public String br() {
        return wrapTag("br", "");
    }

    public String h1(String... content) {
        return wrapTag("h1", String.join("", content));
    }

    private String wrapTag(String tag, String content) {
        return String.format("<%s>%s</%s>", tag, content, tag);
    }
}
