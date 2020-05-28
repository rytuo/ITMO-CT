package expression.generic;

public class MyShort extends MyType<Short> {
    public MyShort(Short value) {
        super(value);
    }

    public static MyType<Short> parse(String value) {
        return new MyShort((short)Integer.parseInt(value));
    }

    @Override
    public MyType<Short> add(MyType<Short> arg) {
        super.value = (short) (super.value + arg.value);
        return this;
    }

    @Override
    public MyType<Short> sub(MyType<Short> arg) {
        super.value = (short) (super.value - arg.value);
        return this;
    }

    @Override
    public MyType<Short> mul(MyType<Short> arg) {
        super.value = (short) (super.value * arg.value);
        return this;
    }

    @Override
    public MyType<Short> div(MyType<Short> arg) {
        super.value = (short) (super.value / arg.value);
        return this;
    }

    @Override
    public MyType<Short> neg() {
        super.value = (short) (-super.value);
        return this;
    }
}
