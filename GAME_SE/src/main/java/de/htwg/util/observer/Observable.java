package de.htwg.util.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * @author Max
 */
public class Observable {

	//<Iobserver >Liste
	private List<IObserver> subscribers = new ArrayList<IObserver>(2); 

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
		for(int i = subscribers.size()-1; i >= 0; i--) {
			IObserver observer = subscribers.get(i);
			observer.update();
		}
//		for ( Iterator<IObserver> iter = subscribers.iterator(); iter.hasNext();) {
//			IObserver observer = iter.next();
//			observer.update();
//		}
	}
}
