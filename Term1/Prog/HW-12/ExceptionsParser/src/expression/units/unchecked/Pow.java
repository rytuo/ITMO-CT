package expression.units.unchecked;

import expression.units.*;

public class Pow extends UnaryOperation {
    public Pow(CommonExpression value) { super(value); }

    @Override
    protected int getAns(int x) {
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
