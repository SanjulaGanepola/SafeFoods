/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is used when the MenuPanel is interacted with
 */

package gui;

import java.util.EventObject;

/**
 * @brief This module is used when the MenuPanel is interacted with
 */
public class MenuEvent extends EventObject {
	
	private static final long serialVersionUID = 2426197396508438774L;
	private String text;

	/**
	 * @brief Constructor for the MenuEvent Class
	 * @param source An object
	 * @param text Text to be displayed
	 */
	public MenuEvent(Object source, String text) {
		super(source);
		this.text = text;
	}
	
	/**
	 * @brief Returns the text that is to be displayed in the GUI
	 * @return The text that is to be displayed in the GUI
	 */
	public String getText() {
		return text;
	}
	
}
