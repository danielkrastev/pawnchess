package test;

import javax.swing.JFrame;

import userInterface.UserInterface;
import game.Game;


public class TestGame {

	public static void main(String[] args) {
		try {
			JFrame frame=new JFrame("Make your move!");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        Game game = new Game();
	        UserInterface ui= new UserInterface(game);
	        frame.add(ui);
	        frame.setSize(800, 700);
	        frame.setVisible(true);
	      	game.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
