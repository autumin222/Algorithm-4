import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> LineSegments;
    private boolean Collinear(Point p1, Point p2, Point p3){
        return p1.slopeTo(p2) == p2.slopeTo(p3);
    }
    public BruteCollinearPoints(Point[] points){
        LineSegments = new ArrayList<>();
        for (int i = 0; i < points.length; i++){
            for (int j = i + 1; j < points.length; j++){
                for (int k = j + 1; k < points.length; k++){
                    if (Collinear(points[i], points[j], points[k])){
                        for (int l = k + 1; l < points.length; l++){
                            if (Collinear(points[i], points[j], points[l])){
                                LineSegments.add(new LineSegment(points[i], points[l]));
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }    // finds all line segments containing 4 points
    public int numberOfSegments(){
        return LineSegments.size();
    }        // the number of line segments
    public LineSegment[] segments(){
        return LineSegments.toArray(new LineSegment[LineSegments.size()]);
    }                // the line segments

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}