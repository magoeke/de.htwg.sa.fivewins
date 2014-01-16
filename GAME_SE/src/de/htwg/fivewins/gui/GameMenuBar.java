package de.htwg.fivewins.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*
 * @author Max
 */
public class GameMenuBar extends JMenuBar implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JMenuItem restart, backToMainMenu;
	private GameFrame jf;
	
	public GameMenuBar(GameFrame jf) {
		JMenu menu = new JMenu("Datei");
		this.jf = jf;
		restart = new JMenuItem("Restart");
		restart.addActionListener(this);
		backToMainMenu = new JMenuItem("Back to main menu");
		backToMainMenu.addActionListener(this);
		menu.add(restart);
		menu.add(backToMainMenu);
		this.add(menu);
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == restart) {
			jf.reset();
		} else if(e.getSource() == backToMainMenu) {
			jf.backToMainMenu();
		}
		
	}
}
