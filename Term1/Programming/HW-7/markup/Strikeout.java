package markup;

import java.util.List;

public class Strikeout extends AbstractMarker {
    public Strikeout(List<TextTool> text) {
        super(text);
        super.mark = "~";
        super.tex1 = "\\textst{";
        super.tex2 = "}";
    }
}