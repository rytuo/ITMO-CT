package expression.units.checked;

import expression.exceptions.OverflowException;
import expression.units.*;

public class CheckedMultiply extends BinaryOperation {
    public CheckedMultiply(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int getAns(int first, int second) throws ArithmeticException {
        if ((first > 0 && second > 0 && second > Integer.MAX_VALUE / first) ||
                (first > 0 && second < 0 && second < Integer.MIN_VALUE / first) ||
                (first < 0 && second > 0 && first < Integer.MIN_VALUE / second) ||
                (first < 0 && second < 0 && second < Integer.MAX_VALUE / first)) {
            throw new OverflowException(first, "*", second);
        }
        return first * second;
    }

    @Override
    protected String getSign() {
        return "*";
    }

    public int checkOrder() {
        return 0;
    }
}
