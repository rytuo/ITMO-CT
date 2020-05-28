import java.util.Scanner;
import java.util.ArrayList;

public class ReverseEven {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList <String> lines = new ArrayList <String>();
		ArrayList <String> line = new ArrayList <String>();
		while (sc.hasNextLine())
			lines.add(sc.nextLine());
		int c;
		for (int j = lines.size() - 1; j >= 0; j--) {
			line = new ArrayList <String>();
			if (lines.get(j).length() > 0) {
				for (String i: lines.get(j).split(" "))
					line.add(i);
				for (int i = line.size()-1; i >= 0; i--) {
					c = line.get(i).charAt(line.get(i).length()-1) - 48;
					if (c % 2 == 0)
						System.out.print(line.get(i) + " ");
				}
			}
			System.out.println("");
		}
	}
}