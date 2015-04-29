package de.htwg.fivewins.plugin;

/**
 * Plugin get call at the end of a turn.
 *
 */
public interface IPlugin{

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
