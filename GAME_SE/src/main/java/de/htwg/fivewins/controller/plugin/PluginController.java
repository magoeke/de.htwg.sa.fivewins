package de.htwg.fivewins.controller.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.fivewins.plugin.IPlugin;
import de.htwg.util.observer.plugin.PluginObservable;

@Singleton
public class PluginController extends PluginObservable implements IPluginController{
	
	private Map<String, IPlugin> mapping = null;
	private Set<IPlugin> plugins = null;
	
	@Inject
	public PluginController(Set<IPlugin> plugins) {
		mapping = generatePluginMap(plugins);
		this.plugins = plugins;
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.controller.plugin.IPluginController#changePluginStatus(de.htwg.fivewins.plugin.IPlugin)
	 */
	@Override
	public void changePluginStatus(IPlugin plugin) {
		plugin.setActive(!plugin.isActive());
		notifyObservers(plugin);
	}
	
	/*
	 * Creates a Map with the plugins.
	 */
	private Map<String, IPlugin> generatePluginMap(Set<IPlugin> plugins) {
		Map<String, IPlugin> tmpMapping = new HashMap<String, IPlugin>();
		Iterator<IPlugin> iter = plugins.iterator();
		while (iter.hasNext()) {
			IPlugin plugin = iter.next();
			tmpMapping.put(plugin.getName().toLowerCase().replaceAll(" ", ""),
					plugin);
		}
		return tmpMapping;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.controller.plugin.IPluginController#getMapping()
	 */
	@Override
	public Map<String, IPlugin> getMapping() {
		return mapping;
	}

	/*
	 * (non-Javadoc)
	 * @see de.htwg.fivewins.controller.plugin.IPluginController#getPlugins()
	 */
	@Override
	public Set<IPlugin> getPlugins() {
		return plugins;
	}
}