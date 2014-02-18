import java.util.ArrayList;
import java.util.Arrays;


public class OldFast {
    
    private static ArrayList<Point[]> redundTuples = new ArrayList<Point[]>();

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
        
        // fast sort
                
        // every point can be centre, except last 3
        for (int p = 0; p < N - 3; p++) {
            
            // for other points, sort with slopes, then get each slopes
            Point[] rest = Arrays.copyOfRange(points, p + 1, N);
            Arrays.sort(rest, points[p].SLOPE_ORDER);
            double[] slopes = new double[rest.length];
            for (int q = 0; q < rest.length; q++) {
                slopes[q] = points[p].slopeTo(rest[q]);
            }
            
            // in those slopes, find consecutive equal items more than 3
            // note: num starts with 1, so num >= 3 means qualified
            int num = 1;
            for (int i = 0; i < slopes.length; i++) {
                if (i != slopes.length - 1 && slopes[i] == slopes[i + 1]) {
                    num++;
                }
                
                // check num >= 3? every time when num need go back to 1
                else {
                    if (num >= 3) {
                        
                        // check redundance
                        boolean redund1 = false;
                        boolean redund2 = false;
                        for (Point[] redundTuple : redundTuples) {
//                            if (Arrays.asList(redundTuple).contains(points[p])
//                             && Arrays.asList(redundTuple).contains(rest[i])) {
//                                redund = true;
//                                break;
//                            }
                            for (Point redundPoint : redundTuple) {
                                if (redundPoint.equals(points[p])) {
                                    redund1 = true;
                                    break;
                                }
                            }
                            if (redund1) {
                                for (Point redundPoint : redundTuple) {
                                    if (redundPoint.equals(rest[i])) {
                                        redund2 = true;
                                        break;
                                    }
                                }
                            }
                            if (redund2) break;
                            else {
                                redund1 = false;
                            }
                        }
                        
                        
                        // output
                        
                        if (!redund2) {
                            // 1, get tuple, sort
                            Point[] tuple = new Point[num + 1];
                            for (int j = 0; j < num; j++) {
                                tuple[j] = rest[i - j];
                            }
                            
                            tuple[num] = points[p];
                            
                            
                            Merge.sort(tuple);
                            
                            // 2, output string
                            for (int k = 0; k < tuple.length - 1; k++) {
                                StdOut.print(tuple[k].toString() + " -> ");
                            }
                            StdOut.print(tuple[tuple.length - 1]);
                            StdOut.println();
                            
                            // 3, draw line
                            tuple[0].drawTo(tuple[tuple.length - 1]);
                            
                            // maybe redundance?
                            if (num > 3) {
                                redundTuples.add(tuple);
                            }
                            

                        }
                        
                    }
                    
                    // num go back to 1 when 
                    num = 1;
                }
            }
        }
        
        // display to screen all at once
        StdDraw.show(0);

    }

}


