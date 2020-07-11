package expression;

import java.util.Objects;

public abstract class UnaryOperation implements CommonExpression {
    private CommonExpression value;

    public UnaryOperation(CommonExpression value) {
        this.value = value;
    }

    protected abstract int getAns(int x);
    protected abstract double getAns(double x);
    protected abstract String getSign();

    public int evaluate(int x, int y, int z) {return (int)(getAns(value.evaluate(x, y, z)));}
    public double evaluate(double x) {
        return getAns(value.evaluate(x));
    }
    public int evaluate(int x) {
        return (int)(getAns(value.evaluate(x)));
    }

    public String toString() {
        return "(" + getSign() + value.toString() + ")";
    }
    public String toMiniString() {
        if (value.checkOrder() == -1)
            return getSign() + value.toMiniString();
        else return getSign() + "(" + value.toMiniString() + ")";
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        UnaryOperation operation = (UnaryOperation) object;
        return (this.value.equals(operation.value));
    }
    public int hashCode() {
        return Objects.hash(value, getClass()) * 2048844;
    }

    public int checkFirstOrder() {
        return value.checkOrder();
    }
}
