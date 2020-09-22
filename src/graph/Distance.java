/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is used to calculate the distance between
 * two coordinates on the globe
 */

package graph;

/**
 * @brief This class will caluclate the distance between two points
 */
public class Distance {

	private static final int EARTH_RADIUS = 6371; // Earth radius stored as constant

	/**
     * @brief Calculates the distance between two set of latitude and longitude points
     * @detail Calcualtes the distance based on a formula that utilizes
     * many methods of the math library
     * @param lat1 latitude 1
     * @param lon1 longitude 1
     * @param lat1 latitude 2
     * @param long2 longitude 2
     * @return distance between two input points
     */
	public static double distance(double lat1, double long1, double lat2, double long2) { 
		double phi1 = Math.toRadians(lat1);
		double phi2 = Math.toRadians(lat2);

		double diffLat = Math.toRadians(lat2 - lat1);
		double diffLon = Math.toRadians(long2 - long1);
		
		double a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2)  //distance formula
				+ Math.cos(phi1) * Math.cos(phi2) * Math.sin(diffLon / 2) * Math.sin(diffLon / 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		double distance = EARTH_RADIUS * c;
		String strDouble = String.format("%.5f", distance); //round the resulting distance to 5 decimal numbers
		return Double.parseDouble(strDouble); //return string to double
	}
	

}
