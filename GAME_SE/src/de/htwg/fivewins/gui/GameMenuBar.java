package de.htwg.fivewins.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameMenuBar extends JMenuBar implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	JMenu menu;
	JMenuItem restart, backToMainMenu;
	GameFrame jf;
	
	public GameMenuBar(GameFrame jf) {
		this.jf = jf;
		menu = new JMenu("Datei");
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
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == restart) {
			
		} else if(source == backToMainMenu) {
			jf.backToMainMenu();
		}
		
	}
}
