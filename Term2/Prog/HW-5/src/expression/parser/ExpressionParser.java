package expression.parser;

import expression.exceptions.ParsingException;
import expression.generic.*;
import expression.units.*;
import expression.units.checked.*;
import java.util.function.Function;

public class ExpressionParser<T extends Number> implements Parser<T> {
    private static Source expression;

    public static String getExpression() {
        return expression.getExpression();
    }

    public static int getPos() {
        return expression.getPos();
    }

    public CommonExpression<T> parse(final String source, Function<String, MyType<T>> function) throws ParsingException {
        expression = new StringSource(source);
        return new GenericParser<T>(expression, function).parse();
    }

    private static class GenericParser<T extends Number> extends BaseParser<T> {
        private Function<String, MyType<T>> function;

        GenericParser(final Source source, Function<String, MyType<T>> function) {
            super(source);
            this.function = function;
            nextChar();
        }

        CommonExpression<T> parse() throws ParsingException {
            final CommonExpression<T> result = parseExpression(0);
            if (test('\0')) {
                return result;
            }
            throw new ParsingException("End of Expression expected");
        }

        CommonExpression<T> parseExpression(int openingPar) throws ParsingException {
            return parseHigh(parseMid(parseLow()), openingPar);
        }

        private CommonExpression<T> parseHigh(CommonExpression<T> left, int openingPar) throws ParsingException {
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
            CommonExpression<T> right = parseMid(parseLow());
            if (trigger == '-') {
                return parseHigh(new CheckedSubtract<>(left, right), openingPar);
            } else {
                return parseHigh(new CheckedAdd<>(left, right), openingPar);
            }
        }

        private CommonExpression<T> parseMid(CommonExpression<T> left) throws ParsingException {
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
            CommonExpression<T> right = parseLow();
            if (trigger == '*') {
                return parseMid(new CheckedMultiply<>(left, right));
            } else {
                return parseMid(new CheckedDivide<>(left, right));
            }
        }

        private CommonExpression<T> parseLow() throws ParsingException {
            skipWhitespace();
            if (between('0', '9')) {
                return new CheckedConst<>(parseConst(), function);
            } else if (test('-')) {
                return parseNegate();
            } else if (test('x')) {
                return new CheckedVariable<>("x");
            } else if (test('y')) {
                return new CheckedVariable<>("y");
            } else if (test('z')) {
                return new CheckedVariable<>("z");
            } else if (test('(')) {
                return parseExpression(1);
            }
            throw new ParsingException("argument expected");
        }

        private String parseConst() {
            StringBuilder value = new StringBuilder();
            while (between('0', '9') || ch == '.') {
                value.append(ch);
                nextChar();
            }
            return value.toString();
        }

        private CommonExpression<T> parseNegate() throws ParsingException {
            skipWhitespace();
            int counter = 1;
            while (test('-')) {
                counter++;
                skipWhitespace();
            }
            if (between('0', '9')) {
                if (counter % 2 == 0) {
                    return new CheckedConst<>(parseConst(), function);
                } else {
                    return new CheckedConst<>("-" + parseConst(), function);
                }
            } else {
                if (counter % 2 == 0) {
                    return parseLow();
                } else {
                    return new CheckedNegate<>(parseLow());
                }
            }
        }

        private void skipWhitespace() {
            while(test(' ') || test('\r') || test('\n') || test('\t')) {

            }
        }
    }
}