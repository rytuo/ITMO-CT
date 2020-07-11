package expression.exceptions;

import static expression.parser.ExpressionParser.getPos;

public class ConstantOverflowException extends ParsingException {
    public ConstantOverflowException(String message) {
        super(message);
    }
}
