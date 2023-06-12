package expression.generic;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;

public class MyInt extends MyType<Integer> {
    public MyInt(Integer value) {
        super(value);
    }

    public static MyType<Integer> parse(String value) {
        return new MyInt(Integer.parseInt(value));
    }

    @Override
    public MyType<Integer> add(MyType<Integer> arg) {
        if ((value > 0 && arg.value > Integer.MAX_VALUE - value) ||
                (value < 0 && arg.value < Integer.MIN_VALUE - value)) {
            throw new OverflowException(value, "+", arg.value);
        }
        value += arg.value;
        return this;
    }

    @Override
    public MyType<Integer> sub(MyType<Integer> arg) {
        if ((value > 0 && arg.value < value - Integer.MAX_VALUE) ||
                (value < 0 && arg.value > value - Integer.MIN_VALUE) ||
                (value == 0 && arg.value == Integer.MIN_VALUE)) {
            throw new OverflowException(value, "-", arg.value);
        }
        value -= arg.value;
        return this;
    }

    @Override
    public MyType<Integer> mul(MyType<Integer> arg) {
        if ((value > 0 && arg.value > 0 && arg.value > Integer.MAX_VALUE / value) ||
                (value > 0 && arg.value < 0 && arg.value < Integer.MIN_VALUE / value) ||
                (value < 0 && arg.value > 0 && value < Integer.MIN_VALUE / arg.value) ||
                (value < 0 && arg.value < 0 && arg.value < Integer.MAX_VALUE / value)) {
            throw new OverflowException(value, "*", arg.valueOf());
        }
        value *= arg.value;
        return this;
    }

    @Override
    public MyType<Integer> div(MyType<Integer> arg) {
        if (arg.value == 0) {
            throw new DivideByZeroException(value);
        }
        if (value == Integer.MIN_VALUE && arg.value == -1) {
            throw new OverflowException(value, "/", arg.value);
        }
        value /= arg.value;
        return this;
    }

    @Override
    public MyType<Integer> neg() {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException("-", value);
        }
        value = -super.value;
        return this;
    }
}
