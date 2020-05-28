import expression.exceptions.ParsingException;
import expression.units.*;
import expression.parser.*;

public class Main {
    public static void main(String[] args) throws ParsingException {
        CommonExpression exp = new ExpressionParser().parse("1--2");
        System.out.println(exp.toMiniString() + " = " + exp.evaluate(0, 0, 1));
    }
}