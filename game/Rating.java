package game;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceColour;

public class Rating {
	private static final int[][] whitePawnsRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
			{ 0, 38, 40, 40, 40, 40, 40, 40, 38, 0 },
			{ 0, 30, 35, 35, 36, 35, 35, 35, 30, 0 },
			{ 0, 50, 50, 50, 50, 50, 50, 50, 50, 0 },
			{ 0, 80, 80, 80, 80, 80, 80, 80, 80, 0 },
			{ 0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
	
	private static final int[][] blackPawnsRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 0 },
			{ 0, 80, 80, 80, 80, 80, 80, 80, 80, 0 },
			{ 0, 50, 50, 50, 50, 50, 50, 50, 50, 0 },
			{ 0, 38, 40, 40, 40, 40, 40, 40, 38, 0 },
			{ 0, 30, 35, 35, 36, 35, 35, 35, 30, 0 },
			{ 0, 3, 3, 3, 3, 3, 3, 3, 3, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };


	private static final int[][] whiteKingRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 20, 20, 25, 25, 0, 0, 0 },
			{ 0, 0, 0, 20, 20, 25, 25, 0, 0, 0 }, 
			{ 0, 0, 6, 30, 35, 35, 35, 10, 0, 0 },
			{ 0, 1, 30, 35, 40, 40, 40, 35, 1, 0 },
			{ 0, 4, 40, 40, 45, 45, 45, 40, 35, 0 },
			{ 0, 2, 2, 40, 40, 40, 40, 3, 2, 0 }, 
			{ 0, 2, 2, 4, 10, 10, 4, 3, 2, 0 },
			{ 0, 0, 0, 20, 20, 25, 25, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	private static final int[][] blackKingRatingBoard = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 20, 20, 25, 25, 0, 0, 0 },
			{ 0, 0, 0, 20, 20, 25, 25, 0, 0, 0 }, 
			{ 0, 0, 6, 30, 35, 35, 35, 10, 0, 0 },
			{ 0, 1, 30, 35, 40, 40, 40, 35, 1, 0 },
			{ 0, 4, 40, 40, 45, 45, 45, 40, 35, 0 },
			{ 0, 2, 2, 40, 40, 40, 40, 3, 2, 0 }, 
			{ 0, 2, 2, 4, 10, 10, 4, 3, 2, 0 },
			{ 0, 0, 0, 20, 20, 25, 25, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	public static int ratePosition(Position position) {
		int rating = 0;
		rating += calculateMaterialRating(position);
		rating += calculatePiecePositionRating(position);
		if (position.getCurrentPlayer() == PieceColour.BLACK){
			rating = 0 - rating;
		}
		return rating;
	}

	private static int calculateMaterialRating(Position position) {
		int materialPts = 0;
		materialPts += (position.getBlackPieces().size() - position.getWhitePieces().size())*15;
		if (position.getCurrentPlayer().equals(PieceColour.WHITE)) {
		//evaluate position for black
			return materialPts;
		}else {
		// evaluate for white	
			return 0 - materialPts;
		}
	}		

	private static int calculatePiecePositionRating(Position position) {
		int positionPts = 0;
		if (position.getCurrentPlayer().equals(PieceColour.WHITE)) {
			 //evaluate position for black
			for (Piece p : position.getBlackPieces()) {
				if (p instanceof Pawn) {
					positionPts += blackPawnsRatingBoard[p.getRow()][p.getColumn()];
				} else if (p instanceof King) {
					positionPts += blackKingRatingBoard[p.getRow()][p.getColumn()];
				}
			}
			for (Piece p : position.getWhitePieces()) {
				if (p instanceof Pawn) {
					positionPts -= whitePawnsRatingBoard[p.getRow()][p.getColumn()];
				} else if (p instanceof King) {
					positionPts -= whiteKingRatingBoard[p.getRow()][p.getColumn()];
				}
			}
		}else {
			//evaluate position for white
			for (Piece p : position.getWhitePieces()) {
				if (p instanceof Pawn) {
					positionPts += whitePawnsRatingBoard[p.getRow()][p.getColumn()];
				} else if (p instanceof King) {
					positionPts += whiteKingRatingBoard[p.getRow()][p.getColumn()];
				}
			}
			for (Piece p : position.getBlackPieces()) {
				if (p instanceof Pawn) {
					positionPts -= blackPawnsRatingBoard[p.getRow()][p.getColumn()];
				} else if (p instanceof King) {
					positionPts -= blackKingRatingBoard[p.getRow()][p.getColumn()];
				}
			}
		}
		return positionPts;
	}
}
