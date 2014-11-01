import java.io.*;
import java.util.*;

/**
 * Created by daria on 01.11.14.
 */
public class spantree {
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
        int x, y;
        Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    class Edge implements Comparable<Edge> {
        int u, v;
        double weight;

        Edge(int u, int v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int compareTo(Edge a) {
            return Double.compare(weight, a.weight);
        }
    }

    FastScanner in;
    PrintWriter out;

    int[] p;


    public void solve() throws IOException {
        Pair[] vertices;
        final double INF = 40000;

        int n = in.nextInt();
        vertices = new Pair[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Pair(in.nextInt(), in.nextInt());
        }

        double[][] graph = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
               graph[i][j] = graph[j][i] = Math.hypot(vertices[i].x - vertices[j].x, (vertices[i].y - vertices[j].y));
            }
        }

        boolean[] used = new boolean[n];
        double[] minEdge = new double[n];
        int[] selEdge = new int[n];
        Arrays.fill(minEdge, INF);
        Arrays.fill(selEdge, -1);
        minEdge[0] = 0;
        double weight = 0;

        for (int i = 0; i < n; i++) {
            int v = -1;
            for (int j = 0; j < n; j++) {
                if (!used[j] && (v == -1 || minEdge[v] > minEdge[j])) {
                    v = j;
                }
            }
            used[v] = true;
            for (int j = 0; j < n; j++) {
                if (minEdge[j] > graph[v][j] && v != j) {
                    minEdge[j] = graph[v][j];
                    selEdge[j] = v;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (selEdge[i] != -1)
                weight += graph[i][selEdge[i]];
        }

        out.println(Double.toString(weight));


    }

    public void run() {
        try {
            in = new FastScanner(new File("spantree.in"));
            out = new PrintWriter("spantree.out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new spantree().run();
    }
}
