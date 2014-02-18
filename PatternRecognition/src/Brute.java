
public class Brute {

    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        
        Point[] points = new Point[N];
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            points[i] = p;
        }
        
        // brute force
        Quick.sort(points);
        for (int p = 0; p < N - 3; p++) {
            for (int q = p + 1; q < N - 2; q++) {
                for (int r = q + 1; r < N - 1; r++) {
                    for (int s = r + 1; s < N; s++) {
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])
                         && points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                            StdOut.println(points[p].toString() + " -> "
                                  + points[q].toString() + " -> "
                                  + points[r].toString() + " -> "
                                  + points[s].toString());
                            points[p].drawTo(points[s]);
                        }
                    }
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);

    }

}
