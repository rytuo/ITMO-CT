package expression.units.unchecked;

import expression.units.*;

public class Divide extends BinaryOperation {
    public Divide(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int getAns(int first, int second) {
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
