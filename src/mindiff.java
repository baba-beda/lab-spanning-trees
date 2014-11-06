import java.io.*;
import java.util.*;

/**
 * Created by daria on 02.11.14.
 */
public class mindiff {
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
    int[] h;
    final int INF = (int) 1e9;


    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < m; i++) {
            edges.add(new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt()));
        }
        Collections.sort(edges);
        boolean exist = false;
        int ans = 2 * INF + 1;

        p = new int[n];
        h = new int[n];

        for(int edge = 0; edge < m; edge++) {
            for (int i = 0; i < n; i++) {
                p[i] = i;
                h[i] = 0;
            }

            int maxEdge = -INF;
            int cntEdges = 0;
            for (int i = edge; i < m; i++) {
                int v = dsuGet(edges.get(i).v);
                int u = dsuGet(edges.get(i).u);
                if (v != u) {
                    dsuUnite(v, u);
                    maxEdge = edges.get(i).weight;
                    cntEdges++;
                }
            }
            if (cntEdges == n - 1) {
                exist = true;
            }
            else {
                continue;
            }
            if (maxEdge - edges.get(edge).weight < ans) {
                ans = maxEdge - edges.get(edge).weight;
            }
        }
        if (!exist) {
            out.println("NO");
        }
        else {
            out.println("YES");
            out.println(ans);
        }
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
            File defaultInput = new File("input.txt");
            if (defaultInput.exists()) {
                in = new FastScanner(new File("input.txt"));
                out = new PrintWriter(new File("output.txt"));
            } else {
                in = new FastScanner(new File("mindiff" + ".in"));
                out = new PrintWriter(new File("mindiff" + ".out"));
            }
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new mindiff().run();
    }
}