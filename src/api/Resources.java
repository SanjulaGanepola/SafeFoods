/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is to store information parsed from
 * the json file, being the point type that is returned.
 */

package api;

import api.Point;

/**
 * @brief Store information from the Resources array, being the point type
 * parsed from a json file.
 * @detail This class does not have a constructor, but has getter and setter.
 */
public class Resources
{

    private Point point;

    
    /**
     * @brief Getter for Point
     * @detail retreive the point
     * @return Point type
     */
    public Point getPoint ()
    {
        return point;
    }

    /**
     * @brief Setter for Point
     * @param point of type Point
     */
    public void setPoint (Point point)
    {
        this.point = point;
    }

    
}
