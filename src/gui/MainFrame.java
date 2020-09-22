/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module acts as the controller for the GUI
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import gui.MenuPanel;
import gui.MenuEvent;
import gui.MenuListener;

/**
 * @brief Module that contains the MainFrame class
 * @details Creates and adds the necessary components that comprise of the GUI.
 * 			The components are an EditorPane, ScrollPane, and MenuPanel
 */
public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -3188591488628667286L;
	private MenuPanel menuPanel;
	private int length = 0;
	
	
	/**
     * @brief Constructor for MainFrame class
     * @details Creates and adds the necessary components for the GUI
     * @param title Title of the GUI
     */
	public MainFrame(String title) {
		super(title);
		
		setLayout(new BorderLayout());
		
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(true);
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(250, 145));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
		
		menuPanel = new MenuPanel();
		
		menuPanel.addMenuListener(new MenuListener() {
			public void menuEventOccured(MenuEvent event) {
				
				String text;
				// the following is a desperate attempt to fix a mistake which I have no idea how I caused
				if (length == 0 || event.getText().length() < 40) {
					text = event.getText();
					if (event.getText().length() > 40)
						length = event.getText().length();
				}
				else {
					text = event.getText().substring(length);
					length += event.getText().length() - length;
				}
				
				editorPane.setText(text);;
				
			}
		});
		
		// Add Swing components to content pane
		
		Container c = getContentPane();
		c.add(editorScrollPane, BorderLayout.CENTER);
		c.add(menuPanel, BorderLayout.WEST);
		
	}
	
}
