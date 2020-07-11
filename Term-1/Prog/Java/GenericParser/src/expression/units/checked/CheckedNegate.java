package expression.units.checked;

import expression.exceptions.OverflowException;
import expression.generic.MyType;
import expression.units.*;

public class CheckedNegate<T extends Number> extends UnaryOperation<T> {
    public CheckedNegate(CommonExpression<T> value) {
        super(value);
    }

    @Override
    protected MyType<T> getAns(MyType<T> x) throws ArithmeticException {
        return x.neg();
    }

    @Override
    protected String getSign() {
        return "-";
    }

    public int checkOrder() {
        return -2;
    }
}
