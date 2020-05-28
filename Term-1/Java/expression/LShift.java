package expression;

public class LShift extends BinaryOperation {
    public LShift(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int getAns(int first, int second) {
        return first << second;
    }

    public double getAns(double first, double second) {
        return (int)first << (int)second;
    }

    @Override
    protected String getSign() {
        return "<<";
    }

    public int checkOrder() {
        return 5;
    }
}
