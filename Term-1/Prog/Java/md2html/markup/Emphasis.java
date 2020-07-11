package markup;

import java.util.List;

public class Emphasis extends AbstractMarker {
    public Emphasis(List<TextTool> text) {
        super(text);
        super.mark = "*";
        super.htmlLeft = "<em>";
        super.htmlRight = "</em>";
    }
}