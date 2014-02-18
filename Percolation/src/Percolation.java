public class Percolation {

	private int n;
	private boolean[][] grids;
	private WeightedQuickUnionUF wquuf;
	private WeightedQuickUnionUF wquuf2;

	public Percolation(int N) {
		// create N-by-N grid, with all sites blocked
		n = N;
		grids = new boolean[n][n];
		wquuf = new WeightedQuickUnionUF(n * n + 2);
		wquuf2 = new WeightedQuickUnionUF(n * n + 1);
	}

	private boolean getGrid(int i, int j) {
		return grids[i - 1][j - 1];
	}

	private void setGrid(int i, int j) {
		grids[i - 1][j - 1] = true;
	}

	public void open(int i, int j) {
		// open site (row i, column j) if it is not already
		if (i < 1 || i > n || j < 1 || j > n) {
			throw new java.lang.IndexOutOfBoundsException(
					"i or j is outside range: 1 - " + n);
		}

		if (!isOpen(i, j)) {
			setGrid(i, j);
			// if it's on top, union it with 0
			if (i == 1) {
				wquuf.union(j, 0);
				wquuf2.union(j, 0);
			}
			// check the group above, union if open
			else {
				if (isOpen(i - 1, j)) {
					wquuf.union((i - 2) * n + j, (i - 1) * n + j);
					wquuf2.union((i - 2) * n + j, (i - 1) * n + j);
				}
			}
			// check the group on the left, union if open
			if (j != 1 && isOpen(i, j - 1)) {
				wquuf.union((i - 1) * n + (j - 1), (i - 1) * n + j);
				wquuf2.union((i - 1) * n + (j - 1), (i - 1) * n + j);
			}
			// check the group on the right, union if open
			if (j != n && isOpen(i, j + 1)) {
				wquuf.union((i - 1) * n + (j + 1), (i - 1) * n + j);
				wquuf2.union((i - 1) * n + (j + 1), (i - 1) * n + j);
			}

			// if it's at bottom, then union it with N*N+1
			if (i == n) {
				wquuf.union((n - 1) * n + j, n * n + 1);
			}
			// check the group below, union if open
			else {
				if (isOpen(i + 1, j)) {
					wquuf.union(i * n + j, (i - 1) * n + j);
					wquuf2.union(i * n + j, (i - 1) * n + j);
				}
			}
		}
	}

	public boolean isOpen(int i, int j) {
		// is site (row i, column j) open?
		if (i < 1 || i > n || j < 1 || j > n) {
			throw new java.lang.IndexOutOfBoundsException(
					"i or j is outside range: 1 - " + n);
		}
		return getGrid(i, j);
	}

	public boolean isFull(int i, int j) {
		// is site (row i, column j) full?
		if (i < 1 || i > n || j < 1 || j > n) {
			throw new java.lang.IndexOutOfBoundsException(
					"i or j is outside range: 1 - " + n);
		}
		return wquuf2.connected(0, (i - 1) * n + j);
	}

	public boolean percolates() {
		// does the system percolate?
		return wquuf.connected(0, n * n + 1);
	}

}
