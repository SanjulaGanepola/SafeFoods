/** 
*   @file StringBinarySearch.java
//  @author Jasper Leung
*   @brief Module that contains the StringBinarySearch class
*   @date April 9, 2020
**/

package search;

import java.util.ArrayList;

/** 
*   @brief Module that contains the FindRestaurant driver
**/
class StringBinarySearch { 
  
    // Returns index of x if it is present in arr[], 
    // else return -1 
	public int index;
	
	/**
	 * @brief constructor for StringBinarySearch class
	 * @details calls binarysearch method and sets the value of index to the index where x is found in the String array list
	 * @param S, the array list of strings to be searched through
	 * @param x, the String to be searched for
	 */
	public StringBinarySearch(ArrayList<String> S, String x) {
		index = binarySearch(S, x);
	}
	
	/**
	 * @brief method which uses the binary search algorithm to search for the string x in array list s
	 * @param S, the array list of strings to be searched through
	 * @param x, the String to be searched for
	 * @return the index where x is found
	 * @return -1 if x is not found in S
	 */
    public static int binarySearch(ArrayList<String> S, String x) { 
        int l = 0, r = S.size() - 1; 
        while (l <= r) { 
            int m = l + (r - l) / 2; 
            
            int res = x.compareTo(S.get(m)); 
  
            if (res == 0) 
                return m; 
 
            if (res > 0) 
                l = m + 1; 
  
            else
                r = m - 1; 
        } 
  
        return -1; 
    } 
    
    /**
	 * @brief getter method for index state variable
	 * @return index, the index where x is found
	 */
    public int getIndex() {
    	return index;
    }
} 
