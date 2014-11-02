import java.io.PrintWriter;

/**
 * Created by daria on 02.11.14.
 */
public class gentest {
    PrintWriter out;

    void run() {
        try {
            out = new PrintWriter("spantree.in");
        } catch (Exception e) {

        }
        out.println(5000);
        for (int i = 0; i < 5000; i++) {
            int n = (int) (Math.random() * 10000);
            int m = (int) (Math.random() * 10000);
            out.print(n + " " + m + '\n');
        }
    }

    public static void main(String[] args) {
        new gentest().run();
    }
}
