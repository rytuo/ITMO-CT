package expression;

public class Add extends BinaryOperation {
    public Add(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public double getAns(double first, double second) {
        return first + second;
    }
    public int getAns(int first, int second) {
        return first + second;
    }

    @Override
    protected String getSign() {
        return "+";
    }

    public int checkOrder() {
        return 1;
    }
}
