package pieces;

import java.util.ArrayList;

import board.Square;
import exceptions.InvalidMoveException;

public class Pawn extends Piece {

	public Pawn(PieceColour colour, Square position) {

		this.position = position;
		this.pieceColour = colour;
	}

	@Override
	public boolean canMove(Square targetSquare) throws InvalidMoveException {
		if (targetSquare.isValidSquare() && isMoveValid(targetSquare)) {
			return true;
		}
		return false;
	}

	private boolean isMoveValid(Square targetSquare) {
		Square currentPosition = this.getPosition();
		Piece.PieceColour colour = this.getPieceColour();

		switch (colour) {
		// white pawns
		case WHITE:

			if (targetSquare.isOneSquareUpFrom(currentPosition)
					&& !targetSquare.isTaken()) {
				return true;
			}
			if ((targetSquare.isOneSquareLeftUpFrom(currentPosition) || targetSquare
					.isOneSquareRightUpFrom(currentPosition))
					&& (targetSquare.isTaken() == true && targetSquare
							.getPiece().getPieceColour()
							.equals(PieceColour.BLACK))) {
				return true;
			}
			if (currentPosition.getRow() == 2
					&& targetSquare.isTwoSquaresUpFrom(currentPosition)
					&& !targetSquare.isTaken()) {
				return true;
			}
			break;

		// black pawns
		case BLACK:

			if (targetSquare.isOneSquareDownFrom(currentPosition)
					&& !targetSquare.isTaken()) {
				return true;
			}
			if ((targetSquare.isOneSquareLeftDownFrom(currentPosition) || targetSquare
					.isOneSquareRightDownFrom(currentPosition))
					&& (targetSquare.isTaken() && targetSquare.getPiece()
							.getPieceColour().equals(PieceColour.WHITE))) {
				return true;
			}
			if (currentPosition.getRow() == 7
					&& targetSquare.isTwoSquaresdDownFrom(currentPosition)
					&& !targetSquare.isTaken()) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public ArrayList<Square> getAccesableSquares() {
		ArrayList<Square> accessableSquares = new ArrayList<Square>();
		if (this.isWhite()) {
			if (this.getPosition().getRow() == 2) {
				accessableSquares.add(position.twoSquaresUp());
			}
			accessableSquares.add(position.oneSquareUp());
			return accessableSquares;
		} else {// pawn is black
			if (this.getPosition().getRow() == 7) {
				accessableSquares.add(position.twoSquaresDown());
			}
			accessableSquares.add(position.oneSquareDown());
			return accessableSquares;
		}
	}

	@Override
	public ArrayList<Square> getAttackedSquares() {
		ArrayList<Square> attackedSquares = new ArrayList<Square>();
		Square target;
		if (this.isWhite()) {
			target = position.oneSquareLeftUp();
			if (target != null) {
				attackedSquares.add(target);
			}
			target = position.oneSquareRightUp();
			if (target != null) {
				attackedSquares.add(target);
			}
		} else {// pawn is black
			target = position.oneSquareLeftDown();
			if (target != null) {
				attackedSquares.add(target);
			}
			target = position.oneSquareRightDown();
			if (target != null) {
				attackedSquares.add(target);
			}
		}
		return attackedSquares;
	}

	public String toString() {
		if (this.pieceColour.equals(PieceColour.WHITE)) {
			return new String(" P ");
		} else {
			return new String(" p ");
		}
	}
	/*
	 * public List<Square> getPossibleSquares(Game game) {
	 * 
	 * List<Square> accessableSquares = new ArrayList<Square>(); Square
	 * currentPosition = this.getPosition();
	 * 
	 * if (this.getPieceColour().equals(PieceColour.WHITE)) {
	 * 
	 * for (Square enemySquare : this.getAccesableSquares(game)){ if
	 * (enemySquare != null && enemySquare.isTaken() &&
	 * enemySquare.getPiece().getPieceColour().equals(PieceColour.BLACK)){
	 * accessableSquares.add(enemySquare); } } Square possibleSquare =
	 * game.getChessBoard().getSquare(this.position.oneSquareUp()); if
	 * (possibleSquare.isValidSquare() && !possibleSquare.isTaken()){
	 * accessableSquares.add(possibleSquare); } if (currentPosition.getRow() ==
	 * 2){ possibleSquare =
	 * game.getChessBoard().getSquare(this.position.twoSquaresUp()); if
	 * (!possibleSquare.oneSquareDown().isTaken() && !possibleSquare.isTaken()){
	 * accessableSquares.add(possibleSquare); } } } else {
	 * 
	 * for (Square enemySquare : this.getAccesableSquares(game)){ if
	 * (enemySquare != null && enemySquare.isTaken() &&
	 * enemySquare.getPiece().getPieceColour().equals(PieceColour.WHITE)){
	 * accessableSquares.add(enemySquare); } }
	 * 
	 * Square possibleSquare =
	 * game.getChessBoard().getSquare(this.position.oneSquareDown()); if
	 * (possibleSquare.isValidSquare() && !possibleSquare.isTaken()){
	 * accessableSquares.add(possibleSquare); } if (currentPosition.getRow() ==
	 * 7){ possibleSquare =
	 * game.getChessBoard().getSquare(this.position.twoSquaresDown()); if
	 * (!possibleSquare.oneSquareDown().isTaken() && !possibleSquare.isTaken()){
	 * accessableSquares.add(possibleSquare); } } } return accessableSquares; }
	 */
}