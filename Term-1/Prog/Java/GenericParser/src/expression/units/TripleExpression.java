package expression.units;

import expression.generic.MyType;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression<T extends Number> extends ToMiniString {
    MyType<T> evaluate(MyType<T> x, MyType<T> y, MyType<T> z);
}
