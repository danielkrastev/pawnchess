package board;

import pieces.Piece;

public class ChessBoard {

	private final Square[][] SQUARES = new Square[10][10];

	public ChessBoard() {

		for (int i = 0; i <= 9; i++) {
			SQUARES[0][i] = new Edge(0,i);
		}

		for (int i = 0; i <= 9; i++) {
			SQUARES[9][i] = new Edge(9,i);
		}

		for (int i = 1; i <= 8; i++) {
			SQUARES[i][0] = new Edge(i,0);
		}
		for (int i = 1; i <= 8; i++) {
			SQUARES[i][9] = new Edge(i,9);
		}
		
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {

				SQUARES[i][j] = new Square(i, j);
				SQUARES[i][j].setTaken(false);
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 9; i >= 0; i--) {
			for (int j = 0; j <= 9 ; j++) {
				sb.append(SQUARES[j][i].toString());
			}
			sb.append("\n");
		}
		sb.append("\n******************************\n");
		return sb.toString();
	}

	public boolean setPiece(Piece piece, Square target) {
		if (!target.isTaken()
			|| (target.isTaken()
			&& !target.getPiece().getPieceColour()
						.equals(piece.getPieceColour()))) {
				piece.setPosition(target);
				SQUARES[target.getColumn()][target.getRow()].setPiece(piece);
				SQUARES[target.getColumn()][target.getRow()].setTaken(true);
	
				return true;
		} else {
			return false;
		}
	}
	public Square getSquare(int column, int row) {
		return SQUARES[column][row];
	}

	public Square getSquare(Square square) {
		if (square.isValidSquare()) {
			return SQUARES[square.getColumn()][square.getRow()];
		}
		return null;
	}

	public Piece getPiece(Square square) {
		return square.getPiece();
	}

	public void freeSquare(Square sq) {
		sq.setTaken(false);
	}

	public Square[][] getChessBoard() {
		return this.SQUARES;
	}

	public Square[] toArray() {
		Square[] result = new Square[64];
		int k = 0;
		
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				result[k++] = SQUARES[j][i];
			}
		}
		return result;
	}
}