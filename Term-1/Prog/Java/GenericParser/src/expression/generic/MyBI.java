package expression.generic;

import java.math.BigInteger;

public class MyBI extends MyType<BigInteger> {
    public MyBI(BigInteger value) { super(value); }

    public static MyType<BigInteger> parse(String value) {
        return new MyBI(new BigInteger(value));
    }

    @Override
    public MyType<BigInteger> add(MyType<BigInteger> arg) {
        value = value.add(arg.value);
        return this;
    }

    @Override
    public MyType<BigInteger> sub(MyType<BigInteger> arg) {
        value = value.add((arg.value).negate());
        return this;
    }

    @Override
    public MyType<BigInteger> mul(MyType<BigInteger> arg) {
        value = value.multiply(arg.value);
        return this;
    }

    @Override
    public MyType<BigInteger> div(MyType<BigInteger> arg) {
        value = value.divide(arg.value);
        return this;
    }

    @Override
    public MyType<BigInteger> neg() {
        value = value.negate();
        return this;
    }
}