import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {

    private final List<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("points are null");
        }
        int n = points.length;

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Point is  null");
            }
        }
        points = Arrays.copyOf(points, n);
        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate(s) found.");
            }
        }

        lineSegments = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];

                        double slopePQ = p.slopeTo(q);
                        double slopePR = p.slopeTo(r);
                        double slopePS = p.slopeTo(s);
                        if (slopePQ == Double.NEGATIVE_INFINITY || slopePR == Double.NEGATIVE_INFINITY || slopePS == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException("Two Points are equal");
                        }
                        if (slopePQ == slopePR && slopePQ == slopePS) {  // collinear
                            List<Point> collinearPoints = new ArrayList<>() {{
                                add(p);
                                add(q);
                                add(r);
                                add(s);
                            }};
                            // check if we need to replace a previous line segment
                            Collections.sort(collinearPoints);
                            lineSegments.add(new LineSegment(collinearPoints.get(0), collinearPoints.get(3)));
                        }
                    }
                }
            }
        }

    }
    public int numberOfSegments() {     // the number of line segments
        return lineSegments.size();
    }
    public LineSegment[] segments() { // the line segments
        return lineSegments.toArray(new LineSegment[0]);
    }
    public static void main(String[] args) {
//        Point[] pointsEx = new Point[1];
//        BruteCollinearPoints collinear01 = new BruteCollinearPoints(pointsEx);
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
