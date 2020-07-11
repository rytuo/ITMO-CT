package markup;

import java.util.List;

public class Strong extends AbstractMarker {
    public Strong(List<TextTool> text) {
        super(text);
        super.mark = "__";
        super.tex1 = "\\textbf{";
        super.tex2 = "}";
    }
}