package de.htwg.fivewins.controller.plugin;

import java.util.Map;
import java.util.Set;

import de.htwg.fivewins.plugin.IPlugin;
import de.htwg.util.observer.plugin.IPluginObservable;
/**
 * Describes the PluginController Interface.
 */
public interface IPluginController extends IPluginObservable{
	
	/**
	 * Changes plugin status. Negates the current status.
	 * @param plugin
	 */
	void changePluginStatus(IPlugin plugin);
	
	/**
	 * Returns the mapping of the plugins.
	 * @return
	 */
	Map<String, IPlugin> getMapping();
	
	/**
	 * Returns a list with all plugins.
	 * @return
	 */
	Set<IPlugin> getPlugins();
}
