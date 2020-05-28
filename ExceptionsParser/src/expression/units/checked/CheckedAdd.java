package expression.units.checked;


import expression.exceptions.OverflowException;
import expression.units.BinaryOperation;
import expression.units.CommonExpression;

public class CheckedAdd extends BinaryOperation {
    public CheckedAdd(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int getAns(int first, int second) throws ArithmeticException {
        if ((first > 0 && second > Integer.MAX_VALUE - first) ||
                (first < 0 && second < Integer.MIN_VALUE - first)) {
            throw new OverflowException(first, "+", second);
        }
        return first + second;
    }

    protected String getSign() {
        return "+";
    }

    public int checkOrder() {
        return 1;
    }
}
