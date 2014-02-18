/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class CopyOfPoint implements Comparable<CopyOfPoint> {

    // compare points by slope
    public final Comparator<CopyOfPoint> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE
    
    private class SlopeOrder implements Comparator<CopyOfPoint> {
        public int compare(CopyOfPoint a, CopyOfPoint b) {
            double slopeA = slopeTo(a);
            double slopeB = slopeTo(b);
            if (slopeA == slopeB) return 0;
            if (slopeA > slopeB) return 1;
            return -1;
        }
    }

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public CopyOfPoint(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(CopyOfPoint that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(CopyOfPoint that) {
        /* YOUR CODE HERE */
        if (this.x == that.x) {
            if (this.y == that.y) {
                return Double.NEGATIVE_INFINITY;
            }
            else {
                return Double.POSITIVE_INFINITY;
            }
        }
        else if (this.y == that.y) {
            return 0;
        }
        return ((double) that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(CopyOfPoint that) {
        /* YOUR CODE HERE */
        if (this.y == that.y) {
            return this.x - that.x;
        }
        return this.y - that.y;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
//        StdDraw.setXscale(-10, 10);
//        StdDraw.setYscale(-10, 10);
//        StdDraw.show(0);
//        Point a = new Point(122,457);
//        a.draw();
//        Point b = new Point(122,273);
//        b.draw();
//        System.out.println(a.slopeTo(b));
//        a.drawTo(b);
//        StdDraw.show(0);
    }
}