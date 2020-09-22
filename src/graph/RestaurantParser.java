/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is used to parse through a given file
 * and create a list of restaurant objects.
 */

package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import graph.Restaurant;

/**
 * @brief This class will create a list of restaurants based on a given
 * file input
 */
public class RestaurantParser {
	
	/**
     * @brief Create a list that contains all LA restaurants
     * @detail Taking the csv file as input, traverse through 
     * the whole file and parse data, specifically address,
     * longitude, latitude, and restuarant name. Then place this
     * information inside a restaurant object. 
     * The csv parser was based on an implementation by Stack Overflow - Bart Kiers CSV Parser.
     * @param restaurantFile file that contains restaurants in LA information 
     * @return List of all restaurants in LA
     * @throws IOException
     */	
	 public static ArrayList<Restaurant>  resFile(File restaurantFile) throws IOException {
		 	ArrayList<Restaurant> res = new ArrayList<Restaurant>();
	        
	        try {
			    String line;
			    BufferedReader br = new BufferedReader(new FileReader(restaurantFile));
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
	        
	            br.readLine();
	            
	            
			    while ((line = br.readLine()) != null) {
			   	    String[] fields = line.split(regex, -1);	//split the string
			   	    
			   	    if (fields[14].length() != 0 && fields[2].length() != 0 && fields[3].length() != 0 ) {		//check if the size of the string is not 0
			   	    	String part1 = fields[14].substring(1);		//substring to retrieve correct data
			   	    	String part2 = part1.substring(1, part1.length() - 2);
			   	    	String[] part3 = part2.split(",");
			   	    	
			   	    	
			   	    	res.add(new Restaurant(fields[2], Double.parseDouble(part3[0]), Double.parseDouble(part3[1]), fields[3])); //create restaurant object with fields
			   	    }
			   	    else {
			   	    	continue;
			   	    }
	            }
	            br.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }	

	        return res;        
	    }
	 
}
