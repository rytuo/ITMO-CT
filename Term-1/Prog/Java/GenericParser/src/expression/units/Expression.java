package expression.units;

import expression.generic.MyType;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Expression<T extends Number> extends ToMiniString {
    MyType<T> evaluate(MyType<T> x);
}
