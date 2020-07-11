package expression.units.unchecked;

import expression.units.*;

public class Negate extends UnaryOperation {
    public Negate(CommonExpression value) {
        super(value);
    }

    @Override
    protected int getAns(int x) {
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
