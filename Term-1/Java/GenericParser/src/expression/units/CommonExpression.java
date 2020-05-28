package expression.units;

public interface CommonExpression<T extends Number> extends Expression<T>, TripleExpression<T> {
    int checkOrder();
}