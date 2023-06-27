import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    Percolation p;
    int trialTimes;
    double[] percolates;

    // single trial for one time and get the number it percolates
    public int trail(int n) {
        p = new Percolation(n);
        int count = 0;
        int[] randomAccessSeq = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            randomAccessSeq[i] = i;
        }
        StdRandom.shuffle(randomAccessSeq);
        while (!p.percolates()) {
            int randRow = randomAccessSeq[count] / n + 1;
            int randCol = randomAccessSeq[count] % n + 1;
            p.open(randRow, randCol);
            count++;
        }
        return count;
    }

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n or trials isn't reasonable.");
        trialTimes = trials;
        percolates = new double[trialTimes];
        for (int i = 0; i < trials; i++) {
            percolates[i] = trail(n) / (n*n*1.0);
        }
    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolates);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolates);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double s = stddev();
        return mean() - 1.96 * s / Math.sqrt(trialTimes);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double s = stddev();
        return mean() + 1.96 * s / Math.sqrt(trialTimes);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java-algs4 PercolationStats n T");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, T);
        System.out.println("mean                    = " + percStats.mean());
        System.out.println("stddev                  = " + percStats.stddev());
        System.out.println("95% confidence interval = [" + percStats.confidenceLo()
                + ", " + percStats.confidenceHi() + "]");
    }

}
