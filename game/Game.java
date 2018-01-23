package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import board.ChessBoard;
import board.Square;
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
	private HashMap<Square, Integer> attackedSquaresFromWhite;
	private HashMap<Square, Integer> attackedSquaresFromBlack;
	private ArrayList<Move> listOfMoves;

	private UserInterface gui;
	private UserInterface.MouseMover mouseMover;
	static private final int DEPTH = 2;
	private MoveValidator moveValidator;
	private Position currentPosition;
	private Random randomGenerator;
	
	public Game() throws Exception {

		currentPosition = new Position("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3");

		whitePieces = new HashSet<Piece>();
		blackPieces = new HashSet<Piece>();
		listOfMoves = new ArrayList<Move>();

		attackedSquaresFromWhite = new HashMap<Square, Integer>();
		attackedSquaresFromBlack = new HashMap<Square, Integer>();
		
		randomGenerator = new Random();
	}

	public void start() throws Exception {

		// updateWhitePieces();
		// updateBlackPieces();
		// updateAttackedSquaresFromWhite();
		// updateAttackedSquaresFromBlack();
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
						if(currentMove.getTargetSquare().getRow() == 1 || 
     						currentMove.getTargetSquare().getRow() == 8	) {
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
		//return new Move(new int [] {7,5}, new int [] {5,5}); 
	}

	public boolean validate(Move move, boolean is_black) {
		List<Move> possibleMoves = currentPosition.getPossibleMoves(is_black);
		if (possibleMoves.contains(move)){
			return true;
		}
		return false;
	}

	public HashMap<Square, Integer> getAttackedSquaresFromWhite() {
		return this.attackedSquaresFromWhite;
	}

	public HashMap<Square, Integer> getAttackedSquaresFromBlack() {
		return this.attackedSquaresFromBlack;
	}

	private boolean isMoveEscapingChess(Move move, PieceColour turn) {

		Square curentSquare = move.getCurrentSquare();
		Square targetSquare = move.getTargetSquare();

		if (turn.equals(PieceColour.WHITE)) {
			if (curentSquare.getPiece().equals(WHITE_KING)) {
				if (!attackedSquaresFromBlack.containsKey(targetSquare)) {
					return true;
				}
			} else {
				if (targetSquare.isTaken()
						&& (targetSquare.isOneSquareLeftUpFrom(WHITE_KING
								.getPosition()) || targetSquare
								.isOneSquareRightUpFrom(WHITE_KING.getPosition()))) {
					return true;
				}
			}
		} else {
			if (curentSquare.getPiece().equals(BLACK_KING)) {
				if (!attackedSquaresFromWhite.containsKey(targetSquare)) {
					return true;
				}
			} else {
				if (targetSquare.isTaken()
						&& (targetSquare.isOneSquareLeftDownFrom(BLACK_KING
								.getPosition()) || targetSquare
								.isOneSquareRightDownFrom(BLACK_KING
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

		if (attackedSquaresFromWhite.containsKey(BLACK_KING.getPosition())) {
			return true;
		}
		if (attackedSquaresFromBlack.containsKey(WHITE_KING.getPosition())) {
			return true;
		}
		return false;
	}

	
	public String toString() {
		return currentPosition.getChessBoard().toString();
	}

	public Move getPlayersMove() {
		Move playersMove = new Move();
		Square current;
		Square target;

		LOGGER.log(Level.INFO, "Waiting for player's move:\nCurrent square: ");

		synchronized (this) {
			try {
				this.wait();
				int column = mouseMover.getClickedColumn();
				int row = mouseMover.getClickedRow();

				Square temp = this.getChessBoard().getSquare(row, column);
				if ( !temp.isTaken()
					|| temp.getPiece().isBlack()) {
						throw new InvalidMoveException("Piece not clicked on!");
				}

				current = this.getChessBoard().getSquare(row, column);
				LOGGER.log(Level.INFO, current.printCoordinates());
				playersMove.setCurrentSquare(current);
				
				LOGGER.log(Level.INFO, "target square:");

				this.wait();
				column = mouseMover.getClickedColumn();
				row = mouseMover.getClickedRow();

				target = this.getChessBoard().getSquare(row, column);
				LOGGER.log(Level.INFO, target.printCoordinates());
				playersMove.setTargetSquare(target);

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
	 * (move.getTargetSquare().getRow() == 8) return true; } else { if
	 * (move.getTargetSquare().getRow() == 1) return true; }
	 * 
	 * Square attacker = move.getTargetSquare(); boolean kingCannotMove = true;
	 * 
	 * if (colour.equals(PieceColour.BLACK)) { Object[]
	 * accessableSquaresFromWhiteKing = whiteKing
	 * .getPossibleSquares(this).toArray();
	 * 
	 * for (Object sq : accessableSquaresFromWhiteKing) { Square square =
	 * (Square) sq; if (!attackedSquaresFromBlack.containsKey(square)) {
	 * kingCannotMove = false; } }
	 * 
	 * if (kingCannotMove && attackedSquaresFromWhite.get(attacker) == 1) {
	 * return true; } } else { Object[] accessableSquaresFromBlackKing =
	 * blackKing .getPossibleSquares(this).toArray();
	 * 
	 * for (Object sq : accessableSquaresFromBlackKing) { Square square =
	 * (Square) sq; if (!attackedSquaresFromWhite.containsKey(square)) {
	 * kingCannotMove = false; } }
	 * 
	 * if (kingCannotMove && attackedSquaresFromBlack.get(attacker) == 1) {
	 * return true; } } return false; }
	 */
	
	static MoveRating miniMax(int depth, Position position, boolean  is_black){
		ArrayList<Move> possibleMoves = position.getPossibleMoves(is_black);
		if (depth <= 1) {
			int bestRating = 0;
			Move bestMove = null;
			for (Move possibleMove : possibleMoves) {
				Position possiblePosition = position._makeMove(possibleMove);
				int rating = Rating.ratePosition(position);

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
				MoveRating currentMoveRating = miniMax(depth-1, position, false);
				if (currentMoveRating.getRating() > bestMove.getRating()) {
					bestMove = currentMoveRating;
				}
				return bestMove;
			}
		}else {
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

	private void undoMove(Move move) {
		CHESS_BOARD.setPiece(move.getPiece(), move.getCurrentSquare());
		CHESS_BOARD.freeSquare(move.getTargetSquare());
	}
	
	public HashSet<Piece> getWhitePieces() {
		return whitePieces;
	}

	public HashSet<Piece> getBlackPieces() {
		return blackPieces;
	}

	public PieceColour getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(PieceColour currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setMouseMover(UserInterface.MouseMover mover) {
		this.mouseMover = mover;
	}

	public void setUI(UserInterface gui) {
		this.gui=gui;
	}
}
