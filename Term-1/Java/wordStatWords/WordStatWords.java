import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class WordStatWords {
	public static void main(String[] args)throws IOException {
		try {
			Scan in = new Scan(new File(args[0]));
			try {
				FileWriter out = new FileWriter(new File(args[1]), StandardCharsets.UTF_8);	
				try {
					StringBuilder string = new StringBuilder();
					TreeMap<String, Integer> words = new TreeMap<String, Integer>();
					int k;
					try {
						while ((k = in.nextChar()) != -1) {
							char symbol = (char) k;
							if (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION || symbol == '\'') {
									string.append(symbol);
							} else if (!string.toString().equals("")) {
								String word = string.toString().toLowerCase();
								if (words.containsKey(word)) {
									words.put(word, words.get(word) + 1);
								} else {
									words.put(word, 1);
								}
								string = new StringBuilder();
							}
						}
						try {
							for (String i : words.keySet()) {
								out.write(i + " " + words.get(i) + "\n");
							}
						} catch (IOException excep) {
							System.out.println("Error:   output problems");
						}
					} catch (IOException excep) {
						System.out.println("Error:   input problems");
					}
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