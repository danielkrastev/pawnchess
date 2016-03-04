package game;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceColour;

public class Rating {

	private static final int[][] whitePawnsRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 9, 9, 9, 9, 9, 9, 9, 9, 0 },
			{ 0, 7, 7, 7, 7, 7, 7, 7, 7, 0 }, { 0, 5, 5, 5, 5, 5, 5, 5, 5, 0 },
			{ 0, 4, 4, 4, 4, 4, 4, 4, 4, 0 }, { 0, 3, 3, 3, 3, 3, 3, 3, 3, 0 },
			{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	private static final int[][] blackPawnsRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
			{ 0, 3, 3, 3, 3, 3, 3, 3, 3, 0 }, { 0, 4, 4, 4, 4, 4, 4, 4, 4, 0 },
			{ 0, 5, 5, 5, 5, 5, 5, 5, 5, 0 }, { 0, 7, 7, 7, 7, 7, 7, 7, 7, 0 },
			{ 0, 9, 9, 9, 9, 9, 9, 9, 9, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	private static final int[][] whiteKingRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
			{ 0, 2, 2, 4, 5, 5, 4, 3, 2, 0 }, { 0, 2, 2, 4, 5, 5, 4, 3, 2, 0 },
			{ 0, 4, 4, 4, 5, 5, 4, 4, 4, 0 }, { 0, 1, 3, 5, 5, 5, 5, 3, 1, 0 },
			{ 0, 0, 1, 2, 2, 2, 2, 1, 0, 0 }, { 0, 0, 0, 0, 2, 2, 2, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	private static final int[][] blackKingRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 2, 2, 2, 0, 0, 0 }, { 0, 0, 1, 2, 2, 2, 2, 1, 0, 0 },
			{ 0, 1, 3, 5, 5, 5, 5, 3, 1, 0 }, { 0, 4, 4, 4, 5, 5, 4, 4, 4, 0 },
			{ 0, 2, 2, 4, 5, 5, 4, 3, 2, 0 }, { 0, 2, 2, 4, 5, 5, 4, 3, 2, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	public static int positionRating(Game game) {

		int rating = 0;

		rating += rateMaterial(game);
		rating += ratePosition(game);
		return rating;
	}

	private static int rateMaterial(Game game) {

		int pawnPts = 0;

		for (Piece p : game.getBlackPieces()) {
			if (p instanceof Pawn) {
				if (game.getCurrentPlayer().equals(PieceColour.BLACK)) {
					pawnPts++;
				} else {
					pawnPts--;
				}
			}
		}
		for (Piece p : game.getWhitePieces()) {
			if (p instanceof Pawn) {
				if (game.getCurrentPlayer().equals(PieceColour.WHITE)) {
					pawnPts--;
				} else {
					pawnPts++;
				}
			}
		}

		return pawnPts;

	}

	private static int ratePosition(Game game) {

		int positionPts = 0;

		for (Piece p : game.getBlackPieces()) {
			if (p instanceof Pawn) {
				if (game.getCurrentPlayer().equals(PieceColour.BLACK)) {
					positionPts += blackPawnsRatingBoard[p.getPosition()
							.getColumn()][p.getPosition().getRow()];
				} else {
					positionPts -= blackPawnsRatingBoard[p.getPosition()
							.getColumn()][p.getPosition().getRow()];
				}
			} else if (p instanceof King) {
				if (game.getCurrentPlayer().equals(PieceColour.BLACK)) {
					positionPts += blackKingRatingBoard[p.getPosition()
							.getColumn()][p.getPosition().getRow()];
				} else {
					positionPts -= whiteKingRatingBoard[p.getPosition()
							.getColumn()][p.getPosition().getRow()];
				}
			}
		}
		for (Piece p : game.getWhitePieces()) {
			if (p instanceof Pawn) {
				if (game.getCurrentPlayer().equals(PieceColour.BLACK)) {
					positionPts -= blackPawnsRatingBoard[p.getPosition()
							.getColumn()][p.getPosition().getRow()];
				} else {
					positionPts += whitePawnsRatingBoard[p.getPosition()
							.getColumn()][p.getPosition().getRow()];
				}
			} else if (p instanceof King) {
				if (game.getCurrentPlayer().equals(PieceColour.BLACK)) {
					positionPts -= blackKingRatingBoard[p.getPosition()
							.getColumn()][p.getPosition().getRow()];
				} else {
					positionPts += whiteKingRatingBoard[p.getPosition()
							.getColumn()][p.getPosition().getRow()];
				}
			}
			
		}
		return positionPts;
	}

}
