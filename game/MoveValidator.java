package game;

import java.util.ArrayList;
import java.util.List;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceColour;
import board.ChessBoard;
import board.Square;

public class MoveValidator {

	public static boolean validate(Move move, Position position, boolean is_black) {
		List<Move> possibleMoves = position.getPossibleMoves(is_black);
		if (possibleMoves.contains(move)) {
			return true;
		}
		return false;
	}

	private static ArrayList<Square> getPossibleSquaresForKing(King king) {
		ArrayList<Square> possibleSquares = new ArrayList<Square>();
		if (king.isWhite()) {
			for (Object sq : king.getAccesableSquares().toArray()) {
				Square targetSquare = CHESS_BOARD.getSquare((Square) sq);
				if ( ! targetSquare.isTaken() &&
					 ! game.getAttackedSquaresFromBlack().containsKey(sq)) {
					possibleSquares.add(targetSquare);
				} else if (targetSquare.getPiece().isBlack()) {

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
				Square targetSquare = CHESS_BOARD.getSquare((Square) sq);
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

	private boolean isProtected(Pawn pawn) {
		Square pawnPosition = pawn.getPosition();
		if (pawn.isWhite()) {
			return (game.getAttackedSquaresFromWhite()
					.containsKey(pawnPosition));
		} else {
			return (game.getAttackedSquaresFromBlack()
					.containsKey(pawnPosition));
		}
	}

	private static ArrayList<Square> getPossibleSquaresForPawn(Pawn pawn) {
		ArrayList<Square> possibleSquares = new ArrayList<Square>();
		for (Object sq : pawn.getAccesableSquares()) {
			Square targetSquare = CHESS_BOARD.getSquare((Square) sq);
			if (!targetSquare.isTaken()) {
				possibleSquares.add(targetSquare);
			}
		}
		if (pawn.isWhite()) {
			for (Object sq : pawn.getAttackedSquares().toArray()) {
				Square targetSquare = CHESS_BOARD.getSquare((Square) sq);
				if (targetSquare.isTaken() && targetSquare.getPiece().isBlack()
						&& (targetSquare.getPiece() instanceof Pawn)) {
					possibleSquares.add(targetSquare);
				}
			}
		} else {// pawn is black
			for (Object sq : pawn.getAttackedSquares().toArray()) {
				Square targetSquare = CHESS_BOARD.getSquare((Square) sq);
				if (targetSquare.isTaken() && targetSquare.getPiece().isWhite()
						&& (targetSquare.getPiece() instanceof Pawn)) {
					possibleSquares.add(targetSquare);
				}
			}
		}
		return possibleSquares;
	}
}