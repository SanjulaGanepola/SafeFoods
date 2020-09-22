/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is to store information parsed from
 * the json file, being the first array that is returned.
 */

package api;

import api.ResourceSets;

/**
 * @brief Store information from the first array block of the json file.
 * @detail This class does not have a constructor, but has getter and setter.
 */

public class Info {

	private ResourceSets[] resourceSets;

	 /**
     * @brief Getter for resource set
     * @detail The first large array parsed  from a json file
     * @return array of ResourceSets
     */
	public ResourceSets[] getResourceSets() {
		return resourceSets;
	}

	/**
     * @brief Setter for resource set
     * @param resourceSets array of ResourceSets
     */
	public void setResourceSets(ResourceSets[] resourceSets) {
		this.resourceSets = resourceSets;
	}

}