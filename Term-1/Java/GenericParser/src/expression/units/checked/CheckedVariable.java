package expression.units.checked;

import expression.generic.MyType;
import expression.units.CommonExpression;
import java.util.Objects;

public class CheckedVariable<T extends Number> implements CommonExpression<T> {
    private String variable;

    public CheckedVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public MyType<T> evaluate(MyType<T> value) {
        return value;
    }

    @Override
    public MyType<T> evaluate(MyType<T> x, MyType<T> y, MyType<T> z) {
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
        CheckedVariable var = (CheckedVariable) object;
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