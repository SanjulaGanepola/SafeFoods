/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This TripPlaner module is designed to take a source and
 * desitnation address and plan the shortest route through the restaurants.
 * The number of restaurants (either 1 or 2) to pass through can be choosen.
 */

package graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import edu.princeton.cs.algs4.*;
import graph.DijkstraUndirectedSP;
import graph.EdgeWeightedGraph;
import graph.Restaurant;
import graph.RestaurantParser;
import graph.WebSearch;

/**
 * @brief Plan the shortest trip between a source and destination address 
 * that also goes through a set number of restaurants (1 or 2).
 */

public class TripPlanner {

    /**
     * @brief Given the address, city, and zip code of two addresses plan
     * a trip that starts at address 1 and ends at address 2 and also
     * goes through the restaurants. The number of restaurants (either
     * 1 or 2) is also inputted which represents the number of restaurants
     * that should be passed through on the path to the final destination
     * @param add1 Address 1.
     * @param city1 City 1.
     * @param zip1 Zip 1.
     * @param add2 Address 2.
     * @param city2 City 2.
     * @param zip2 Zip 2.
     * @param numRestaurants An integer value of either 1 or 2 represeting 
     * the number of restaurants to pass through.
     * @throws IOException 
     */
    public static String planTrip(String add1, String city1, String zip1, String add2, String city2, String zip2, int numRestaurants) throws IOException {

        try { 
            Integer.parseInt(zip1);
            Integer.parseInt(zip2); 
        } catch (NumberFormatException nfe) { 
            return "Zip code must not contain letters!";
        } 
        String trip = "";

        ArrayList<Restaurant> res = RestaurantParser.resFile(new File("Restaurants in LA.csv")); //create a list of all the restaurants in LA
        
        Restaurant Location1 = WebSearch.fetchAPI(zip1, city1, add1); //fetch longitude and latitude for the two addresses input
        Restaurant Location2 = WebSearch.fetchAPI(zip2, city2, add2);
        
        if(numRestaurants == 1) { //if the number of restaurants input by the user is 1
        	EdgeWeightedGraph G = EdgeWeightedGraph.createGraph(res, 2); //create an edgeweightedgraph
            G = EdgeWeightedGraph.addTwoAddress(G, Location1, Location2);  //add the two addresses to the graph
            DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, EdgeWeightedGraph.map(Location1)); //apply graph algorithm on edgeweightedgraph where the source is the first location 
            Stack<Edge> shortestPath = sp.pathTo(EdgeWeightedGraph.map(Location2)); //find shortest path to location 2 from location 1
            
            trip += ("Starting Address: " + Location1.address() + "\n"); //build string for formatted output
            trip += pathToString(shortestPath, Location1, Location2);
            trip += ("Destination Address: " + Location2.address());

        } else if(numRestaurants == 2) { //if the number of restaurants input by the user is 2
            double [] latLot = midPoint(Location1.lat(), Location1.lon(), Location2.lat(), Location2.lon()); //double array that contains this information
            
            Restaurant Location3 = new Restaurant("", latLot[0],latLot[1],""); //restaurant object for midpoint between two locatiosn input
            EdgeWeightedGraph G1 = EdgeWeightedGraph.createGraph(res, 3); //create an edgeweightedgraph 
            
            G1 = EdgeWeightedGraph.addTwoAddress(G1, Location1, Location3); //add locations to graph
            
            G1 = EdgeWeightedGraph.addOneAddress(G1, Location2); 
            
            DijkstraUndirectedSP sp1 = new DijkstraUndirectedSP(G1, EdgeWeightedGraph.map(Location1)); //apply graph algorithm where location 1 is starting point
            DijkstraUndirectedSP sp2 = new DijkstraUndirectedSP(G1, EdgeWeightedGraph.map(Location3)); //apply graph algorithm where the midpoint is the starting point
          
            Stack<Edge> shortestPath1 = sp1.pathTo(EdgeWeightedGraph.map(Location3)); //find shortest path from location 1 to midpoint
            Stack<Edge> shortestPath2 = sp2.pathTo(EdgeWeightedGraph.map(Location2)); //find shortest path from midpoint to location 2
            
            
            trip += ("Starting Address: " + Location1.address() + "\n");   //build string for formatted output
            String path1 = pathToString(shortestPath1, Location1, Location3); 
            String path2 = pathToString(shortestPath2, Location3, Location2);
            trip += path1;
            trip += path2;
            trip += ("Destination Address: " + Location2.address());
        }
        return trip;	//return formatted string
    }

    /**
     * @brief Given a stack of edges that correspond to the shortest path, return a string
     * that represents the entire path.
     * @param shortestPath The stack of edges making up the shortest path.
     * @return The String representing the stack of edges.
     */
    private static String pathToString(Stack<Edge> shortestPath, Restaurant Location1, Restaurant Location2) {
    	String trip = "";
    	int counter = 0;
        while (!shortestPath.isEmpty()) {   //traverse stack and print the correct restaurant that user will visit on trip  
            Edge E = shortestPath.pop();
            if (((E.either() != EdgeWeightedGraph.map(Location1)) || //make sure not to print the current location
                    (E.either() != EdgeWeightedGraph.map(Location2)) ||
                    (E.other(E.either()) != EdgeWeightedGraph.map(Location1)) ||
                    (E.other(E.either()) != EdgeWeightedGraph.map(Location2))) && counter != 1) {
                int edge = E.other(E.either());
                trip += ("Address: " + EdgeWeightedGraph.revMap(edge).address() + ", Restaurant: " + EdgeWeightedGraph.revMap(edge).restaurant() + "\n");
                counter++;
            }            
        }
        return trip;
    }

    /**
     * @brief Given two locations by latitude and longitude, calculate and return
     * the midpoint latitude and longitude. Implementation based on Movable Type Scripts.
     * @param lat1 The first latitude.
     * @param lon1 The first longitude.
     * @param lat2 The second latitude.
     * @param lon2 The second longitude.
     * @return array of doubles that stores the latitude and longitude respectively
     */
    private static double [] midPoint(double lat1, double lon1, double lat2,double lon2){

        double [] latLot = new double [2];
        double dLon = Math.toRadians(lon2 - lon1);
    
        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);
    
        //calculate
        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);
    
        //save new latitude and longitude
        latLot[0] = Math.toDegrees(lat3);
        latLot[1] = Math.toDegrees(lon3);

        return latLot;
    }
    
}


