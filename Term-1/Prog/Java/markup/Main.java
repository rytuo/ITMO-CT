import java.util.Arrays;
import java.util.Collections;
import markup.*;

public class Main {
    public static void main(String[] args) {
        Paragraph paragraph = new Paragraph(Collections.singletonList(
        new Strong(Arrays.asList(
            new Text("1"),
            new Strikeout(Arrays.asList(
                new Text("2"),
                new Emphasis(Arrays.asList(
                    new Text("3"),
                    new Text("4")
                )),
                new Text("5")
            )),
            new Text("6")
            ))
        ));
        StringBuilder string1 = new StringBuilder();
        StringBuilder string2 = new StringBuilder();
        paragraph.toMarkdown(string1);
        paragraph.toTex(string2);
        System.out.println(string1.toString());
        System.out.println(string2.toString());
    }
}