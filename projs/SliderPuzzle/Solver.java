import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Stack;

public class Solver {
    Deque<SearchNode> path = null;
    boolean solvable = false;
    private class SearchNode implements Comparable<SearchNode>{
        Board b;
        int moves=0;
        int priority;
        SearchNode parent = null;
        private SearchNode(Board board){
            b = board;
        }
        private void move(){moves++;}
        private int  HammingPriority(){
            return  moves + b.hamming();
        }

        private int  ManhattanPriority(){
            return moves + b.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            return priority - that.priority;
        }

        public SearchNode getParent() {
            return parent;
        }

        public void setParent(SearchNode parent) {
            this.parent = parent;
        }

        /**calculate moves and priority*/
        public void movefrom(SearchNode parent) {
            moves = parent.moves+1;
//            priority = HammingPriority();
            priority = ManhattanPriority();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if (initial == null) throw new NullPointerException();
        path = search(initial);
        if (path != null) solvable = true;
    }

    private Deque<SearchNode> search(Board board){
        ArrayList<Board> closed = new ArrayList<>();
        ArrayList<Board> twinclosed = new ArrayList<>();
        Deque<SearchNode> rightpath = new ArrayDeque<>();
//        Deque<SearchNode> twinrightpath = new ArrayDeque<>();
        MinPQ<SearchNode> searchQueue = new MinPQ<>();
        MinPQ<SearchNode> twinsearchQueue = new MinPQ<>();
        searchQueue.insert(new SearchNode(board));
        twinsearchQueue.insert(new SearchNode(board.twin()));
        SearchNode cur = searchQueue.delMin();
        SearchNode twincur = twinsearchQueue.delMin();

        while (!cur.b.isGoal()) {
            if (twincur.b.isGoal()) {
                return null;
            }
            closed.add(cur.b);
            twinclosed.add(cur.b);

            for (Board neig: cur.b.neighbors()) {
                if (!closed.contains(neig)) {
                    SearchNode newNode = new SearchNode(neig);
                    newNode.setParent(cur);
                    newNode.movefrom(cur);
                    searchQueue.insert(newNode);
                }
            }

            for (Board neig: twincur.b.neighbors()) {
                if (!twinclosed.contains(neig)) {
                    SearchNode newNode = new SearchNode(neig);
                    newNode.setParent(cur);
                    newNode.movefrom(cur);
                    twinsearchQueue.insert(newNode);
                }
            }

            if (searchQueue.isEmpty()) {
                return null;
            }
            cur = searchQueue.delMin();
            twincur = twinsearchQueue.delMin();
//            System.out.println(closed.size());
        }

        while(cur != null){
            rightpath.addFirst(cur);
            cur = cur.parent;
        }
        return rightpath;

    }
    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return path.getLast().moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvable == false) return null;
        ArrayList<Board> nodes = new ArrayList<>();
        for (SearchNode item: path){
            nodes.add(item.b);
        }
        return nodes;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}