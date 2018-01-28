package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import board.ChessBoard;
import board.Field;
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
	private PieceColour currentPlayer;

	
	public ChessBoard getChessBoard() {
		return chessBoard;
	}
	
	public PieceColour getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(PieceColour currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	
	public Position(ChessBoard chessBoard, PieceColour currentPlayer) {
	    this.chessBoard = chessBoard;
	    this.currentPlayer = currentPlayer;
	}
	
	//copy constructor
	public Position(Position old) {
		this.chessBoard = new ChessBoard(old.getChessBoard());
	    this.currentPlayer = old.getCurrentPlayer();
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
					int [] boardPos =  {row, col};
					int po = 1 + + + + +2;
					switch (c) {
					
						case 'P':
							Pawn white_pawn = new Pawn(PieceColour.WHITE, boardPos);
							chessBoard.setPiece(white_pawn, chessBoard.getField(row, col));
							break;
						case 'p':
							Pawn black_pawn = new Pawn(PieceColour.BLACK, boardPos);
							chessBoard.setPiece(black_pawn, chessBoard.getField(row, col));
							break;
						case 'K':
							King white_king = new King(PieceColour.WHITE, boardPos);
							chessBoard.setPiece(white_king, chessBoard.getField(row, col));
							break;
						case 'k':
							King black_king = new King(PieceColour.BLACK, boardPos);
							chessBoard.setPiece(black_king, chessBoard.getField(row, col));
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
		for (Field sq : chessBoard.toArray()) {
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
		Field current_field = currentMove.getCurrentField();
		Field target_field = currentMove.getTargetField();
		Piece p = chessBoard.getPiece(current_field);
		chessBoard.freeField(currentMove.getCurrentField());
		chessBoard.setPiece(p, target_field);
		changeTurn();
	}
	
	public Position _makeMove(Move currentMove) {
		Position newPos = new Position(this);
		Field newCurrent = newPos.chessBoard.getField(currentMove.getCurrentField().getRow(),
														currentMove.getCurrentField().getColumn());
		Field newTarget = newPos.chessBoard.getField(currentMove.getTargetField().getRow(),
				currentMove.getTargetField().getColumn());
		Move newMove = new Move(newCurrent, newTarget);
		newPos.makeMove(newMove);
		return newPos;
	}
	
	public ArrayList<Field> getPossibleFields(Piece piece) {
		if (piece instanceof King) {
			King king = (King) piece;
			return getPossibleFieldsForKing(king);
		} else {
			Pawn pawn = (Pawn) piece;
			return getPossibleFieldsForPawn(pawn);
		}
	}
	
	private ArrayList<Field> getPossibleFieldsForKing(King king) {
		/*
		 * Moves out the attacked fields from the opposite pieces
		 * 
		 * */
		ArrayList<Field> possibleFields = new ArrayList<Field>();
		Field currentField = chessBoard.getField(king.getRow(), king.getColumn());
		if (king.isWhite()) {
		    for (Field field : king.getAccesableFields(currentField)) {
			    Field targetField = chessBoard.getField(field);
				if ( ! targetField.isTaken()) {
					if (! getAttackedFieldsFromBlack().contains(targetField)){
						possibleFields.add(targetField);						
					}
				} else if (targetField.isTaken() && targetField.getPiece().isBlack()) {
					Pawn blackPawn = (Pawn) targetField.getPiece(); // only
																	// possible
																	// that this
																	// piece is
																	// pawn
					if (!isProtected(blackPawn)) {
						possibleFields.add(targetField);
					}
				}
			}
		} else {// king is black
			List<Field> attackedFieldsFromWhite = getAttackedFieldsFromWhite();
			for (Field field : king.getAccesableFields(currentField)) {
				Field targetField = this.chessBoard.getField(field);
				if ( ! targetField.isTaken()) {
					if (! attackedFieldsFromWhite.contains(targetField)) {
						possibleFields.add(targetField);
					}
				} else // square is taken
				if (targetField.getPiece().isWhite()) {
					Pawn whitePawn = (Pawn) targetField.getPiece();// only
																	// possible
																	// that this
																	// piece is
																	// pawn
					if (!isProtected(whitePawn)) {
						possibleFields.add(targetField);
					}
				}
			}
		}
		return possibleFields;
	}
	
	public List<Field> getAttackedFieldsFromBlack() {
		List<Field> attackedFieldsFromBlack = new ArrayList<Field>();
		for(Piece p : getBlackPieces()) {
			Field current = chessBoard.getField(p.getBoardPosition()[0],
												p.getBoardPosition()[1]);
			List<Field> attackedFields = p.getAttackedFields(current);
			for (Field as : attackedFields) {
				if (! (as instanceof board.Edge)) {
					attackedFieldsFromBlack.add(as);
				}
			}
		}
		return attackedFieldsFromBlack;
	}
	
	private boolean isProtected(Pawn pawn) {
		Field pawnPosition = chessBoard.getField(pawn.getRow(), pawn.getColumn());
		if (pawn.isWhite()) {
			Field leftDown = pawnPosition.oneFieldLeftDown();
			Piece p = leftDown.getPiece();
			if (p instanceof Pawn && p.isWhite()) {
				return true;
			}
			Field rightDown = pawnPosition.oneFieldRightDown();
			p = rightDown.getPiece();
			if (p instanceof Pawn && p.isBlack()) {
				return true;
			}
			
			King whiteKing = getWhiteKing();
			Field kingPosition = chessBoard.getField(whiteKing.getRow(), whiteKing.getColumn());
			if (whiteKing.getAccesableFields(kingPosition).contains(pawnPosition)){
				return true;
			}
		}else { //pawn is black
			Field upLeft = pawnPosition.oneFieldLeftUp();
			Piece p = upLeft.getPiece();
			if (p instanceof Pawn && p.isBlack()) {
				return true;
			}
			Field upRight = pawnPosition.oneFieldRightUp();
			p = upRight.getPiece();
			if (p instanceof Pawn && p.isBlack()) {
				return true;
			}
			
			King blackKing = getBlackKing();
			Field kingPosition = chessBoard.getField(blackKing.getRow(), blackKing.getColumn());
			if (blackKing.getAccesableFields(kingPosition).contains(pawnPosition)){
				return true;
			}
		}
		return false;
	} 
	
	private King getWhiteKing() {
		for (Piece p : this.getWhitePieces()) {
			if (p instanceof King) {
				return (King) p;
			}
		}
		return null;
	}
	
	private King getBlackKing() {
		for (Piece p : this.getBlackPieces()) {
			if (p instanceof King) {
				return (King) p;
			}
		}
		return null;
	}
	
	public List<Field> getAttackedFieldsFromWhite() {
		List<Field> attackedFieldsFromWhite = new ArrayList<Field>();
		for(Piece p : getWhitePieces()) {
			List<Field> attackedFields = p.getAttackedFields(chessBoard.getField(p.getRow(), p.getColumn()));
			for (Field as : attackedFields) {
				if (! (as instanceof board.Edge)) {
					attackedFieldsFromWhite.add(as);
				}
			}
		}
		return attackedFieldsFromWhite;
	}
	
	ArrayList<Move> getPossibleMoves(boolean is_black) {
		boolean is_white = ! is_black;
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ArrayList<Field> accessableFields;
		if (is_white) {
			for (Piece piece : getWhitePieces()) {
				accessableFields = getPossibleFields(piece);
				for (Field target : accessableFields) {
					Move move = new Move();
					move.setCurrentField(chessBoard.getField(piece.getRow(),
															 piece.getColumn()));
					move.setTargetField(target);
					possibleMoves.add(move);
				}
			}
		} else { // black to move
			for (Piece piece : getBlackPieces()) {
				accessableFields = getPossibleFields(piece);
				for (Field target : accessableFields) {
					Move move = new Move();
					move.setCurrentField(chessBoard.getField(piece.getBoardPosition()[0],
							 			 piece.getBoardPosition()[1]));
			    	move.setTargetField(target);
					possibleMoves.add(move);
				}
			}
		}
		return possibleMoves;
	}
	
	public boolean isCheckDeclared() {
		King blackKing = this.getBlackKing();
		Field blackKingPosition = chessBoard.getField(blackKing.getRow(), blackKing.getColumn());
		if (this.getAttackedFieldsFromWhite().contains(blackKingPosition)) {
			return true;
		}
		King whiteKing = this.getWhiteKing();
		Field whiteKingPosition = chessBoard.getField(whiteKing.getRow(), whiteKing.getColumn());
		if (this.getAttackedFieldsFromBlack().contains(whiteKingPosition)) {
			return true;
		}
		return false;
	}
	
	private  ArrayList<Field> getPossibleFieldsForPawn(Pawn pawn) {
		ArrayList<Field> possibleFields = new ArrayList<Field>();
		Field currentField = chessBoard.getField(pawn.getBoardPosition()[0],
 												 pawn.getBoardPosition()[1]);
		for (Field sq : pawn.getAccesableFields(currentField)) {
			Field targetField = chessBoard.getField((Field) sq);
			if (!targetField.isTaken()) {
				possibleFields.add(targetField);
			}
		}
		if (pawn.isWhite()) {
			for (Object sq : pawn.getAttackedFields(currentField).toArray()) {
				Field targetField = chessBoard.getField((Field) sq);
				if (targetField.isTaken() && targetField.getPiece().isBlack()
						&& (targetField.getPiece() instanceof Pawn)) {
					possibleFields.add(targetField);
				}
			}
		} else {// pawn is black
			for (Object sq : pawn.getAttackedFields(currentField).toArray()) {
				Field targetField = chessBoard.getField((Field) sq);
				if (targetField.isTaken() && targetField.getPiece().isWhite()
						&& (targetField.getPiece() instanceof Pawn)) {
					possibleFields.add(targetField);
				}
			}
		}
		return possibleFields;
	}
}
