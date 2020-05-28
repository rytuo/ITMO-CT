package markup;

import java.util.List;

abstract class AbstractMarker implements TextTool {
    protected List<TextTool> text;
    protected String mark;
    protected String htmlLeft, htmlRight;

    protected AbstractMarker(List<TextTool> text) {
        this.text = text;
    }

    public void toMarkdown(StringBuilder string) {
        string.append(mark);
        for (TextTool i : this.text) {
            i.toMarkdown(string);
        }
        string.append(mark);
    }

    public void toHtml(StringBuilder string) {
        string.append(htmlLeft);
        for (TextTool i : this.text) {
            i.toHtml(string);
        }
        string.append(htmlRight);
    }
}