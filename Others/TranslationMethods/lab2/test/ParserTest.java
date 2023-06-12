import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ParserTest {
    private static final Parser parser = new Parser();
    private static int num = 0;

    public static void main(String... args) {
        // valid
        boolean res =
           testTrue("lambda : 1") // no args
        && testTrue("lambda x : x") // one args
        && testTrue("lambda x , y , z : x + y + z") // many args
        && testTrue("lambda : 1") // num
        && testTrue("lambda : Xx0_") // var
        && testTrue("lambda : - 1") // single minus
        && testTrue("lambda : - - - - - 1") // many minuses
        && testTrue("lambda : 1 * 1") // monomial
        && testTrue("lambda : - 1 * 1") // monomial with first minus
        && testTrue("lambda : 1 * - 1") // monomial with second minus
        && testTrue("lambda : 1 + 2 + 3") // polynomial
        && testTrue("lambda : 1 * 1 + 2 * 2 + 3 * 3") // polynomial of monomials
        && testTrue("lambda : ( ( 1 ) + ( 2 ) - ( 3 ) )") // parenthesis
        && testTrue("lambda x , y , z : x + y - z * - ( - 1 / - - 2 // 1 % 1 )") // sample test
        && testTrue("lambda x, y, z : x + y - z * -(-1 / - - 2 // 1 % 1) * (lambda x : 7575)") // lambda in lambda

        // invalid
        && testFalse("") // empty
        && testFalse("lambda") // lambda only
        && testFalse("lambda x, y,:") // invalid args
        && testFalse("lambda : _x") // invalid var
        && testFalse("lambda : (test + x") // invalid parenthesis 1
        && testFalse("lambda : test + x)") // invalid parenthesis 2
        && testFalse("lambda : -") // invalid operation 1
        && testFalse("lambda : 1 -") // invalid operation 1
        && testFalse("lambda : 1 +") // invalid operation 2
        && testFalse("lambda : 1 *") // invalid operation 3
        && testFalse("lambda : 1 /") // invalid operation 4
        && testFalse("lambda : 1 %") // invalid operation 5
        && testFalse("lambda : 1 //") // invalid operation 6
        && testFalse("lambda : 1 // ( lambda x : )") // invalid lambda in lambda
        ;

        if (res) {
            System.out.printf("%nAll tests passed%n");
        }
    }

    private static boolean testTrue(String test) {
        return test(true, test);
    }

    private static boolean testFalse(String test) {
        return test(false, test);
    }

    private static boolean test(boolean res, String test) {
        num++;

        String parsedString, normalizedTest;

        try {
            parsedString = parser.parse(new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8))).toString();
            normalizedTest = Arrays.stream(test.split(" ")).map(String::trim)
                    .filter(t -> !t.isEmpty()).collect(Collectors.joining(" "));
        } catch (ParseException e) {
            if (res) {
                System.err.printf("Test %d: FAIL%n%n%s", num, e.getMessage());
                return false;
            } else {
                System.out.printf("Test %d: OK%n", num);
                return true;
            }
        }

        if (parsedString.equals(normalizedTest)) {
            System.out.printf("Test %d: OK%n", num);
            return true;
        } else {
            System.err.printf("Test %d: FAIL%n%nExpected: %s%nResult: %s%n", num, normalizedTest, parsedString);
            return false;
        }
    }
}
