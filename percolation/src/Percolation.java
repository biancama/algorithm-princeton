import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] grid;

    private final WeightedQuickUnionUF quickUnionUF;
    private final WeightedQuickUnionUF quickUnionUFForFull;

    private final int bottom;
    private final int top;
    private final int n;

    private int numberOfOpenSites;

    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        numberOfOpenSites = 0;
        percolates = false;
        this.n = n;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        /*
         2 virtual nodes, and the grid
           top virtual is 0
         1   |  2  |  3 ...... | n
        n+1  | n+2 | n +3 .....\ 2n
         ..........................
          bottom virtual n^2
         */
        quickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        quickUnionUFForFull = new WeightedQuickUnionUF(n * n + 1);

        top = 0;
        bottom = n*n + 1;

    }

    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("row or col out of range");
        }
    }

    private int indexOfElement(int row, int col) {
        return (row - 1) * n + col;
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col) ) {
            numberOfOpenSites++;
            grid[row-1][col-1] = true;
            int elemInUnion = indexOfElement(row, col);
            if (row == 1) { // connect at least with top
                quickUnionUF.union(elemInUnion, top);
                quickUnionUFForFull.union(elemInUnion, top);
            }
            if (row == n) { // connect at least with bottom
                quickUnionUF.union(elemInUnion, bottom);
            }
            if (row > 1 && grid[row-2][col-1]) { // need to connect the top
                quickUnionUF.union(elemInUnion, indexOfElement(row - 1, col));
                quickUnionUFForFull.union(elemInUnion, indexOfElement(row - 1, col));
            }
            if (col > 1 && grid[row-1][col -2]) {  // need to connect the left
                quickUnionUF.union(elemInUnion, indexOfElement(row, col - 1));
                quickUnionUFForFull.union(elemInUnion, indexOfElement(row, col - 1));
            }
            if (col < n && grid[row-1][col]) { // need to connect the right
                quickUnionUF.union(elemInUnion, indexOfElement(row, col + 1));
                quickUnionUFForFull.union(elemInUnion, indexOfElement(row, col + 1));
            }
            if (row < n && grid[row][col-1]) { // need to connect the bottom
                quickUnionUF.union(elemInUnion, indexOfElement(row + 1, col));
                quickUnionUFForFull.union(elemInUnion, indexOfElement(row + 1, col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.isOpen(row, col) && quickUnionUFForFull.find(top) == quickUnionUFForFull.find(indexOfElement(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (!percolates) {
            percolates =  quickUnionUF.find(bottom) == quickUnionUF.find(top);
        }
        return percolates;
    }

    // test client (optional)
    public static void main(String[] args) {
        int size = 1;
        Percolation test = new Percolation(size);
//        while (true) {
//            int row = StdRandom.uniform(1, size + 1);
//            int col = StdRandom.uniform(1, size + 1);
//
//            test.open(row, col);
//            if (test.percolates()) {
//                double threshold = (double) test.numberOfOpenSites() / (double) (size*size);
//                StdOut.println(String.format("The threshold is %.2f", threshold));
//                break;
//            }
//        }
        StdOut.println(String.format("The site percolates %b", test.percolates()));

        test.open(1, 1);
        StdOut.println(String.format("The site percolates %b", test.percolates()));

//        test.open(2, 3);
//        test.open(3, 3);
//        test.open(3, 1);
//        StdOut.println(String.format("The site is full %b", test.isFull(3, 1)));
    }
}
