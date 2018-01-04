package userInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import board.Square;
import game.Game;

public class UserInterface extends JPanel {

	private static final long serialVersionUID = 1L;
	private Game game;
	private ChessGraphics chessGraphics;
	Square markedSquare;

	public UserInterface(final Game game) {
		super();
		this.chessGraphics = new ChessGraphics();
		this.game = game;

		MouseMover mover = new MouseMover();
		this.addMouseListener(mover);
		game.setMouseMover(mover);
		game.setUI(this);
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		this.setBackground(Color.WHITE);
		chessGraphics.drawChessBoard(graphics, game);
		chessGraphics.drawPieces(graphics, game, this);
		chessGraphics.markSquare(graphics, markedSquare, this);
	}

	public class MouseMover implements MouseListener{
		private int columnClicked;
		private int rowClicked;

		@Override
		public void mouseClicked(MouseEvent event) {

		}

		public int getClickedColumn() {
			return columnClicked;
		}
		
		public void setClickedColumn(int column){
			this.columnClicked = column;
		}
		
		public int getClickedRow(){
			return rowClicked;	
		}
		
		public void setClickedRow (int row){
			this.rowClicked = row;
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			synchronized(game){
				columnClicked = event.getX()/64;
				rowClicked = 9 - event.getY()/64;
				markedSquare = game.getChessBoard().getSquare(rowClicked, columnClicked);
			    UserInterface.this.repaint();
				game.notify();
			}
		}
	}
}
