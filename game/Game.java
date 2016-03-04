package game;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import exceptions.InvalidMoveException;
import exceptions.InvalidSquareException;
import exceptions.NotYourTurnException;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceColour;
import userInterface.UserInterface;
import board.ChessBoard;
import board.Square;

public class Game {

	private final ChessBoard CHESS_BOARD;

	private final King WHITE_KING = new King(PieceColour.WHITE);
	private final King BLACK_KING = new King(PieceColour.BLACK);

	private HashSet<Piece> whitePieces;
	private HashSet<Piece> blackPieces;
	private HashMap<Square, Integer> attackedSquaresFromWhite;
	private HashMap<Square, Integer> attackedSquaresFromBlack;
	private ArrayList<Move> listOfMoves;
	private PieceColour currentPlayer;

	private UserInterface.MouseMover mouseMover;
	static private final int DEPTH = 1;

	private MoveValidator moveValidator;

	public Game() {

		CHESS_BOARD = new ChessBoard();

		whitePieces = new HashSet<Piece>();
		blackPieces = new HashSet<Piece>();
		listOfMoves = new ArrayList<Move>();

		attackedSquaresFromWhite = new HashMap<Square, Integer>();
		attackedSquaresFromBlack = new HashMap<Square, Integer>();

		moveValidator = new MoveValidator(this);
		setPiecesForNewGame();
		currentPlayer = PieceColour.WHITE;
	}

