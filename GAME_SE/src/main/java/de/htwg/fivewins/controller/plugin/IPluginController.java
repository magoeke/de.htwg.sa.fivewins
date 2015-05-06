package de.htwg.fivewins.controller.plugin;

import java.util.Map;
import java.util.Set;

import de.htwg.fivewins.plugin.IPlugin;
import de.htwg.util.observer.plugin.IPluginObservable;
import de.htwg.util.observer.plugin.PluginObservable;

public interface IPluginController extends IPluginObservable{
	
	void changePluginStatus(IPlugin plugin);
	
	Map<String, IPlugin> getMapping();
	
	Set<IPlugin> getPlugins();
}
