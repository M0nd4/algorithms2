public class Board {
    private int[][] board;
    private int dim;
    private int[][] goal;

    /**
     * Construct a board from an N-by-N array of blocks (where blocks[i][j] =
     * block in row i, column j)
     */
    public Board(int[][] blocks) {
        this.board = copyBlocks(blocks);
        this.dim = blocks.length;
        this.goal = new int[this.dim][this.dim];
        int count = 0;
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                this.goal[i][j] = ++count;
            }
        }
        this.goal[this.dim - 1][this.dim - 1] = 0;
    }

    /**
     * Copy blocks to new board
     */
    private int[][] copyBlocks(int[][] blocks) {
        int[][] b = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                b[i][j] = blocks[i][j];
            }
        }
        return b;
    }

    /**
     * Board dimension N
     */
    public int dimension() {
        return this.dim;
    }

    /**
     * should return the number of blocks out of position
     */
    public int hamming() {
        int num = 0;
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (this.board[i][j] != 0
                        && this.board[i][j] != this.goal[i][j]) {
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * sum of Manhattan distances between blocks and goal, blank square not
     * counted
     */
    public int manhattan() {
        int sum = 0;
        int valInPlace;
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                valInPlace = this.board[r][c];
                if (valInPlace == 0)
                    continue;
                sum += Math.abs(r - (valInPlace - 1) / dim)
                        + Math.abs(c - (valInPlace - 1) % dim);
            }
        }
        return sum;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        return new Board(this.board).equals(new Board(this.goal));
    }

    /**
     * A board obtained by exchanging two adjacent blocks in the same row.
     * Exactly one of a board and its twin are solvable. A twin is obtained by
     * swapping two adjacent blocks (the blank does not count) in the same row.
     */
    public Board twin() {
        int zeroRow = 0;
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (this.board[i][j] == 0)
                    zeroRow = i;
            }
        }
        int r = 0;
        if (r == zeroRow) {
            r++;
        }
        int[][] t = copyBlocks(this.board);
        int swap = t[r][0];
        t[r][0] = t[r][1];
        t[r][1] = swap;
        return new Board(t);
    }

    /**
     * does this board equal x?
     */
    public boolean equals(Object x) {
        if (x == this)
            return true;
        if (x == null)
            return false;
        if (x.getClass() != this.getClass())
            return false;
        Board that = (Board) x;
        if (this.dim != that.dim)
            return false;
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (that.board[i][j] != this.board[i][j])
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns a Queue of all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();
        // Find zero
        int row = 0;
        int col = 0;
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (this.board[r][c] == 0) {
                    row = r;
                    col = c;
                }
            }
        }
        if (row > 0)
            q.enqueue(new Board(swap(this.board, row, col, row - 1, col)));
        if (row < dim - 1)
            q.enqueue(new Board(swap(this.board, row, col, row + 1, col)));
        if (col > 0)
            q.enqueue(new Board(swap(this.board, row, col, row, col - 1)));
        if (col < dim - 1)
            q.enqueue(new Board(swap(this.board, row, col, row, col + 1)));
        return q;
    }

    /**
     * Helper function to swap blocks in Iterable
     */
    private int[][] swap(int[][] array, int fromRow, int fromCol, int toRow,
            int toCol) {
        int[][] copy = copyBlocks(array);
        int tmp = copy[toRow][toCol];
        copy[toRow][toCol] = copy[fromRow][fromCol];
        copy[fromRow][fromCol] = tmp;
        return copy;
    }

    /**
     * string representation of the board (in the output format specified below)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.dim + "\n");
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                s.append(String.format("%2d ", this.board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Test Client
     */
    public static void main(String[] args) {
        int[][] initial = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        int[][] end = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board goal = new Board(end);
        Board test = new Board(initial);
        System.out.println("The dimension is " + test.dimension()
                + "\nThe hamming distance is: " + test.hamming()
                + "\nThis board is \n" + test.toString() + "\nAnd its twin:\n"
                + test.twin().toString());
        System.out.println(goal.isGoal() + "\nThe Manhattan: "
                + test.manhattan() + " " + 2 / 3);
    }
}