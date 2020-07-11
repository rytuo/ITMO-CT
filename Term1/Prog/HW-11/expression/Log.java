package expression;

public class Log extends UnaryOperation {
    public Log(CommonExpression value) { super(value); }

    @Override
    protected int getAns(int x) {
        return (int)(Math.log(x) / Math.log(2));
    }

    @Override
    protected double getAns(double x) {
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