	public void start() {

		updateWhitePieces();
		updateBlackPieces();
		updateAttackedSquaresFromWhite();
		updateAttackedSquaresFromBlack();

		while (true) {
			try {
				System.out.println(currentPlayer.toString() + " to move:");
				System.out.println(this);

				if (isCheckDeclared()) {
					System.out.println("Check!");
				}

				Move currentMove;
				if (currentPlayer.equals(PieceColour.WHITE)) {
					currentMove = getPlayersMove();
				} else {
					currentMove = alphaBetaPruning(DEPTH, 500, -500, null, 0);
					System.out.println("computer decides:" + currentMove);
				}

				boolean isMoveValid = moveValidator.validate(currentMove);

				if (isMoveValid == true) {
					makeMove(currentMove);
					updateWhitePieces();
					updateBlackPieces();
					updateAttackedSquaresFromWhite();
					updateAttackedSquaresFromBlack();

					/*
					 * if (isCheckDeclared()) { if
					 * (isCheckMateDeclaredOrPawnreachedEnd(currentPlayer,
					 * currentMove)) { System.out.println("End of game!");
					 * break; } }
					 */
					Thread.sleep(1000);
					changeTurn();
				}
			} catch (InvalidSquareException e) {
				e.printStackTrace();
			} catch (NotYourTurnException e) {
				e.printStackTrace();
			} catch (InvalidMoveException e) {
				System.out.println("you must escape from attack");
				revertBoard(listOfMoves.get(listOfMoves.size() - 1));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ChessBoard getChessBoard() {
		return CHESS_BOARD;
	}

	public HashMap<Square, Integer> getAttackedSquaresFromWhite() {
		return this.attackedSquaresFromWhite;
	}

	public HashMap<Square, Integer> getAttackedSquaresFromBlack() {
		return this.attackedSquaresFromBlack;
	}

	private boolean makeMove(Move move) throws NotYourTurnException,
			InvalidMoveException, InvalidSquareException {

		Square currentSquare = CHESS_BOARD.getSquare((move.getCurrentSquare()));

		Piece piece = currentSquare.getPiece();

		if (!piece.getPieceColour().equals(currentPlayer)) {
			System.out.println("not your turn");
			return false;
		}

		/*if (piece.canMove(move.getTargetSquare()) && isMoveValid(move, piece)) {

			if (isCheckDeclared() && !isMoveEscapingChess(move, currentPlayer)) {
				System.out.println("you must escape from attack");
				return false;
			}*/

			CHESS_BOARD.freeSquare(piece.getPosition());
			CHESS_BOARD.setPiece(piece, move.getTargetSquare());

			return true;

		/*} else {
			if (currentPlayer.equals(PieceColour.WHITE)) {
				System.out.println("invalid move");
			}
			return false;
		}*/
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


	private boolean isCheckDeclared() {

		if (attackedSquaresFromWhite.containsKey(BLACK_KING.getPosition())) {
			return true;
		}
		if (attackedSquaresFromBlack.containsKey(WHITE_KING.getPosition())) {
			return true;
		}
		return false;
	}

	private void updateAttackedSquaresFromWhite() {

		attackedSquaresFromWhite = new HashMap<Square, Integer>();
		ArrayList<Square> attackedSquares;
		int counter;
		for (Piece piece : whitePieces) {
			attackedSquares = piece.getAttackedSquares();
			for (Object sqr : attackedSquares) {
				if (sqr != null) {
					counter = 1;
					Square square = CHESS_BOARD.getSquare((Square) sqr);
					if (attackedSquaresFromWhite.containsKey(square)) {
						counter += attackedSquaresFromWhite.get(square);
					}
					attackedSquaresFromWhite.put(square, counter);
				}
			}
		}
	}

	private void updateAttackedSquaresFromBlack() {

		attackedSquaresFromBlack = new HashMap<Square, Integer>();
		ArrayList<Square> attackedSquares;
		int counter;
		for (Piece piece : blackPieces) {
			attackedSquares = piece.getAttackedSquares();
			for (Object sqr : attackedSquares) {
				if (sqr != null) {
					counter = 1;
					Square square = CHESS_BOARD.getSquare((Square) sqr);
					if (attackedSquaresFromBlack.containsKey(square)) {
						counter += attackedSquaresFromBlack.get(square);
					}
					attackedSquaresFromWhite.put(square, counter);
				}
			}
		}
	}

	public void setPiecesForNewGame() {

		CHESS_BOARD.setPiece(WHITE_KING, CHESS_BOARD.getSquare(5, 1));
		CHESS_BOARD.setPiece(BLACK_KING, CHESS_BOARD.getSquare(5, 8));

		Pawn p0 = new Pawn(PieceColour.WHITE, CHESS_BOARD.getSquare(1, 2));
		CHESS_BOARD.setPiece(p0, CHESS_BOARD.getSquare(1, 2));
		Pawn p1 = new Pawn(PieceColour.WHITE, CHESS_BOARD.getSquare(2, 2));
		CHESS_BOARD.setPiece(p1, CHESS_BOARD.getSquare(2, 2));
		Pawn p2 = new Pawn(PieceColour.WHITE, CHESS_BOARD.getSquare(3, 2));
		CHESS_BOARD.setPiece(p2, CHESS_BOARD.getSquare(3, 2));
		Pawn p3 = new Pawn(PieceColour.WHITE, CHESS_BOARD.getSquare(4, 2));
		CHESS_BOARD.setPiece(p3, CHESS_BOARD.getSquare(4, 2));
		Pawn p4 = new Pawn(PieceColour.WHITE, CHESS_BOARD.getSquare(5, 2));
		CHESS_BOARD.setPiece(p4, CHESS_BOARD.getSquare(5, 2));
		Pawn p5 = new Pawn(PieceColour.WHITE, CHESS_BOARD.getSquare(6, 2));
		CHESS_BOARD.setPiece(p5, CHESS_BOARD.getSquare(6, 2));
		Pawn p6 = new Pawn(PieceColour.WHITE, CHESS_BOARD.getSquare(7, 2));
		CHESS_BOARD.setPiece(p6, CHESS_BOARD.getSquare(7, 2));
		Pawn p7 = new Pawn(PieceColour.WHITE, CHESS_BOARD.getSquare(8, 2));
		CHESS_BOARD.setPiece(p7, CHESS_BOARD.getSquare(8, 2));

		Pawn p_0 = new Pawn(PieceColour.BLACK, CHESS_BOARD.getSquare(1, 7));
		CHESS_BOARD.setPiece(p_0, CHESS_BOARD.getSquare(1, 7));
		Pawn p_1 = new Pawn(PieceColour.BLACK, CHESS_BOARD.getSquare(2, 7));
		CHESS_BOARD.setPiece(p_1, CHESS_BOARD.getSquare(2, 7));
		Pawn p_2 = new Pawn(PieceColour.BLACK, CHESS_BOARD.getSquare(3, 7));
		CHESS_BOARD.setPiece(p_2, CHESS_BOARD.getSquare(3, 7));
		Pawn p_3 = new Pawn(PieceColour.BLACK, CHESS_BOARD.getSquare(4, 7));
		CHESS_BOARD.setPiece(p_3, CHESS_BOARD.getSquare(4, 7));
		Pawn p_4 = new Pawn(PieceColour.BLACK, CHESS_BOARD.getSquare(5, 7));
		CHESS_BOARD.setPiece(p_4, CHESS_BOARD.getSquare(5, 7));
		Pawn p_5 = new Pawn(PieceColour.BLACK, CHESS_BOARD.getSquare(6, 7));
		CHESS_BOARD.setPiece(p_5, CHESS_BOARD.getSquare(6, 7));
		Pawn p_6 = new Pawn(PieceColour.BLACK, CHESS_BOARD.getSquare(7, 7));
		CHESS_BOARD.setPiece(p_6, CHESS_BOARD.getSquare(7, 7));
		Pawn p_7 = new Pawn(PieceColour.BLACK, CHESS_BOARD.getSquare(8, 7));
		CHESS_BOARD.setPiece(p_7, CHESS_BOARD.getSquare(8, 7));
	}

	public String toString() {
		return CHESS_BOARD.toString();
	}

	private void changeTurn() {

		if (currentPlayer.equals(PieceColour.WHITE)) {
			currentPlayer = PieceColour.BLACK;
		} else {
			currentPlayer = PieceColour.WHITE;
		}
	}

	public Move getPlayersMove() {
		Move playersMove = new Move();
		Square current;
		Square target;

		System.out.println("waiting for your move:");
		System.out.print("current square: ");

		synchronized (this) {
			try {
				wait();
				int column = mouseMover.getClickedColumn();
				int row = mouseMover.getClickedRow();

				Square temp = CHESS_BOARD.getSquare(column, row);
				if (!temp.isTaken()
						|| temp.getPiece().isBlack()) {
					throw new InvalidMoveException("Piece not clicked on!");
				}

				current = CHESS_BOARD.getSquare(column, row);
				System.out.println(current.printCoordinates());
				playersMove.setCurrentSquare(current);
				System.out.print("target square:");

				wait();
				column = mouseMover.getClickedColumn();
				row = mouseMover.getClickedRow();

				target = CHESS_BOARD.getSquare(column, row);
				System.out.println(target.printCoordinates());
				playersMove.setTargetSquare(target);

				return playersMove;

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvalidMoveException e) {
				return getPlayersMove();
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
	private void updateWhitePieces() {

		whitePieces = new HashSet<Piece>();
		Square[] chessBouardArray = CHESS_BOARD.toArray();
		for (Square square : chessBouardArray) {
			if (square.isTaken()) {
				Piece piece = square.getPiece();
				if (piece.getPieceColour().equals(PieceColour.WHITE)) {
					whitePieces.add(piece);
				}
			}
		}
	}

	private void updateBlackPieces() {

		blackPieces = new HashSet<Piece>();
		Square[] chessBouardArray = CHESS_BOARD.toArray();
		for (Square square : chessBouardArray) {
			if (square.isTaken()) {
				Piece piece = square.getPiece();
				if (piece.getPieceColour().equals(PieceColour.BLACK)) {
					blackPieces.add(piece);
				}
			}
		}
	}

	private void revertBoard(Move mv) {

		CHESS_BOARD.setPiece(mv.getTargetSquare().getPiece(),
				mv.getCurrentSquare());
		mv.getTargetSquare().setTaken(false);

	}

	private Move alphaBetaPruning(int depth, int beta, int alpha, Move move,
			int player) throws NotYourTurnException, InvalidMoveException,
			InvalidSquareException {

		ArrayList<Move> possibleMoves = getPossibleMoves();
		if (depth == 0 || possibleMoves.size() == 0) {
			move.multiplyRatingBy(player); // -1 or 1

			return move;
		}
		player = 1 - player;

		for (Move possibleMove : possibleMoves) {
			makeMove(possibleMove);
			changeTurn();
			Move returnMove = alphaBetaPruning(depth - 1, beta, alpha,
					possibleMove, player);
			int value = returnMove.getRating(this);
			changeTurn();
			undoMove(returnMove);

			if (player == 0) {
				if (value <= beta) {
					beta = value;
					if (depth == DEPTH) {
						move = returnMove;
					}
				}
			} else {
				if (value > alpha) {
					alpha = value;
					if (depth == DEPTH) {
						move = returnMove;
					}
				}
			}
			if (alpha >= beta) {
				if (player == 0) {
					move.setRating(beta);
					return move;
				} else {
					move.setRating(alpha);
					return move;
				}
			}
		}
		if (player == 0) {
			move.setRating(beta);
			return move;
		} else {
			move.setRating(alpha);
			return move;
		}
		/*
		 * System.out.println ("computer decides:" +
		 * move.getCurrentSquare().getColumn() + " " + +
		 * move.getCurrentSquare().getRow() + ";" +
		 * move.getTargetSquare().getColumn() + " " + +
		 * move.getTargetSquare().getRow());
		 */

	}

	ArrayList<Move> getPossibleMoves() {

		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ArrayList<Square> accessableSquares;

		if (currentPlayer.equals(PieceColour.WHITE)) {
			for (Piece piece : whitePieces) {
				accessableSquares = moveValidator.getPossibleSquares(piece);
				for (Square target : accessableSquares) {
					Move move = new Move();
					move.setCurrentSquare(piece.getPosition());
					move.setTargetSquare(target);
					possibleMoves.add(move);
				}
			}
		} else { // black to move
			for (Piece piece : blackPieces) {
				accessableSquares = moveValidator.getPossibleSquares(piece);
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
}