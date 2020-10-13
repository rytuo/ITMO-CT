package expression.parser;

import expression.exceptions.ParsingException;
import expression.generic.MyType;
import expression.units.CommonExpression;

import java.util.function.Function;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser<T extends Number> {
    CommonExpression<T> parse(String expression, Function<String, MyType<T>> function) throws ParsingException;
}