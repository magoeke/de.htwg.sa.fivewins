package de.htwg.util.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable implements IObservable{

	private List<IObserver> subscribers = new ArrayList<IObserver>();

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
	
}
