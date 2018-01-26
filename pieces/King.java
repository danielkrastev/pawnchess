package pieces;

import java.util.ArrayList;

import board.Field;
import exceptions.InvalidMoveException;

public class King extends Piece {

	public King(PieceColour colour, Field position) {
		this.currentField = position;
		this.pieceColour = colour;
	}
	public King(PieceColour colour) {
		this.pieceColour = colour;
	}

	public King(King old) {
		this.pieceColour = old.getPieceColour();
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
		if ((targetField.isOneFieldUpFrom(currentField)
				|| targetField.isOneFieldRightFrom(currentField)
				|| targetField.isOneFieldUpFrom(currentField)
				|| targetField.isOneFieldDownFrom(currentField)
				|| targetField.isOneFieldLeftUpFrom(currentField)
				|| targetField.isOneFieldRightUpFrom(currentField)
				|| targetField.isOneFieldLeftDownFrom(currentField) || targetField
				.isOneFieldRightDownFrom(currentField))) {

			return true;
		}
		return false;
	}

	//all the fields surrounding the king
	public ArrayList<Field> getAccesableFields() {

     ArrayList <Field> accessableFields = new ArrayList<Field>();

     	 Field leftField = currentField.oneFieldLeft();
         if (leftField != null){
        	 accessableFields.add(leftField);
         }
         Field rightField = currentField.oneFieldRight();
         if (rightField !=null){
        	 accessableFields.add(rightField);
         }
         Field upField = currentField.oneFieldUp();
         if(upField != null){
        	 accessableFields.add(upField);
         }
         Field downField = currentField.oneFieldDown();
         if (downField != null){
        	 accessableFields.add(downField);
         }
         Field leftUpField = currentField.oneFieldLeftUp();
         if (leftUpField != null){
        	 accessableFields.add(leftUpField);
         }
         Field leftDownField = currentField.oneFieldLeftDown();
         if (leftDownField != null){
         	accessableFields.add(leftDownField);
         }
         Field rightUpField = currentField.oneFieldRightUp();
         if (rightUpField != null){
        	 accessableFields.add(rightUpField);
         }
         Field rightDownField = currentField.oneFieldRightDown();
         if(rightDownField != null){
        	 accessableFields.add(rightDownField);
         }
         return   accessableFields;
    }
	
	public ArrayList<Field> getAttackedFields(){
		return getAccesableFields();
	}
}