import java.io.*;
import java.util.*;

/**
 * Created by daria on 01.11.14.
 */
public class spantree2 {
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

    int[] p;


    public void solve() throws IOException {

        int n = in.nextInt(); int m = in.nextInt();

        ArrayList<Edge> edges = new ArrayList<Edge>();

        for (int i = 0; i < m; i++) {
            edges.add(new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt()));
        }
        Collections.sort(edges);
        p = new int[n];

        long weight = 0;
        for (int i = 0; i < n; i++) {
            p[i] = i;
        }

        for (Edge e : edges) {
            if (dsuGet(e.u) != dsuGet(e.v)) {
                weight += e.weight;
                dsuUnite(e.u, e.v);
            }
        }
        out.println(weight);
    }

    int dsuGet(int v) {
        return (v == p[v]) ? v : (p[v] = dsuGet(p[v]));
    }

    void dsuUnite (int a, int b) {
        a = dsuGet(a);
        b = dsuGet(b);
        if (a != b) {
            p[a] = b;
        }
    }

    public void run() {
        try {
            in = new FastScanner(new File("spantree2.in"));
            out = new PrintWriter("spantree2.out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new spantree2().run();
    }
}
