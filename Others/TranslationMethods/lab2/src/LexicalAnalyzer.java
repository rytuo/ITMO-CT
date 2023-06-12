import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

public class LexicalAnalyzer {
    private final InputStream is;
    private int curPos = 0;
    private char curChar;
    private Token curToken;
    private String curVal;

    private static final Set<String> keyWords = new HashSet<>(Arrays.asList(
            "False", "def", "if", "raise",
            "None", "del", "import", "return",
            "True", "elif", "in", "try",
            "and", "else", "is", "while",
            "as", "except", "lambda", "with",
            "assert", "finally", "nonlocal", "yield",
            "break", "for", "not", "class",
            "from", "or", "continue", "global", "pass"
    ));

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        this.nextChar();
    }

    public Token curToken() {
        return this.curToken;
    }

    public int curPos() {
        return this.curPos;
    }

    public String curVal() {
        return this.curVal;
    }

    private void nextChar() throws ParseException {
        this.curPos++;
        try {
            this.curChar = (char) this.is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), this.curPos);
        }
    }

    private boolean isWs(char c) {
        return Character.isWhitespace(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isValidLetter(char c) {
        return c == '_' || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public void nextToken() throws ParseException {
        this.curVal = null;

        while(isWs(this.curChar)) {
            nextChar();
        }

        switch (this.curChar) {
            case ',':
                this.curToken = Token.QUOTE;
                nextChar();
                return;
            case ':':
                this.curToken = Token.COLON;
                nextChar();
                return;
            case '+':
                this.curToken = Token.ADD;
                nextChar();
                return;
            case '-':
                this.curToken = Token.SUB;
                nextChar();
                return;
            case '*':
                this.curToken = Token.MUL;
                nextChar();
                return;
            case '/':
                this.curToken = Token.DIV;
                nextChar();
                if (this.curChar == '/') {
                    nextChar();
                    this.curToken = Token.FLOOR_DIV;
                }
                return;
            case '%':
                this.curToken = Token.MOD;
                nextChar();
                return;
            case '(':
                this.curToken = Token.OPEN;
                nextChar();
                return;
            case ')':
                this.curToken = Token.CLOSE;
                nextChar();
                return;
            case (char)-1:
                this.curToken = Token.END;
                return;
        }

        if (isDigit(this.curChar)) {
            StringBuilder sb = new StringBuilder();
            while (isDigit(this.curChar)) {
                sb.append(this.curChar);
                nextChar();
            }
            this.curVal = sb.toString();

            this.curToken = Token.NUM;
            return;
        }

        if (isValidLetter(this.curChar)) {
            StringBuilder sb = new StringBuilder();
            while(isValidLetter(this.curChar) || isDigit(this.curChar)) {
                sb.append(this.curChar);
                nextChar();
            }
            this.curVal = sb.toString();

            if (keyWords.contains(this.curVal)) {
                this.curToken = Token.KEY;
            } else {
                this.curToken = Token.VAR;
            }
            return;
        }

        throw new ParseException("Illegal character: " + this.curChar, this.curPos);
    }
}
