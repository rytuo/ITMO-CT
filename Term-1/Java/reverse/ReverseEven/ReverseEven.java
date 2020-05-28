import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class ReverseEven {
	public static void main(String[] args) {
		try {
			Scan s = new Scan(System.in);
			ArrayList<ArrayList<Integer>> nums = new ArrayList<>();
			String line;
			int k = -1;

			while (s.hasNext()) {
				k++;
				line = s.nextLine();
				nums.add(new ArrayList<Integer>());
				Scan s2 = new Scan(line);
				while(s2.hasNext()) {
					String word = s2.next();
					if (!Character.isWhitespace(word.charAt(0)))
						nums.get(k).add(Integer.parseInt(word));
				}
				s2.close();
			}
			s.close();
			
			for (int i = k; i >= 0; i--) {
				if (nums.get(i).size() > 0) {
					for (int j = nums.get(i).size()-1; j >= 0; j--)
						if (nums.get(i).get(j) % 2 == 0)
							System.out.print(nums.get(i).get(j) + " ");
				}
				System.out.println("");
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}