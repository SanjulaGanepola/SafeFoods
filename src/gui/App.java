/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is used to run the Swing GUI
 */

package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @brief This class creates a graphical interface for users to interact with
 */
public class App {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new MainFrame("SafeFoods");
				frame.setSize(1300, 500);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		
	}

}
