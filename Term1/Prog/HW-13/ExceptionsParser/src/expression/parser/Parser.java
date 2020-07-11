package expression.parser;

import expression.exceptions.ParsingException;
import expression.units.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression) throws ParsingException;
}
