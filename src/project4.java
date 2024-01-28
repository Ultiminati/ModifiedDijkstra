import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class project4 {
    public record Edge(int connectedVertex, int weight){}
    public static void main(String[] args) throws IOException {
        FileReader file = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(file);
        int nOfVertices = Integer.parseInt(br.readLine());
        br.readLine();
        String[] endpoints = br.readLine().split(" ", 2);
        String[] flags = br.readLine().split(" ");
        HashMap<String, Integer> vertexMap = new HashMap<>();
        Vertex[] vertices = new Vertex[nOfVertices];

        int ind = 0;
        for (int i = 0; i < nOfVertices; i++) {
            String[] a = br.readLine().split(" ");
            Vertex v;
            if (!vertexMap.containsKey(a[0])) {
                v = new Vertex(ind);
                vertices[ind] = v;
                vertexMap.put(a[0], ind);
                ind += 1;
            } else {
                v = vertices[vertexMap.get(a[0])];
            }
            for (int b = 0; b < a.length / 2; b++) {
                Vertex t;
                if (!vertexMap.containsKey(a[1 + 2 * b])) {
                    t = new Vertex(ind);
                    vertices[ind] = t;
                    vertexMap.put(a[1 + 2 * b], ind);
                    ind += 1;
                } else {
                    t = vertices[vertexMap.get(a[1 + 2 * b])];
                }
                int weight = Integer.parseInt(a[1 + 2 * b + 1]);
                v.addEdge(new Edge(t.id, weight));
                t.addEdge(new Edge(v.id, weight));
            }

        }

        HashSet<Integer> flagSet = new HashSet<>();
        for (String s : flags) {
            flagSet.add(vertexMap.get(s));
        }

        HashSet<Integer> endPs = new HashSet<>();
        endPs.add(vertexMap.get(endpoints[0]));
        endPs.add(vertexMap.get(endpoints[1]));
        Graph graph = new Graph(vertices);
        int firstResult = graph.span(endPs);
        int secondResult = graph.span(flagSet);
        outputMachine(args[1], firstResult + "\n" + secondResult);
    }

    public static void outputMachine(String output, String log) throws IOException {
        new File(output);
        FileWriter writer = new FileWriter(output);
        writer.write(log);
        writer.close();

    }
}



