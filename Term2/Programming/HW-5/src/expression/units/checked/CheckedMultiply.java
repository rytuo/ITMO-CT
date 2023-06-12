package expression.units.checked;

import expression.exceptions.OverflowException;
import expression.generic.MyType;
import expression.units.*;

public class CheckedMultiply<T extends Number> extends BinaryOperation<T> {
    public CheckedMultiply(CommonExpression<T> first, CommonExpression<T> second) {
        super(first, second);
    }

    public MyType<T> getAns(MyType<T> first, MyType<T> second) throws ArithmeticException {
        return first.mul(second);
    }

    @Override
    protected String getSign() {
        return "*";
    }

    public int checkOrder() {
        return 0;
    }
}
