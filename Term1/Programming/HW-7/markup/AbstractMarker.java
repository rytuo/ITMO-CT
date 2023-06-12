package markup;

import java.util.List;

abstract class AbstractMarker implements TextTool {
    protected List<TextTool> text;
    protected String mark;
    protected String tex1, tex2;

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

    public void toTex(StringBuilder string) {
        string.append(tex1);
        for (TextTool i : this.text) {
            i.toTex(string);
        }
        string.append(tex2);
    }
}