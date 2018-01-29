package pieces;

import java.util.ArrayList;

import board.Edge;
import board.Field;
import exceptions.InvalidMoveException;

public class Pawn extends Piece {

	public Pawn(PieceColour colour, int [] position) {
		this.boardPosition = position;
		this.pieceColour = colour;
	}

	//copy constructor
	public Pawn(Pawn old){
		this.boardPosition = new int [] {old.getBoardPosition()[0], old.getBoardPosition()[1]};
		this.pieceColour = old.getPieceColour();
	}
	
	@Override
	public boolean canMove(Field targetField) throws InvalidMoveException {
		if (targetField.isValidField() && isMoveValid(targetField)) {
			return true;
		}
		return false;
	}
	
	private boolean isMoveValid(Field currentField, Field targetField) {
		Piece.PieceColour colour = this.getPieceColour();
		switch (colour) {
		// white pawns
		case WHITE:
			if (targetField.isOneFieldUpFrom(currentField)
					&& !targetField.isTaken()) {
				return true;
			}
			if ((targetField.isOneFieldLeftUpFrom(currentField) || targetField
					.isOneFieldRightUpFrom(currentField))
					&& (targetField.isTaken() == true && targetField
							.getPiece().getPieceColour()
							.equals(PieceColour.BLACK))) {
				return true;
			}
			if (currentField.getRow() == 2
					&& targetField.isTwoFieldsUpFrom(currentField)
					&& !targetField.isTaken()) {
				return true;
			}
			break;

		// black pawns
		case BLACK:
			if (targetField.isOneFieldDownFrom(currentField)
					&& !targetField.isTaken()) {
				return true;
			}
			if ((targetField.isOneFieldLeftDownFrom(currentField) || targetField
					.isOneFieldRightDownFrom(currentField))
					&& (targetField.isTaken() && targetField.getPiece()
							.getPieceColour().equals(PieceColour.WHITE))) {
				return true;
			}
			if (currentField.getRow() == 7
					&& targetField.isTwoFieldsdDownFrom(currentField)
					&& !targetField.isTaken()) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public ArrayList<Field> getAccesableFields(Field boardPosition) {
		ArrayList<Field> accessableFields = new ArrayList<Field>();
		if (this.isWhite()) {
			if (boardPosition.getRow() == 2) {
				accessableFields.add(boardPosition.twoFieldsUp());
			}
			accessableFields.add(boardPosition.oneFieldUp());
			return accessableFields;
		} else {// pawn is black
			if (boardPosition.getRow() == 7) {
				accessableFields.add(boardPosition.twoFieldsDown());
			}
			accessableFields.add(boardPosition.oneFieldDown());
			return accessableFields;
		}
	}

	@Override
	public ArrayList<Field> getAttackedFields(Field boardPosition) {
		ArrayList<Field> attackedFields = new ArrayList<Field>();
		Field target;
		if (this.isWhite()) {
			Field leftUp = boardPosition.oneFieldLeftUp();
			if (! (leftUp instanceof Edge)) {
				attackedFields.add(leftUp);
			}
			Field rightUp = boardPosition.oneFieldRightUp();
			if (! (rightUp instanceof Edge)) {
				attackedFields.add(rightUp);
			}
		} else {// pawn is black
			Field leftDown = boardPosition.oneFieldLeftDown();
			if (! (leftDown instanceof Edge)) {
				attackedFields.add(leftDown);
			}
        	Field rightDown = boardPosition.oneFieldRightDown();
        	if (! (leftDown instanceof Edge)) {
				attackedFields.add(rightDown);
			}
		}
		return attackedFields;
	}

	public String toString() {
		if (this.pieceColour.equals(PieceColour.WHITE)) {
			return new String(" P ");
		} else {
			return new String(" p ");
		}
	}
	/*
	 * public List<Field> getPossibleFields(Game game) {
	 * 
	 * List<Field> accessableFields = new ArrayList<Field>(); Field
	 * currentPosition = this.getPosition();
	 * 
	 * if (this.getPieceColour().equals(PieceColour.WHITE)) {
	 * 
	 * for (Field enemyField : this.getAccesableFields(game)){ if
	 * (enemyField != null && enemyField.isTaken() &&
	 * enemyField.getPiece().getPieceColour().equals(PieceColour.BLACK)){
	 * accessableFields.add(enemyField); } } Field possibleField =
	 * game.getChessBoard().getField(this.position.oneFieldUp()); if
	 * (possibleField.isValidField() && !possibleField.isTaken()){
	 * accessableFields.add(possibleField); } if (currentPosition.getRow() ==
	 * 2){ possibleField =
	 * game.getChessBoard().getField(this.position.twoFieldsUp()); if
	 * (!possibleField.oneFieldDown().isTaken() && !possibleField.isTaken()){
	 * accessableFields.add(possibleField); } } } else {
	 * 
	 * for (Field enemyField : this.getAccesableFields(game)){ if
	 * (enemyField != null && enemyField.isTaken() &&
	 * enemyField.getPiece().getPieceColour().equals(PieceColour.WHITE)){
	 * accessableFields.add(enemyField); } }
	 * 
	 * Field possibleField =
	 * game.getChessBoard().getField(this.position.oneFieldDown()); if
	 * (possibleField.isValidField() && !possibleField.isTaken()){
	 * accessableFields.add(possibleField); } if (currentPosition.getRow() ==
	 * 7){ possibleField =
	 * game.getChessBoard().getField(this.position.twoFieldsDown()); if
	 * (!possibleField.oneFieldDown().isTaken() && !possibleField.isTaken()){
	 * accessableFields.add(possibleField); } } } return accessableFields; }
	 */
}