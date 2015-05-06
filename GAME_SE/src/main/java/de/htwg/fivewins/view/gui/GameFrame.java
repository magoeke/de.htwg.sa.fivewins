package de.htwg.fivewins.view.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.inject.Inject;

import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.controller.IPluginController;
import de.htwg.fivewins.plugin.IPlugin;


public class GameFrame extends JFrame {

	/*
	 * To switch between GamePanel and MainMenuPanel is a cardlayout used.
	 */

	private static final long serialVersionUID = 1L;
	private static final String GAMEPANEL = "GamePanel";
	private static final String MAINMENUPANEL = "MainMenuPanel";
	private static final int BOTTOMBORDER = 0;
	private static final int TOPBORDER = 20;
	private static final int HEIGHT = 640;
	private static final int WIDTH = 400;

	private IFiveWinsController controller = null;
	private GamePanel gamePanel;
	private JPanel mainPanel;

	/**
	 * Constructor. initialize GameFrame with a controller
	 * 
	 * @param controller
	 */
	@Inject
	public GameFrame(IFiveWinsController controller, IPluginController pluginController) {
		this.controller = controller;
		this.setTitle("FiveWins");
		this.setLocationRelativeTo(null);
		this.setSize(HEIGHT, WIDTH);
		this.setJMenuBar(new GameMenuBar(this, controller, pluginController));

		MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
		gamePanel = new GamePanel(1, this, controller);

		mainPanel = new JPanel();
		mainPanel.setLayout(new CardLayout());
		mainPanel.add(mainMenuPanel, MAINMENUPANEL);
		mainPanel.add(gamePanel, GAMEPANEL);

		this.add(mainPanel, BorderLayout.CENTER);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * start a new player vs. player game
	 * 
	 * @param fieldsize
	 */
	public void startGamePlayer(int fieldsize) {
		gamePanel.setPlayer("X");
		if (fieldsize > BOTTOMBORDER && fieldsize <= TOPBORDER) {
			resizeGameField(fieldsize);
			controller.resizeGameField(fieldsize);
			CardLayout c1 = (CardLayout) (mainPanel.getLayout());
			c1.show(mainPanel, GAMEPANEL);
		}
	}

	/**
	 * start a new player vs. npc game
	 * 
	 * @param fieldsize
	 * @param levelOfDifficulty
	 */
	public void startGameNPC(int fieldsize, String levelOfDifficulty) {
		gamePanel.setPlayer("X");
		controller.createAI(levelOfDifficulty);

		if (fieldsize > BOTTOMBORDER && fieldsize <= TOPBORDER) {
			resizeGameField(fieldsize);
			controller.resizeGameField(fieldsize);
			CardLayout c1 = (CardLayout) (mainPanel.getLayout());
			c1.show(mainPanel, GAMEPANEL);
		}
	}

	/**
	 * start a new player vs. player game
	 * 
	 * @param fieldsize
	 */
	public void resizeGameField(int fieldsize) {
		controller.removeObserver(gamePanel);
		gamePanel = new GamePanel(fieldsize, this, controller);
		mainPanel.add(gamePanel, GAMEPANEL);
	}

	/**
	 * leave the game and go back to the main menu
	 */
	public void backToMainMenu() {
		CardLayout c1 = (CardLayout) (mainPanel.getLayout());
		c1.show(mainPanel, MAINMENUPANEL);
	}

	/**
	 * @return the actual player sign
	 */
	public String getPlayerSign() {
		return controller.getPlayerSign();
	}

	/**
	 * Handle action when a game cell is pressed.
	 * 
	 * @param command
	 */
	public void handleAction(String command) {
		controller.handleInputOrQuit(command);
		gamePanel.setTurn(controller.getTurn());
		gamePanel.setPlayer(controller.getPlayerSign());
	}

	/*
	 * Shows message when a game ends. Either win message or draw message.
	 */
	protected void displayGameOverMessages() {
		if (controller.getWinner()) {
			// output win message and go back to main menu
			JOptionPane.showMessageDialog(null,
					"Player " + controller.getWinnerSign() + " has won!",
					"Game Over", JOptionPane.INFORMATION_MESSAGE);

			gamePanel.allButtonsEnabled();
		}
		if (controller.getDraw()) {
			// output draw message and go back to main menu
			JOptionPane.showMessageDialog(null, "It's a draw!", "Game Over",
					JOptionPane.INFORMATION_MESSAGE);

			gamePanel.allButtonsEnabled();
		}
	}

	/**
	 * reset the game
	 */
	public void reset() {
		gamePanel.reset();
		controller.reset();
	}

	/**
	 * @return if game is won or not
	 */
	public boolean isWon() {
		return controller.getWinner();
	}

	public String[][] getGamefield() {
		return controller.getField();
	}

}
