public class Board {

    private int N;
    private int[][] blocks;

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        N = blocks[0].length;
        this.blocks = new int[N][N];
        for (int k = 0; k < N * N; k++) {
            this.blocks[k / N][k % N] = blocks[k / N][k % N];
        }
    }

    public int dimension() {
        // board dimension N
        return N;
    }

    public int hamming() {
        // number of blocks out of place
        int hamming = 0;

        for (int k = 0; k < N * N; k++) {
            int v = blocks[k / N][k % N];
            if (v != 0 && v != k + 1)
                hamming++;
        }

        return hamming;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattan = 0;

        for (int k = 0; k < N * N; k++) {
            int v = blocks[k / N][k % N];
            // ignore block "0", other block should be at
            // blocks[(v-1)/N][(v-1)%N]
            if (v != 0)
                manhattan += Math.abs((v - 1) / N - k / N)
                        + Math.abs((v - 1) % N - k % N);
        }

        return manhattan;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return hamming() == 0;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row

        int[][] newBlocks = new int[N][N];
        for (int k = 0; k < N * N; k++) {
            newBlocks[k / N][k % N] = blocks[k / N][k % N];
        }

        int row = 0;
        if (blocks[0][0] == 0 || blocks[0][1] == 0)
            row = 1;

        int swap = newBlocks[row][0];
        newBlocks[row][0] = newBlocks[row][1];
        newBlocks[row][1] = swap;

        return new Board(newBlocks);
    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (this.N != that.N)
            return false;
        for (int k = 0; k < N * N; k++) {
            if (this.blocks[k / N][k % N] != that.blocks[k / N][k % N])
                return false;
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        // get the position of block "0" if yet
        int zeroi = 0;
        int zeroj = 0;
        for (int k = 0; k < N * N; k++) {
            if (blocks[k / N][k % N] == 0) {
                zeroi = k / N;
                zeroj = k % N;
                break;
            }
        }
        Queue<Board> neighbors = new Queue<Board>();
        if (zeroi != 0) {
            neighbors.enqueue(neighbor(zeroi, zeroj, zeroi - 1, zeroj));
        }
        if (zeroi != N - 1) {
            neighbors.enqueue(neighbor(zeroi, zeroj, zeroi + 1, zeroj));
        }
        if (zeroj != 0) {
            neighbors.enqueue(neighbor(zeroi, zeroj, zeroi, zeroj - 1));
        }
        if (zeroj != N - 1) {
            neighbors.enqueue(neighbor(zeroi, zeroj, zeroi, zeroj + 1));
        }
        return neighbors;
    }

    private Board neighbor(int i0, int j0, int i1, int j1) {
        int[][] newBlocks = new int[N][N];
        for (int k = 0; k < N * N; k++) {
            newBlocks[k / N][k % N] = blocks[k / N][k % N];
        }
        newBlocks[i0][j0] = newBlocks[i1][j1];
        newBlocks[i1][j1] = 0;
        return new Board(newBlocks);
    }

    public String toString() {
        // string representation of the board (in the output format specified
        // below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}
