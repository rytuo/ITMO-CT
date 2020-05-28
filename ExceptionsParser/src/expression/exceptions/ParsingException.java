package expression.exceptions;

import static expression.parser.ExpressionParser.getPos;

public class ParsingException extends Exception {
    private static String makeExpression() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("\t\t\t\t\t\t\t\t");
        sb.append(expression.parser.ExpressionParser.getExpression());
        sb.append("\n\t\t\t\t\t\t\t\t");
        sb.append(" ".repeat(Math.max(0, getPos() - 1)));
        sb.append("^");
        return sb.toString();
    }

    public ParsingException(String message) {
        super(String.format("%s at position %d%s", message, getPos(), makeExpression()));
    }
}
