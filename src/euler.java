
import java.io.*;
import java.util.*;

/**
* Created by daria on 02.11.14.
*/
public class euler {
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
    int n;
    ArrayList<Integer>[] graph;
    ArrayList<Boolean>[] used;
    ArrayList<Integer> result;

    public void solve() throws IOException {
        n = in.nextInt();
        graph = new ArrayList[n];
        used = new ArrayList[n];
        int edges = 0;
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Integer>();
            used[i] = new ArrayList<Boolean>();
        }
        int oddCount = 0;
        for (int i = 0; i < n; i++) {
            int deg = in.nextInt();
            edges += deg;
            if (deg % 2 != 0) {
                oddCount++;
            }
            if (oddCount > 2) {
                out.println(-1);
                return;
            }
            for (int j = 0; j < deg; j++) {
                int a = in.nextInt() - 1;
                graph[i].add(a);
                used[i].add(false);
            }
        }

        result = new ArrayList<Integer>();


        findPath(0);

        if (result.size() - 1 != edges / 2)
            out.println(-1);
        else {
            out.println(result.size() - 1);
            for (int v : result) {
                out.print(v + 1 + " ");
            }
        }

    }

    void findPath(int v) {
        for (int i = 0; i < graph[v].size(); i++) {
            if (used[v].get(i))
                continue;
            used[v].set(i, true);
            int to = graph[v].get(i);
            for (int j = 0; j < graph[to].size(); j++) {
                if (!used[to].get(j) && graph[to].get(j) == v) {
                    used[to].set(j, true);
                    break;
                }
            }
            findPath(to);
        }
        result.add(v);
    }

    public void run() {
        try {
            File defaultInput = new File("input.txt");
            if (defaultInput.exists()) {
                in = new FastScanner(new File("input.txt"));
                out = new PrintWriter(new File("output.txt"));
            } else {
                in = new FastScanner(new File("euler" + ".in"));
                out = new PrintWriter(new File("euler" + ".out"));
            }
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new euler().run();
    }
}
