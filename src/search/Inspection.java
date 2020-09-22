/**
 * Author: SafeFoods
 * Revised: April 8, 2020
 * 
 * Description: The Inspection ADT is for storing the data
 * of a restaurants inspection.
 */
 
 package search;

import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * @brief Inspection ADT that stores the information regarding a restaurants inspection.
 */
public class Inspection implements Comparable<Inspection> {

    private String activityDate;
    private String facilityName;
    private String facilityAddress;
    private String facilityCity;
    private String facilityZip;
    private int score;
    private String grade;

    /**
     * @brief Inspection ADT constructor.
     * @param date The data of the violation.
     * @param name The name of the restaurant.
     * @param address The address of the restaurant.
     * @param city The city of the restaurant.
     * @param zip The zip code of the restaurant.
     * @param score The score out of 100 of a restaurant.
     * @param grade The letter grade of the restaurant.
     */
    public Inspection(String date, String name, String address, String city, String zip, int score, String grade) {
        this.activityDate = date;
        this.facilityName = name;
        this.facilityAddress = address;
        this.facilityCity = city;
        this.facilityZip = zip;
        this.score = score;       
        this.grade = grade;
    }
    /**
     * @brief Given a csv file of inspections of many restaurants, return an array of Inspection.
     * The csv parser was based on an implementation by Stack Overflow - Bart Kiers CSV Parser.
     * @param inspectionFile The file of the inspections.
     * @exception IOException Generated if the bufferReader fails to read the file.
     * @return The array of inspections of all restaurants.
     */
    public static Inspection [] inspectionFileToArray(File inspectionFile) throws IOException {
        //Store inspections
        ArrayList<Inspection> inspections = new ArrayList<Inspection>();
        
        try {
		    String line;
		
		    BufferedReader br = new BufferedReader(new FileReader(inspectionFile));
		    String otherThanQuote = " [^\"] ";
	        String quotedString = String.format(" \" %s* \" ", otherThanQuote);
	        String regex = String.format("(?x) "+ // enable comments, ignore white spaces
	                ",                         "+ // match a comma
	                "(?=                       "+ // start positive look ahead
	                "  (?:                     "+ //   start non-capturing group 1
	                "    %s*                   "+ //     match 'otherThanQuote' zero or more times
	                "    %s                    "+ //     match 'quotedString'
	                "  )*                      "+ //   end group 1 and repeat it zero or more times
	                "  %s*                     "+ //   match 'otherThanQuote'
	                "  $                       "+ // match the end of the string
	                ")                         ", // stop positive look ahead
	                otherThanQuote, quotedString, otherThanQuote);
        
            //Read the header of the file
            br.readLine();

            //Read until file is empty
		    while ((line = br.readLine()) != null) {
                //Split by commas
                String[] fields = line.split(regex, -1);

                //Create inspection object using data read
                Inspection i = new Inspection(fields[0],fields[1],fields[2],fields[3],fields[4],Integer.parseInt(fields[5]),fields[6]);
                
                //Add Inspection object to arraylist
                inspections.add(i);
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }	

        //Convert arraylist to an array
        Inspection [] inspectionArray = new Inspection[inspections.size()];
        for (int i =0; i < inspections.size(); i++) {
            inspectionArray[i] = inspections.get(i); 
        }

        return inspectionArray;        
    }

    /**
     * @brief Return the restaurant's violation date.
     * @return Inspection activity date.
     */
    public String getActivityDate() {
        return this.activityDate;
    } 

    /**
     * @brief Return the restaurant's name.
     * @return Restaurant's name.
     */
    public String getFacilityName() {
        return facilityName;
    }

    /**
     * @brief Return the restaurant's address.
     * @return Restaurant's address.
     */
    public String getFacilityAddress() {
        return facilityAddress;
    }

    /**
     * @brief Return the restaurant's city.
     * @return Restaurant's city.
     */
    public String getFacilityCity() {
        return facilityCity;
    }

    /**
     * @brief Return the restaurant's zip code.
     * @return Restaurant's zip code.
     */
    public String getFacilityZip() {
        return facilityZip;
    }

    /**
     * @brief Return the restaurant's score.
     * @return Restaurant's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * @brief Return the restaurant's letter grade.
     * @return Restaurant's letter grade.
     */
    public String getGrade() {
        return grade;
    }
    
    /**
     * @brief Return a String representing a Inspection object.
     * @return String that has the information of a inspection.
     */
    public String toString() {
        return activityDate + "," + facilityName + "," + facilityAddress + "," + facilityCity + "," + facilityZip + "," + score + "," + grade;
    }


    /**
     * @brief For comparing a two Inspection objects based on their restaurant
     * name.
     * @param o The Inspection object to compare to.
     * @return Return 1 if the current name is larger, -1 if
     * the other name is larger, 0 otherwise.
     */
    public int compareTo(Inspection o) {
        return facilityName.compareTo(o.getFacilityName());
    }
}