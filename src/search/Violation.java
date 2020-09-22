/**
 * Author: SafeFoods
 * Revised: April 8, 2020
 * 
 * Description: The Violation ADT is for storing the data
 * of a restaurants violation.
 */

package search;

import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * @brief Violation ADT that stores the information regarding a restaurants violation.
 */
public class Violation implements Comparable<Violation> {

    private String activityDate;
    private String facilityZip;
    private String facilityAddress;
    private String facilityName;
    private String violationCode;
    private String serviceDescription;
    private int points;
    private String grade;
    private int score;
    private String violationDescription;

    /**
     * @brief Violation ADT constructor.
     * @param date The date of the violation.
     * @param zip The zip code of the restaurant.
     * @param address The address of the restaurant.
     * @param name The name of the restaurant.
     * @param code The violation code broken.
     * @param service The service description.
     * @param points The number of points lost.
     * @param grade The letter grade of the restaurant.
     * @param score The score out of 100 of a restaurant.
     * @param violation The violation description.
     */
    public Violation(String date, String zip, String address, String name, String code, String service, int points, String grade, int score, String violation) {
        this.activityDate = date;
        this.facilityZip = zip;
        this.facilityAddress = address;
        this.facilityName = name;
        this.violationCode = code;
        this.serviceDescription = service;
        this.points = points;
        this.grade = grade;
        this.score = score;
        this.violationDescription = violation; 
    }

    /**
     * @brief Given a csv file of violations of many restaurants, return an array of Violation.
     * The csv parser was based on an implementation by Stack Overflow - Bart Kiers CSV Parser.
     * @param violationFile The file of the violations.
     * @exception IOException Generated if the bufferReader fails to read the file.
     * @return The array of violations of all restaurants.
     */
    public static Violation [] violationFileToArray(File violationFile) throws IOException {
        //Store violations
        ArrayList<Violation> violations = new ArrayList<Violation>();

        try {
		    String line;
		
		    BufferedReader br = new BufferedReader(new FileReader(violationFile));
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
                   
                //Create Violation object using data read
                Violation v = new Violation(fields[0],fields[1],fields[2],fields[3],fields[4],fields[5],Integer.parseInt(fields[6]),fields[7],Integer.parseInt(fields[8]), fields[9]);
                
                //Add Violation object to arraylist
                violations.add(v);
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }	

        //Convert arraylist to an array
        Violation [] violationArray = new Violation[violations.size()];
        for (int i =0; i < violations.size(); i++) {
            violationArray[i] = violations.get(i); 
        }

        return violationArray;
    }

    /**
     * @brief Return the restaurant's violation date.
     * @return Violation activity date.
     */
    public String getActivityDate() {
        return this.activityDate;
    }

    /**
     * @brief Return the restaurant's zip code.
     * @return Restaurant's zip code.
     */
    public String getFacilityZip() {
        return facilityZip;
    }

    /**
     * @brief Return the restaurant's address.
     * @return Restaurant's address.
     */
    public String getFacilityAddress() {
        return facilityAddress;
    }

    /**
     * @brief Return the restaurant's name.
     * @return Restaurant's name.
     */
    public String getFacilityName() {
        return facilityName;
    }

    /**
     * @brief Return the restaurant's violation code.
     * @return Restaurant's violation code.
     */
    public String getViolationCode() {
        return violationCode;
    }

    /**
     * @brief Return the violation description.
     * @return Restaurant's violation description.
     */
    public String getViolationDescription() {
        return violationDescription;
    }

    /**
     * @brief Return the violation service description.
     * @return Restaurant service description.
     */
    public String getServiceDescription() {
        return serviceDescription;
    }  

    /**
     * @brief Return the restaurant's points lost.
     * @return Restaurant's points lost.
     */
    public int getPoints() {
        return points;
    }

    /**
     * @brief Return the restaurant's letter grade.
     * @return Restaurant's letter grade.
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @brief Return the restaurant's score.
     * @return Restaurant's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * @brief Return a String representing a Violation object.
     * @return String that has the information of a violation.
     */
    public String toString() {
        return activityDate + "," +facilityZip+ "," +facilityAddress+ "," +facilityName + ","  +violationCode+ "," +serviceDescription+ "," +points+ "," +grade+ "," +score+"," +violationDescription;
    }

    /**
     * @brief For comparing a two Violation objects based on their restaurant
     * name.
     * @param o The Violation object to compare to.
     * @return Return 1 if the current name is larger, -1 if
     * the other name is larger, 0 otherwise.
     */
    public int compareTo(Violation o) {
        return facilityName.compareTo(o.getFacilityName());
    }
}