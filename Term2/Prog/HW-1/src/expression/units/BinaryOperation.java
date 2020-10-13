package expression.units;

import expression.generic.MyType;

import java.util.Objects;

public abstract class BinaryOperation<T extends Number> implements CommonExpression<T> {
    private CommonExpression<T> first;
    private CommonExpression<T> second;

    public BinaryOperation(CommonExpression<T> first, CommonExpression<T> second) {
        this.first = first;
        this.second = second;
    }

    protected abstract MyType<T> getAns(MyType<T> a, MyType<T> b);
    protected abstract String getSign();

    public MyType<T> evaluate(MyType<T> x, MyType<T> y, MyType<T> z) {return getAns(first.evaluate(x, y, z), second.evaluate(x, y, z));}

    public MyType<T> evaluate(MyType<T> x) {
        return getAns(first.evaluate(x), second.evaluate(x));
    }

    public String toString() {
        return '(' + first.toString() + ' ' + getSign() + ' ' + second.toString() + ')';
    }

    public String toMiniString() {
        String string = "";

        if (!(checkFirstOrder() == -2 && ((UnaryOperation)first).checkFirstOrder() == -1) &&
                (checkFirstOrder() != -1 && checkOrder() != 1 && checkOrder() != 2 &&
                (checkOrder() != 0 || (checkFirstOrder() != 0 && checkFirstOrder() != 3)) &&
                (checkOrder() != 3 || (checkFirstOrder() != 0 && checkFirstOrder() != 3)))) {
            string = "(" + first.toMiniString() + ")";
        } else {
            string = first.toMiniString();
        }
        string += " " + getSign() + " ";
        if (checkOrder() == -2 || (checkSecondOrder() != -1 && checkOrder() != 1 &&
                (checkOrder() != 2 || (checkSecondOrder() == 1 || checkSecondOrder() == 2)) &&
                (checkOrder() != 0 || checkSecondOrder() != 0)))  {
            string += "(" + second.toMiniString() + ")";
        } else {
            string += second.toMiniString();
        }

        return string;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        BinaryOperation operation = (BinaryOperation) object;
        return (this.first.equals(operation.first) &&
                this.second.equals(operation.second));
    }

    public int hashCode() {
        return Objects.hash(first, getClass(), second) * 2048844;
    }

    public int checkFirstOrder() {
        return first.checkOrder();
    }

    public int checkSecondOrder() {
        return second.checkOrder();
    }
}
