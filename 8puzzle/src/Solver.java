import java.util.Comparator;

public class Solver {

    private MinPQ<Node> solvePQ;
    private MinPQ<Node> unsolvePQ;
    private Node solution;

    private class Node {
        private Board board;
        private Node previous;
        private int move;
        private int manhattan;

        public Node(Board board) {
            this.board = board;
            previous = null;
            move = 0;
            manhattan = this.board.manhattan();
        }
        
        public Board getBoard() {
            return board;
        }
        public Node getPrev() {
            return previous;
        }
        public void setPrev(Node node) {
            previous = node;
        }
        public int getMove() {
            return move;
        }
        public void setMove(int theMove) {
            move = theMove;
        }
        public int getMan() {
            return manhattan;
        }
    }

    private Comparator<Node> MANHATTAN = new ManhattanPriority();

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        Node initialNode = new Node(initial);
        Node twinNode = new Node(initial.twin());

        solvePQ = new MinPQ<Node>(MANHATTAN);
        unsolvePQ = new MinPQ<Node>(MANHATTAN);

        solvePQ.insert(initialNode);
        unsolvePQ.insert(twinNode);

        boolean stop = false;

        while (!stop) {
            // 1, one more move to solve
            // 1.1 get the best Node
            Node current1 = solvePQ.delMin();
            // 1.2 success?
            if (current1.getMan() == 0) {
                solution = current1;
                break;
            }
            // 1.3 add its neighbors after casting, ignore its previous
            for (Board neighbor : current1.getBoard().neighbors()) {
                if (current1.getPrev() == null
                        || !neighbor.equals(current1.getPrev().getBoard())) {
                    Node newNode = new Node(neighbor);
                    newNode.setPrev(current1);
                    newNode.setMove(current1.getMove() + 1);
                    solvePQ.insert(newNode);
                }
            }
            // 2, one more move to prove unsolvable
            // 2.1 get the best move
            Node current2 = unsolvePQ.delMin();
            // 2.2 success?
            if (current2.getMan() == 1) {
                solution = null;
                break;
            }
            // 2.3 add its neighbors, ignore its previous
            for (Board neighbor : current2.getBoard().neighbors()) {
                if (current2.getPrev() == null
                        || !neighbor.equals(current2.getPrev().getBoard())) {
                    Node newNode = new Node(neighbor);
                    newNode.setPrev(current2);
                    newNode.setMove(current2.getMove() + 1);
                    unsolvePQ.insert(newNode);
                }
            }
        }
    }

    private class ManhattanPriority implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return a.getMan() + a.getMove() - b.getMan() - b.getMove();
        }
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return solution != null;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if no solution
        if (isSolvable()) return solution.getMove();
        else return -1;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if no solution
        if (!isSolvable())
            return null;
        Stack<Board> process = new Stack<Board>();
        Node chase = solution;
        while (chase != null) {
            process.push(chase.getBoard());
            chase = chase.getPrev();
        }
        return process;
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
