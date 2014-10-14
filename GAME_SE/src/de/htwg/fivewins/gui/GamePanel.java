package de.htwg.fivewins.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.htwg.fivewins.controller.IFiveWinsController;
import de.htwg.fivewins.field.Field;
import de.htwg.util.observer.IObserver;

/*
 * @author Max
 */
public class GamePanel extends JPanel implements IObserver{

	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 300;
	private static final int WIDTH = 50;

	private JButton buttons[][];
	private int fieldsize;
	private JLabel turn, player;
	private GameFrame jf;
	private IFiveWinsController controller;
	
	public GamePanel(int fieldsize, GameFrame jf, IFiveWinsController controller) {
		this.controller = controller;
		this.fieldsize = fieldsize;
		this.jf = jf;
		controller.addObserver(this);
		JPanel gamefield = new JPanel();
		gamefield.setLayout(new GridLayout(fieldsize, fieldsize));
		
		buttons = new JButton[fieldsize][fieldsize];
		for(int i = 0; i < fieldsize; i++) {
			for(int j = 0; j< fieldsize; j++) {
				buttons[i][j] = new JButton("");
				buttons[i][j].addActionListener(new ActionListener() {
					 
                    public void actionPerformed(ActionEvent evt) {
                        buttonClicked(evt);
                    }
                });
				gamefield.add(buttons[i][j]);
			}
		}
		
		turn = new JLabel("1");
		player = new JLabel("Initial");
		JPanel optionsLabel = new JPanel();
		optionsLabel.setLayout(new FlowLayout());
		optionsLabel.add(new JLabel("Turn: "));
		optionsLabel.add(turn);
		optionsLabel.add(new JLabel("Player Turn: "));
		optionsLabel.add(player);
		optionsLabel.setMaximumSize(new Dimension(HEIGHT, WIDTH));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(gamefield);
		this.add(optionsLabel);
	}
	
	/*
	 * get actual player
	 */
	public String getPlayer() {
		return jf.getPlayerSign();
	}
	
	/*
	 * calls GameFrame.handelaction with the pressed coordinates
	 */
	public void buttonClicked(ActionEvent evt) {
		if(isAITurn()) {
			handleAITurn();
		}
		
        JButton button = (JButton) evt.getSource();
        int x = 0; int y = 0;
        for (int i = 0; i < fieldsize; i++) {
            for (int j = 0; j < fieldsize; j++) {
                if (buttons[i][j] == button) {
                    x = i; y = j;
                }
            }
        }
        
        //change because the tui want it this way
        jf.handleAction((y+1)+","+(x+1));
        

        
        if(!jf.isWon() && isAITurn()) {
        	handleAITurn();
        }
        
    }
	
	/*
	 * return if it's ai turn or player
	 */
	public boolean isAITurn() {
		return jf.nowSecondPlayer();
	}
	
	/*
	 * handel the behavior of a ai
	 */
	public void handleAITurn() {
		String command = jf.getSecondPlayer().getCommand();
		jf.handleAction(command);
		String[] splitted = command.split(",");
		int x = Integer.parseInt(splitted[0]) - 1;
		int y = Integer.parseInt(splitted[1]) - 1;
		//change x and y because ai output it in the wrong order
		buttons[y][x].setEnabled(false);
		buttons[y][x].setText(jf.getSecondPlayer().getWhichPlayer());
	}
	
	/*
	 * the method is needed if the ai starts
	 */
	public void firstAction() {
		if(isAITurn()) {
			handleAITurn();
		}
	}
	
	/*
	 * disable all buttons
	 */
	public void allButtonsEnabled() {
		for (int i = 0; i < fieldsize; i++) {
            for (int j = 0; j < fieldsize; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
	}
	
	/*
	 * change Jlabel to actual player
	 */
	public void setPlayer(String player) {
		this.player.setText(player);
	}
	
	/*
	 * change turn to actual turn
	 */
	public void setTurn(int turn) {
		this.turn.setText(turn+"");
	}
	
	public void printGui() {
		String[][] field = controller.getField();
		for(int i = 0; i < field.length; i++) {
			for(int j = 0; j < field.length; j++) {
				if(field[i][j] != "-" && buttons[j][i].isEnabled()) {
					buttons[j][i].setText(field[i][j]);
					buttons[j][i].setEnabled(false);
				}
			}
		}
	}
	
	/*
	 * reset all buttons to default
	 */
	public  void reset() {
		for (int i = 0; i < fieldsize; i++) {
            for (int j = 0; j < fieldsize; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
            }
        }
	}

	@Override
	public void update() {
		printGui();
		
	}
}
