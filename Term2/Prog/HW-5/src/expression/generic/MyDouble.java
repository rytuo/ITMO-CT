package expression.generic;

public class MyDouble extends MyType<Double> {
    public MyDouble(Double value) { super(value); }

    public static MyType<Double> parse(String value) {
        return new MyDouble(Double.parseDouble(value));
    }

    @Override
    public MyType<Double> add(MyType<Double> arg) {
        super.value += arg.value;
        return this;
    }

    @Override
    public MyType<Double> sub(MyType<Double> arg) {
        super.value -= arg.value;
        return this;
    }

    @Override
    public MyType<Double> mul(MyType<Double> arg) {
        super.value *= arg.value;
        return this;
    }

    @Override
    public MyType<Double> div(MyType<Double> arg) {
        super.value /= arg.value;
        return this;
    }

    @Override
    public MyType<Double> neg() {
        super.value = -super.value;
        return this;
    }
}
