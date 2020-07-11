import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.lang.String;
import java.lang.StringBuilder;
import java.nio.charset.StandardCharsets;

public class Scan {
    private InputStreamReader in;

    public Scan (InputStream mode) throws IOException {
        this.in = new InputStreamReader(mode, StandardCharsets.UTF_8);
    }

    public Scan (String line) throws IOException {
        this.in = new InputStreamReader(
                new  ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public Scan (File file) throws FileNotFoundException {
        this.in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
    }

    public int nextChar() throws IOException {
        int k;
        if (in.ready()) {
			k = in.read();
			return k;
		}
        else return -1;
    }

    public String next() throws IOException {
        StringBuilder string = new StringBuilder();
        char k;
        int go = 1, sym = 0;
        while (in.ready() && go == 1) {
            k = (char)in.read();
            if (Character.isWhitespace(k)) {
                if (sym == 1) go = 0;
            } else {
                string.append(k);
                sym = 1;
            }
        }
        if (string.length() > 0)
            return string.toString();
        else
            return "";
    }

    public String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();
        char k = '1';
        int ch = 0;
        while (in.ready() && k != '\n') {
            k = (char)in.read();
            if (k != '\n')
                line.append(k);
            if (!Character.isWhitespace(k))
                ch = 1;
        }
        if (ch == 1)
            return line.toString();
        else
            return "";
    }

    public boolean hasNext() throws IOException {
        return in.ready();
    }

    public void close() throws IOException {
        in.close();
    }
}