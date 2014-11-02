
import java.io.*;
import java.util.*;

/**
 * Created by daria on 01.11.14.
 */
public class spantree_for_test {
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
        ArrayList<Edge> edges;
        int n = in.nextInt();
        vertices = new Pair[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Pair(in.nextInt(), in.nextInt());
        }

        edges = new ArrayList<Edge>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.add(new Edge(i, j,  Math.sqrt((vertices[i].x - vertices[j].x)*(vertices[i].x - vertices[j].x)  + (vertices[i].y - vertices[j].y)*(vertices[i].y - vertices[j].y))));
            }
        }
        Collections.sort(edges);
        p = new int[n];

        Double weight = new Double(0);
        for (int i = 0; i < n; i++) {
            p[i] = i;
        }

        for (Edge e : edges) {
            if (dsuGet(e.u) != dsuGet(e.v)) {
                weight += e.weight;
                dsuUnite(e.u, e.v);
            }
        }
        String s = Double.toString(weight);
        out.println(s);
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
            in = new FastScanner(new File("spantree.in"));
            out = new PrintWriter("spantree.out");
            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new spantree_for_test().run();
    }
}
