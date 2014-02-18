public class PercolationStats1 {
	
	private double[] data;

	public PercolationStats1(int N, int T) {
		// perform T independent computational experiments on an N-by-N grid
		if ( N <= 0 | T <= 0) {
			throw new java.lang.IllegalArgumentException("invalid input");
		}
		data = new double[T];
		
		StdRandom.setSeed(1);
		
		for (int k = 0; k < data.length; k++) {
			
			Percolation p = new Percolation(N);
			
			double count = 0;
			boolean stop = false;
			while(!stop) {
				int i = StdRandom.uniform(N) + 1;
				int j = StdRandom.uniform(N) + 1;
				if ( !p.isOpen(i, j) ) {
					p.open(i, j);
					count++;
					stop = p.percolates();
				}
			}
			data[k] = count / (N * N);
		}
	}
	public double mean() {
		// sample mean of percolation threshold
		double sum = 0;
	    for (int i = 0; i < data.length; i++) {
	        sum += data[i];
	    }
	    return sum / data.length;
	}
	public double stddev() {
		// sample standard deviation of percolation threshold
		double sum = 0;
		double u = mean();
	    for (int i = 0; i < data.length; i++) {
	        sum += (data[i] - u) * (data[i] - u);
	    }
	    return Math.sqrt(sum / (data.length - 1));
	}
	public double confidenceLo() {
		// returns lower bound of the 95% confidence interval
		double u = mean();
		double sigma = stddev();
		return u - 1.96 * sigma / Math.sqrt(data.length);
	}
	public double confidenceHi() {
		// returns upper bound of the 95% confidence interval
		double u = mean();
		double sigma = stddev();
		return u + 1.96 * sigma / Math.sqrt(data.length);
	}
	public static void main(String[] args) {
		// test client, described below
		int N = 10;
		int T = 1;

		for (N=10; N<=640; N *= 2) {
			
			long start = System.currentTimeMillis();
			
			PercolationStats p = new PercolationStats(N, T);
			
			long now = System.currentTimeMillis();
		
		
			System.out.println("mean                    = " + p.mean());
			System.out.println("stddev                  = " + p.stddev());
			System.out.println("95% confidence interval = " + p.confidenceLo() + ", " + p.confidenceHi());
		
			System.out.println("Running time: " + (now - start) / 1000.0 + "s for N = " + N + ", T = " + T);
		}
	}

}
