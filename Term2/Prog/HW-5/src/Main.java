import expression.generic.GenericTabulator;

public class Main {
    public static void main(String[] args) throws Exception {
        GenericTabulator table = new GenericTabulator();
        Object[][][] res = table.tabulate("s", "10", 2147483632, 2147483646, 2147483642, 2147483646, 2147483637, 2147483646);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                for (int k = 0; k < res[0][0].length; k++) {
                    System.out.print(" x = " + i + ", y = " + j + ", k = " + k + ": " + res[i][j][k] + "\n");;
                }
            }
        }
    }
}