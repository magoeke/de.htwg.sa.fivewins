package de.htwg.util.observer.plugin;

import java.util.ArrayList;
import java.util.List;

import de.htwg.fivewins.plugin.IPlugin;

public class PluginObservable {
	public static final int TWO = 2;
	private List<IPluginObserver> subscribers = new ArrayList<IPluginObserver>(TWO);

	public void addObserver(IPluginObserver s) {
		subscribers.add(s);
	}

	public void removeObserver(IPluginObserver s) {
		subscribers.remove(s);
	}

	public void removeAllObservers() {
		subscribers.clear();
	}

	public void notifyObservers(IPlugin plugin) {
		for (int i = subscribers.size() - 1; i >= 0; i--) {
			IPluginObserver observer = subscribers.get(i);
			observer.updatePlugin(plugin);
		}
	}
	
}
