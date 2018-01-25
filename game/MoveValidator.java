package game;

import java.util.ArrayList;
import java.util.List;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceColour;
import board.ChessBoard;
import board.Field;

public class MoveValidator {

	public static boolean validate(Move move, Position position, boolean is_black) {
		List<Move> possibleMoves = position.getPossibleMoves(is_black);
		if (possibleMoves.contains(move)) {
			return true;
		}
		return false;
	}

	private static ArrayList<Field> getPossibleFieldsForKing(King king) {
		ArrayList<Field> possibleFields = new ArrayList<Field>();
		if (king.isWhite()) {
			for (Object sq : king.getAccesableFields().toArray()) {
				Field targetField = CHESS_BOARD.getField((Field) sq);
				if ( ! targetField.isTaken() &&
					 ! game.getAttackedFieldsFromBlack().containsKey(sq)) {
					possibleFields.add(targetField);
				} else if (targetField.getPiece().isBlack()) {

					Pawn blackPawn = (Pawn) targetField.getPiece();// only
																	// possible
																	// that this
																	// piece is
																	// pawn
					if ( ! isProtected(blackPawn)) {
						possibleFields.add(targetField);
					}
				}
			}
		} else {// king is black
			for (Object sq : king.getAccesableFields().toArray()) {
				Field targetField = CHESS_BOARD.getField((Field) sq);
				if (! targetField.isTaken()) {
					possibleFields.add(targetField);
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

	private boolean isProtected(Pawn pawn) {
		Field pawnPosition = pawn.getPosition();
		if (pawn.isWhite()) {
			return (game.getAttackedFieldsFromWhite()
					.containsKey(pawnPosition));
		} else {
			return (game.getAttackedFieldsFromBlack()
					.containsKey(pawnPosition));
		}
	}

	private static ArrayList<Field> getPossibleFieldsForPawn(Pawn pawn) {
		ArrayList<Field> possibleFields = new ArrayList<Field>();
		for (Object sq : pawn.getAccesableFields()) {
			Field targetField = CHESS_BOARD.getField((Field) sq);
			if (!targetField.isTaken()) {
				possibleFields.add(targetField);
			}
		}
		if (pawn.isWhite()) {
			for (Object sq : pawn.getAttackedFields().toArray()) {
				Field targetField = CHESS_BOARD.getField((Field) sq);
				if (targetField.isTaken() && targetField.getPiece().isBlack()
						&& (targetField.getPiece() instanceof Pawn)) {
					possibleFields.add(targetField);
				}
			}
		} else {// pawn is black
			for (Object sq : pawn.getAttackedFields().toArray()) {
				Field targetField = CHESS_BOARD.getField((Field) sq);
				if (targetField.isTaken() && targetField.getPiece().isWhite()
						&& (targetField.getPiece() instanceof Pawn)) {
					possibleFields.add(targetField);
				}
			}
		}
		return possibleFields;
	}
}