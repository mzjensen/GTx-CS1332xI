import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of Prim's algorithm.
 */
public class GraphAlgorithms {

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use java.util.PriorityQueue, java.util.Set, and any
     * class that implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the adjacency
     * list from graph. DO NOT create new instances of Map for this method
     * (storing the adjacency list in a variable is fine).
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin Prims on.
     * @param graph The graph we are applying Prims to.
     * @return The MST of the graph or null if there is no valid MST.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {

        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Set<Edge<T>> mst = new HashSet<>();

        for (VertexDistance<T> vd : graph.getAdjList().get(start)) {
            queue.add(new Edge<>(start, vd.getVertex(), vd.getDistance()));
        }

        visited.add(start);

        while (!queue.isEmpty() && visited.size() < graph.getVertices().size()) {
            Edge<T> tmp = queue.remove();
            if (!visited.contains(tmp.getV())) {
                visited.add(tmp.getV());
                mst.add(tmp);
                mst.add(new Edge<T>(tmp.getV(), tmp.getU(), tmp.getWeight()));
                for (VertexDistance<T> vd : graph.getAdjList().get(tmp.getV())) {
                    if (!visited.contains(vd.getVertex())) {
                        queue.add(new Edge<T>(tmp.getV(), vd.getVertex(), vd.getDistance()));
                    }
                }
            }
        }
        if (mst.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return mst;
    }
}