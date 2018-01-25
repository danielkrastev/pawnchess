package pieces;

import java.util.ArrayList;

import board.Field;
import exceptions.InvalidMoveException;

public class King extends Piece {

	public King(PieceColour colour, Field position) {
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
	public boolean canMove(Field targetField) throws InvalidMoveException {
		if (targetField.isValidField() && isMoveValid(targetField) && isFieldAccessible(targetField)) {
			return true;
		}
		return false;
	}

	private boolean isFieldAccessible(Field targetField) {
		if (!targetField.isTaken()){
			return true;
		}
        
		PieceColour colour = this.getPieceColour();
		
		switch (colour){
		case WHITE:
			if (targetField.getPiece().getPieceColour().equals(PieceColour.BLACK)){
				return true;
			}else {
				return false;
			}
		case BLACK:
			if (targetField.getPiece().getPieceColour().equals(PieceColour.WHITE)){
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	private boolean isMoveValid(Field targetField)
			throws InvalidMoveException {

		Field currentPosition = this.getPosition();		
		if ((targetField.isOneFieldUpFrom(currentPosition)
				|| targetField.isOneFieldRightFrom(currentPosition)
				|| targetField.isOneFieldUpFrom(currentPosition)
				|| targetField.isOneFieldDownFrom(currentPosition)
				|| targetField.isOneFieldLeftUpFrom(currentPosition)
				|| targetField.isOneFieldRightUpFrom(currentPosition)
				|| targetField.isOneFieldLeftDownFrom(currentPosition) || targetField
				.isOneFieldRightDownFrom(currentPosition))) {

			return true;
		}
		return false;
	}

	//all the fields surrounding the king
	public ArrayList<Field> getAccesableFields() {

     ArrayList <Field> accessableFields = new ArrayList<Field>();

     	 Field leftField = position.oneFieldLeft();
         if (leftField != null){
        	 accessableFields.add(leftField);
         }
         Field rightField = position.oneFieldRight();
         if (rightField !=null){
        	 accessableFields.add(rightField);
         }
         Field upField = position.oneFieldUp();
         if(upField != null){
        	 accessableFields.add(upField);
         }
         Field downField = position.oneFieldDown();
         if (downField != null){
        	 accessableFields.add(downField);
         }
         Field leftUpField = position.oneFieldLeftUp();
         if (leftUpField != null){
        	 accessableFields.add(leftUpField);
         }
         Field leftDownField = position.oneFieldLeftDown();
         if (leftDownField != null){
         	accessableFields.add(leftDownField);
         }
         Field rightUpField = position.oneFieldRightUp();
         if (rightUpField != null){
        	 accessableFields.add(rightUpField);
         }
         Field rightDownField = position.oneFieldRightDown();
         if(rightDownField != null){
        	 accessableFields.add(rightDownField);
         }
         return   accessableFields;
    }
	
	public ArrayList<Field> getAttackedFields(){
		return getAccesableFields();
	}
}