package de.htwg.fivewins.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton pvp, npc, silly, strong, x, o;
	private JOptionPane inputFieldSize;
	private GameFrame jf;
	private String strength;
	
	
	
	public MainMenuPanel(GameFrame jf) {
		this.jf= jf;
		JLabel headline = new JLabel("Five Wins");
		headline.setSize(20, 20);
		headline.setBounds(50, 50, 300, 50);
		headline.setFont(new Font("Dialog", 0, 50));
		

		
		x = new JButton("X");
		x.addActionListener(new PlayerSelectionListener());
		x.setVisible(false);
		x.setBounds(300, 240, 150, 30);
		o = new JButton("O");
		o.addActionListener(new PlayerSelectionListener());
		o.setVisible(false);
		o.setBounds(300, 270, 150, 30);
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
		this.add(x);
		this.add(o);
		this.add(headline);
	}
	
	/*
	 * pop up a window which ask you
	 * how big should the game field be
	 */
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
	
	/*
	 * ActionListener für Button pvp und npc
	 */
	class GameSelectionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == npc) {
				silly.setVisible(true);
				strong.setVisible(true);
			} else {
				silly.setVisible(false);
				strong.setVisible(false);
				x.setVisible(false);
				o.setVisible(false);
				jf.startGamePlayer(getFieldSize());
			}
		}
	}
	
	/*
	 * ActionListener for silly and stron button
	 */
	class levelOfDifficultyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == silly) {
				strength = "silly";
			} else {
				strength = "strong";
			}
			x.setVisible(true);
			o.setVisible(true);
		}
	}
	
	/*
	 * ActionListener for x and o button
	 */
	class PlayerSelectionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int fieldSize = getFieldSize();
			if(e.getSource() == x) {
				jf.startGameNPC(fieldSize, strength, "X");
			} else {
				jf.startGameNPC(fieldSize, strength, "O");
			}
		}
		
	}
}
