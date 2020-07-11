public class SumLong {
	public static void main(String[] args) {
		long s = 0;
		int k, l, j;
		
		for (int i = 0; i < args.length; i++) {
			k = 0;
			l = 0;
			j = 0;
			while (j < args[i].length()) {
				if (Character.isWhitespace(args[i].charAt(l))) {
					if (k != l) {
						s += Long.parseLong(args[i].substring(k, l));
						k = l;
					}
					l += 1;
					k += 1;
				} else {
					l += 1;
				}
				j++;
			}
			if (k != l)
				s += Long.parseLong(args[i].substring(k, l));
		}

		System.out.println(s);
	}
}