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
	private static final int HEADLINESIZE = 20;
	private static final int HEADLINEXY = 50;
	private static final int HEADLINEWIDTH = 300;
	private static final int HEADLINEHEIGHT = 50;
	private static final int FONTSTYLE = 0;
	private static final int FONTSIZE = 50;
	private static final int BUTTONHEIGHT = 30;
	private static final int BUTTONNORMALWIDTH = 150;
	private static final int BUTTONLARGEWIDTH = 180;
	private static final int XOX = 300;
	private static final int XOY = 240;
	private static final int NVPX = 25;
	private static final int NVPY = 150;
	private static final int SILLONGX = 225;
	private static final int SILLONGY = 160;
	private static final int FAILURENUMBER = 0;
	
	private JButton npc, silly, strong, x, o;
	private GameFrame jf;
	private String strength;
	
	
	
	public MainMenuPanel(GameFrame jf) {
		this.jf= jf;
		JLabel headline = new JLabel("Five Wins");
		headline.setSize(HEADLINESIZE, HEADLINESIZE);
		headline.setBounds(HEADLINEXY, HEADLINEXY, HEADLINEWIDTH, HEADLINEHEIGHT);
		headline.setFont(new Font("Dialog", FONTSTYLE, FONTSIZE));
		

		
		x = new JButton("X");
		x.addActionListener(new PlayerSelectionListener());
		x.setVisible(false);
		x.setBounds(XOX, XOY, BUTTONNORMALWIDTH, BUTTONHEIGHT);
		o = new JButton("O");
		o.addActionListener(new PlayerSelectionListener());
		o.setVisible(false);
		o.setBounds(XOX, (XOY+BUTTONHEIGHT), BUTTONNORMALWIDTH, BUTTONHEIGHT);
		JButton pvp = new JButton("Player vs. Player");
		pvp.addActionListener(new GameSelectionListener());
		pvp.setBounds(NVPX, NVPY, BUTTONLARGEWIDTH, BUTTONHEIGHT);
		npc = new JButton("Player vs. NPC");
		npc.addActionListener(new GameSelectionListener());
		npc.setBounds(NVPX,(NVPY + BUTTONHEIGHT), BUTTONLARGEWIDTH, BUTTONHEIGHT);
		silly = new JButton("Einfach");
		silly.setVisible(false);
		silly.addActionListener(new levelOfDifficultyListener());
		silly.setBounds(SILLONGX, SILLONGY, BUTTONNORMALWIDTH, BUTTONHEIGHT);
		strong = new JButton("Schwer");
		strong.setVisible(false);
		strong.addActionListener(new levelOfDifficultyListener());
		strong.setBounds(SILLONGX, (SILLONGY+BUTTONHEIGHT), BUTTONNORMALWIDTH, BUTTONHEIGHT);
		
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
			input = FAILURENUMBER;
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
