import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int numberOfTrials;
    private final double mean;
    private final double stddev;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must greater than 0");
        }
        double[] x  = new double[trials];
        numberOfTrials = trials;

        for (int i = 0; i < numberOfTrials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            double threshold = (double) percolation.numberOfOpenSites()/ (double) (n*n);
            x[i] = threshold;
        }
        mean = StdStats.mean(x);
        stddev = StdStats.stddev(x);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - CONFIDENCE_95 * stddev/ Math.sqrt(numberOfTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + CONFIDENCE_95 * stddev/ Math.sqrt(numberOfTrials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int numberOfTrials = StdIn.readInt();
        PercolationStats percolationStats = new PercolationStats(n, numberOfTrials);
        StdOut.println(String.format("mean                    = %f", percolationStats.mean()));
        StdOut.println(String.format("stddev                  = %f", percolationStats.stddev()));
        StdOut.println(String.format("95%% confidence interval = [%f, %f]",  percolationStats.confidenceLo(),  percolationStats.confidenceHi()));
    }
}
