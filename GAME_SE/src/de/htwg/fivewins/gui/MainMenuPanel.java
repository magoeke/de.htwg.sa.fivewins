package de.htwg.fivewins.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	JButton pvp, npc, silly, strong;
	JOptionPane inputFieldSize;
	GameFrame jf;
	
	public MainMenuPanel(GameFrame jf) {
		this.jf= jf;
		JLabel headline = new JLabel("Five Wins");
		headline.setSize(20, 20);
		headline.setBounds(50, 50, 300, 50);
		headline.setFont(new Font("Dialog", 0, 50));
		
		pvp = new JButton("Player vs. Player");
		pvp.addActionListener(new GameSelectionListener());
		pvp.setBounds(25, 150, 180, 30);
		npc = new JButton("Player vs. NPC");
		npc.addActionListener(new GameSelectionListener());
		npc.setBounds(25,180, 180,30);
		silly = new JButton("Einfach");
		silly.setVisible(false);
		silly.addActionListener(new levelOfDifficultyListener());
		silly.setBounds(225, 160, 150, 30);
		strong = new JButton("Schwer");
		strong.setVisible(false);
		strong.addActionListener(new levelOfDifficultyListener());
		strong.setBounds(225, 190, 150, 30);
		
		this.setLayout(null);
		this.add(pvp);
		this.add(npc);
		this.add(silly);
		this.add(strong);
		this.add(headline);
	}
	
	protected int getFieldSize() {
		String inputValue = JOptionPane.showInputDialog("Please input the field size you wante(Between 1-20)");
		int input;
		try {
			input = Integer.parseInt(inputValue);
		} catch(NumberFormatException ex) {
			input = 0;
		}
		return input;
	}
	
	class GameSelectionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == npc) {
				silly.setVisible(true);
				strong.setVisible(true);
			} else {
				silly.setVisible(false);
				strong.setVisible(false);
				jf.startGamePlayer(getFieldSize());
			}
		}
	}
	
	class levelOfDifficultyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//Pop up field size value
			int fieldSize = getFieldSize();
			if(e.getSource() == silly) {
				jf.startGameNPC(fieldSize, "silly");
			} else {
				jf.startGameNPC(fieldSize, "strong");
			}
		}
	}
}
