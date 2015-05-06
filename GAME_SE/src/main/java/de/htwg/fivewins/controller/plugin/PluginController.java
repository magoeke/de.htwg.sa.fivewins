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

	public void changePluginStatus(IPlugin plugin) {
		plugin.setActive(!plugin.isActive());
		notifyObservers(plugin);
	}
	
	private Map<String, IPlugin> generatePluginMap(Set<IPlugin> plugins) {
		Map<String, IPlugin>mapping = new HashMap<String, IPlugin>();
		Iterator<IPlugin> iter = plugins.iterator();
		while (iter.hasNext()) {
			IPlugin plugin = iter.next();
			mapping.put(plugin.getName().toLowerCase().replaceAll(" ", ""),
					plugin);
		}
		return mapping;
	}
	
	public Map<String, IPlugin> getMapping() {
		return mapping;
	}

	@Override
	public Set<IPlugin> getPlugins() {
		return plugins;
	}
}