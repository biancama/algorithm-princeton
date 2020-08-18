import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FastCollinearPoints {

    private final List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
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
            Point p = points[i];
            Point[] pointsBySlope = Arrays.copyOf(points, n);
            Arrays.sort(pointsBySlope, p.slopeOrder());
            int j = 1;
            while (j < n) {

                List<Point> candidates = new ArrayList<>();
                final double slope = p.slopeTo(pointsBySlope[j]);
                do {
                    candidates.add(pointsBySlope[j++]);
                } while (j < n && p.slopeTo(pointsBySlope[j]) == slope);

                if (candidates.size() >= 3
                        && p.compareTo(candidates.get(0)) < 0) {
                    Point min = p;
                    Point max = candidates.get(candidates.size() - 1);
                    lineSegments.add(new LineSegment(min, max));
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

        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            StdDraw.circle(x, y, 2.0);
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
