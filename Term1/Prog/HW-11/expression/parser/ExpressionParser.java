package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {
    public CommonExpression parse(final String source) {
        return parse(new StringSource(source));
    }

    public CommonExpression parse(Source source) {
        return new MathParser(source).parse();
    }

    private static class MathParser extends BaseParser {
        private int level = 0;

        public MathParser(final Source source) {
            super(source);
            nextChar();
        }

        // Expression:
        //      1) << >>
        //      2) + -
        //      3) * /
        //      4) var/const/log/-...

        public CommonExpression parse() {
            final CommonExpression result = parseExperssion();
            if (test('\0')) {
                return result;
            }
            throw error("End of Expression expected");
        }

        // parse Expression
        // *(* Expression *)*

        public CommonExpression parseExperssion() {
            return parseShifts(parseHigh(parseMid(parseLow())));
        }

        // parse Shifts
        // "exp" shift "exp"

        private CommonExpression parseShifts(CommonExpression left) {
            char trigger;
            skipWhitespace();
            if (test('\0') || test(')')) {
                return left;
            } else if (test('<')) {
                expect("<");
                trigger = '<';
            } else if (test('>')) {
                expect(">");
                trigger = '>';
            } else {
                throw error("Shifts level error " + ch);
            }
            skipWhitespace();
            CommonExpression right = parseHigh(parseMid(parseLow()));
            if (trigger == '<') {
                return parseShifts(new LShift(left, right));
            } else if (trigger == '>') {
                return parseShifts(new RShift(left, right));
            }
            throw error("Shifts level error " + ch);
        }

        // parse sum/sub
        // "exp" +- "exp"

        private CommonExpression parseHigh(CommonExpression left) {
            char trigger;
            skipWhitespace();
            if (test('\0') || ch == ')' || ch == '<' || ch == '>') {
                return left;
            } else if (test('-')) {
                skipWhitespace();
                int counter = 1;
                while (test('-')) {
                    counter++;
                    skipWhitespace();
                }
                if (counter % 2 == 0) {
                    trigger = '+';
                } else {
                    trigger = '-';
                }
            } else if (test('+')) {
                trigger = '+';
            } else {
                throw error("Smth else expected " + ch);
            }
            skipWhitespace();
            CommonExpression right = parseMid(parseLow());
            if (trigger == '-') {
                return parseHigh(new Subtract(left, right));
            } else if (trigger == '+') {
                return parseHigh(new Add(left, right));
            }
            throw error("Smth else expected " + ch);
        }

        // parse multi/div
        // "exp" */ "exp"

        private CommonExpression parseMid(CommonExpression left) {
            skipWhitespace();
            char trigger;
            if (test('\0') || ch == ')' || ch == '+' || ch == '-' || ch == '<' || ch == '>') {
                return left;
            } else if (test('*')) {
                trigger = '*';
            } else if (test('/')) {
                trigger = '/';
            } else {
                throw error("Smth else expected #2 " + ch);
            }
            skipWhitespace();
            CommonExpression right = parseLow();
            if (trigger == '*') {
                return parseMid(new Multiply(left, right));
            } else if (trigger == '/') {
                return parseMid(new Divide(left, right));
            }
            throw error("Smth else expected #2 " + ch);
        }

        // parse single var/const/log/-..

        private CommonExpression parseLow() {
            skipWhitespace();
            if (between('0', '9')) {
                return new Const(parseConst());
            } else if (test('-')) {
                skipWhitespace();
                int counter = 1;
                while (test('-')) {
                    counter++;
                    skipWhitespace();
                }
                if (counter % 2 == 0) {
                    return parseLow();
                } else {
                    return new UnaryMinus(parseLow());
                }
            } else if (test('x')) {
                return new Variable("x");
            } else if (test('y')) {
                return new Variable("y");
            } else if (test('z')) {
                return new Variable("z");
            } else if (test('l')) {
                expect("og2");
                skipWhitespace();
                return new Log(parseLow());
            } else if (test('p')) {
                expect("ow2");
                skipWhitespace();
                return new Pow(parseLow());
            } else if (test('(')) {
                return parseExperssion();
            }
            return null;
        }

        // parse Digits

        private Integer parseConst() {
            int value = 0;
            while (between('0', '9')) {
                value = value * 10 + ch - 48;
                nextChar();
            }
            return value;
        }

        private void skipWhitespace() {
            while(test(' ') || test('\r') || test('\n') || test('\t')) {

            }
        }
    }
}