package expression;

public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(CommonExpression value) {
        super(value);
    }

    @Override
    protected int getAns(int x) {
        return (-x);
    }

    protected double getAns(double x) {
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
