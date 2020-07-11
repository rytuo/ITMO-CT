package md2html;

import markup.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) throws IOException {
        StringBuilder md = new StringBuilder();
        StringBuilder html = new StringBuilder();
        Scan fi = null;
        FileWriter fo = null;

        try {
            fi = new Scan(new File(args[0]));
            try {
                fo = new FileWriter(new File(args[1]), StandardCharsets.UTF_8);

                String line;
                while (fi.hasNext()) {
                    line = fi.nextLine();
                    if (line.equals("\n") || line.equals("\r")) {
                        if (md.length() > 0) {
                            TextParser frame = new TextParser(md);
                            TextTool paragraph = frame.parseText();
                            paragraph.toHtml(html);
                            fo.write(html.toString() + "\n");
                            md = new StringBuilder();
                            html = new StringBuilder();
                        }
                    } else {
                        md.append(line);
                    }
                }
                if (md.length() > 0) {
                    TextParser frame = new TextParser(md);
                    TextTool paragraph = frame.parseText();
                    paragraph.toHtml(html);
                    fo.write(html.toString() + "\n");
                    md = new StringBuilder();
                    html = new StringBuilder();
                }

                fo.close();
            } catch (NullPointerException e) {
                System.out.println("Error: no output file");
            } catch (FileNotFoundException e) {
                System.out.println("Error: no such output file found");
            } finally {
                fi.close();
            }
        } catch (NullPointerException e) {
            System.out.println("Error: no input file");
        } catch (FileNotFoundException e) {
            System.out.println("Error: no such input file found");
        }
    }
}