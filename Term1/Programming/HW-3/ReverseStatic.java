import java.util.Scanner;
public class Reverse {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] lines = new String[1000_000];
		String[] line = new String[1000_000];
		int j = -1;
		while (sc.hasNextLine()) {
			j++;
			lines[j] = sc.nextLine();
		}
		int k;
		for (j = j; j >= 0; j--) {
			k = -1;
			if (lines[j] != "") {
				for (String i: lines[j].split(" ")) {
					k++;
					line[k] = i;
				}
				for (int i = k; i >= 0; i--)
					System.out.print(line[i] + " ");
			}
			System.out.println(" ");
		}
	}
}