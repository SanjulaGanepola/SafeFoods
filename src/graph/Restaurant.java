/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: The Restaurant ADT is for storing
 * data of restaurants
 */

package graph;

/**
 * @brief Restaurant class that stores restaurant info
 */

public class Restaurant {
	private String restaurant;
	private double lat;
	private double lon;
	private String address;
	
	/**
 	 * @brief Constructor for Restaurant object
 	 * @param restaurant Restaurant name
 	 * @param lat latitude coordinate
 	 * @param lon longitude coordinate
 	 * @param address Address of restaurant
 	 */
	public Restaurant(String restaurant, double lat, double lon, String address) {
		this.restaurant = restaurant;
		this.lat = lat;
		this.lon = lon;
		this.address = address;
	}
	
	/**
 	 * @brief address getter
	 * @return String address of restaurant
 	 */
	public String address() {
		return this.address;
	}
	
	/**
 	 * @brief restaurant name getter
	 * @return String name of restaurant
 	 */
	public String restaurant() {
		return this.restaurant;
	}
	
	/**
 	 * @brief latitude getter
	 * @return double value of latitude 
 	 */
	public double lat() {
		return this.lat;
	}
	
	/**
 	 * @brief longitude getter
	 * @return double value of longitude
 	 */
	public double lon() {
		return this.lon;
	}
	
}
