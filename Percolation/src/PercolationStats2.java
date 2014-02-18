public class PercolationStats2 {

	private double[] data;

	public PercolationStats2(int N, int T) {
		// perform T independent computational experiments on an N-by-N grid
		if (N <= 0 | T <= 0) {
			throw new java.lang.IllegalArgumentException("invalid input");
		}
		data = new double[T];

		StdRandom.setSeed(1);

		for (int k = 0; k < data.length; k++) {

			Percolation p = new Percolation(N);

			double count = 0;
			boolean stop = false;
			while (!stop) {
				int i = StdRandom.uniform(N) + 1;
				int j = StdRandom.uniform(N) + 1;
				if (!p.isOpen(i, j)) {
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
		return StdStats.mean(data);
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		return StdStats.stddev(data);
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
		int N = 20;
		int T = 10;

		for (N = 10; N <= 1280; N *= 2) {

			Stopwatch stopwatch = new Stopwatch();

			PercolationStats p = new PercolationStats(N, T);

			double time = stopwatch.elapsedTime();

			System.out.println("mean                    = " + p.mean());
			System.out.println("stddev                  = " + p.stddev());
			System.out.println("95% confidence interval = " + p.confidenceLo()
					+ ", " + p.confidenceHi());

			System.out.println("Running time: " + time + " for N = " + N
					+ ", T = " + T);

		}
	}

}
