public class Percolation2 {
	
	private int n;
	private int group = 2;
	private int[][] grids;
	
	public Percolation2(int N) {
		// create N-by-N grid, with all sites blocked
		n = N; 
		grids = new int[n][n];
	}
	
	public int getGrid(int i, int j) {
		return grids[i - 1][j - 1];
	}
	
	public void setGrid(int i, int j, int num) {
		grids[i - 1][j - 1] = num;
	}

	public void open(int i, int j) {
		// open site (row i, column j) if it is not already
		if ( !isOpen(i, j) ) {
			// if it is in top row, set group id as 1. 1 means full
			if (i == 1) {
				setGrid(i, j, 1);
			}
			// if it's not, let it join the group on it if open, otherwise give a new group id
			else {
				if ( isOpen(i - 1, j) ) {
					setGrid(i, j, getGrid(i - 1, j));
				}
				else {
					setGrid(i, j, group);
					group++;
				}
			}
			// check the group on the left, merge if open
			if ( j != 1 ) {
				if ( isOpen(i, j - 1) ) {
					merge(getGrid(i, j - 1), getGrid(i, j));
				}
			}
			// check the group on the right, merge if open
			if ( j != n ) {
				if ( isOpen(i, j + 1) ) {
					merge(getGrid(i, j + 1), getGrid(i, j));
				}
			}
			// check the group below, merge if open
			if ( i != n ) {
				if ( isOpen(i + 1, j) ) {
					merge(getGrid(i, j), getGrid(i + 1, j));
				}
			}
		}
	}
	
	public boolean isOpen(int i, int j) {
		// is site (row i, column j) open?
		return getGrid(i, j) > 0;
	}
	
	public boolean isFull(int i, int j) {
		// is site (row i, column j) full?
		return getGrid(i, j) == 1;
	}
	
	public boolean percolates() {
		// does the system percolate?
		for (int j = 1; j <= n; j++) {
			if ( isFull(n, j) ) {
				return true;
			}
		}
		return false;
	}
	
	public void merge(int group1, int group2) {
		if (group2 == 1) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (getGrid(i, j) == group1) {
						setGrid(i, j, 1);
					}
				}
			}
		}
		else {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (getGrid(i, j) == group2) {
						setGrid(i, j, group1);
					}
				}
			}
		}
	}

}
