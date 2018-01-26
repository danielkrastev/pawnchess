package board;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceColour;

public class ChessBoard {

	private final Field[][] FIELDS = new Field[10][10];

	public ChessBoard() {

		for (int i = 0; i <= 9; i++) {
			FIELDS[0][i] = new Edge(0, i);
		}

		for (int i = 0; i <= 9; i++) {
			FIELDS[9][i] = new Edge(9, i);
		}

		for (int i = 1; i <= 8; i++) {
			FIELDS[i][0] = new Edge(i, 0);
		}
		for (int i = 1; i <= 8; i++) {
			FIELDS[i][9] = new Edge(i, 9);
		}

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				FIELDS[i][j] = new Field(i, j);
				FIELDS[i][j].setTaken(false);
			}
		}
	}

	// Copy constructor
	public ChessBoard(ChessBoard cb) {
		Field[][] newFields = new Field[10][10];
		Field[][] oldFields = cb.getFields();
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				newFields[i][j] = new Field(oldFields[i][j]);
				if (oldFields[i][j].isTaken()) {
					Piece oldPiece = oldFields[i][j].getPiece();
					PieceColour colour = oldPiece.getPieceColour();
					if (oldPiece instanceof King) {
						King newKing = new King((King) oldPiece);
						newKing.setCurrentField(newFields[i][j]);
						newFields[i][j].setPiece(newKing);
					}
					if (oldPiece instanceof Pawn) {
						Pawn newPawn = new Pawn((Pawn) oldPiece);
						newPawn.setCurrentField(newFields[i][j]);
						newFields[i][j].setPiece(newPawn);
					}
				}
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 9; i >= 0; i--) {
			for (int j = 0; j <= 9; j++) {
				sb.append(FIELDS[i][j].toString());
			}
			sb.append("\n");
		}
		sb.append("\n******************************\n");
		return sb.toString();
	}

	public boolean setPiece(Piece piece, Field target) {
		if (!target.isTaken()
				|| (target.isTaken() && !target.getPiece().getPieceColour().equals(piece.getPieceColour()))) {
			piece.setCurrentField(target);
			FIELDS[target.getRow()][target.getColumn()].setPiece(piece);
			FIELDS[target.getRow()][target.getColumn()].setTaken(true);
			return true;
		} else {
			return false;
		}
	}

	public Field getField(int row, int column) {
		return FIELDS[row][column];
	}

	public Field getField(Field square) {
		if (square.isValidField()) {
			return FIELDS[square.getRow()][square.getColumn()];
		}
		return null;
	}

	public Piece getPiece(Field square) {
		return getField(square).getPiece();
		// return square.getPiece();
	}

	public void freeField(Field sq) {
		sq.setTaken(false);
	}

	public Field[][] getFields() {
		return this.FIELDS;
	}

	public void setEmptyField(int row, int col) {
		Field field = this.getField(row, col);
		this.freeField(field);
	}

	public Field[] toArray() {
		Field[] result = new Field[64];
		int k = 0;

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				result[k++] = FIELDS[i][j];
			}
		}
		return result;
	}
}