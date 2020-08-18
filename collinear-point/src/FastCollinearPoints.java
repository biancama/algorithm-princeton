import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class BruteCollinearPoints {

    private Map<Double, List<Point>> segments = new HashMap<>();

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("points are null");
        }
        int N = points.length;
        for (int i = 0; i < N - 3; i++) {
            for (int j = i + 1; j < N - 2; j++) {
                for (int k = j + 1; k < N - 1; k++) {
                    for (int l = k + 1; l < N; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        if (p == null || q == null || r == null || s == null ) {
                            throw new IllegalArgumentException("One Point is null");
                        }
                        double slope_p_q = p.slopeTo(q);
                        double slope_p_r = p.slopeTo(r);
                        double slope_p_s = p.slopeTo(s);
                        if (slope_p_q == Double.NEGATIVE_INFINITY || slope_p_r == Double.NEGATIVE_INFINITY || slope_p_s == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException("Two Points are equal");
                        }
                        if (slope_p_q == slope_p_r && slope_p_q == slope_p_s) {  // collinear
                            List<Point> collinearPoints = new ArrayList<>(){{
                                add(p);
                                add(q);
                                add(r);
                                add(s);
                            }};
                            // check if we need to replace a previous line segment
                            if (segments.containsKey(Double.valueOf(slope_p_q))) {
                                segments.get(Double.valueOf(slope_p_q)).addAll(collinearPoints);
                            } else {
                                segments.put (Double.valueOf(slope_p_q), collinearPoints);
                            }

                        };
                    }
                }
            }
        }

    }
    public int numberOfSegments() {     // the number of line segments
        return segments.size();
    }
    public LineSegment[] segments() { // the line segments
        LineSegment[] result = new LineSegment[segments.size()];
        int i = 0;
        for (List<Point> points : segments.values()) {
            Collections.sort(points);
            result[i++] = new LineSegment(points.get(0), points.get(points.size() - 1));
        }
        return result;
    }
    public static void main(String[] args) {

        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
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
