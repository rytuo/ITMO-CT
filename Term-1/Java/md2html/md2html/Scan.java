package md2html;

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
	private char[] buffer;
    private int current = 0;
    private int len = 0;

    public Scan(InputStream mode) throws IOException {
        this.in = new InputStreamReader(mode, StandardCharsets.UTF_8);
        bufferExtend();
    }

    public Scan(String line) throws IOException {
        this.in = new InputStreamReader(
						new  ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        bufferExtend();
    }

    public Scan(File file) throws FileNotFoundException {
        this.in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        try {
            bufferExtend();
        } catch (IOException e) {
            System.out.print(e);
        }
    }

	private boolean bufferExtend() throws IOException {
		if (in.ready()) {
            this.buffer = new char[100];
			this.len = in.read(this.buffer);
			return true;
		} else {
			return false;
		}
	}

    public int nextChar() throws IOException {
        if (this.current == this.len) {
			if (!bufferExtend())
				return -1;
			this.current = 0;
		}
		return (int)this.buffer[this.current++];
    }

    public String next() throws IOException {
        StringBuilder string = new StringBuilder();
        char k;
        int sym = 0;
        while (hasNext()) {
            k = (char)nextChar();
            if (Character.isWhitespace(k)) {
                if (sym == 1) {
					break;
				}
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
        while (hasNext()) {
            k = (char)nextChar();
            if (k == '\n')
				break;
            line.append((char)k);
        }
        return line.toString();
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