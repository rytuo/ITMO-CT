package expression;

public class RShift extends BinaryOperation {
    public RShift(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int getAns(int first, int second) {
        return first >> second;
    }

    public double getAns(double first, double second) {
        return (int)first >> (int)second;
    }

    @Override
    protected String getSign() {
        return ">>";
    }

    public int checkOrder() {
        return 5;
    }
}
