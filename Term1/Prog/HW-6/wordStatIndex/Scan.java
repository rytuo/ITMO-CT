import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.lang.String;
import java.lang.StringBuilder;
import java.nio.charset.StandardCharsets;

public class Scan {
    private InputStreamReader in;
	private char[] Buff;
    private int current = 0;
    private int len = 0;

    public Scan (InputStream mode) throws IOException {
        this.in = new InputStreamReader(mode, StandardCharsets.UTF_8);
        Buff_extend();
    }

    public Scan (String line) throws IOException {
        this.in = new InputStreamReader(
						new  ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        Buff_extend();
    }

    public Scan (File file) throws FileNotFoundException {
        this.in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        try {
            Buff_extend();
        } catch (IOException e) {
            System.out.print(e);
        }
    }

	private boolean Buff_extend() throws IOException {
		if (in.ready()) {
            this.Buff = new char[100];
			this.len = in.read(this.Buff);
			return true;
		} else {
			return false;
		}
	}

    public int nextChar() throws IOException {
        if (this.current == 100) {
			this.current = 0;
			if (!Buff_extend())
				return -1;
		}
		return (int)this.Buff[this.current++];
    }

    public String next() throws IOException {
        StringBuilder string = new StringBuilder();
        char k;
        int sym = 0;
        while (hasNext()) {
            k = (char)nextChar();
            if (Character.isWhitespace(k)) {
                if (sym == 1) break;
            } else {
                string.append(k);
                sym = 1;
            }
        }
        if (string.length() > 0) {
            return string.toString();
        } else {
            return "";
		}
    }

    public String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();
        char k = '1';
        int ch = 0;
        while (k != '\n' && hasNext()) {
            k = (char)nextChar();
            if (k != '\n')
                line.append((char)k);
            if (!Character.isWhitespace((char)k))
                ch = 1;
        }
        if (ch == 1) {
            return line.toString();
        } else {
            return "";
		}
    }

    public boolean hasNext() throws IOException {
        if (this.current < this.len) {
            return true;
        } else {
            return in.ready();
        }
    }

    public void close() throws IOException {
        in.close();
    }
}