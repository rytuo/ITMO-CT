package expression.exceptions;

public class ConstantOverflowException extends ParsingException {
    public ConstantOverflowException(String message) {
        super(message);
    }
}
