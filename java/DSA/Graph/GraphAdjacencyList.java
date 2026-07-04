import java.util.*;

// Define the Graph class
class Graph {
    private int vertices; // Number of vertices
    private LinkedList<Integer>[] adjList; // Adjacency list

    // Constructor
    public Graph(int vertices) {
        this.vertices = vertices;
        adjList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    // Method to add an edge to the graph
    void addEdge(int v, int w) {
        adjList[v].add(w);
        adjList[w].add(v); // For undirected graph
    }

    // Method to perform DFS traversal
    void DFS(int start) {
        boolean[] visited = new boolean[vertices];
        DFSUtil(start, visited);
    }

    // Utility method for DFS
    private void DFSUtil(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");

        for (int n : adjList[v]) {
            if (!visited[n]) {
                DFSUtil(n, visited);
            }
        }
    }

    // Method to perform BFS traversal
    void BFS(int start) {
        boolean[] visited = new boolean[vertices];
        LinkedList<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            System.out.print(v + " ");

            for (int n : adjList[v]) {
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }
}

// Main class to test the graph traversal
public class GraphTraversal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of vertices in the graph: ");
        int vertices = scanner.nextInt();
        Graph graph = new Graph(vertices);

        System.out.print("Enter the number of edges: ");
        int edges = scanner.nextInt();

        System.out.println("Enter the edges (format: v w):");
        for (int i = 0; i < edges; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            graph.addEdge(v, w);
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. DFS Traversal");
            System.out.println("2. BFS Traversal");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter starting vertex for DFS: ");
                    int startDFS = scanner.nextInt();
                    System.out.print("DFS Traversal: ");
                    graph.DFS(startDFS);
                    System.out.println();
                    break;
                case 2:
                    System.out.print("Enter starting vertex for BFS: ");
                    int startBFS = scanner.nextInt();
                    System.out.print("BFS Traversal: ");
                    graph.BFS(startBFS);
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

