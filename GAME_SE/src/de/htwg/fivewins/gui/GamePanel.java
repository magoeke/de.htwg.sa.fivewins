package de.htwg.fivewins.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	JButton buttons[][];
	int fieldsize;
	JLabel turn, player;
	GameFrame jf;
	
	public GamePanel(int fieldsize, GameFrame jf) {
		this.fieldsize = fieldsize;
		this.jf = jf;
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
		optionsLabel.setMaximumSize(new Dimension(300, 50));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(gamefield);
		this.add(optionsLabel);
	}
	
	public String getPlayer() {
		return jf.getPlayerSign();
	}
	
	public void buttonClicked(ActionEvent evt) {
		if(isAITurn()) {
			handleAITurn();
		}
		
        JButton button = (JButton) evt.getSource();
        button.setText(jf.getPlayerSign());
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
        
        //button.setBackground(new Color(255));
        button.setEnabled(false);
        
        if(isAITurn()) {
			handleAITurn();
		}
        
    }
	
	public boolean isAITurn() {
		return jf.nowSecondPlayer();
	}
	
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
	
	public void firstAction() {
		if(isAITurn()) {
			handleAITurn();
		}
	}
	
	public void allButtonsEnabled() {
		for (int i = 0; i < fieldsize; i++) {
            for (int j = 0; j < fieldsize; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
	}
	
	public void setPlayer(String player) {
		this.player.setText(player);
	}
	
	public void setTurn(int turn) {
		this.turn.setText(turn+"");
	}
	
	public  void reset() {
		for (int i = 0; i < fieldsize; i++) {
            for (int j = 0; j < fieldsize; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
            }
        }
	}
}
