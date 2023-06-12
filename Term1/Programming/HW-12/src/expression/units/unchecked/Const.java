package expression.units.unchecked;

import java.util.Objects;
import expression.units.*;

public class Const implements CommonExpression {
    private int value;

    public Const(String value) {
        this.value = Integer.parseInt(value);
    }
    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int value) {
        return this.value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.value;
    }

    public String toString() {
        return toMiniString();
    }

    @Override
    public String toMiniString() {
        return Integer.toString((int) this.value);
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