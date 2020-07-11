import java.util.Scanner;
import java.util.ArrayList;

public class ReverseEven {
	public static void main(String[] args) {
		Scanner s1 = new Scanner(System.in);
		ArrayList<ArrayList<Integer>> nums = new ArrayList<>();
		String line;
		int k = -1;

		while (s1.hasNextLine()) {
			k++;
			line = s1.nextLine();
			nums.add(new ArrayList<Integer>());
			Scanner s2 = new Scanner(line);
			while(s2.hasNextInt())
				nums.get(k).add(s2.nextInt());
			s2.close();
		}
		s1.close();
		
		for (int i = k; i >= 0; i--) {
			if (nums.get(i).size() > 0) {
				for (int j = nums.get(i).size()-1; j >= 0; j--)
					if (nums.get(i).get(j) % 2 == 0)
						System.out.print(nums.get(i).get(j) + " ");
			}
			System.out.println("");
		}
	}
}