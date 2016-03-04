package pieces;

import java.util.ArrayList;

import board.Square;
import exceptions.InvalidMoveException;
import exceptions.InvalidSquareException;

public abstract class Piece {

	public enum PieceColour {
		WHITE, BLACK
	}

	protected Square position;
	protected PieceColour pieceColour;

	public PieceColour getPieceColour() {
		return pieceColour;
	}

	public void setPieceColour(PieceColour pieceColour) {
		this.pieceColour = pieceColour;
	}

	public Square getPosition() {
		return position;
	}

	public void setPosition(Square position) {
		this.position = position;
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
	
	abstract public boolean canMove(Square targetSquare)
			throws InvalidMoveException, InvalidSquareException;

	public abstract ArrayList<Square> getAccesableSquares();
	public abstract ArrayList<Square> getAttackedSquares();

}
