package de.htwg.fivewins.controller;

import java.util.Map;
import java.util.Set;

import de.htwg.fivewins.plugin.IPlugin;
import de.htwg.util.observer.IPluginObservable;
import de.htwg.util.observer.PluginObservable;

public interface IPluginController extends IPluginObservable{
	
	void changePluginStatus(IPlugin plugin);
	
	Map<String, IPlugin> getMapping();
	
	Set<IPlugin> getPlugins();
}
