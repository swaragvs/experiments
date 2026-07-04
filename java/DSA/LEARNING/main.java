import java.util.*;


class Graph {
    int vertices;
    ArrayList<ArrayList<Integer>>adjList;

    Graph(int v) {
        vertices = v;
        adjList= new ArrayList<>();

        for (int i=0; i< v;i++) {
            adjList.add(new ArrayList<>());
        
        }
    }

    void addEdge(int u,int v){
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }

}

public class main {
    
    public static void main(String[] args) {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);

        System.out.println(g.adjList);

    }
    
}
