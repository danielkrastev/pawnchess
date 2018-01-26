package board;

import pieces.King;
import pieces.Piece;

public class Field {

	protected int column;
	protected int row;
	private boolean taken;
	private Piece piece;
	private static final int squareSize = 64;

	public Field() {
	}
	
	/*Copy constructor*/
	public Field(Field sq){
		this.row = sq.getRow();
		this.column = sq.getColumn();
		this.taken = sq.isTaken();
		this.piece = null;
		
		if (this.isTaken()) {
			Piece oldPiece = sq.getPiece();
			if (oldPiece instanceof King) {
				King newKing = new King()
			}
		}
	}
	
	public Field(int row, int column) {
		this.row = row;
		this.column = column;
		this.taken = false;
		this.piece = null;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public boolean isTaken() {
		return this.taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public boolean isWhite() {
		if (this.isValidField()) {
			if (column % 2 == 0) {
				if (row % 2 != 0) {
					return true;
				} else {
					return false;
				}
			} else {
				if (row % 2 == 0) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public boolean isBlack() {
		return isValidField() && !isWhite();
	}

	public boolean isValidField() {
		if (column >= 1 && column <= 8 && row >= 1 && row <= 8) {
			return true;
		}
		return false;
	}

	public String toString() {
		if (this.isTaken()) {
			return new String(this.getPiece().toString());
		} else {
			if (this.isValidField()) {
				if (this.isWhite()) {
					return "   ";
				} else {
					return " # ";
				}
			}
		}
		return null;
	}

	public boolean isOneFieldLeftFrom(Field pos) {
		if ((this.column == pos.column - 1) && (this.row == pos.row)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOneFieldRightFrom(Field pos) {
		if ((this.column == pos.column + 1) && (this.row == pos.row)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOneFieldUpFrom(Field pos) {
		if ((this.row == pos.row + 1) && (this.column == pos.column)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOneFieldDownFrom(Field pos) {
		if ((this.row == pos.row - 1) && (this.column == pos.column)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isOneFieldLeftUpFrom(Field square) {
		if ((this.column == square.column - 1) && (this.row == square.row + 1)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isOneFieldRightUpFrom(Field square) {
		if ((this.column == square.column + 1) && (this.row == square.row + 1)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isOneFieldLeftDownFrom(Field square) {
		if ((this.column == square.column - 1) && (this.row == square.row - 1)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isOneFieldRightDownFrom(Field square) {
		if ((this.column == square.column + 1) && (this.row == square.row - 1)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTwoFieldsUpFrom(Field pos) {
		if ((this.column == pos.column) && (this.row == pos.row + 2)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTwoFieldsdDownFrom(Field pos) {
		if ((this.column == pos.column) && (this.row == pos.row - 2)) {
			return true;
		} else {
			return false;
		}
	}

	public String getPositionOnBoard() {
		return new String(this.getColumn() + "," + this.getRow());
	}

	public Field oneFieldLeft() {
		Field sq = new Field(row, column - 1);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public Field twoFieldsUp() {
		Field sq = new Field(row + 2, column);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public Field twoFieldsDown() {
		Field sq = new Field(row - 2, column);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public Field oneFieldRight() {
		Field sq = new Field(row, column + 1);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public Field oneFieldUp() {
		Field sq = new Field(row + 1, column);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public Field oneFieldDown() {
		Field sq = new Field(row - 1, column);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public Field oneFieldLeftUp() {
		Field sq = new Field(row + 1, column - 1);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public Field oneFieldRightUp() {

		Field sq = new Field(row + 1, column + 1);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public Field oneFieldLeftDown() {
		Field sq = new Field(row - 1, column - 1);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public Field oneFieldRightDown() {
		Field sq = new Field(row - 1, column + 1);
		if (sq.isValidField()) {
			return sq;
		} else {
			return null;
		}
	}

	public boolean equals(Object o) {
		if (o instanceof Field) {
			Field square = (Field) o;

			if (this.getColumn() == square.getColumn() && this.getRow() == square.getRow()) {
				return true;
			}
		}
		return false;
	}

	public int getX() {
		return (this.column) * squareSize;
	}

	public int getY() {
		return (9 - this.row) * squareSize;
	}

	@Override
	public int hashCode() {
		return column + (row - 1) * 8;
	}

	public String printCoordinates() {
		return String.format("[%d %d]", this.row, this.column);
	}
}