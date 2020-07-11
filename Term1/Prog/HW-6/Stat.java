import java.util.ArrayList;
import java.lang.StringBuilder;

public class Stat {
	private int first;
	private ArrayList<Integer> line;
	private ArrayList<Integer> row;
	
	public Stat() {
		this.first = 0;
		this.line = new ArrayList<Integer>();
		this.row = new ArrayList<Integer>();
	}
	
	public void plus(int line, int row) {
		this.first++;
		this.line.add(line);
		this.row.add(row);
	}
	
	public int getFirst() {
		return this.first;
	}
	
	public String getStat() {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < this.row.size(); i++) {
			string.append(this.line.get(i) + ":" + this.row.get(i) + " ");
		}
		string.setLength(string.length() - 1);
		return(string.toString());
	}
}