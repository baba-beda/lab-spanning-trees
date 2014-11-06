
import java.io.*;
import java.util.*;

/**
 * Created by daria on 02.11.14.
 */
public class chinese {
    class FastScanner {
        StreamTokenizer st;

        FastScanner() {
            st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        }

        FastScanner(File f) {
            try {
                st = new StreamTokenizer(new BufferedReader(new FileReader(f)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        int nextInt() throws IOException {
            st.nextToken();
            return (int) st.nval;
        }

        String nextString() throws IOException {
            st.nextToken();
            return st.sval;
        }
    }


    class Pair {
        int v, weight;

        Pair(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }
    }

    class Edge implements Comparable<Edge> {
        int u, v;
        int weight;

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return Integer.compare(weight, edge.weight);
        }
    }

    FastScanner in;
    PrintWriter out;
    int k = 0;
    final int INF = 1000000500;

    public void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt();

        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Pair>[] graph = new ArrayList[n];

        boolean[] used = new boolean[n];


        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Pair>();
        }

        for (int i = 0; i < m; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1, w = in.nextInt();
            if (a != b) {
                edges.add(new Edge(a, b, w));
                graph[a].add(new Pair(b, w));
            }
        }
        int start = 0;
        k = n;
        dfs(start, used, graph);

        if (k != 0) {
            out.print("NO");
            return;
        }
        out.print("YES\n" + findMST(edges, n, start));
    }

    void dfs(int v, boolean[] used, ArrayList<Pair>[] graph) {
        k--;
        used[v] = true;
        for (Pair p : graph[v]) {
            if (!used[p.v]) {
                dfs(p.v, used, graph);
            }
        }

    }

    void dfs1(int v, boolean[] used, ArrayList<Pair>[] graph, ArrayList<Integer> order) {
        used[v] = true;
        for (Pair p : graph[v]) {
            if (!used[p.v]) {
                dfs1(p.v, used, graph, order);
            }
        }
        order.add(v);
    }

    void dfs2 (int v, boolean[] used, ArrayList<Pair>[] graph, ArrayList<Integer> component) {
        used[v] = true;
        component.add(v);
        for(Pair p : graph[v]) {
            if (!used[p.v]) {
                dfs2(p.v, used, graph, component);
            }
        }
    }

    long findMST(ArrayList<Edge> edges, int n, int root) {
        long res = 0;
        int[] minEdge = new int[n];
        Arrays.fill(minEdge, INF);
        ArrayList<Pair>[] graphOfZero = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graphOfZero[i] = new ArrayList<Pair>();
        }
        ArrayList<Edge> zeroEdges = new ArrayList<Edge>();

        for (Edge e : edges) {
            minEdge[e.v] = Math.min(e.weight, minEdge[e.v]);
        }
        for (int i = 0; i < n; i++) {
            if (i == root)
                continue;
            res+= minEdge[i];
        }
        for (Edge e : edges) {
            if (e.weight == minEdge[e.v]) {
                zeroEdges.add(new Edge(e.u, e.v, 0));
                graphOfZero[e.u].add(new Pair(e.v, 0));
            }
        }
        boolean[] used = new boolean[n];
        k = n;
        dfs(root, used, graphOfZero);
        if (k == 0)
            return res;

        int[] newComponents = condensation(graphOfZero);
        ArrayList<Edge> newEdges = new ArrayList<Edge>();
        for (Edge e : edges) {
            if (newComponents[e.u] != newComponents[e.v] && !newEdges.contains(new Edge(newComponents[e.u], newComponents[e.v], e.weight - minEdge[e.v]))) {
                newEdges.add(new Edge(newComponents[e.u], newComponents[e.v], e.weight - minEdge[e.v]));
            }
        }
        int compCount = 0;
        for (int i = 0; i < n; i++) {
            compCount = Math.max(compCount, newComponents[i]);
        }
        res += findMST(newEdges, compCount + 1, newComponents[root]);
        return res;
    }

    int[] condensation(ArrayList<Pair>[] graph) {
        int n = graph.length;
        int[] components = new int[n];
        int compCount = 0;
        boolean[] used = new boolean[n];
        ArrayList<Integer> order = new ArrayList<Integer>();
        ArrayList<Integer> component = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            if (!used[i])
                dfs1(i, used, graph, order);
        }

        ArrayList<Pair>[] graph_t = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph_t[i] = new ArrayList<Pair>();
        }

        for (int i = 0; i < n; i++) {
            for (Pair p : graph[i]) {
                graph_t[p.v].add(new Pair(i, p.weight));
            }
        }


        Arrays.fill(used, false);
        for (int i = 0; i < n; i++) {
            int v = order.get(n - 1 - i);
            if (!used[v]) {
                dfs2(v, used, graph_t, component);
                for (int c : component) {
                    components[c] = compCount;
                }
                component.clear();
                compCount++;
            }
        }
        return components;
    }

    public void run() {
        try {
            File defaultInput = new File("input.txt");
            if (defaultInput.exists()) {
                in = new FastScanner(new File("input.txt"));
                out = new PrintWriter(new File("output.txt"));
            } else {
                in = new FastScanner(new File("chinese" + ".in"));
                out = new PrintWriter(new File("chinese" + ".out"));
            }
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new chinese().run();
    }
}
