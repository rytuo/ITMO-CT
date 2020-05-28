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
import java.util.List;
import java.util.Map;

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

public class Main {
    public static void main(String[] args) {
        Scan in = new Scan(System.in);
        int t = Integer.parseInt(in.next()), n, s;

        for (int p = 0; p < t; p++) {
            n = Integer.parseInt(in.next());
            s = 0;
            List<Integer> a = new ArrayList();
            Map<Integer, Integer> c = new HashMap();
            for (int j = 0; j < n; j++) {
                a.add(Integer.parseInt(in.next()));
                c.add(a.get(a.size() - 1), Integer(0));
            }
            for (int j = n - 1; j > 1; j--) {
                for (int i = 1; i < j; i++) {
                    s += c.get(2*a.get(j) - a.get(i));
                    c.put(a.get(j), c.get(a.get(j))+1);
                }
            }
            System.out.println(s);
        }
    }
}