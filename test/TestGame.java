package test;

import javax.swing.JFrame;

import userInterface.UserInterface;
import game.Game;


public class TestGame {

	public static void main(String[] args) {

		JFrame frame=new JFrame("The great chess program!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game game = new Game();
    	game.setPiecesForNewGame();
        UserInterface ui= new UserInterface(game);
        frame.add(ui);
        frame.setSize(800, 700);
        frame.setVisible(true);
      	game.start();
	}
}
