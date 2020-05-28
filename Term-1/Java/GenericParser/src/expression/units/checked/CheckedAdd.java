package expression.units.checked;

import expression.generic.MyType;
import expression.units.BinaryOperation;
import expression.units.CommonExpression;

public class CheckedAdd<T extends Number> extends BinaryOperation<T> {
    public CheckedAdd(CommonExpression<T> first, CommonExpression<T> second) {
        super(first, second);
    }

    public MyType<T> getAns(MyType<T> first, MyType<T> second) throws ArithmeticException {
        return first.add(second);
    }

    protected String getSign() {
        return "+";
    }

    public int checkOrder() {
        return 1;
    }
}
