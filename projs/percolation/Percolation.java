import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF sets;
    boolean[] grids;
    int width;
    int top, down;
    int openSiteNum;

    // find the index in the grids refer to the row and col
    // index(1, 1) -> 0
    int index(int row, int col, int rowlen) {
        check(row, col);
        return (row - 1) * rowlen + (col - 1);
    }

    // check if the num is in the range
    void check(int row, int col) {
        if (row < 1 || col < 1 || row > width || col > width) {
            throw new IllegalArgumentException("the point isn't in the reasonable range!");
        }
    }

    void check(int index) {
        if (index < 0 || index >= width * width) {
            throw new IllegalArgumentException("the index " + index +" isn't in the reasonable range!");
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        width = n;
        grids = new boolean[n * n];
        sets = new WeightedQuickUnionUF(n * n + 2);
        top = n*n;
        down = n*n+1;
        openSiteNum = 0;

    }

    // opens the site (row, col){} if it is not open already
    public void open(int row, int col) {
        if(isOpen(row, col)) return ;
        int index1 = index(row, col, width);
        if (row == 1){
            sets.union(index1, top);
        }
        if (row == width){
            sets.union(index1, down);
        }

        if (col != 1 && isOpen(row, col - 1)) {
            int index2 = index(row, col - 1, width);
            sets.union(index1, index2);
        }
        if (col != width && isOpen(row, col + 1)) {
            int index2 = index(row, col + 1, width);
            sets.union(index1, index2);
        }
        if (row != 1 && isOpen(row - 1, col)) {
            int index2 = index(row - 1, col, width);
            sets.union(index1, index2);
        }
        if (row != width && isOpen(row + 1, col)){
            int index2 = index(row + 1, col, width);
            sets.union(index1, index2);
        }
        grids[index(row, col, width)] = true;
        openSiteNum++;

    }

    // is the site (row, col){} open?
    public boolean isOpen(int row, int col) {
        return grids[index(row, col, width)];
    }

    public boolean isOpen(int index) {
        check(index);
        return grids[index];
    }

    /* is the site (row, col){} full?
     * see if it's root is open?*/
    public boolean isFull(int row, int col) {
        check(row, col);
        int root = sets.find(index(row, col, width));
        if (root != sets.find(top)){
            return false;
        }else return true;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return sets.find(top) == sets.find(down);
    }

    void test(){
        open(1,1);
        open(2,1);
        open(2,2);
        open(2,3);
        open(3,3);
        open(4,3);
        open(5,3);
        if(percolates()) System.out.println("yes");

    }
    // test client (optional){}
    public static void main(String[] args) {

        Percolation p = new Percolation(5);
        p.test();

    }
}
