package expression.units.checked;

import expression.exceptions.OverflowException;
import expression.units.*;

public class CheckedPow extends UnaryOperation {
    public CheckedPow(CommonExpression value) { super(value); }

    @Override
    protected int getAns(int x) throws ArithmeticException {
        if (x < 0) {
            throw new IllegalArgumentException("pow2" + x);
        }
        if (x >= 32) {
            throw new OverflowException("pow2", x);
        }
        return (int)Math.pow(2, x);
    }

    @Override
    protected String getSign() {
        return "pow2";
    }

    @Override
    public int checkOrder() {
        return -2;
    }
}
