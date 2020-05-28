package expression.units.checked;

import expression.exceptions.OverflowException;
import expression.units.*;

public class CheckedSubtract extends BinaryOperation {
    public CheckedSubtract(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int getAns(int first, int second) throws ArithmeticException {
        if ((first > 0 && second < first - Integer.MAX_VALUE) ||
                (first < 0 && second > first - Integer.MIN_VALUE) ||
                (first == 0 && second == Integer.MIN_VALUE)) {
            throw new OverflowException(first, "-", second);
        }
        return first - second;
    }

    @Override
    protected String getSign() {
        return "-";
    }

    public int checkOrder() {
        return 2;
    }
}
