package de.htwg.fivewins.view.tui;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.inject.Inject;

import de.htwg.fivewins.controller.game.IFiveWinsController;
import de.htwg.fivewins.controller.plugin.IPluginController;
import de.htwg.fivewins.plugin.IPlugin;
import de.htwg.util.observer.IObserver;
import de.htwg.util.observer.plugin.IPluginObserver;

/**
 * TextUI implementation for FiveWins.
 */
public class TextUI implements IObserver, IPluginObserver {

	private IFiveWinsController controller;
	private IPluginController pluginController;

	private static final Logger TEXTUILOGGER = Logger
			.getLogger("de.htwg.fivewins.tui");
	private Map<String, IPlugin> mapping;

	/**
	 * Constructor.
	 * 
	 * @param controller
	 */
	@Inject
	public TextUI(IFiveWinsController controller, IPluginController pluginController) {
		this.controller = controller;
		this.pluginController = pluginController;
		controller.addObserver(this);
		pluginController.addObserver(this);
		mapping = pluginController.getMapping();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.htwg.util.observer.IObserver#update()
	 */
	public void update() {
		printTUI();
	}

	/**
	 * Is used for TUI inputs.
	 */
	public boolean iterate(String line) {
		if (line.equals("p")) {
			return printPlugins();
		} else if (mapping.containsKey(line.replaceFirst("p,", ""))) {
			return activatePlugin(line.replaceFirst("p,", ""));
		} else {
			return controller.handleInputOrQuit(line);
		}
	}

	/**
	 * print tui on the console
	 */
	public void printTUI() {
		TEXTUILOGGER.info("\n" + controller.getFieldString() + "\n");
		TEXTUILOGGER.info(controller.getStatus() + "\n");
		TEXTUILOGGER.info("\n");
		TEXTUILOGGER
				.info("Please enter a command( q - quit, u - update, n - new, x,y - set cell(x,y)), p - show plugins, p,command - (de)activate Plugin:\n");

		if (controller.getWinner()) {
			TEXTUILOGGER.info("The winner is " + controller.getWinnerSign()
					+ "\n");
		}

		if (controller.getDraw()) {
			TEXTUILOGGER.info("It's a draw!");
		}

	}

	/*
	 * resets the controller and update the tui
	 */
	public void reset() {
		controller.reset();
		update();
	}

	public boolean printPlugins() {
		StringBuilder sb = new StringBuilder();
		sb.append("Plugins:\n");
		sb.append("command\t\tname\t\tactive\n");
		for (Map.Entry<String, IPlugin> e : mapping.entrySet()) {
			String s = e.getKey();
			IPlugin plugin = e.getValue();
			sb.append(s + "\t" + plugin.getName() + "\t" + plugin.isActive()
					+ "\n");
		}
		TEXTUILOGGER.info(sb.toString());

		return false;
	}

	private boolean activatePlugin(String key) {
		IPlugin plugin = mapping.get(key);
		pluginController.changePluginStatus(plugin);
		return false;
	}

	@Override
	public void updatePlugin(IPlugin plugin) {
		printPlugins();		
	}

}
