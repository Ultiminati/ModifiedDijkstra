import java.util.ArrayList;

/**
 * A class to represent a vertex in a graph.
 */
class Vertex {
    /**
     * The unique identifier of the vertex.
     */
    int id;
    /**
     * A list of all the edges that are connected to this vertex.
     */
    ArrayList<project4.Edge> neighbors = new ArrayList<>();

    /**
     * Instantiates a new Vertex.
     *
     * @param id The unique identifier of the vertex.
     */
    Vertex(int id) {
        this.id = id;
    }

    /**
     * Adds an edge to this vertex's list of neighbors.
     *
     * @param e Edge to be added
     */
    public void addEdge(project4.Edge e) {
        neighbors.add(e);
    }
}
