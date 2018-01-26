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

}