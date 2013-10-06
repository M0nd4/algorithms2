public class Solver {
    private BoardNode result;

    /**
     * Private class of comparable board nodes, compare by Manhattan priority
     * function
     */
    private class BoardNode implements Comparable<BoardNode> {
        private int moves;
        private int priority;
        private Board board;
        private BoardNode previous;

        private BoardNode(Board board, BoardNode previous) {
            this.board = board;
            this.previous = previous;
            if (previous == null)
                moves = 0;
            if (previous != null)
                moves = previous.moves + 1;
            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(BoardNode that) {
            return this.priority - that.priority;
        }
    }

    /**
     * Find a solution to the initial board (using the A* algorithm), at each
     * step check to see if twin is solvable - it if is result is null.
     */
    public Solver1(Board initial) {
        if (initial.isGoal())
            result = new BoardNode(initial, null);
        else
            result = solve(initial);
    }

    private BoardNode solve(Board initial) {
        BoardNode soln;
        BoardNode twinSoln;
        Board twin = initial.twin();
        // run two priority queues simultaneously to see if twin or initial
        // completes
        MinPQ<BoardNode> pq1 = new MinPQ<BoardNode>();
        pq1.insert(new BoardNode(initial, null));
        MinPQ<BoardNode> pq2 = new MinPQ<BoardNode>();
        pq2.insert(new BoardNode(twin, null));

        while (true) {
            soln = next(pq1);
            twinSoln = next(pq2);
            if (soln.board.isGoal())
                return soln;
            if (twinSoln.board.isGoal())
                return null;
        }
    }

    // create neighbors and find the next with smallest priority
    private BoardNode next(MinPQ<BoardNode> pq) {
        BoardNode next = pq.delMin();
        for (Board neighbor : next.board.neighbors()) {
            // check to see current node is the first node or
            // if the next neighbor has just been inserted
            if (next.previous == null || neighbor.equals(next.previous.board))
                pq.insert(new BoardNode(neighbor, next));
        }
        return next;
    }

    /**
     * Is the initial board solvable?
     */
    public boolean isSolvable() {
        if (result != null)
            return true;
        return false;
    }

    /**
     * Min number of moves to solve initial board; -1 if no solution
     */
    public int moves() {
        if (result != null)
            return result.moves;
        return -1;
    }

    /**
     * Sequence of boards in a shortest solution; null if no solution
     */
    public Iterable<Board> solution() {
        if (result == null)
            return null;
        Stack<Board> path = new Stack<Board>();
        BoardNode current = result;
        while (current.previous != null) {
            path.push(current.board);
            current = current.previous;
        }
        return path;
    }

    /**
     * Test client reads puzzles from file specified as command-line arguments
     * and prints the solution to standard output.
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver1 solver = new Solver1(initial);

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
