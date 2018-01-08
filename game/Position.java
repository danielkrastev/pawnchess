package game;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.Square;
import pieces.Pawn;
import pieces.Piece;
import pieces.King;
import pieces.Piece.PieceColour;

public class Position {

	/*
	 * FEN Forsyth–Edwards Notation
	 * 
	 * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
	 *
	 **/

	private ChessBoard chessBoard;
	
	public ChessBoard getChessBoard() {
		return chessBoard;
	}

	private PieceColour currentPlayer;

	public PieceColour getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(PieceColour currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Position(String fen) throws Exception {
		
		this.chessBoard  = new ChessBoard();
		this.currentPlayer = PieceColour.WHITE;
		// Invalid fen can occur
		
		String[] rows = fen.split("/");
		if (rows.length != 8) {
			throw new Exception("Invalid FEN format");
		}

		for (int i = 0; i < 8; i++) { // fen notation starts from the 8 rank
			int empty_fields = 0;
			int row = 8 - i;
			int col = 0;
			for (char c : rows[i].toCharArray()) {
				if (c >= '0' && c <= '9') {
					empty_fields = c - '0'; // some ascii arithmetics here
					for (int j = 0; j < empty_fields; j ++) {
						col ++;
						chessBoard.setEmptyField(row, col); // fen notation starts from the 8 rank
					}
				} else {
					col ++;
					switch (c) {
						case 'P':
							Pawn white_pawn = new Pawn(PieceColour.WHITE, chessBoard.getSquare(row, col));
							chessBoard.setPiece(white_pawn, chessBoard.getSquare(row, col));
							break;
						case 'p':
							Pawn black_pawn = new Pawn(PieceColour.BLACK, chessBoard.getSquare(row, col));
							chessBoard.setPiece(black_pawn, chessBoard.getSquare(row, col));
							break;
						case 'K':
							King white_king = new King(PieceColour.WHITE, chessBoard.getSquare(row, col));
							chessBoard.setPiece(white_king, chessBoard.getSquare(row, col));
							break;
						case 'k':
							King black_king = new King(PieceColour.BLACK, chessBoard.getSquare(row, col));
							chessBoard.setPiece(black_king, chessBoard.getSquare(row, col));
							break;
					}
				}
			}
		}
	}

	public String toFan() {
	/*TBD*/
			return "";
	}

	public String toString() {
		return this.chessBoard.toString();
	}
	
	public List<Piece> getWhitePieces() {
		List<Piece> whitePieces = new ArrayList<Piece>();
		for (Square sq : chessBoard.toArray()) {
			if (sq.isTaken()) {
				Piece piece = sq.getPiece();
				if (piece.isWhite()) {
					whitePieces.add(piece);
				}
			}
		}
		return whitePieces;
	}

	public void makeMove(Move currentMove) {
		
		
		
		
	}
}
