package game;

import java.util.ArrayList;
import java.util.HashMap;
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
			if (p.isWhite()) {
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
	
	public Position makeMove(Move currentMove) {
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
	
	private ArrayList<Square> getPossibleSquaresForKing(King king) {
		ArrayList<Square> possibleSquares = new ArrayList<Square>();
		if (king.isWhite()) {
			for (Object sq : king.getAccesableSquares().toArray()) {
				Square targetSquare = chessBoard.getSquare((Square) sq);
				if ( ! targetSquare.isTaken() &&
					 ! getAttackedSquaresFromBlack().contains(sq)) {
					possibleSquares.add(targetSquare);
				} else if (targetSquare.isTaken() && targetSquare.getPiece().isBlack()) {

					Pawn blackPawn = (Pawn) targetSquare.getPiece();// only
																	// possible
																	// that this
																	// piece is
																	// pawn
					if ( ! isProtected(blackPawn)) {
						possibleSquares.add(targetSquare);
					}
				}
			}
		} else {// king is black
			for (Object sq : king.getAccesableSquares().toArray()) {
				Square targetSquare = chessBoard.getSquare((Square) sq);
				if (! targetSquare.isTaken()) {
					possibleSquares.add(targetSquare);
				} else // square is taken
				if (targetSquare.getPiece().isWhite()) {
					Pawn whitePawn = (Pawn) targetSquare.getPiece();// only
																	// possible
																	// that this
																	// piece is
																	// pawn
					if (!isProtected(whitePawn)) {
						possibleSquares.add(targetSquare);
					}
				}
			}
		}
		return possibleSquares;
	}
	
	public List<Square> getAttackedSquaresFromBlack() {
		List<Square> attackedSquaresFromBlack = new ArrayList<Square>();
		for(Piece p : getBlackPieces()) {
			List<Square> attackedSquares = p.getAttackedSquares();
			for (Square as : attackedSquares) {
				if (! (as instanceof board.Edge)) {
					attackedSquaresFromBlack.add(as);
				}
			}
		}
		return attackedSquaresFromBlack;
	}
	
	private boolean isProtected(Pawn pawn) {
		Square pos = pawn.getPosition();
		if (pawn.isWhite()) {
			
		}else { //pawn is black
			Square upLeft = pos.oneSquareLeftUp();
			Piece p = upLeft.getPiece();
			if (p instanceof Pawn && p.isBlack()) {
				return true;
			}
			Square upRight = pos.oneSquareRightUp();
			p = upRight.getPiece();
			if (p instanceof Pawn && p.isBlack()) {
				return true;
			}
			
			King blackKing = getBlackKing();
			if (blackKing.getAccesableSquares().contains(pos)){
				return true;
			}
		}
		return false;
	} 
	
	private King getBlackKing() {
		for (Piece p : this.getBlackPieces()) {
			if (p instanceof King) {
				return (King) p;
			}
		}
		return null;
	}
	
	public List<Square> getAttackedSquaresFromWhite() {
		List<Square> attackedSquaresFromWhite = new ArrayList<Square>();
		for(Piece p : getBlackPieces()) {
			List<Square> attackedSquares = p.getAttackedSquares();
			for (Square as : attackedSquares) {
				if (! (as instanceof board.Edge)) {
					attackedSquaresFromWhite.add(as);
				}
			}
		}
		return attackedSquaresFromWhite;
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
	
	private  ArrayList<Square> getPossibleSquaresForPawn(Pawn pawn) {
		ArrayList<Square> possibleSquares = new ArrayList<Square>();
		for (Object sq : pawn.getAccesableSquares()) {
			Square targetSquare = chessBoard.getSquare((Square) sq);
			if (!targetSquare.isTaken()) {
				possibleSquares.add(targetSquare);
			}
		}
		if (pawn.isWhite()) {
			for (Object sq : pawn.getAttackedSquares().toArray()) {
				Square targetSquare = chessBoard.getSquare((Square) sq);
				if (targetSquare.isTaken() && targetSquare.getPiece().isBlack()
						&& (targetSquare.getPiece() instanceof Pawn)) {
					possibleSquares.add(targetSquare);
				}
			}
		} else {// pawn is black
			for (Object sq : pawn.getAttackedSquares().toArray()) {
				Square targetSquare = chessBoard.getSquare((Square) sq);
				if (targetSquare.isTaken() && targetSquare.getPiece().isWhite()
						&& (targetSquare.getPiece() instanceof Pawn)) {
					possibleSquares.add(targetSquare);
				}
			}
		}
		return possibleSquares;
	}
	
}
