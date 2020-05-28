package expression.parser;

import expression.exceptions.ParsingException;

import static expression.parser.ExpressionParser.getPos;

public class BaseParser<T extends Number> {
    private final Source source;
    protected char ch;

    protected BaseParser(final Source source) {
        this.source = source;
    }

    protected void nextChar() {
        ch = source.hasNext() ? source.next() : '\0';
    }

    protected boolean test(char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected void expect(final char c) throws ParsingException {
        if (ch != c) {
            throw new ParsingException("Expected '" + c + "', found '" + ch + "'");
        }
        nextChar();
    }

    protected void expect(final String value) throws ParsingException{
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}
