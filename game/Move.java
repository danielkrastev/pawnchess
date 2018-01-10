package game;

import pieces.Piece;
import board.Square;

public class Move {

	private Square currentSquare;
	private Square targetSquare;
	private int rating=0;
	
	public Move(Square currentSquare, Square targetSquare) {
		this.currentSquare = currentSquare;
		this.targetSquare = targetSquare;
	}
	
	public Move() {
		this.currentSquare = null;
		this.targetSquare = null;
	}

	
	public Move(int [] current, int [] target) {
		this.currentSquare = new Square(current[0], current[1]);
		this.targetSquare = new Square(target[0], target[1]);
	}

	public Square getCurrentSquare() {
		return currentSquare;
	}

	public void setCurrentSquare(Square currentSquare) {
		this.currentSquare = currentSquare;
	}

	public Square getTargetSquare() {
		return targetSquare;
	}

	public void setTargetSquare(Square targetSquare) {
		this.targetSquare = targetSquare;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void multiplyRatingBy(int player) {
		rating = rating * player;
	}

	public Piece getPiece() {
		return currentSquare.getPiece();
	}

	public void calculateRating(Game game) {
	     this.rating = Rating.positionRating(game);
	}

	public int getRating() {
	    return this.rating;
	}

	@Override
	public String toString(){
		 return String.format("[%d %d] [%d %d]", 
				             this.getCurrentSquare().getRow(),
							 this.getCurrentSquare().getColumn(),
							 this.getTargetSquare().getRow(),
							 this.getTargetSquare().getColumn()
							 );
	 }

	@Override
	public boolean equals(Object o) {
		if (o instanceof Move) {
			Move move = (Move) o;
			return this.currentSquare.equals(move.currentSquare)
					&& this.targetSquare.equals(move.targetSquare);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.currentSquare.hashCode();
	}
	
	public static Move max(Move first, Move second) {
		if (null == first) {
			return second;
		}
		if (null == second) {
			return first;
		}
		if (first.getRating() > second.getRating()){
			return first;
		}else {
			return second;
		}
	}
	
	public static Move min(Move first, Move second) {
		if (null == first) {
			return second;
		}
		if (null == second) {
			return first;
		}
		if (first.getRating() < second.getRating()){
			return first;
		}else {
			return second;
		}
	}
}
