package expression.parser;

import expression.exceptions.ParsingException;
import expression.units.*;
import expression.units.checked.*;

public class ExpressionParser implements Parser {
    private static Source expression;

    public static String getExpression() {
        return expression.getExpression();
    }

    public static int getPos() {
        return expression.getPos();
    }

    public CommonExpression parse(final String source) throws ParsingException {
        return parse(new StringSource(source));
    }

    public CommonExpression parse(Source source) throws ParsingException {
        expression = source;
        return new MathParser(source).parse();
    }

    private static class MathParser extends BaseParser {
        public MathParser(final Source source) {
            super(source);
            nextChar();
        }

        public CommonExpression parse() throws ParsingException {
            final CommonExpression result = parseExpression(0);
            if (test('\0')) {
                return result;
            }
            throw new ParsingException("End of Expression expected");
        }

        public CommonExpression parseExpression(int openingPar) throws ParsingException {
            return parseHigh(parseMid(parseLow()), openingPar);
        }

        private CommonExpression parseHigh(CommonExpression left, int openingPar) throws ParsingException {
            char trigger;
            skipWhitespace();
            if (test('\0')) {
                if (openingPar == 0) {
                    return left;
                } else {
                    throw new ParsingException("No closing parenthesis");
                }
            }else if (test(')')) {
                if (openingPar == 1) {
                    return left;
                } else {
                    throw new ParsingException("No opening parenthesis for closing one");
                }
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
                throw new ParsingException("Operation expected, found '" + ch + '\'');
            }
            skipWhitespace();
            CommonExpression right = parseMid(parseLow());
            if (trigger == '-') {
                return parseHigh(new CheckedSubtract(left, right), openingPar);
            } else {
                return parseHigh(new CheckedAdd(left, right), openingPar);
            }
        }

        private CommonExpression parseMid(CommonExpression left) throws ParsingException {
            skipWhitespace();
            char trigger;
            if ( test('\0') || ch == ')' || ch == '+' || ch == '-') {
                return left;
            } else if (test('*')) {
                trigger = '*';
            } else if (test('/')) {
                trigger = '/';
            } else {
                    throw new ParsingException("Operation expected, found '" + ch + '\'');
            }
            skipWhitespace();
            CommonExpression right = parseLow();
            if (trigger == '*') {
                return parseMid(new CheckedMultiply(left, right));
            } else {
                return parseMid(new CheckedDivide(left, right));
            }
        }

        private CommonExpression parseLow() throws ParsingException {
            skipWhitespace();
            if (between('0', '9')) {
                return new CheckedConst(parseConst());
            } else if (test('-')) {
                skipWhitespace();
                return parseNegate();
            } else if (test('x')) {
                return new CheckedVariable("x");
            } else if (test('y')) {
                return new CheckedVariable("y");
            } else if (test('z')) {
                return new CheckedVariable("z");
            } else if (test('l')) {
                expect("og2");
                if (test('\0')) {
                    throw new ParsingException("log2 argument expected");
                }
                else if (ch == '(' || ch == '-' || ch == ' ') {
                    CommonExpression arg;
                    arg = parseLow();
                    return new CheckedLog(arg);
                } else {
                    throw new ParsingException("Unexpected symbol " + ch);
                }
            } else if (test('p')) {
                expect("ow2");
                if (test('\0')) {
                    throw new ParsingException("pow2 argument expected");
                }
                if (ch == '(' || ch == '-' || ch == ' ') {
                    skipWhitespace();
                    return new CheckedPow(parseLow());
                } else {
                    throw new ParsingException("Unexpected symbol " + ch);
                }
            } else if (test('(')) {
                return parseExpression(1);
            }

            throw new ParsingException("argument expected");
        }

        private String parseConst() {
            StringBuilder value = new StringBuilder();
            while (between('0', '9')) {
                value.append(ch);
                nextChar();
            }
            return value.toString();
        }

        private CommonExpression parseNegate() throws ParsingException {
            skipWhitespace();
            int counter = 1;
            while (test('-')) {
                counter++;
                skipWhitespace();
            }
            if (between('0', '9')) {
                if (counter % 2 == 0) {
                    return new CheckedConst(parseConst());
                } else {
                    return new CheckedConst("-" + parseConst());
                }
            } else {
                if (counter % 2 == 0) {
                    return parseLow();
                } else {
                    return new CheckedNegate(parseLow());
                }
            }
        }

        private void skipWhitespace() {
            while(test(' ') || test('\r') || test('\n') || test('\t')) {

            }
        }
    }
}