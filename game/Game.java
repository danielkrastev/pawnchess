package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import board.ChessBoard;
import board.Field;
import exceptions.InvalidMoveException;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceColour;
import userInterface.UserInterface;
import java.util.logging.*;

public class Game {
	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

	private HashSet<Piece> whitePieces;
	private HashSet<Piece> blackPieces;
	private ArrayList<Move> listOfMoves;

	private UserInterface gui;
	private UserInterface.MouseMover mouseMover;
	static private final int DEPTH = 1;
	private MoveValidator moveValidator;
	private Position currentPosition;
	private Random randomGenerator;
	
	public Game() throws Exception {

		currentPosition = new Position("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3");

		whitePieces = new HashSet<Piece>();
		blackPieces = new HashSet<Piece>();
		listOfMoves = new ArrayList<Move>();

		randomGenerator = new Random();
	}

	public void start() throws Exception {

		// updateWhitePieces();
		// updateBlackPieces();
		// updateAttackedFieldsFromWhite();
		// updateAttackedFieldsFromBlack();
		boolean gameFinished = false;
		
		while (! gameFinished) {
			try {
				LOGGER.log(Level.INFO, currentPosition.getCurrentPlayer() + " to move:\nCurrent position:\n"
						+ currentPosition.toString());
				// if (isCheckDeclared()) {
				// LOGGER.log(Level.INFO, "Check!");
				// }
				boolean is_black = true;
				if (currentPosition.getCurrentPlayer().equals(PieceColour.WHITE))
					is_black = false;
				boolean is_white = !is_black;

				Move currentMove = null;
				if (is_white) {
					currentMove = getPlayersMove();
				} else {
					currentMove = getEngineMove(currentPosition);
					LOGGER.log(Level.INFO, "The program decides " + currentMove);
				}
				boolean isMoveValid = validate(currentMove, is_black);

				if (isMoveValid == true) {
					LOGGER.log(Level.INFO, "THE MOVE IS VALID");
					currentPosition.makeMove(currentMove);
					gui.repaint();
					
					//check if pawn reached 8th or 1st rank.
					Piece piece = currentMove.getPiece();
					if(piece instanceof Pawn){
						if(currentMove.getTargetField().getRow() == 1 || 
     						currentMove.getTargetField().getRow() == 8	) {
							LOGGER.log(Level.INFO, "Game ended!");
							gameFinished = true;
							if(piece.isWhite()) {
								LOGGER.log(Level.INFO, "White won!");
							}
							else {
								LOGGER.log(Level.INFO, "Black won");
							}
						}
					}
					if (is_white) {
						Thread.sleep(2000);
					}
				} else {
					LOGGER.log(Level.INFO, "THE MOVE IS NOT VALID");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Move getEngineMove(Position currentPosition) {
		MoveRating mv = miniMax(DEPTH, currentPosition, true);
		return mv.getMove();
		//List<Move> possibleMoves = currentPosition.getPossibleMoves(true);
		//int index = randomGenerator.nextInt(possibleMoves.size());
		//return possibleMoves.get(index);
	}

	public boolean validate(Move move, boolean is_black) {
		List<Move> possibleMoves = currentPosition.getPossibleMoves(is_black);
		if (possibleMoves.contains(move)){
			return true;
		}
		return false;
	}


	private boolean isMoveEscapingChess(Move move, PieceColour turn) {
		Field curentField = move.getCurrentField();
		Field targetField = move.getTargetField();

		if (turn.equals(PieceColour.WHITE)) {
			if (curentField.getPiece().equals(WHITE_KING)) {
				if (!attackedFieldsFromBlack.containsKey(targetField)) {
					return true;
				}
			} else {
				if (targetField.isTaken()
						&& (targetField.isOneFieldLeftUpFrom(WHITE_KING
								.getPosition()) || targetField
								.isOneFieldRightUpFrom(WHITE_KING.getPosition()))) {
					return true;
				}
			}
		} else {
			if (curentField.getPiece().equals(BLACK_KING)) {
				if (!attackedFieldsFromWhite.containsKey(targetField)) {
					return true;
				}
			} else {
				if (targetField.isTaken()
						&& (targetField.isOneFieldLeftDownFrom(BLACK_KING
								.getPosition()) || targetField
								.isOneFieldRightDownFrom(BLACK_KING
										.getPosition()))) {
					return true;
				}
			}
		}
		return false;
	}

	
	public ChessBoard getChessBoard() {
		return this.getCurrentPosition().getChessBoard();
	}

	private Position getCurrentPosition() {
		return this.currentPosition;
	}

	private boolean isCheckDeclared() {

		if (attackedFieldsFromWhite.containsKey(BLACK_KING.getPosition())) {
			return true;
		}
		if (attackedFieldsFromBlack.containsKey(WHITE_KING.getPosition())) {
			return true;
		}
		return false;
	}

	
	public String toString() {
		return currentPosition.getChessBoard().toString();
	}

	public Move getPlayersMove() {
		Move playersMove = new Move();
		Field current;
		Field target;

		LOGGER.log(Level.INFO, "Waiting for player's move:\nCurrent square: ");

		synchronized (this) {
			try {
				this.wait();
				int column = mouseMover.getClickedColumn();
				int row = mouseMover.getClickedRow();

				Field temp = this.getChessBoard().getField(row, column);
				if ( !temp.isTaken()
					|| temp.getPiece().isBlack()) {
						throw new InvalidMoveException("Piece not clicked on!");
				}

				current = this.getChessBoard().getField(row, column);
				LOGGER.log(Level.INFO, current.printCoordinates());
				playersMove.setCurrentField(current);
				
				LOGGER.log(Level.INFO, "target square:");

				this.wait();
				column = mouseMover.getClickedColumn();
				row = mouseMover.getClickedRow();

				target = this.getChessBoard().getField(row, column);
				LOGGER.log(Level.INFO, target.printCoordinates());
				playersMove.setTargetField(target);

				return playersMove;

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvalidMoveException e) {
				getPlayersMove();
			}
		}
		return null;
	}

	/*
	 * private boolean isCheckMateDeclaredOrPawnreachedEnd(PieceColour colour,
	 * Move move) {
	 * 
	 * if (colour.equals(PieceColour.WHITE)) { if
	 * (move.getTargetField().getRow() == 8) return true; } else { if
	 * (move.getTargetField().getRow() == 1) return true; }
	 * 
	 * Field attacker = move.getTargetField(); boolean kingCannotMove = true;
	 * 
	 * if (colour.equals(PieceColour.BLACK)) { Object[]
	 * accessableFieldsFromWhiteKing = whiteKing
	 * .getPossibleFields(this).toArray();
	 * 
	 * for (Object sq : accessableFieldsFromWhiteKing) { Field square =
	 * (Field) sq; if (!attackedFieldsFromBlack.containsKey(square)) {
	 * kingCannotMove = false; } }
	 * 
	 * if (kingCannotMove && attackedFieldsFromWhite.get(attacker) == 1) {
	 * return true; } } else { Object[] accessableFieldsFromBlackKing =
	 * blackKing .getPossibleFields(this).toArray();
	 * 
	 * for (Object sq : accessableFieldsFromBlackKing) { Field square =
	 * (Field) sq; if (!attackedFieldsFromWhite.containsKey(square)) {
	 * kingCannotMove = false; } }
	 * 
	 * if (kingCannotMove && attackedFieldsFromBlack.get(attacker) == 1) {
	 * return true; } } return false; }
	 */
	
	static MoveRating miniMax(int depth, Position position, boolean  is_black){
		ArrayList<Move> possibleMoves = position.getPossibleMoves(is_black);
		if (depth <= 1) {
			int bestRating = 0;
			Move bestMove = null;
			for (Move possibleMove : possibleMoves) {
				Position possiblePosition = position._makeMove(possibleMove);
				int rating = Rating.ratePosition(possiblePosition);

				if (rating > bestRating) {
					bestRating = rating;
					bestMove = possibleMove;
				}
			}
			return new MoveRating(bestMove, bestRating);
		}
		if (is_black) { // maximizing player
			int best_move_rating = Integer.MIN_VALUE;
			MoveRating bestMove = new MoveRating(null, best_move_rating);
			for (Move possibleMove : possibleMoves) {
				Position possible_position = position._makeMove(possibleMove);
				MoveRating currentMoveRating = miniMax(depth-1, possible_position, false);
				if (currentMoveRating.getRating() > bestMove.getRating()) {
					bestMove = currentMoveRating;
				}
				return bestMove;
			}
		}else {
			/*//*/
			int best_move_rating = Integer.MAX_VALUE;
			MoveRating bestMove = new MoveRating(null, best_move_rating);
			for (Move possibleMove : possibleMoves) {
				Position possible_position = position._makeMove(possibleMove);
				MoveRating currentMoveRating = miniMax(depth-1, position, false);
				if (currentMoveRating.getRating() < bestMove.getRating()) {
					bestMove = currentMoveRating;
				}
				return bestMove;
			}
		}
		return null;
	}

	public HashSet<Piece> getWhitePieces() {
		return whitePieces;
	}

	public HashSet<Piece> getBlackPieces() {
		return blackPieces;
	}

	public void setMouseMover(UserInterface.MouseMover mover) {
		this.mouseMover = mover;
	}

	public void setUI(UserInterface gui) {
		this.gui=gui;
	}
}
