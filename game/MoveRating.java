package game;

public class MoveRating {
	
	private Move move;
	private int rating;
	
	public MoveRating(Move m, int r) {
		this.move = m;
		this.rating = r;
	}
	
	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
