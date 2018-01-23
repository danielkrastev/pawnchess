package game;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceColour;

public class Rating {

	private static final int[][] whitePawnsRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 100, 100, 100, 100, 100, 100, 100, 100, 0 },
			{ 0, 80, 80, 80, 80, 80, 80, 80, 80, 0 },
			{ 0, 50, 50, 50, 50, 50, 50, 50, 50, 0 },
			{ 0, 4, 4, 4, 4, 4, 4, 4, 4, 0 },
			{ 0, 3, 3, 3, 3, 3, 3, 3, 3, 0 },
			{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	private static final int[][] blackPawnsRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
			{ 0, 3, 3, 3, 3, 3, 3, 3, 3, 0 }, 
			{ 0, 4, 4, 4, 4, 4, 4, 4, 4, 0 },
			{ 0, 50, 50, 50, 50, 50, 50, 50, 50, 0 },
			{ 0, 80, 80, 80, 80, 80, 80, 80, 80, 0 },
			{ 0, 100, 100, 100, 100, 100, 100, 100, 100, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

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

	public static int ratePosition(Position game) {
		int rating = 0;
		rating += calculateMaterialRating(game);
		rating += calculatePiecePositionRating(game);
		if (game.getCurrentPlayer() == PieceColour.WHITE){
			rating = 0 - rating;
		}
		return rating;
	}

	private static int calculateMaterialRating(Position game) {
		return game.getBlackPieces().size() - game.getWhitePieces().size();
		//return 1;
	}		

	private static int calculatePiecePositionRating(Position game) {

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
