
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

        public int compareTo(Edge a) {
            return Integer.compare(weight, a.weight);
        }
    }

    FastScanner in;
    PrintWriter out;
    ArrayList<Pair>[] graph;
    Edge[] edges;
    Edge[] edgesCopy;
    ArrayList<Edge> zeroEdges;
    final int INF = 1000000000;
    final long INF2 = 10000000000000L;

    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Pair>();
        }
        edges = new Edge[m];
        edgesCopy = edges;
        zeroEdges = new ArrayList<Edge>();


        for (int i = 0; i < m; i++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            int weight = in.nextInt();
            graph[v].add(new Pair(u, weight));
            edges[i] = new Edge(v, u, weight);
        }

        for (int v = 0; v < n; v++) {
            int min = INF;
            for (int i = 0; i < m; i++) {
                if (edges[i].v == v && edges[i].weight < min) {
                    min = edges[i].weight;
                }
            }

            for (int i = 0; i < m; i++) {
                if (edges[i].v == v) {
                    edgesCopy[i].weight -= min;
                }
                if (edgesCopy[i].weight == 0) {
                    zeroEdges.add(edges[i]);
                }
            }
        }
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
