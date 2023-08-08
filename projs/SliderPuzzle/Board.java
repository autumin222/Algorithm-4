import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Board {
    int[][] tiles;
    int[] emptyLocation = new int[2];


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        if (tiles == null)
            throw new java.lang.IllegalArgumentException();
        if (tiles.length != tiles[0].length)
            throw new java.lang.IllegalArgumentException();
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i<tiles.length; i++){
            for (int j = 0; j< tiles.length; j++){
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    emptyLocation[0] = i;
                    emptyLocation[1] = j;
                }
            }
        }

    }

    // string representation of this board
    public String toString() {
        String newstr = new String();
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles.length; j++){
                newstr += " "+tiles[i][j];
            }
            newstr += "\n";
        }
        return tiles.length+"\n"+newstr;
    }

    // board dimension n
    public int dimension(){
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming(){
        int num = 0;
        int standard = 1;
        for (int i = 0; i<dimension(); i++){
            for (int j = 0; j<dimension(); j++){
                if (standard != dimension()*dimension() && tiles[i][j] != standard++) num++;
            }
        }
        return num;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int sum = 0;
        int standard = 1;
        // searching
        while (standard < dimension()*dimension()){
            int curdistance = 0;
            for (int i = 0; i<dimension(); i++){
                for (int j = 0; j<dimension(); j++){
                    if (tiles[i][j] == standard) curdistance = distance(i, j, standard);
                }
            }
            sum += curdistance;
            standard++;
        }
        return sum;
    }

    private int distance (int i, int j, int standard){
        int standX = (standard-1)/dimension();
        int standY = (standard-1)%dimension();
        return Math.abs(i - standX) + Math.abs(j - standY);
    }

    // is this board the goal board?
    public boolean isGoal(){
        int standard = 1;
        for (int i = 0; i<dimension(); i++){
            for (int j = 0; j<dimension(); j++){
                if (standard != dimension()*dimension() && tiles[i][j] != standard++) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        if (dimension() != ((Board)y).dimension()) return false;
        for (int i = 0; i<dimension(); i++){
            for (int j = 0; j<dimension(); j++){
                if (tiles[i][j] != ((Board)y).tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        List<Board> neighbors = new ArrayList<>();
        for (int i = 0; i<4; i++){
            int neighX = emptyLocation[0];
            int neighY = emptyLocation[1];
            if (i%2 == 0) {
                neighX += i - 1;
            }else {
                neighY += i - 2;
            }
            if (CheckBoundary(neighX, neighY)){
                Board newNeighbor = new Board(tiles);
                newNeighbor.ExchangePoint(neighX, neighY, emptyLocation[0], emptyLocation[1]);
                newNeighbor.locateEmpty();
                neighbors.add(newNeighbor);
            }
        }
        return neighbors;
    }

    private boolean CheckBoundary(int x, int y){
        return x >= 0 && x < dimension() && y >= 0 && y < dimension();
    }


    private void ExchangePoint(int x1, int y1, int x2, int y2){
        int temp = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = temp;
    }
    private void locateEmpty(){
        for (int i = 0; i<dimension(); i++){
            for (int j = 0; j<dimension(); j++){
                if (tiles[i][j] == 0) {
                    emptyLocation[0] = i;
                    emptyLocation[1] = j;
                }
            }
        }
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        Board twinBoard = new Board(tiles);
        int row = emptyLocation[0];
        int col1 = 0;
        while (col1 == emptyLocation[1]){
            col1++;
        }
        int col2 = col1+1;
        while (col2 == emptyLocation[1]){
            col2++;
        }
        twinBoard.ExchangePoint(row,col1,row,col2);
        twinBoard.locateEmpty();
        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int[][] tiles = {{1,2,0},{4,1,6},{7,8,5}};

        Board b = new Board(tiles);
        System.out.print(b);
        System.out.println("neighbors: ");
        for (Board neig: b.neighbors()){
            System.out.println(neig);
        }
    }

}