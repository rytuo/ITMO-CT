package expression.units.checked;

import java.util.Objects;
import java.util.function.Function;

import expression.generic.MyType;
import expression.units.*;

public class CheckedConst<T extends Number> implements CommonExpression<T> {
    private String value;
    private Function<String, MyType<T>> function;

    public CheckedConst(String value, Function<String, MyType<T>> function) {
        this.value = value;
        this.function = function;
    }

    @Override
    public MyType<T> evaluate(MyType<T> value) {
        return function.apply(this.value);
    }

    @Override
    public MyType<T> evaluate(MyType<T> x, MyType<T> y, MyType<T> z) {
        return function.apply(this.value);
    }

    public String toString() {
        return toMiniString();
    }

    @Override
    public String toMiniString() {
        return value;
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