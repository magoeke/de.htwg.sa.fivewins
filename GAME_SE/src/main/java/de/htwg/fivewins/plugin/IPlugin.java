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
	 * Plugin works
	 */
	void work();
	
	/**
	 * Returns if Plugin is active.
	 * @return
	 */
	boolean isActive();
	
	/**
	 * Activates or deactivates plugin.
	 * @param active
	 */
	void setActive(boolean active);
	
	
}
