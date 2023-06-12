package expression.units.checked;

import expression.exceptions.OverflowException;
import expression.units.*;

public class CheckedNegate extends UnaryOperation {
    public CheckedNegate(CommonExpression value) {
        super(value);
    }

    @Override
    protected int getAns(int x) throws ArithmeticException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("-", x);
        }
        return (-x);
    }

    @Override
    protected String getSign() {
        return "-";
    }

    public int checkOrder() {
        return -2;
    }
}
