package pieces;

import java.util.ArrayList;

import board.Edge;
import board.Field;
import exceptions.InvalidMoveException;

public class King extends Piece {

	public King(PieceColour colour, int [] boardPosition) {
		this.boardPosition = boardPosition;
		this.pieceColour = colour;
	}
	
	//copy constructor
	public King(King old){
		this.boardPosition = new int [] {old.getBoardPosition()[0], old.getBoardPosition()[1]};
		this.pieceColour = old.getPieceColour();
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
	
	private boolean isMoveValid(Field currentField, Field targetField)
			throws InvalidMoveException {
		if ((targetField.isOneFieldUpFrom(currentField)
				|| targetField.isOneFieldRightFrom(currentField)
				|| targetField.isOneFieldUpFrom(currentField)
				|| targetField.isOneFieldDownFrom(currentField)
				|| targetField.isOneFieldLeftUpFrom(currentField)
				|| targetField.isOneFieldRightUpFrom(currentField)
				|| targetField.isOneFieldLeftDownFrom(currentField) 
				|| targetField.isOneFieldRightDownFrom(currentField))) {

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

	//all the fields surrounding the king, except the board edges :/
	public ArrayList<Field> getAccesableFields(Field boardPosition) {
     ArrayList <Field> accessableFields = new ArrayList<Field>();
     	 Field leftField =  boardPosition.oneFieldLeft();
         if (!(leftField instanceof Edge)){
        	 accessableFields.add(leftField);
         }
         Field rightField = boardPosition.oneFieldRight();
         if (!(rightField instanceof Edge)){
        	 accessableFields.add(rightField);
         }
         Field upField = boardPosition.oneFieldUp();
         if (!(upField instanceof Edge)){
        	 accessableFields.add(upField);
         }
         Field downField = boardPosition.oneFieldDown();
         if (!(downField instanceof Edge)){
        	 accessableFields.add(downField);
         }
         Field leftUpField = boardPosition.oneFieldLeftUp();
         if (!(leftUpField instanceof Edge)){
        	 accessableFields.add(leftUpField);
         }
         Field leftDownField = boardPosition.oneFieldLeftDown();
         if (!(leftDownField instanceof Edge)){
         	accessableFields.add(leftDownField);
         }
         Field rightUpField = boardPosition.oneFieldRightUp();
         if (!(rightUpField instanceof Edge)){
        	 accessableFields.add(rightUpField);
         }
         Field rightDownField = boardPosition.oneFieldRightDown();
         if(!(rightDownField instanceof Edge)){
        	 accessableFields.add(rightDownField);
         }
         return   accessableFields;
    }
	
	public ArrayList<Field> getAttackedFields(Field boardPosition){
		return getAccesableFields(boardPosition);
	}
}