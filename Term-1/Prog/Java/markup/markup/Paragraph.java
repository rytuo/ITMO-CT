package markup;

import java.util.List;

public class Paragraph implements TextTool {
    List<TextTool> text;

    public Paragraph(List<TextTool> text) {
        this.text = text;
    }

    public void toMarkdown(StringBuilder string) {
        for (TextTool i : this.text) {
            i.toMarkdown(string);
        }
    }

    public void toTex(StringBuilder string) {
        for (TextTool i : this.text) {
            i.toTex(string);
        }
    }
}