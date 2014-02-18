import java.util.Arrays;


public class CopyOfFast {

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
        for (int p = 0; p < N - 3;p++) {
        	Point[] rest = Arrays.copyOfRange(points, p + 1, N);
        	// double[] slopes = new double[N - p - 1];
        	// for (int q = 0; q < N - p - 1; q++) {slopes[q] = points[p].slopeTo(points[p + q + 1]);}
        	// Merge.sort(slopes);
        	Arrays.sort(rest, points[p].SLOPE_ORDER);
        	double[] slopes = new double[N - p - 1];
        	for (int q = 0; q < slopes.length; q++) {
        		slopes[q] = points[p].slopeTo(points[p + q + 1]);
        	}
        	for (int a = 0; a < slopes.length; a++) {
        		System.out.print(slopes[a] + ", ");
        	}
        	System.out.println();
        	int num = 0;
        	for (int i = 0; i < slopes.length; i++) {
//        		System.out.println(i);
        		if (i != slopes.length - 1 && slopes[i] == slopes[i + 1]) {
        			
        			num++;
        		}
        		else {
        			if (num >= 3) {
        				Point[] tuple = new Point[num + 1];
        				for (int j = 0; j < num; j++) {
        					tuple[j] = rest[i - j];
        				}
        				tuple[num] = points[p];
        				Merge.sort(tuple);
        				for (int k = 0; k < tuple.length - 1; k++) {
        					StdOut.print(tuple[k].toString() + " -> ");
        				}
        				StdOut.print(tuple[tuple.length - 1]);
        				StdOut.println();
        				tuple[0].drawTo(tuple[tuple.length - 1]);
        			}
        			num = 0;
        		}
        		
        	}
        }
        
        // display to screen all at once
        StdDraw.show(0);

    }

}
