package expression;

import java.util.Objects;

public class Variable implements CommonExpression, DoubleExpression {
    private String variable;

    public Variable (String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(int value) {
        return value;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (variable.equals("x")) {
            return x;
        } else if (variable.equals("y")) {
            return y;
        } else {
            return z;
        }
    }

    public String toString() {
        return variable;
    }

    @Override
    public String toMiniString() {
        return variable;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Variable var = (Variable) object;
        return Objects.equals(this.variable, var.variable);
    }

    public int hashCode() {
        return Objects.hash(this.variable);
    }

    @Override
    public int checkOrder() {
        return -1;
    }
}