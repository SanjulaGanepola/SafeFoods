/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module provides an interface for a MenuListener
 */

package gui;

import java.util.EventListener;

/**
 * @brief Module that provides an interface for a MenuListener
 */
public interface MenuListener extends EventListener{

	/**
	 * @brief Method that determines if an event has occured
	 */
	public void menuEventOccured(MenuEvent e);
	
}
