package markup;

public class Text implements TextTool {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    public void toMarkdown(StringBuilder string) {
        string.append(this.text);
    }

    public void toHtml(StringBuilder string) {
        string.append(this.text);
    }
}