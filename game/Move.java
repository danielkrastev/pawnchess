package game;

import pieces.Piece;
import board.Square;

public class Move {

	private Square currentSquare;
	private Square targetSquare;
	private int rating;

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

	public int getRating(Game game) {
		//return Rating.positionRating(game);
		return 0;
	}
	
@Override
public String toString(){
	 return String.format("[%d %d] [%d %d]", this.getCurrentSquare().getColumn(),
			 								 this.getCurrentSquare().getRow(),
			 								 this.getTargetSquare().getColumn(),
			 								 this.getTargetSquare().getRow()
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
}
