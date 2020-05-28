package expression.units.checked;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;
import expression.units.*;

public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int getAns(int first, int second) throws ArithmeticException {
        if (second == 0) {
            throw new DivideByZeroException(first);
        }
        if (first == Integer.MIN_VALUE && second == -1) {
            throw new OverflowException(first, "/", second);
        }
        return first / second;
    }

    @Override
    protected String getSign() {
        return "/";
    }

    public int checkOrder() {
        return 3;
    }
}
