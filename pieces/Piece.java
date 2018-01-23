package pieces;

import java.util.ArrayList;
import board.Square;
import exceptions.InvalidMoveException;

public abstract class Piece {

	public enum PieceColour {
		WHITE, BLACK
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
	
	abstract public boolean canMove(Square targetSquare)
			throws InvalidMoveException;

	public abstract ArrayList<Square> getAccesableSquares();
	public abstract ArrayList<Square> getAttackedSquares();

}
