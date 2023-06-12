package markup;

import java.util.List;

public class Mark extends AbstractMarker {
    public Mark(List<TextTool> text) {
        super(text);
        super.mark = "~";
        super.htmlLeft = "<mark>";
        super.htmlRight = "</mark>";
    }
}