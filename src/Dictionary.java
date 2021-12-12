
public class Dictionary implements Comparable<Dictionary>{
	public String word;
	public int distance;
	
	public Dictionary() {
		super();
	}
	public Dictionary(String word, int distance) {
		super();
		this.word = word;
		this.distance = distance;
	}
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String toString() {
		return "word: "+ word + " MED:" + distance;
	}
	@Override
	public int compareTo(Dictionary o) {
		if(this.distance != o.getDistance()) {
			return this.distance - o.getDistance();
		}
		return this.word.compareTo(o.getWord());
	}
	
 
}
