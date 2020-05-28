import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class ReverseTranspose {
	public static void main(String[] args) {
		try {
			Scan s = new Scan(System.in);
			ArrayList<ArrayList<Integer>> nums = new ArrayList<>();
			String line, word;
			int k = 0, h = 0;

			while (s.hasNext()) {
				line = s.nextLine();
				k = 0;
				Scan s2 = new Scan(line);
				while(s2.hasNext()) {
					k++;
					if (k > h)
						nums.add(new ArrayList<Integer>());
					word = s2.next();
					if (word.length() > 0)
						nums.get(k-1).add(Integer.parseInt(word));
				}
				if (k > h)
					h = k;
				s2.close();
			}
			s.close();
			
			for (int i = 0; i < h; i++) {
				if (nums.get(i).size() > 0) {
					for (int j = 0; j < nums.get(i).size(); j++)
							System.out.print(nums.get(i).get(j) + " ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}