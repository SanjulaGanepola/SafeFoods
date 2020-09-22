/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is used to create an edge weighted graph.
 * Adapted from Algorithms 4th ed. by Robert Segdewick and 2xb3 labs
 */
package graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import graph.Restaurant;
import graph.RestaurantParser;
import graph.Distance;

import edu.princeton.cs.algs4.*;

/**
 * @brief This class will have methods for creating a graph and retreiving
 * information about the created graph
 */
public class EdgeWeightedGraph {
    private static HashMap<Restaurant, Integer> revMap; //two maps for 2 way key to value retrieval
	private static HashMap<Integer, Restaurant> map;
    private final int V;
    private int E;
    private Bag<Edge>[] adj;
    
    /**
     * @brief Initializes an empty edge-weighted graph with vertices and 0 edges.
     * @param  V the number of vertices
     * @throws IllegalArgumentException 
     */
    public EdgeWeightedGraph(int V) {
    	revMap = new HashMap<Restaurant, Integer>();
    	map = new HashMap<Integer, Restaurant>();
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }
    
    /**
     * @brief get value based on map key
     * @param  key Restaurant that maps to integer
     * @return corresponding integer
     */
    public static Integer map(Restaurant key) {
    	return revMap.get(key);
    }

    /**
     * @brief get value based on revMap key
     * @param  key integer that maps to restaurant
     * @return corresponding restaurant
     */
    public static Restaurant revMap(int key) {
    	return map.get(key);
    }

    /**
     * @brief return the number of vertices in graph
     * @return the number of vertices in this edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * @brief return the number of edges in graph
     * @return the number of vertices in this edge-weighted graph
     */
    public int E() {
        return E;
    }

    /**
     * @brief Adds the undirected edge to graph
     * @param  e edge to be added
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    /**
     * @brief Create an iterable of incident edges
     * @param  v the vertex
     * @return the edges incident on vertex as an iterable
     */
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    /**
     * @brief create iterable with all edges graph.
     * @return all edges in this graph, as an iterable
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // add only one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    /**
     * @brief create a graph with a list of restaurants and a number of extra vertices
     * @param R List of restaurants
     * @param extraRes number of extra restaurants to add to the graph
     * @return Resutling edgeweighted graph
     */
    public static EdgeWeightedGraph createGraph(ArrayList<Restaurant> R, int extraRes) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(R.size() + extraRes); //create a graph with the size of the total number of restaurants, plus user input number
        int counter = 0;
        for (Restaurant x: R) { //put key and value pair in both hashmaps for easy traversal
            revMap.put(x, counter); 
            map.put(counter, x);
            counter++;
        }
        
        
        int totalVertex = 0;    //add edges to the graph
        while (totalVertex != R.size()) {
            for (Restaurant x : R) {
                if (totalVertex == revMap.get(x)) { //case where adding edge from and to the same vertex
                    continue; //skip this iteration, move onto next iteration
                }
                //alternate between edges to add to the adjacency list
                if (totalVertex % 2 == 0 && revMap.get(x)% 2 == 0) { //even number vertex are connected
                    Edge newEdge = new Edge(totalVertex, revMap.get(x), 
                    Distance.distance(map.get(totalVertex).lat(), 
                    map.get(totalVertex).lon(), x.lat(), x.lon())); //create an edge with the weight being distance between
                    G.addEdge(newEdge); //add edge to graph
                } else if (totalVertex % 2 == 1 && revMap.get(x)% 2 == 1) { //odd number vertex are connected
                    Edge newEdge = new Edge(totalVertex, revMap.get(x), 
                    Distance.distance(map.get(totalVertex).lat(), 
                    map.get(totalVertex).lon(), x.lat(), x.lon())); //create an edge with the weight being distance between
                    G.addEdge(newEdge);
                }
            }
            
            totalVertex++; //increment the total number of vertices
        } 
        
        return G; //return resulting graph
    }

    /**
     * @brief add two restaurants addesses to a graph
     * @param G EdgeWeightedGraph
     * @param start restaurant 1
     * @param end restaurant 2
     * @return Resutling edgeweighted graph
     * @throws IOException
     */
    public static EdgeWeightedGraph addTwoAddress(EdgeWeightedGraph G, Restaurant start, Restaurant end) throws IOException {
    	int size = RestaurantParser.resFile(new File("Restaurants in LA.csv")).size(); //size of  list of restaurants in LA
    	
    	int totalVertex = size;
    	int totalExtraRes = 2;
    	
    	revMap.put(start, size); //add the two locations to our dictionary for easy traversal
    	map.put(size, start);
    	revMap.put(end, size + 1);
    	map.put(size + 1, end);
    	
    	while (totalVertex != size + totalExtraRes) {  //add edges to the input restaurants
    		for (int i = 0; i < size + totalExtraRes; i++) {
    			if (i == size || i == size + 1) { //don't add edges to the restaurants themselves
    				continue;
    			}
    			Edge newEdge = new Edge(totalVertex, i, Distance.distance(map.get(totalVertex).lat(), 
                map.get(totalVertex).lon(), map.get(i).lat(), map.get(i).lon())); //create an edge with the weight being distance between
				G.addEdge(newEdge); //add edge to graph
    		}
    		
    		totalVertex++;
    	}
    	
    	return G;
    }

    /**
     * @brief add one restaurant address to a graph
     * @param G EdgeWeightedGraph
     * @param start restaurant 1
     * @return Resutling edgeweighted graph
     * @throws IOException
     */
    public static EdgeWeightedGraph addOneAddress(EdgeWeightedGraph G, Restaurant start) throws IOException {
        int size = RestaurantParser.resFile(new File("Restaurants in LA.csv")).size() + 2; //size will be equal to 2 + size of list
        int totalVertex = size;
        int totalExtraRes = 1;
        
        revMap.put(start, size); //add the two locations to our dictionary for easy traversal
        map.put(size, start);
        
        while (totalVertex != size + totalExtraRes) { //loop through while the total vertex doesn't equal the size + totalExtraRes
            for (int i = 0; i < size + totalExtraRes; i++) {
                if (i == size || i == size - 1) { //don't add edges to start restaurant, if it is i is equal to size or size - 1
                    continue; //this will prevent a path from a starting location to ending location
                }
                Edge newEdge = new Edge(totalVertex, i, Distance.distance(map.get(totalVertex).lat(), 
                map.get(totalVertex).lon(), map.get(i).lat(), map.get(i).lon())); //create an edge with the weight being distance between
                G.addEdge(newEdge); //add edge to graph
            }
            totalVertex++;
        }
        
        return G;
    }
    
}
