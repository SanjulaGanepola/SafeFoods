/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is to store information parsed from
 * the json file, being the second array that is returned
 */

package api;

import api.Resources;

/**
 * @brief Store information from the second array block in the json file
 * @detail This class does not have a constructor, but has getter and setter.
 */
public class ResourceSets
{
    private Resources[] resources;

    /**
     * @brief Getter for resources
     * @detail The second large array parsed from a json file
     * @return array of resources
     */
    public Resources[] getResources ()
    {
        return resources;
    }

    /**
     * @brief Setter for resources
     * @param resources array of resources
     */
    public void setResources (Resources[] resources)
    {
        this.resources = resources;
    }

}
