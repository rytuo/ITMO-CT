package ru.akirakozov.sd.refactoring.view;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlTagBuilderTest {

    private final HtmlTagBuilder templateBuilder = new HtmlTagBuilder();

    @Test
    public void testDocument() {
        String content = "testString1";
        String document = templateBuilder.document(content);
        assertThat(document).isEqualTo("<html><body>" + content + "</body></html>");
    }

    @Test
    public void testBr() {
        assertThat(templateBuilder.br()).isEqualTo("<br></br>");
    }

    @Test
    public void testH1() {
        String content = "testString3";
        String h1 = templateBuilder.h1(content);
        assertThat(h1).isEqualTo("<h1>" + content + "</h1>");
    }
}
