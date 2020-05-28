package expression;

public class Divide extends BinaryOperation {
    public Divide(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int getAns(int first, int second) {
        return first / second;
    }

    public double getAns(double first, double second) {
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
