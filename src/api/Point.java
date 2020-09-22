/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is to store information parsed from
 * the json file, being the set of coordinates that is returned
 */

package api;

/**
 * @brief Store information from the point type parsed from a json file
 * @detail This class does not have a constructor, but has getter and setter.
 */
public class Point
{
    private String[] coordinates;

    /**
     * @brief Getter for coordinates
     * @detail retreive the longitude and latitude coordinates
     * @return String array containing latitude and longitude
     */
    public String[] getCoordinates ()
    {
        return coordinates;
    }

    /**
     * @brief Setter for coordinates
     * @param array of Strings to set coordinates
     */
    public void setCoordinates (String[] coordinates)
    {
        this.coordinates = coordinates;
    }

}