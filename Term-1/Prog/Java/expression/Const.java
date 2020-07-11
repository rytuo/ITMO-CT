package expression;

import java.util.Objects;

public class Const implements CommonExpression, DoubleExpression {
    private double value;
    boolean isInt = false;

    public Const(double value) {
        this.value = value;
    }
    public Const(int value) {
        this.value = (double) value;
        isInt = true;
    }

    @Override
    public int evaluate(int value) {
        return (int)this.value;
    }

    @Override
    public double evaluate(double value) {
        return this.value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int)this.value;
    }

    public String toString() {
        return toMiniString();
    }

    @Override
    public String toMiniString() {
        if (isInt) {
            return Integer.toString((int) this.value);
        }
        else {
            return Double.toString(this.value);
        }
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Const variable = (Const) object;
        return Objects.equals(this.value, variable.value);
    }

    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int checkOrder() {
        return -1;
    }
}