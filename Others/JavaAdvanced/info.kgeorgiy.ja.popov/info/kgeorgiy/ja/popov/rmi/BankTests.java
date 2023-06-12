package info.kgeorgiy.ja.popov.rmi;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class BankTests {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(Tests.class);
        result.getFailures().forEach(failure ->
                System.out.println(failure.getTestHeader() + " : " + failure.getMessage()));

        final int exitCode = result.wasSuccessful() ? 0 : 1;
        System.exit(exitCode);
    }
}