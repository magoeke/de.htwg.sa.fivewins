package de.htwg.fivewins.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.htwg.fivewins.controller.game.IFiveWinsController;
import de.htwg.fivewins.controller.plugin.IPluginController;
import de.htwg.fivewins.plugin.IPlugin;
import de.htwg.util.observer.plugin.IPluginObserver;

public class GameMenuBar extends JMenuBar implements ActionListener,
		IPluginObserver {

	private static final long serialVersionUID = 1L;

	private JMenuItem restart, backToMainMenu, deleteAllGames, saveGame;
	private List<JCheckBoxMenuItem> pluginMenuItems;
	private GameFrame gameFrame;
	private Map<String, IPlugin> mapping;
	private IPluginController pluginController;
	private IFiveWinsController controller;

	public GameMenuBar(GameFrame jf, IFiveWinsController controller,
			IPluginController pluginController) {
		this.controller = controller;
		Set<IPlugin> plugins = pluginController.getPlugins();
		pluginController.addObserver(this);
		this.pluginController = pluginController;
		JMenu menu = new JMenu("Data");
		this.gameFrame = jf;
		restart = new JMenuItem("Restart");
		restart.addActionListener(this);
		backToMainMenu = new JMenuItem("Back to main menu");
		backToMainMenu.addActionListener(this);
		saveGame = new JMenuItem("Save this game");
		saveGame.addActionListener(this);
		deleteAllGames = new JMenuItem("Delete all saved games");
		deleteAllGames.addActionListener(this);
		menu.add(restart);
		menu.add(backToMainMenu);
		menu.add(deleteAllGames);
		menu.add(saveGame);
		this.add(menu);

		// for plugin
		mapping = pluginController.getMapping();
		pluginMenuItems = new LinkedList<JCheckBoxMenuItem>();
		JMenu menuPlugin = new JMenu("Plugin");
		JCheckBoxMenuItem buffer;
		Iterator<IPlugin> iter = plugins.iterator();
		while (iter.hasNext()) {
			IPlugin plugin = iter.next();
			buffer = new JCheckBoxMenuItem(plugin.getName());
			buffer.addActionListener(this);
			menuPlugin.add(buffer);
			pluginMenuItems.add(buffer);
		}
		this.add(menuPlugin);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == restart) {
			gameFrame.reset();
		} else if (e.getSource() == backToMainMenu) {
			gameFrame.backToMainMenu();
		} else if (pluginMenuItems.contains(e.getSource())) {
			activatePlugin((JCheckBoxMenuItem) e.getSource());
		} else if (e.getSource() == deleteAllGames) {

			int selection = JOptionPane.showConfirmDialog(null,
					"all saved games will be deleted!",
					"Delete games?",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			if (selection == JOptionPane.OK_OPTION) {
				controller.deleteAllGames();

			}

		} else if (e.getSource() == saveGame) {
			controller.saveGame();
		}
	}

	private void activatePlugin(JCheckBoxMenuItem menuItem) {
		String name = menuItem.getText().toLowerCase().replaceAll(" ", "");
		pluginController.changePluginStatus(mapping.get(name));
	}

	public void reset() {
		// TODO:
	}

	@Override
	public void updatePlugin(IPlugin plugin) {
		for (JCheckBoxMenuItem jcbm : pluginMenuItems) {
			if (plugin.getName().equals(jcbm.getText())) {
				jcbm.setSelected(plugin.isActive());
				break;
			}
		}
	}

}
