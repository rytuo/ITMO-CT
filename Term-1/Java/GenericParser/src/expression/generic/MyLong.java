package expression.generic;

public class MyLong extends MyType<Long> {
    public MyLong(Long value) {
        super(value);
    }

    public static MyType<Long> parse(String value) {
        return new MyLong(Long.parseLong(value));
    }

    @Override
    public MyType<Long> add(MyType<Long> arg) {
        value += arg.value;
        return this;
    }

    @Override
    public MyType<Long> sub(MyType<Long> arg) {
        value -= arg.value;
        return this;
    }

    @Override
    public MyType<Long> mul(MyType<Long> arg) {
        value *= arg.value;
        return this;
    }

    @Override
    public MyType<Long> div(MyType<Long> arg) {
        value /= arg.value;
        return this;
    }

    @Override
    public MyType<Long> neg() {
        value = -value;
        return this;
    }
}
