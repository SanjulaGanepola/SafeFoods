/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is used to search the web for an 
 * API request.
 */
package graph;

import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import com.google.gson.*;

import api.Info;
import graph.Restaurant;

/**
 * @brief This class will fetch the API results, and parse the resulting 
 * json file into a Restaurant object.
 */
public class WebSearch {

    /**
     * @brief Search the web for coordinates, based on user input
     * @detail Taking the strings, and using them to create a URL 
     * that will be used to make a GET request. Then using a String buffer, the 
     * data is returned as a json file, and parsed into a restaurant object. 
     * @param zip String representing zipcode
     * @param city String representing city
     * @param add String representing address
     * @return Restaurant object with data from user input
     * @throws MalformedURLException 
     */
	public static Restaurant fetchAPI(String zip, String city, String add) throws MalformedURLException {
		Restaurant newRes;
		String addy = add;
		if (city.contains(" ")) {	// if the city contains spaces, replace them with a + 
			city = city.replace(" ", "+");
		}
		if (add.contains(" ")) {	// if the address contains spaces, replace them with a +
			addy = add.replace(" ", "+"); 
		}	//these are replaced with a '+' so that the URL is in correct format as '+' represent spaces
		URL url = new URL( //create a url with the user input in the correct format
				"http://dev.virtualearth.net/REST/v1/Locations/US/CA/"+ zip + "/" + city + "/" + addy + "?o=json&key=AtZam4iKGy3WLVsVmr67w5NQj3gGBdwTGaw2F_mbRseTr8kCutdTfqg8eHWdcKNo");
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); //initialize a connection
			con.setRequestMethod("GET"); //create a GET request
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); //BufferedReader object to read input stream from connection
			String inputLine;
			StringBuffer content = new StringBuffer(); 
			while ((inputLine = in.readLine()) != null) { //read all the lines of the json file
				content.append(inputLine); //put the current line read in string buffer
			}
			in.close();
			Gson g = new Gson(); //create new Gson object from google class
			Info loc = g.fromJson(content.toString(), Info.class); //Parse information from Json file into correct format using Info object
			newRes = new Restaurant(" ",  
				Double.parseDouble(loc.getResourceSets()[0].getResources()[0].getPoint().getCoordinates()[0]), 
				Double.parseDouble(loc.getResourceSets()[0].getResources()[0].getPoint().getCoordinates()[1]), 
				add); //create new restaurant object, with correct latitude and longitude retreived from API request
			return newRes;
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		newRes = new Restaurant(" ", 1.2, 3.4, " "); // if we cannot access the API database, use default information
		return newRes;
	}
}
