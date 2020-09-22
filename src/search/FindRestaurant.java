/** 
*   @file FindRestaurant.java
//  @author Jasper Leung
*   @brief Module that contains the FindRestaurant class
*   @date April 9, 2020
**/

package search;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sort.Quicksort;

/**
 * @brief Module that contains the FindRestaurant class
 * @details searches the market health violations and inspections data set for restaurants 
 * corresponding to a user inputed name and stores two array lists as class variables, one for 
 * the violations of restaurants with the same name and one for inspections.
 * Each index of the array lists represents 1 address of each restaurant.
 */
public class FindRestaurant {
	
	private static ArrayList<ArrayList<Violation>> allRestaurantViolations = new ArrayList<ArrayList<Violation>>();
	private static ArrayList<ArrayList<Inspection>> allRestaurantInspections = new ArrayList<ArrayList<Inspection>>();
	private static boolean found = true;
	
	/**
     * @brief constructor for FindRestaurant class
     * @details allRestaurantViolations and allRestaurantsInspections array lists are created,
     * containing all the restaurants corresponding to the user's search and their violation
     * and inspection history respectively
     * @param restaurantSearch: the name of the restaurant to be searched for
     * @param violationsFile: the csv file which contains the violation history of restaurants
     * @param inspectionsFile: the csv file which contains the inspection history of restaurants
     */
	public FindRestaurant(String restaurantSearch, File violationsFile, File inspectionsFile) throws IOException {

		Violation [] v = Violation.violationFileToArray(violationsFile);
		Inspection [] in = Inspection.inspectionFileToArray(inspectionsFile);
		Quicksort.sort(v);
		Quicksort.sort(in);

		ArrayList<Violation> violations = new ArrayList<Violation>();
		ArrayList<Inspection> inspections= new ArrayList<Inspection>();

		StringBinarySearch sbs = new StringBinarySearch(getViolationFacilityNames(v),restaurantSearch);
		StringBinarySearch sbs2 = new StringBinarySearch(getInspectionFacilityNames(in),restaurantSearch);

		int index = sbs.getIndex();
		int index2 = sbs2.getIndex();
		
		//if the index is -1, the restaurant name wasn't found in the violation array
		//if index2 is -1, the restaurant name wasn't found in the inspection array
		//note that this loop omits restaurants with violations but no inspections
		if (index2 !=-1) {
			if (index !=-1)
				violations.add(v[index]);
			inspections.add(in[index2]);

			boolean end = false;
			int count = 1;

			if (index !=-1) {
				//must check other rows in the dataset that share the same restaurant name
				//check lower indexes in violations array
				while(!end) {
					if (index !=0) {
						if (v[index - count].getFacilityName().equals(v[index].getFacilityName())) {
							violations.add(v[index - count]);
							count+=1;
						}
						else {
							end = true;
						}
					}
					else
						end = true;
				}

				//check higher indexes in violations array
				end = false;
				count = 1;
				while(!end) {
					if (index !=v.length-1) {
						if (v[index + count].getFacilityName().equals(v[index].getFacilityName())) {
							violations.add(v[index + count]);
							count+=1;
						}
						else
							end = true;
					}
					else
						end = true;
				}
				end = false;
				count = 1;
			}

			//check lower indexes in inspections array
			end = false;
			count = 1;
			while(!end) {
				if (index2 !=0) {
					if (in[index2 - count].getFacilityName().equals(in[index2].getFacilityName())) {
						inspections.add(in[index2 - count]);
						count+=1;
					}
					else {
						end = true;
					}
				}
				else
					end = true;
			}
			end = false;
			count = 1;
			//check higher indexes in inspections array
			while(!end) {
				if (index2 !=in.length-1) {
					if (in[index2 + count].getFacilityName().equals(in[index2].getFacilityName())) {
						inspections.add(in[index2 + count]);
						count+=1;
					}
					else {
						end = true;
					}
				}
				else
					end = true;
			}
			
			//here, find all the addresses corresponding to restaurant name
			//by using map to keep track of addresses, prevents duplicate addresses
			Map<String, Integer> Addresses = new HashMap<String,Integer>();
			for (int i = 0; i < violations.size(); i ++) {
				Addresses.put(violations.get(i).getFacilityAddress(), i);
			}
			for (int i = 0; i < inspections.size(); i ++) {
				Addresses.put(inspections.get(i).getFacilityAddress(), i);
			}
			
			//VIOLATIONS AND INSPECTIONS STORED INTO THESE ARRAYLISTS BASED ON THEIR ADDRESS
			for (String Address: Addresses.keySet()) {
				ArrayList<Violation> restaurantViolations = new ArrayList<Violation>();
				for (int j = 0; j < violations.size(); j++) {
					if (violations.get(j).getFacilityAddress().equals(Address)) {
						restaurantViolations.add(violations.get(j));
					}
				}
				ArrayList<Inspection> restaurantInspections = new ArrayList<Inspection>();
				for (int k = 0; k < inspections.size(); k++) {
					if (inspections.get(k).getFacilityAddress().equals(Address)) {
						restaurantInspections.add(inspections.get(k));
					}
				}
				if (restaurantInspections.size() != 0) {
					allRestaurantViolations.add(restaurantViolations);
					allRestaurantInspections.add(restaurantInspections);
					found = true;
				}
			}
		}
		else
			found = false;
	}
	
	/**
     * @brief Getter method for the 'found' state variable
     * @return found: if found is true, the searched restaurant name was found in the data set
     */
	public boolean getFound(){
		return found;
	}
	
	/**
     * @brief Getter method for the allRestaurantViolations state variable
     * @return allRestaurantViolations: an arraylist of Violations that contains all the 
     * violations of restaurants corresponding to the searched name
     */
	public ArrayList<ArrayList<Violation>> getViolationArrayList(){
		return allRestaurantViolations;
	}
	
	/**
     * @brief Getter method for the allRestaurantInpections state variable
     * @return allRestaurantInspections: an arraylist of Inspections that contains all the 
     * inspections of restaurants corresponding to the searched name
     */
	public ArrayList<ArrayList<Inspection>> getInspectionArrayList(){
		return allRestaurantInspections;
	}
	
	/**
     * @brief local function takes the violations read from the dataset 
     * and returns a string arraylist of their names
     * @param v, unorganized array of violations read from the dataset
     * @return s, a String arraylist containing the restaurant names
     */
	private ArrayList<String> getViolationFacilityNames(Violation[] v) {
		ArrayList<String> s = new ArrayList<String>();
		for (int i = 0; i < v.length; i ++) {
			s.add(v[i].getFacilityName());
		}
		return s;
	}
	
	/**
     * @brief local function takes the inspections read from the dataset 
     * and returns a string arraylist of their names
     * @param i, unorganized array of inspections read from the dataset
     * @return s, a String arraylist containing the restaurant names
     */
	private ArrayList<String> getInspectionFacilityNames(Inspection[] i) {
		ArrayList<String> s = new ArrayList<String>();
		for (int j = 0; j < i.length; j ++) {
			s.add(i[j].getFacilityName());
		}
		return s;
	}
	
}
