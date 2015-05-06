package de.htwg.util.observer.plugin;

import de.htwg.fivewins.plugin.IPlugin;

public interface IPluginObservable {
	void addObserver(IPluginObserver s);

	void removeObserver(IPluginObserver s);

	void removeAllObservers();

	void notifyObservers(IPlugin plugin);
}
