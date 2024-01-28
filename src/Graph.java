import java.util.*;

/**
 * Graph class represents a graph with an array of vertices (indices of this are their IDs)
 * and those vertices have adjacency lists, an ArrayList of a record class called Edge.
 * It has a span method that finds the total shortest distance between a given set of vertices.
 */
public class Graph {
    /**
     * Array of vertices, indices of this are their IDs.
     */
    Vertex[] vertices;

    /**
     * Instantiates a new Graph.
     *
     * @param vertices Array of vertices, indices of this are their IDs
     */
    Graph(Vertex[] vertices){
        this.vertices = vertices;
    }

    /**
     * Inner class representing a priority object that holds a vertex id and a buildup value.
     * The buildup value is to distinguish vertices that may have entered the queue multiple times.
     */
    public class PriorityObject {
        /**
         *  ID of the vertex.
         */
        int id;
        /**
         * Buildup value of the vertex in the time of its creation.
         */
        int buildup;

        /**
         * Instantiates a new Priority object.
         *
         * @param id    ID of the vertex.
         * @param buildup Buildup value of the vertex in the time of its creation.
         */
        PriorityObject(int id, int buildup){
            this.id = id;
            this.buildup = buildup;
        }
    }

    /**
     * Finds the total shortest distance between the given set of vertices, "nodes".
     *
     * @param nodes vertices to be spanned
     * @return result, the total shortest distance between "nodes".
     */
    public int span(HashSet<Integer> nodes){
        // Array of Integers, where index stores the smallest buildup value of a vertex
        // that may have entered the queue multiple times
        Integer[] buildup = new Integer[vertices.length];
        int result = 0;
        // Priority queue that orders the vertices based on the value of buildup field
        // which is vertex's known smallest buildup in its time of creation of its PriorityObject.
        PriorityQueue<PriorityObject> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.buildup));
        // Add any vertex in the set "nodes" to the queue, and don't iterate
        for (int i : nodes){
            buildup[i] = 0;
            pq.add(new PriorityObject(i,0));
            break;
        }
        while (true){
            PriorityObject a = pq.poll();
            // Return -1 if the queue is empty, meaning there is no path between the given set of vertices
            if (a == null){
                return -1;
            }
            int iTestNode = a.id;
            // Get the current buildup value for the vertex at the top of the queue
            int currentBuildup = a.buildup;
            // If the current value bigger than the minimum buildup that's stored in the buildup array,
            // continue to the next iteration
            if (currentBuildup>buildup[iTestNode]) continue;
            // If the current vertex is in the set "nodes",
            // add its buildup value to the result and remove it from the set
            if (nodes.contains(iTestNode)){
                result += currentBuildup;
                // If "nodes" has only one element left, return the result
                // because the last one is about to be deleted as well.
                if (nodes.size() == 1){
                    return result;
                }
                // Reset the current buildup value to 0 because we've reached one of our goals
                currentBuildup = 0;
                nodes.remove(iTestNode);
            }
            // Iterate through the neighbors of the current vertex
            for (project4.Edge edge : vertices[iTestNode].neighbors){
                int neighborId = edge.connectedVertex();
                // Calculate the proposed value for the buildup value of the neighbor,
                // which is the sum of the current buildup value and the weight of the edge connecting them
                int proposal = currentBuildup + edge.weight();
                // Check if the neighbor has not been visited before
                if (buildup[neighborId] == null){
                    // If it hasn't been visited, set the buildup value for this vertex in the buildup array
                    buildup[neighborId] = proposal;
                    // Add the neighbor to the priority queue with a buildup of proposal value
                    pq.add(new PriorityObject(neighborId,proposal));
                    // Skip to the next iteration
                    continue;
                }
                // If the neighbor has been visited before,
                // we need to check if the current proposal is a better path to it
                // Check if the current proposal is better than the
                // buildup value for this vertex in the array, which has to be the minimum
                if (proposal < buildup[neighborId]){
                    // If it is, update the minimum buildup value
                    buildup[neighborId] = proposal;
                    // Add the neighbor to the priority queue with updated buildup value with a brand new object,
                    // since it is being enqueued again and we don't want to change any field inside the queue
                    // to keep the heap property.
                    pq.add(new PriorityObject(neighborId, proposal));
                }
            }
        }
    }
}
