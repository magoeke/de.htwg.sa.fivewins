package de.htwg.fivewins.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.htwg.fivewins.controller.FiveWinsController;
import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.field.AIAdapter;
import de.htwg.fivewins.field.Field;
import de.htwg.fivewins.field.StrongAI;
import de.htwg.fivewins.field.VerySillyAI;
import de.htwg.fivewins.tui.TextUI;

public class GameFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private IFiveWinsController controller = null;
	private final static String GAMEPANEL = "GamePanel";
	private final static String MAINMENUPANEL = "MainMenuPanel";
	private final static int BOTTOMBORDER = 0;
	private final static int TOPBORDER=20;
	private GamePanel gamePanel;
	private JPanel mainMenuPanel, mainPanel;
	private TextUI textUI;
	
	public GameFrame(IFiveWinsController controller) {
		this.controller = controller;
		this.setTitle("FiveWins");
		this.setLocationRelativeTo(null);
		this.setSize( 640, 400 );
		this.setJMenuBar(new GameMenuBar(this));
		
		mainMenuPanel = new MainMenuPanel(this);
		gamePanel = new GamePanel(1, this);
				
		mainPanel = new JPanel();
		mainPanel.setLayout(new CardLayout());
		mainPanel.add(mainMenuPanel, MAINMENUPANEL);
		mainPanel.add(gamePanel, GAMEPANEL);
		
		this.add(mainPanel, BorderLayout.CENTER);
		
		this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void startGamePlayer(int fieldsize) {
		gamePanel.setPlayer("X");
		controller = new FiveWinsController(new Field(fieldsize));
		textUI = new TextUI(controller);
		if(fieldsize > BOTTOMBORDER && fieldsize <= TOPBORDER) {
			resizeGameField(fieldsize);
			CardLayout c1 = (CardLayout)(mainPanel.getLayout());
			c1.show(mainPanel, GAMEPANEL);
		}
	}
	
	public void startGameNPC(int fieldsize, String levelOfDifficulty, String sign) {
		gamePanel.setPlayer("X");
		Field field = new Field(fieldsize);
		if(levelOfDifficulty.equals("silly")) {
			controller = new FiveWinsController(field, new VerySillyAI(sign, field));
		} else {
			controller = new FiveWinsController(field, new StrongAI(sign, field));
		}
		textUI = new TextUI(controller);
		if(fieldsize > BOTTOMBORDER && fieldsize <= TOPBORDER) {
			resizeGameField(fieldsize);
			CardLayout c1 = (CardLayout)(mainPanel.getLayout());
			c1.show(mainPanel, GAMEPANEL);
		}
		gamePanel.firstAction();
	}
	
	public void resizeGameField(int fieldsize) {
		gamePanel = new GamePanel(fieldsize, this);
		mainPanel.add(gamePanel, GAMEPANEL);
	}
	
	public void backToMainMenu() {
		CardLayout c1 = (CardLayout)(mainPanel.getLayout());
		c1.show(mainPanel, MAINMENUPANEL);
	}
	
	public String getPlayerSign() {
		return controller.getPlayerSign();
	}
	
	public boolean nowSecondPlayer() {
		AIAdapter aia = controller.getSecondPlayer();
		boolean returnValue = false;
		if(aia != null && aia.getWhichPlayer().equals(getPlayerSign())) {
			returnValue = true;
		}
		return returnValue;
	}
	
	public AIAdapter getSecondPlayer() {
		return controller.getSecondPlayer();
	}
	
	public void handleAction(String command) {
		textUI.handleInputOrQuit(command);
		gamePanel.setTurn(controller.getTurn());
		gamePanel.setPlayer(controller.getPlayerSign());
		
		if(controller.getWinner()) {
			//output win message and go back to main menu
			JOptionPane.showMessageDialog(null, "Spieler " + controller.getWinnerSign() + " hat gewonnen!",
				        "Game Over", JOptionPane.INFORMATION_MESSAGE);
			
			gamePanel.allButtonsEnabled();
		}
	}

	public void reset() {
		gamePanel.reset();
		controller.reset();
	}
	
}
