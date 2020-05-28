package expression.generic;

import expression.parser.ExpressionParser;
import expression.units.CommonExpression;
import java.util.function.Function;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] ans;
        switch (mode) {
            case "i":
                ans = calculate(expression,  MyInt::parse, x1, x2, y1, y2, z1, z2);
                break;
            case "d":
                ans = calculate(expression,  MyDouble::parse, x1, x2, y1, y2, z1, z2);
                break;
            case "bi":
                ans = calculate(expression, MyBI::parse, x1, x2, y1, y2, z1, z2);
                break;
            case "l":
                ans = calculate(expression,  MyLong::parse, x1, x2, y1, y2, z1, z2);
                break;
            default:
                ans = calculate(expression,  MyShort::parse, x1, x2, y1, y2, z1, z2);
                break;
        }
        return ans;
    }

    private <T extends Number>Object[][][] calculate(String expression, Function<String, MyType<T>> function, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        CommonExpression<T> parsedExpression = new ExpressionParser<T>().parse(expression, function);
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                for (int k = z1; k <= z2; k++)
                    try {
                        ans[i - x1][j - y1][k - z1] = parsedExpression.evaluate(
                                function.apply(Integer.toString(i)),
                                function.apply(Integer.toString(j)),
                                function.apply(Integer.toString(k))).valueOf();
                    } catch (Exception e) {
                        ans[i - x1][j - y1][k - z1] = null;
                    }
        return ans;
    }
}
