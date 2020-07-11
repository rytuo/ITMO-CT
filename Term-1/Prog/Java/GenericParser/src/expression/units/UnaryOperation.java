package expression.units;

import expression.generic.MyType;

import java.util.Objects;

public abstract class UnaryOperation<T extends Number> implements CommonExpression<T> {
    private CommonExpression<T> value;

    public UnaryOperation(CommonExpression<T> value) {
        this.value = value;
    }

    protected abstract MyType<T> getAns(MyType<T> x);
    protected abstract String getSign();

    public MyType<T> evaluate(MyType<T> x, MyType<T> y, MyType<T> z) {return getAns(value.evaluate(x, y, z));}
    public MyType<T> evaluate(MyType<T> x) {
        return getAns(value.evaluate(x));
    }

    public String toString() {
        return "(" + getSign() + value.toString() + ")";
    }
    public String toMiniString() {
        if (value.checkOrder() == -1)
            return getSign() + value.toMiniString();
        else return getSign() + "(" + value.toMiniString() + ")";
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        UnaryOperation operation = (UnaryOperation) object;
        return (this.value.equals(operation.value));
    }
    public int hashCode() {
        return Objects.hash(value, getClass()) * 2048844;
    }

    public int checkFirstOrder() {
        return value.checkOrder();
    }
}
