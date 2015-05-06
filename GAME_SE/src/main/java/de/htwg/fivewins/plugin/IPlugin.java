package de.htwg.fivewins.plugin;

import de.htwg.util.observer.IObserver;

/**
 * Plugin get call at the end of a turn.
 *
 */
public interface IPlugin extends IObserver{

	/**
	 * @return name of plugin
	 */
	String getName();
	
	/**
	 * 
	 */
	void work();
	
	boolean isActive();
	
	void setActive(boolean active);
	
	
}
