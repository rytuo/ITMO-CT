package markup;

import java.util.List;

public class Code extends AbstractMarker {
    public Code(List<TextTool> text) {
        super(text);
        super.mark = "'";
        super.htmlLeft = "<code>";
        super.htmlRight = "</code>";
    }
}