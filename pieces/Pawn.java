package pieces;

import java.util.ArrayList;

import board.Field;
import exceptions.InvalidMoveException;

public class Pawn extends Piece {

	public Pawn(PieceColour colour, Field currentField) {

		this.currentField = currentField;
		this.pieceColour = colour;
	}

	public Pawn(PieceColour colour) {
		this.pieceColour = colour;
	}

	//Copy constructor
	public Pawn(Pawn old) {
		this.pieceColour = old.getPieceColour();
	}

	
	
	@Override
	public boolean canMove(Field targetField) throws InvalidMoveException {
		if (targetField.isValidField() && isMoveValid(targetField)) {
			return true;
		}
		return false;
	}

	private boolean isMoveValid(Field targetField) {
		Field currentPosition = this.getCurrentField();
		Piece.PieceColour colour = this.getPieceColour();

		switch (colour) {
		// white pawns
		case WHITE:

			if (targetField.isOneFieldUpFrom(currentPosition)
					&& !targetField.isTaken()) {
				return true;
			}
			if ((targetField.isOneFieldLeftUpFrom(currentPosition) || targetField
					.isOneFieldRightUpFrom(currentPosition))
					&& (targetField.isTaken() == true && targetField
							.getPiece().getPieceColour()
							.equals(PieceColour.BLACK))) {
				return true;
			}
			if (currentPosition.getRow() == 2
					&& targetField.isTwoFieldsUpFrom(currentPosition)
					&& !targetField.isTaken()) {
				return true;
			}
			break;

		// black pawns
		case BLACK:

			if (targetField.isOneFieldDownFrom(currentPosition)
					&& !targetField.isTaken()) {
				return true;
			}
			if ((targetField.isOneFieldLeftDownFrom(currentPosition) || targetField
					.isOneFieldRightDownFrom(currentPosition))
					&& (targetField.isTaken() && targetField.getPiece()
							.getPieceColour().equals(PieceColour.WHITE))) {
				return true;
			}
			if (currentPosition.getRow() == 7
					&& targetField.isTwoFieldsdDownFrom(currentPosition)
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
	public ArrayList<Field> getAccesableFields() {
		ArrayList<Field> accessableFields = new ArrayList<Field>();
		if (this.isWhite()) {
			if (currentField.getRow() == 2) {
				accessableFields.add(currentField.twoFieldsUp());
			}
			accessableFields.add(currentField.oneFieldUp());
			return accessableFields;
		} else {// pawn is black
			if (this.currentField.getRow() == 7) {
				accessableFields.add(currentField.twoFieldsDown());
			}
			accessableFields.add(currentField.oneFieldDown());
			return accessableFields;
		}
	}

	@Override
	public ArrayList<Field> getAttackedFields() {
		ArrayList<Field> attackedFields = new ArrayList<Field>();
		Field target;
		if (this.isWhite()) {
			target = currentField.oneFieldLeftUp();
			if (target != null) {
				attackedFields.add(target);
			}
			target = currentField.oneFieldRightUp();
			if (target != null) {
				attackedFields.add(target);
			}
		} else {// pawn is black
			target = currentField.oneFieldLeftDown();
			if (target != null) {
				attackedFields.add(target);
			}
			target = currentField.oneFieldRightDown();
			if (target != null) {
				attackedFields.add(target);
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