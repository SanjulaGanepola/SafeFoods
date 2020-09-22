/**
 * Author: SafeFoods
 * Revised: April 8, 2020
 * 
 * Description: This module is used to perform quicksort on an array in
 * order to sort it.
 */

package sort;

/**
 * @brief Sorting an array using quicksort.
 */
public class Quicksort {

    /**
     * @brief Rearranges the array in ascending order, using the natural order.
     * @param a The array to be sorted.
     */
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    /**
     * @brief Quicksort the subarray from a[lo] to a[hi].
     * @param a The array to be sorted.
     * @param lo The lower index.
     * @param hi The higher index.
     */
    private static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    /**
     * @brief Partition the subarray a[lo..hi] so that 
     * a[lo..j-1] <= a[j] <= a[j+1..hi] and also return the index j.
     * @param a The array to be sorted.
     * @param lo The lower index.
     * @param hi The higher index.
     * @return The index of j.
     */
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) { 

            // find item on lo to swap
            while (less(a[++i], v)) {
                if (i == hi) break;
            }

            // find item on hi to swap
            while (less(v, a[--j])) {
                if (j == lo) break;      // redundant since a[lo] acts as sentinel
            }

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }
    
    /**
     * @brief Determine if v < w.
     * @param v The first Comparable object to compare.
     * @param w The Comparable object to compare to.
     * @return True if v < w, false otherwise.
     */
    private static boolean less(Comparable v, Comparable w) {
        if (v == w) return false;   // optimization when reference equals
        return v.compareTo(w) < 0;
    }
        
    /**
     * @brief Used to exchange a[i] and a[j].
     * @param a The array of objects where the exchange is going
     * to be done.
     * @param i The first index of the object in the array to exchange.
     * @param j The second index of the object in the array to exchange.
     */
    private static void exch(Object[] a, int i, int j) {
        //Temporary object
        Object swap = a[i];
        //Exchange
        a[i] = a[j];
        a[j] = swap;
    }
}