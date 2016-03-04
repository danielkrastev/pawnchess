package pieces;

import java.util.ArrayList;
import java.util.List;



import exceptions.InvalidMoveException;
import exceptions.InvalidSquareException;
import game.Game;

import board.Square;

public class King extends Piece {

	public King(PieceColour colour, Square position) {
		this.position = position;
		this.pieceColour = colour;
	}
	public King(PieceColour colour) {
		this.pieceColour = colour;
	}

	public String toString() {
		if (this.pieceColour.equals(PieceColour.WHITE)) {
			return new String(" K ");
		} else {
			return new String(" k ");
		}
	}

	@Override
	public boolean canMove(Square targetSquare) throws InvalidMoveException,
			InvalidSquareException {

		if (targetSquare.isValidSquare() && isMoveValid(targetSquare) && isSquareAccessible(targetSquare)) {
			return true;
		}
		return false;
	}

	private boolean isSquareAccessible(Square targetSquare) {
	
		if (!targetSquare.isTaken()){
			return true;
		}
        
		PieceColour colour = this.getPieceColour();
		
		switch (colour){
		case WHITE:
			if (targetSquare.getPiece().getPieceColour().equals(PieceColour.BLACK)){
				return true;
			}else {
				return false;
			}
		case BLACK:
			if (targetSquare.getPiece().getPieceColour().equals(PieceColour.WHITE)){
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	private boolean isMoveValid(Square targetSquare)
			throws InvalidMoveException {

		Square currentPosition = this.getPosition();		
		if ((targetSquare.isOneSquareUpFrom(currentPosition)
				|| targetSquare.isOneSquareRightFrom(currentPosition)
				|| targetSquare.isOneSquareUpFrom(currentPosition)
				|| targetSquare.isOneSquareDownFrom(currentPosition)
				|| targetSquare.isOneSquareLeftUpFrom(currentPosition)
				|| targetSquare.isOneSquareRightUpFrom(currentPosition)
				|| targetSquare.isOneSquareLeftDownFrom(currentPosition) || targetSquare
				.isOneSquareRightDownFrom(currentPosition))) {

			return true;
		}
		return false;
	}


	//where the king can actually move
		
	//all the fields surrounding the king
	public ArrayList<Square> getAccesableSquares() {

     ArrayList <Square> accessableSquares = new ArrayList<Square>();

     	 Square leftSquare = position.oneSquareLeft();
         if (leftSquare != null){
        	 accessableSquares.add(leftSquare);
         }
         Square rightSquare = position.oneSquareRight();
         if (rightSquare !=null){
        	 accessableSquares.add(rightSquare);
         }
         Square upSquare = position.oneSquareUp();
         if(upSquare != null){
        	 accessableSquares.add(upSquare);
         }
         Square downSquare = position.oneSquareDown();
         if (downSquare != null){
        	 accessableSquares.add(downSquare);
         }
         Square leftUpSquare = position.oneSquareLeftUp();
         if (leftUpSquare != null){
        	 accessableSquares.add(leftUpSquare);
         }
         Square leftDownSquare = position.oneSquareLeftDown();
         if (leftDownSquare != null){
         	accessableSquares.add(leftDownSquare);
         }
         Square rightUpSquare = position.oneSquareRightUp();
         if (rightUpSquare != null){
        	 accessableSquares.add(rightUpSquare);
         }
         Square rightDownSquare = position.oneSquareRightDown();
         if(rightDownSquare != null){
        	 accessableSquares.add(rightDownSquare);
         }
         return   accessableSquares;
    }
	
	public ArrayList<Square> getAttackedSquares(){
		return getAccesableSquares();
	}
}