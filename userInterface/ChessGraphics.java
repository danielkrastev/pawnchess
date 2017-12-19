package userInterface;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import pieces.King;
import pieces.Pawn;
import pieces.Piece.PieceColour;
import board.Square;

public class ChessGraphics {

	private final String pathToWhiteKing = "/home/daniel/pawnchess/userInterface/white_king.png";
	private final String pathToBlackKing = "/home/daniel/pawnchess/userInterface/black_king.png";
	private final String pathToWhitePawn = "/home/daniel/pawnchess/userInterface/white_pawn.png";
	private final String pathToBlackPawn = "/home/daniel/pawnchess/userInterface/black_pawn.png";
	private final String pathToMarkedSquare = "/home/daniel/pawnchess/userInterface/marked_square.png";
	
	private final int squareSize = 64;
	private final Color WOODEN_BOARD_BLACK = new Color(150, 50, 30);
	private final Color WOODEN_BOARD_WHITE = new Color(255, 200, 100);
	
	private final Color FANCY_BOARD_BLACK = new Color(18, 142, 14);
	private final Color FANCY_BOARD_WHITE = new Color(255, 252, 198);

	private Image whiteKing;
	private Image blackKing;
	private Image whitePawn;
	private Image blackPawn;
    private Image markedSquare;
	
	ChessGraphics() {
		whiteKing = new ImageIcon(pathToWhiteKing).getImage();
		blackKing = new ImageIcon(pathToBlackKing).getImage();
		whitePawn = new ImageIcon(pathToWhitePawn).getImage();
		blackPawn = new ImageIcon(pathToBlackPawn).getImage();
		markedSquare = new ImageIcon(pathToMarkedSquare).getImage();
	}

	void drawChessBoard(Graphics graphics, Game game) {
		for (Square sq : game.getChessBoard().toArray()){
			if (sq.isWhite()){
				graphics.setColor(FANCY_BOARD_WHITE);
			}else {
				graphics.setColor(FANCY_BOARD_BLACK);
			}
			graphics.fillRect(sq.getX(), sq.getY(), squareSize, squareSize);
		}
	}

	void drawPieces(Graphics graphics, Game game, UserInterface ui) {
		for (Square sq : game.getChessBoard().toArray()) {
			if (sq.isTaken()) {
				if (sq.getPiece() instanceof Pawn) {
					if (sq.getPiece().getPieceColour()
							.equals(PieceColour.WHITE)) {
						graphics.drawImage(whitePawn, sq.getX() ,sq.getY(), ui);
					} else {
						graphics.drawImage(blackPawn, sq.getX(), sq.getY(), ui);
					}
				} else if (sq.getPiece() instanceof King) {
					if (sq.getPiece().getPieceColour()
							.equals(PieceColour.WHITE)) {
						graphics.drawImage(whiteKing, sq.getX(), sq.getY(), ui);
					} else {
						graphics.drawImage(blackKing, sq.getX(), sq.getY(), ui);

					}
				}
			}
		}
	}

	public void markSquare(Graphics graphics, Square sq, UserInterface ui) {
		if (sq != null && sq.isTaken()){
			graphics.drawImage(markedSquare, sq.getX(), sq.getY(), ui);
		}
	}
}