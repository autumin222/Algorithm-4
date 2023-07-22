import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    List<LineSegment> LineSegments;
    private boolean Collinear(Point p1, Point p2, Point p3){
        return p1.slopeTo(p2) == p2.slopeTo(p3);
    }
    public FastCollinearPoints(Point[] points){
        // corner case checking
        if (points == null) {
            throw new IllegalArgumentException("argument to constructor is null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("one point is null");
            }
        }
        int len = points.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }
            }
        }
        if (len < 4) {
            return;
        }

        Point[] backupPoints = Arrays.copyOf(points, points.length);
        LineSegments = new ArrayList<>();
        for (Point p: backupPoints){
            Arrays.sort(points, p.slopeOrder());
            for (int i = 1; i < points.length; ){
                int j = i + 1;
                while (j < points.length && Collinear(p, points[i], points[j])){
                    j++;
                }
                // 只在参考点为线段的端点时添加该线段
                if ((j - i) >= 3 && p.compareTo(min(points, i, j - 1)) < 0) LineSegments.add(new LineSegment(max(points, i, j-1), p));
                if (j >= points.length) break;
                i = j;
            }
        }


    }     // finds all line segments containing 4 or more points
    private Point min(Point[] a, int lo, int hi) {
        if (lo > hi || a == null) {
            throw new IllegalArgumentException();
        }
        Point ret = a[lo];
        for (int i = lo + 1; i <= hi; i++) {
            if (ret.compareTo(a[i]) > 0) {
                ret = a[i];
            }
        }
        return ret;
    }

    private Point max(Point[] a, int lo, int hi) {
        if (lo > hi || a == null) {
            throw new IllegalArgumentException();
        }
        Point ret = a[lo];
        for (int i = lo + 1; i <= hi; i++) {
            if (ret.compareTo(a[i]) < 0) {
                ret = a[i];
            }
        }
        return ret;
    }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}