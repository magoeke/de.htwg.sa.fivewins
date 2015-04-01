package de.htwg.fivewins.view.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {

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
	private static final int NVPX = 25;
	private static final int NVPY = 150;
	private static final int SILLONGX = 225;
	private static final int SILLONGY = 160;
	private static final int FAILURENUMBER = 0;

	private JButton npc, silly, strong;
	private GameFrame jf;

	/**
	 * Constructor. Creates the main panel.
	 * 
	 * @param jf
	 */
	public MainMenuPanel(GameFrame jf) {
		this.jf = jf;
		JLabel headline = new JLabel("Five Wins");
		headline.setSize(HEADLINESIZE, HEADLINESIZE);
		headline.setBounds(HEADLINEXY, HEADLINEXY, HEADLINEWIDTH,
				HEADLINEHEIGHT);
		headline.setFont(new Font("Dialog", FONTSTYLE, FONTSIZE));

		JButton pvp = new JButton("Player vs. Player");
		pvp.addActionListener(new GameSelectionListener());
		pvp.setBounds(NVPX, NVPY, BUTTONLARGEWIDTH, BUTTONHEIGHT);
		npc = new JButton("Player vs. NPC");
		npc.addActionListener(new GameSelectionListener());
		npc.setBounds(NVPX, (NVPY + BUTTONHEIGHT), BUTTONLARGEWIDTH,
				BUTTONHEIGHT);
		silly = new JButton("Easy");
		silly.setVisible(false);
		silly.addActionListener(new LevelOfDifficultyListener());
		silly.setBounds(SILLONGX, SILLONGY, BUTTONNORMALWIDTH, BUTTONHEIGHT);
		strong = new JButton("Hard");
		strong.setVisible(false);
		strong.addActionListener(new LevelOfDifficultyListener());
		strong.setBounds(SILLONGX, (SILLONGY + BUTTONHEIGHT),
				BUTTONNORMALWIDTH, BUTTONHEIGHT);

		this.setLayout(null);
		this.add(pvp);
		this.add(npc);
		this.add(silly);
		this.add(strong);
		this.add(headline);
	}

	/*
	 * pop up a window which ask you how big should the game field be
	 */
	protected int getFieldSize() {
		String inputValue = JOptionPane
				.showInputDialog("Please input the field size you wante(Between 1-20)");
		int input;
		try {
			input = Integer.parseInt(inputValue);
		} catch (NumberFormatException ex) {
			input = FAILURENUMBER;
		}
		return input;
	}

	/*
	 * ActionListener für Button pvp und npc
	 */
	class GameSelectionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == npc) {
				silly.setVisible(true);
				strong.setVisible(true);
			} else {
				// starts pvp
				jf.startGamePlayer(getFieldSize());
			}
		}
	}

	/*
	 * ActionListener for silly and stron button
	 */
	class LevelOfDifficultyListener implements ActionListener {
		// start player vs. npc

		public void actionPerformed(ActionEvent e) {
			int fieldSize = getFieldSize();
			if (e.getSource() == silly) {
				jf.startGameNPC(fieldSize, "silly");
			} else {
				jf.startGameNPC(fieldSize, "strong");
			}
		}
	}
}
