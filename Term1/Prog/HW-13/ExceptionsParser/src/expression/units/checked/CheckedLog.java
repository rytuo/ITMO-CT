package expression.units.checked;

import expression.units.*;

public class CheckedLog extends UnaryOperation {
    public CheckedLog(CommonExpression value) { super(value); }

    @Override
    protected int getAns(int x) throws ArithmeticException {
        if (x <= 0) {
            throw new IllegalArgumentException("log2 " + x);
        }
        return (int)(Math.log(x) / Math.log(2));
    }

    @Override
    protected String getSign() {
        return "log2";
    }

    @Override
    public int checkOrder() {
        return -2;
    }
}
