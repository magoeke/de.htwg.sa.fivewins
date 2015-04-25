package de.htwg.util.observer;

import java.util.ArrayList;
import java.util.List;

import de.htwg.fivewins.plugin.IPlugin;

public class Observable implements IObservable{

	public static final int TWO = 2;
	private List<IObserver> subscribers = new ArrayList<IObserver>(TWO);

	public void addObserver(IObserver s) {
		subscribers.add(s);
	}

	public void removeObserver(IObserver s) {
		subscribers.remove(s);
	}

	public void removeAllObservers() {
		subscribers.clear();
	}

	public void notifyObservers() {
		for (int i = subscribers.size() - 1; i >= 0; i--) {
			IObserver observer = subscribers.get(i);
			observer.update();
		}
	}
	
	public void notifyObservers(IPlugin plugin) {
		for (int i = subscribers.size() - 1; i >= 0; i--) {
			IObserver observer = subscribers.get(i);
			observer.update(plugin);
		}
	}
}
