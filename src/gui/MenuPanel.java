/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module represents the MenuPanel component for the GUI
 */

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;  
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

import graph.TripPlanner;
import search.FindRestaurant;
import sort.Score;
import gui.MenuEvent;
import gui.MenuListener;

/**
 * @brief Module that represents the MenuPanel component for the GUI
 */
public class MenuPanel extends JPanel {
	
	private static final long serialVersionUID = -7079857545281001908L;
	private EventListenerList listenerList = new EventListenerList();
	private String newLine = "\n";
	private boolean validAddress = false;
	private String startZip;
	private String startCity;
	private String startStreet;
	private String destZip;
	private String destCity;
	private String destStreet;
	private int numRest = 1;
	private File violationsFile = new File("Clean Restaurant and Market Health Violations.csv");
	private File inspectionsFile = new File("Clean Restaurant Inspections.csv");
	
	/**
     * @brief Constructor for MaiuPanel class
     * @details Creates and adds the necessary components for the panel
     */
	public MenuPanel() {
		
		Dimension size = getPreferredSize();
		size.width = 400;
		setPreferredSize(size);
		
		setBorder(BorderFactory.createTitledBorder("Main menu"));
		
		JLabel planner = new JLabel("Trip Planner");
		
		JLabel restaurantLabel = new JLabel("Restaurant name: ");
		final JTextField restaurantField = new JTextField(14);
		JButton restaurantBtn = new JButton("Is My Food Safe?");
		
		JLabel startStreetLabel = new JLabel("Start street: ");
		final JTextField startStreetField = new JTextField(14);
		
		JLabel startZipLabel = new JLabel("Start zip code: ");
		final JTextField startZipField = new JTextField(14);
		
		JLabel startCityLabel = new JLabel("Start city: ");
		final JTextField startCityField = new JTextField(14);
		
		JLabel destStreetLabel = new JLabel("Destination street: ");
		final JTextField destStreetField = new JTextField(14);
		
		JLabel destZipLabel = new JLabel("Destination zip code: ");
		final JTextField destZipField = new JTextField(14);
		
		JLabel destCityLabel = new JLabel("Destination city: ");
		final JTextField destCityField = new JTextField(14);
		
		JLabel numRestLabel = new JLabel("Number of Restaurants: ");
		
		JButton addressBtn = new JButton("Save Trip");
		JButton clearBtn = new JButton("Clear Trip");
		
		JButton tripBtn = new JButton("Plan Trip");
		
	    JRadioButton one = new JRadioButton("1");
	    one.setActionCommand("1");
	    one.setSelected(true);
		one.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				numRest = 1;
			}
			
		});
		
	    JRadioButton two = new JRadioButton("2");
	    two.setActionCommand("2");
	    two.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				numRest = 2;
			}
			
		});
	    
	    // adds the 2 radio buttons into a ButtonGroup so only one can be selected at a time
	    ButtonGroup bg = new ButtonGroup();
	    bg.add(one);
	    bg.add(two);
		
		// adds an action whenever a certain button is pressed
		restaurantBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				String restaurant = restaurantField.getText();
				
				if (restaurant.equals(""))
					fireMenuEvent(new MenuEvent(this, "Please enter a restaurant name.\n"));
				else
					try {
						output(restaurant.toUpperCase());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	
			}
			
		});
		
		tripBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				validAddress = validAddresses(startZipField.getText(), 
						startStreetField.getText(), 
						startCityField.getText(), 
						destZipField.getText(), 
						destStreetField.getText(), 
						destCityField.getText());
				
				if (!validAddress) 
						fireMenuEvent(new MenuEvent(this, "Please enter a valid trip.\n"));
				
				else {
					try {
						fireMenuEvent(new MenuEvent(this, TripPlanner.planTrip(startStreet, startCity, startZip, destStreet, destCity, destZip, numRest)));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			
		});
		
		addressBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				validAddress = validAddresses(startZipField.getText(), 
						startStreetField.getText(), 
						startCityField.getText(), 
						destZipField.getText(), 
						destStreetField.getText(), 
						destCityField.getText());
				
				if (validAddress)
					fireMenuEvent(new MenuEvent(this, "Trip saved.\n"));
				else
					fireMenuEvent(new MenuEvent(this, "Please enter non-empty addresses.\n"));
			}
			
		});
		
		clearBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				validAddress = false;
				fireMenuEvent(new MenuEvent(this, "Trip cleared.\n"));
			}
		});
		
		// determines the location of components on the panel
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 0;
		add(restaurantLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 0;
		add(restaurantField, gc);
		
		gc.weighty = 10;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = 1;
		gc.gridy = 3;
		add(restaurantBtn, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 4;
		add(planner, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 5;
		add(startStreetLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 5;
		add(startStreetField, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 6;
		add(startZipLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 6;
		add(startZipField, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 7;
		add(startCityLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 7;
		add(startCityField, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 8;
		add(destStreetLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 8;
		add(destStreetField, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 9;
		add(destZipLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 9;
		add(destZipField, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 10;
		add(destCityLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 10;
		add(destCityField, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 11;
		add(numRestLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 11;
		add(one, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 2;
		gc.gridy = 11;
		add(two, gc);
		
		gc.weighty = 10;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = 1;
		gc.gridy = 14;
		add(addressBtn, gc);
		
		gc.weighty = 10;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = 0;
		gc.gridy = 14;
		add(clearBtn, gc);
		
		gc.weighty = 10;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = 2;
		gc.gridy = 14;
		add(tripBtn, gc);
		
	}
	
	/**
     * @brief Setter method for the start zip code
     * @param zip The zip code of the starting address
     */
	private void setStartZip(String zip) {
		startZip = zip;
	}
	
	/**
     * @brief Setter method for the start street
     * @param street The street of the starting address
     */
	private void setStartStreet(String street) {
		startStreet = street;
	}
	
	/**
     * @brief Setter method for the start city
     * @param city The city of the starting address
     */
	private void setStartCity(String city) {
		startCity = city;
	}
	
	/**
     * @brief Setter method for the destination zip code
     * @param zip The zip code of the destination address
     */
	private void setDestZip(String zip) {
		destZip = zip;
	}
	
	/**
     * @brief Setter method for the destination street
     * @param street The street of the destination address
     */
	private void setDestStreet(String street) {
		destStreet = street;
	}
	
	/**
     * @brief Setter method for the destination city
     * @param city The city of the destination address
     */
	private void setDestCity(String city) {
		destCity = city;
	}
	
	/**
     * @brief Sets the instance variables to user inputed values and finds if 2 addresses are non-empty
     * @param sZ Zip code of the starting address
     * @param sS Street of the starting address
     * @param sC City of the starting address
     * @param dZ Zip code of the destination address
     * @param dS Street of the destination address
     * @param dC City of the destination address
     * @return True if the addresses are valid, false if not
     */
	private boolean validAddresses(String sZ, String sS, String sC, String dZ, String dS, String dC) {
		setStartZip(sZ);
		setStartStreet(sS);
		setStartCity(sC);
		setDestZip(dZ);
		setDestStreet(dS);
		setDestCity(dC);
		return startZip.equals("") ? false : 
			startStreet.equals("") ? false : 
				startCity.equals("") ? false : 
					destZip.equals("") ? false : 
						destStreet.equals("") ? false :
							destCity.equals("") ? false : true;
	}
	
	/**
     * @brief Outputs the violations and inspections of a restaurant
     * @param restaurant The restaurant the user wishes to inspect
     */
	private void output(String restaurant) throws IOException {

		FindRestaurant fr = new FindRestaurant(restaurant, violationsFile, inspectionsFile);
		boolean found1 = fr.getFound();
		System.out.println(restaurant);
		System.out.println(found1);
		String details = "";
		
        if (found1 == true) {
            
            for (int i = 0; i < fr.getViolationArrayList().size(); i ++) {
                
                details += fr.getInspectionArrayList().get(i).get(0).getFacilityName() + ", ";
                details += fr.getInspectionArrayList().get(i).get(0).getFacilityAddress() + newLine + newLine;
                
                details += "VIOLATIONS:" + newLine;
                boolean noViolations = fr.getViolationArrayList().get(i).size() > 0 ? false : true;
                
                if (noViolations)
                    details += "None" + newLine;
                
                else {
                    details += String.format("%-47s %s - %s","Date", "Service Type", "Violation Description") + newLine;
                    for (int j = 0; j < fr.getViolationArrayList().get(i).size(); j ++) {
                        details += String.format("%-30s %s - %s", 
                                fr.getViolationArrayList().get(i).get(j).getActivityDate(), 
                                fr.getViolationArrayList().get(i).get(j).getServiceDescription(), 
                                fr.getViolationArrayList().get(i).get(j).getViolationDescription()) 
                                + newLine;
                    }             
                }
                
                details += newLine + "INSPECTIONS:" + newLine;
                details += String.format("%-20s %s", "Date", "Grade") + newLine;
                
                for (int k = 0; k < fr.getInspectionArrayList().get(i).size(); k ++) {
                    details += String.format("%-15s %s", fr.getInspectionArrayList().get(i).get(k).getActivityDate(), 
                    		fr.getInspectionArrayList().get(i).get(k).getGrade()) + newLine;
                }
                
                details += newLine + "SafeFoodsScore: " + Score.safeFoodScore(fr.getViolationArrayList().get(i), 
                        fr.getInspectionArrayList().get(i)) + newLine + newLine;
                
            }
            
        }
			
		else
			details += "Restaurant not found." + newLine;
		
		fireMenuEvent(new MenuEvent(this, details));
		
	}
	
	/**
     * @brief Outputs a string in the GUI
     * @param even A MenuEvent object
     */
	public void fireMenuEvent(MenuEvent event) {
		
		Object[] listeners = listenerList.getListenerList();
		
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == MenuListener.class)
				((MenuListener) listeners[i + 1]).menuEventOccured(event);
		}
		
	}
	
	/**
     * @brief Addds a MenuListener to instance variable of a list of listeners
     * @param listener A MenuListener object
     */
	public void addMenuListener(MenuListener listener) {
		listenerList.add(MenuListener.class, listener);
	}

}
