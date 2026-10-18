# Dijskstra Algorithm

Dijkstra’s Algorithm is a greedy algorithm used to find the shortest distances from a single source vertex to all other vertices in a weighted graph with non-negative edge weights. It repeatedly processes the vertex with the smallest known distance and updates the distances of its neighboring vertices if a shorter path is found.

## Pattern

Greedy Algorithm + Priority Queue (Min-Heap) + Shortest Path

---

## Optimal Approach

### Code

```java
class Edge {
    int v;
    int wt;

    public Edge(int v, int wt) {
        this.v = v;
        this.wt = wt;
    }
}

class Pair {
    int dist;
    int v;

    public Pair(int dist, int v) {
        this.dist = dist;
        this.v = v;
    }
}

public class Solution {

    public static void dijkstra(int src, int V, List<List<Edge>> g) {
        List<Integer> dist = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            dist.add(Integer.MAX_VALUE);
        }

        Queue<Pair> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));

        dist.set(src, 0);
        pq.add(new Pair(dist.get(src), src));

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();

            for (Edge neigh : g.get(curr.v)) {
                if (dist.get(neigh.v) > dist.get(curr.v) + neigh.wt) {
                    dist.set(neigh.v, dist.get(curr.v) + neigh.wt);
                    pq.add(new Pair(dist.get(neigh.v), neigh.v));
                }
            }
        }

        for (int val : dist) {
            System.out.print(val + " ");
        }
    }

    public static void main(String[] args) {
        int V = 6;
        List<List<Edge>> g = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            g.add(new ArrayList<>());
        }

        g.get(0).add(new Edge(1, 2));
        g.get(0).add(new Edge(2, 4));

        g.get(1).add(new Edge(2, 1));
        g.get(1).add(new Edge(3, 7));

        g.get(2).add(new Edge(4, 3));

        g.get(3).add(new Edge(5, 1));

        g.get(4).add(new Edge(3, 2));
        g.get(4).add(new Edge(5, 5));

        dijkstra(0, V, g);
    }
}
```

### Time Complexity

- O((V + E) log V)

### Space Complexity

- O(V+E)

---

### Explanation

I use Dijkstra's Algorithm to find the shortest distance from a source vertex to all other vertices in a weighted graph with non-negative edge weights.
First, I initialize the distance of every vertex to infinity, except the source vertex, whose distance is zero. I use a min-heap priority queue to process the vertex with the smallest known distance first.
For each vertex removed from the queue, I explore its neighbors and check whether going through the current vertex gives a shorter distance. If it does, I update the neighbor's distance and add the updated distance to the priority queue.
I repeat this process until the priority queue becomes empty. Finally, I print the shortest distances from the source to every vertex
