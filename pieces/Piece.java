package pieces;

import java.util.ArrayList;
import board.Field;
import exceptions.InvalidMoveException;

public abstract class Piece {

	public enum PieceColour {
		WHITE, BLACK
	}

	protected PieceColour pieceColour;
	protected Field currentField;
	
	
	public Field getCurrentField() {
		return currentField;
	}

	public void setCurrentField(Field currentField) {
		this.currentField = currentField;
	}

	public PieceColour getPieceColour() {
		return pieceColour;
	}
	
	public void setPieceColour(PieceColour pieceColour) {
		this.pieceColour = pieceColour;
	}


	public String toString() {
		return "Unidentified square";
	}

	public boolean isWhite(){
		if (this.pieceColour.equals(PieceColour.WHITE)){
			return true;
		}
		return false;
	}
	public boolean isBlack(){
		return !isWhite();
	}
	
	abstract public boolean canMove(Field targetField)
			throws InvalidMoveException;

	public abstract ArrayList<Field> getAccesableFields();
	public abstract ArrayList<Field> getAttackedFields();

}
