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
		List<Piece> pieces =  getPieces();
		List<Piece> whitePieces =  new ArrayList<Piece>();
		for (Piece p: pieces){
			if (p.isBlack()) {
				whitePieces.add(p);
			}
		}
		return whitePieces;
	}

	public List<Piece> getBlackPieces() {
		List<Piece> pieces =  getPieces();
		List<Piece> blackPieces =  new ArrayList<Piece>();
		for (Piece p: pieces){
			if (p.isBlack()){
				blackPieces.add(p);
			}
		}
		return blackPieces;
	}

	public List<Piece> getPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		for (Square sq : chessBoard.toArray()) {
			if (sq.isTaken()) {
				Piece piece = sq.getPiece();
				pieces.add(piece);
			}
		}
		return pieces;
	}
	
	private void changeTurn() {
		if (currentPlayer.equals(PieceColour.WHITE)) {
			currentPlayer = PieceColour.BLACK;
		} else {
			currentPlayer = PieceColour.WHITE;
		}
	}
	
	public void makeMove(Move currentMove) {
		Square current_field = currentMove.getCurrentSquare();
		Square target_field = currentMove.getTargetSquare();
		Piece p = chessBoard.getPiece(current_field);
		chessBoard.freeSquare(currentMove.getCurrentSquare());
		chessBoard.setPiece(p, target_field);
		changeTurn();
	}
	
	public ArrayList<Square> getPossibleFields(Piece piece) {
		if (piece instanceof King) {
			King king = (King) piece;
			return getPossibleSquaresForKing(king);
		} else {
			Pawn pawn = (Pawn) piece;
			return getPossibleSquaresForPawn(pawn);
		}
	}
	
	ArrayList<Move> getPossibleMoves(boolean is_black) {
		boolean is_white = ! is_black;
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ArrayList<Square> accessableSquares;

		if (is_white) {
			for (Piece piece : getWhitePieces()) {
				accessableSquares = getPossibleFields(piece);
				for (Square target : accessableSquares) {
					Move move = new Move();
					move.setCurrentSquare(piece.getPosition());
					move.setTargetSquare(target);
					possibleMoves.add(move);
				}
			}
		} else { // black to move
			for (Piece piece : getBlackPieces()) {
				accessableSquares = getPossibleFields(piece);
				for (Square target : accessableSquares) {
					Move move = new Move();
					move.setCurrentSquare(piece.getPosition());
			    	move.setTargetSquare(target);
					possibleMoves.add(move);
				}
			}
		}
		return possibleMoves;
	}
}
