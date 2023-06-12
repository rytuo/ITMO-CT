package generator.parser;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LL1Lexer {
    private final List<List<String>> terms;
    private InputStream is;
    private Stack<Character> buffer;

    private String curToken;
    private String curVal;
    private char curChar;
    private int curPos;

    public LL1Lexer(List<List<String>> terms) {
        this.terms = terms;
    }

    public void setInputStream(InputStream is) throws ParseException {
        this.is = is;
        this.curPos = 0;
        this.curVal = null;
        this.buffer = new Stack<>();
        this.nextChar();
        this.nextToken();
    }

    public String curToken() { return this.curToken; }

    public String curVal() {
        return this.curVal;
    }

    public int curPos() { return this.curPos; }

    private void nextChar() throws ParseException {
        this.curPos++;
        if (!this.buffer.isEmpty()) {
            this.curChar = this.buffer.pop();
            return;
        }

        try {
            this.curChar = (char) this.is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), this.curPos);
        }
    }

    public void nextToken() throws ParseException {
        if (this.curChar == (char)-1) {
            this.curToken = "EOF";
            this.curVal = "$";
            return;
        }

        StringBuilder sb = new StringBuilder();
        int lastMatched = terms.size();
        int matchPos = 0;

        while (true) {
            // test with new char
            sb.append(this.curChar);
            String curString = sb.toString();

            // check match
            boolean newHit = false;
            for (int i = 0; i < terms.size() && i <= lastMatched; i++) {
                Matcher m = Pattern.compile(terms.get(i).get(1)).matcher(curString);

                if (m.matches()) {
                    lastMatched = i;
                    matchPos = this.curPos;
                }

                if (m.hitEnd()) {
                    newHit = true;
                }
            }

            if (newHit) {
                nextChar();
                continue;
            }

            // try return
            if (lastMatched == terms.size()) {
                throw new ParseException("Invalid input: " + sb, this.curPos);
            } else {
                // save unused chars
                this.curToken = terms.get(lastMatched).get(0);

                int i = sb.length();
                while (this.curPos > matchPos) {
                    this.buffer.push(sb.charAt(--i));
                    this.curPos--;
                }

                sb.delete(i, sb.length());
                this.curVal = sb.toString();
                nextChar();

                if (Objects.equals(this.curToken, "WS")) {
                    nextToken();
                }
                return;
            }
        }
    }
}
