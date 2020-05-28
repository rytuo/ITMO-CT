package expression.units.checked;

import java.util.Objects;
import expression.exceptions.ConstantOverflowException;
import expression.exceptions.ParsingException;
import expression.units.*;

public class CheckedConst implements CommonExpression {
    private int value;

    public CheckedConst(String value) throws ParsingException {
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ConstantOverflowException(value);
        }
    }

    public CheckedConst(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int value) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    public String toString() {
        return toMiniString();
    }

    @Override
    public String toMiniString() {
        return Integer.toString(value);
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        CheckedConst variable = (CheckedConst) object;
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