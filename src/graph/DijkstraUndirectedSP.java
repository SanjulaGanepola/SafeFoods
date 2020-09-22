/**
 * Author: SafeFoods
 * Revised: April 11, 2020
 * 
 * Description: This module is used to apply dijkstra's
 * algorithm on an existing graph. Adapted from 
 * Algorithms 4th ed. by Robert Segdewick and 2xb3 labs
 */
package graph;

import java.util.Stack;

import edu.princeton.cs.algs4.*;
import graph.EdgeWeightedGraph;

/**
 * @brief This class will have methods for a shortest path
 * algorithm
 */
public class DijkstraUndirectedSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * @brief Computes a shortest-paths tree from the source vertex  to every
     * other vertex in provided graph.
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     */
    public DijkstraUndirectedSP(EdgeWeightedGraph G, int s) {
        for (Edge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        
        validateVertex(s);

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adj(v))
                relax(e, v);
        }

        // check optimality conditions
        assert check(G, s);
    }

    /**
     * @brief relax edge e and update pq if changed
     * @param  e edge
     * @param  v vertex to relax
     */
    private void relax(Edge e, int v) {
        int w = e.other(v);
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    /**
     * @brief retreive the length of a shortest path between the source vertex and
     * input vertex
     * @param  v the destination vertex
     * @return the length of a shortest path between the source vertex  and
     * input vertex
     * @throws IllegalArgumentException for vertex validation 
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * @brief Returns true if there is a path between the source vertex and
     * input vertex 
     * @param  v the destination vertex
     * @return boolean value depending on if there is a path or not
     * @throws IllegalArgumentException for vertex validation
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    
    /**
     * @brief create stack of edges which contains a shortest path between the 
     * source vertex and input vertex
     * @param  v the destination vertex
     * @return a shortest path between the source vertex and vertex input in a stack
     * of edges
     * @throws IllegalArgumentException vertex validation
     */
    public Stack<Edge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            path.push(e);
            x = e.other(x);
        }
        return path;
    }


    /**
     * @brief check optimal conditions of graph, to ensure graph is 
     * built correctly 
     * @param G Graph to check
     * @param s source vertex integer
     * @return Boolean value based on check conditions
     */
    private boolean check(EdgeWeightedGraph G, int s) {

        // check that edge weights are nonnegative
        for (Edge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            if (w != e.either() && w != e.other(e.either())) return false;
            int v = e.other(w);
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    /**
     * @brief check if vertex is valid 
     * @param v verte to check
     * @throws IllegalArugmentException vertex is not valid
     */
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    
}