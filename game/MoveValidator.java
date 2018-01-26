package game;

import java.util.List;


public class MoveValidator {

	public static boolean validate(Move move, Position position, boolean is_black) {
		List<Move> possibleMoves = position.getPossibleMoves(is_black);
		if (possibleMoves.contains(move)) {
			return true;
		}
		return false;
	}
}