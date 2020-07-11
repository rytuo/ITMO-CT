package markup;

import java.util.List;

public class Head implements TextTool {
    List<TextTool> text;
    private int level;

    public Head(List<TextTool> text, int level) {
        this.text = text;
        this.level = level;
    }

    public void toMarkdown(StringBuilder string) {
        for (TextTool i : this.text) {
            i.toMarkdown(string);
        }
    }

    public void toHtml(StringBuilder string) {
        string.append("<h" + level + ">");
        for (TextTool i : this.text) {
            i.toHtml(string);
        }
        string.append("</h" + level + ">");
    }
}