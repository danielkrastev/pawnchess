package game;

import pieces.Piece;
import board.Field;

public class Move {

	private Field currentField;
	private Field targetField;
	private int rating=0;
	
	public Move(Field currentField, Field targetField) {
		this.currentField = currentField;
		this.targetField = targetField;
	}
	
	public Move() {
		this.currentField = null;
		this.targetField = null;
	}

	
	public Move(int [] current, int [] target) {
		this.currentField = new Field(current[0], current[1]);
		this.targetField = new Field(target[0], target[1]);
	}

	public Field getCurrentField() {
		return currentField;
	}

	public void setCurrentField(Field currentField) {
		this.currentField = currentField;
	}

	public Field getTargetField() {
		return targetField;
	}

	public void setTargetField(Field targetField) {
		this.targetField = targetField;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void multiplyRatingBy(int player) {
		rating = rating * player;
	}

	public Piece getPiece() {
		return currentField.getPiece();
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
				             this.getCurrentField().getRow(),
							 this.getCurrentField().getColumn(),
							 this.getTargetField().getRow(),
							 this.getTargetField().getColumn()
							 );
	 }

	@Override
	public boolean equals(Object o) {
		if (o instanceof Move) {
			Move move = (Move) o;
			return this.currentField.equals(move.currentField)
					&& this.targetField.equals(move.targetField);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.currentField.hashCode();
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
