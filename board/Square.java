package board;

import pieces.Piece;

public class Square {

	protected int column;
	protected int row;
	private boolean taken;
	private Piece piece;
	private static final int squareSize = 64;

	public Square() {
	}

	public Square(int column, int row) {

		this.column = column;
		this.row = row;
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
		if (this.isValidSquare()) {
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
		return isValidSquare() && !isWhite();
	}

	public boolean isValidSquare() {
		if (column >= 1 && column <= 8 && row >= 1 && row <= 8) {
			return true;
		}
		return false;
	}

	public String toString() {
		if (this.isTaken()) {
			return new String(this.getPiece().toString());
		} else {
			if (this.isValidSquare()) {
				if (this.isWhite()) {
					return "   ";
				} else {
					return " # ";
				}
			}
		}
		return null;
	}

	public boolean isOneSquareLeftFrom(Square pos) {
		if ((this.column == pos.column - 1) && (this.row == pos.row)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOneSquareRightFrom(Square pos) {
		if ((this.column == pos.column + 1) && (this.row == pos.row)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOneSquareUpFrom(Square pos) {
		if ((this.row == pos.row + 1) && (this.column == pos.column)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOneSquareDownFrom(Square pos) {
		if ((this.row == pos.row - 1) && (this.column == pos.column)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isOneSquareLeftUpFrom(Square square) {
		if ((this.column == square.column - 1) && (this.row == square.row + 1)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isOneSquareRightUpFrom(Square square) {
		if ((this.column == square.column + 1) && (this.row == square.row + 1)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isOneSquareLeftDownFrom(Square square) {
		if ((this.column == square.column - 1) && (this.row == square.row - 1)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isOneSquareRightDownFrom(Square square) {
		if ((this.column == square.column + 1) && (this.row == square.row - 1)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTwoSquaresUpFrom(Square pos) {
		if ((this.column == pos.column) && (this.row == pos.row + 2)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTwoSquaresdDownFrom(Square pos) {
		if ((this.column == pos.column) && (this.row == pos.row - 2)) {
			return true;
		} else {
			return false;
		}
	}

	public String getPositionOnBoard() {
		return new String(this.getColumn() + "," + this.getRow());
	}

	public Square oneSquareLeft() {
		Square sq = new Square(column - 1, row);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}
	}

	public Square twoSquaresUp() {
		Square sq = new Square(column, row + 2);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}
	}

	public Square twoSquaresDown() {
		Square sq = new Square(column, row - 2);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}
	}

	public Square oneSquareRight() {
		Square sq = new Square(column + 1, row);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}
	}

	public Square oneSquareUp() {

		Square sq = new Square(column, row + 1);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}
	}

	public Square oneSquareDown() {
		Square sq = new Square(column, row - 1);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}
	}

	public Square oneSquareLeftUp() {
		Square sq = new Square(column - 1, row + 1);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}
	}

	public Square oneSquareRightUp() {

		Square sq = new Square(column + 1, row + 1);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}
	}

	public Square oneSquareLeftDown() {
		Square sq = new Square(column - 1, row - 1);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}

	}

	public Square oneSquareRightDown() {
		Square sq = new Square(column + 1, row - 1);
		if (sq.isValidSquare()) {
			return sq;
		} else {
			return null;
		}
	}

	public boolean equals(Object o) {
		if (o instanceof Square) {
			Square square = (Square) o;

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