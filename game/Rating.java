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

	public static int ratePosition(Position position) {
		int rating = 0;
		//rating += calculateMaterialRating(position);
		rating += calculatePiecePositionRating(position);
		if (position.getCurrentPlayer() == PieceColour.BLACK){
			rating = 0 - rating;
		}
		return rating;
	}

	private static int calculateMaterialRating(Position game) {
		return game.getBlackPieces().size() - game.getWhitePieces().size();
		//return 1;
	}		

	private static int calculatePiecePositionRating(Position position) {
		int positionPts = 0;
		for (Piece p : position.getBlackPieces()) {
			if (p instanceof Pawn) {
				if (position.getCurrentPlayer().equals(PieceColour.WHITE)) {
					positionPts += blackPawnsRatingBoard[p.getRow()][p.getColumn()];
				} else {
					positionPts -= blackPawnsRatingBoard[p.getRow()][p.getColumn()];
				}
			} else if (p instanceof King) {
				if (position.getCurrentPlayer().equals(PieceColour.WHITE)) {
					positionPts += blackKingRatingBoard[p.getRow()][p.getColumn()];
				} else {
					positionPts -= whiteKingRatingBoard[p.getRow()][p.getColumn()];
				}
			}
		}
		
		for (Piece p : position.getWhitePieces()) {
			if (p instanceof Pawn) {
				if (position.getCurrentPlayer().equals(PieceColour.WHITE)) {
					positionPts -= blackPawnsRatingBoard[p.getRow()][p.getColumn()];
				} else {
					positionPts += whitePawnsRatingBoard[p.getRow()][p.getColumn()];
				}
			} else if (p instanceof King) {
				if (position.getCurrentPlayer().equals(PieceColour.WHITE)) {
					positionPts -= blackKingRatingBoard[p.getRow()][p.getColumn()];
				} else {
					positionPts += whiteKingRatingBoard[p.getRow()][p.getColumn()];
				}
			}
		}
		return positionPts;
	}
}
