package expression;

public interface CommonExpression extends Expression, DoubleExpression, TripleExpression {
    int checkOrder();
}
