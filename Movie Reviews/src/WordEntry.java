
public class WordEntry {
	// fields
	private String word;
	private double score;
	private int appearances;
	
	// constructors
	public WordEntry(String word, double score, int appearances) {
		this.word = word;
		this.score = score;
		this.appearances = appearances;
	}
	
	// methods
	public double getScore() {
		return score / appearances;
	}
	
	public int getAppearances() {
		return appearances;
	}
	
	public void update(double score) {
		this.score += score;
		++appearances;
	}
	
}
