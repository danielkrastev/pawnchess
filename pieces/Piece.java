package pieces;

import java.util.ArrayList;
import board.Field;
import exceptions.InvalidMoveException;

public abstract class Piece {

	public enum PieceColour {
		WHITE, BLACK
	}
	
	protected int [] boardPosition;
	
	public int [] getBoardPosition() {
		return boardPosition;
	}

	public void setBoardPosition(int [] position) {
		this.boardPosition = position;
	}
	protected PieceColour pieceColour;

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
	
	public int getRow() {
		return boardPosition[0];
	}
	
	public int getColumn(){
		return boardPosition[1];
	}
	
	abstract public boolean canMove(Field targetField)
			throws InvalidMoveException;

	public abstract ArrayList<Field> getAccesableFields(Field boardPosition);
	public abstract ArrayList<Field> getAttackedFields(Field boardPosition);

}
