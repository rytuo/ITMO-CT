import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class WordStatLineIndex {
	public static void main(String[] args)throws IOException {
		try {
			Scan in = new Scan(new File(args[0]));
			try {
				FileWriter out = new FileWriter(new File(args[1]), StandardCharsets.UTF_8);	
				try {
					StringBuilder string = new StringBuilder();
					TreeMap<String, Stat> words = new TreeMap<String, Stat>();
					int k, count = 0, line = 1;
					
					while ((k = in.nextChar()) != -1) {
						char symbol = (char) k;
						
						if (Character.isLetter(symbol) ||
							Character.getType(symbol) == Character.DASH_PUNCTUATION ||
							symbol == '\'') {
								
								string.append(symbol);
								
						} else {
							if (symbol == '\n') {
								line++;
								count = 0;
							}
							if (string.toString().length() > 0) {
								String word = string.toString().toLowerCase();
								if (words.containsKey(word)) {
									Stat t = words.get(word);
									t.plus(line, ++count);
									words.put(word, t);
								} else {
									Stat p = new Stat();
									p.plus(line, ++count);
									words.put(word, p);
								}
								string = new StringBuilder();
							}
						}
					}
					try {
						for (String i : words.keySet()) {
							out.write(i + " " + words.get(i).getFirst() + " "
										+ words.get(i).getStat());
							out.write("\n");
						}
					} catch (IOException except) {
						System.out.println("Error: output problems");
					}
				} catch (IOException excep) {
					System.out.println("Error: input problems");
				} finally {
					out.close();
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Error: no output file");
			} finally {
				in.close();
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: no input file");
		} catch (FileNotFoundException exc) {
			System.out.println("Error: there is no such input file");
		}
	}
}