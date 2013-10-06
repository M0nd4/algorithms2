public class Percolation {
	private WeightedQuickUnionUF uf;
	private boolean [][] cond;
	private int dim;


	public Percolation(int N) {	       
		uf = new WeightedQuickUnionUF((N + 3) * (N + 2));
		cond = new boolean[N + 3][N + 2];
		dim = N;
		for (int i = 0; i < dim + 3; i++) {
			for (int j = 0; j < dim + 2; j++) {
				cond[i][j] = false;
			}
		}	
		// Set the first pixel and the last to open
		cond[dim + 1][dim + 1] = true; 
		// Then any open pixel in the first 
		// or last row points to these
		cond[dim + 2][dim + 1] = true;
	}

	public void open(int i, int j)	{     				
		validate(i, j);
		// open site (row i, column j) if it is not already
		if (!isOpen(i, j)) {
			cond[i][j] = true;	// open cell
			if (i == 1) {	// cell opened in the first row
				uf.union(to1D(i, j), to1D(dim + 1, dim + 1));
				if (isOpen(i + 1, j)) {
					uf.union(to1D(i, j), to1D(i + 1, j));
				}
			}
			else if (i == dim) {
				uf.union(to1D(i, j), to1D(dim + 2, dim + 1));
				if (isOpen(i - 1, j)) {
					uf.union(to1D(i, j), to1D(i - 1, j));
				}
			}
			else {		// Not first or last row
				if (isOpen(i - 1, j)) {	// row before, same column
					uf.union(to1D(i, j), to1D(i - 1, j));
				}
				if (isOpen(i + 1, j)) {	// row ahead, same column
					uf.union(to1D(i, j), to1D(i + 1, j));
				}
				// same row, column before
				if (isOpen(i, j - 1)) {		
					uf.union(to1D(i, j), to1D(i, j - 1));
				}
				// same row, column ahead
				if (isOpen(i, j + 1)) {		
					uf.union(to1D(i, j), to1D(i, j + 1));
				}
			}
		}
	}

	public boolean isOpen(int i, int j) {   
		validate(i, j);
		return cond[i][j];
	}

	public boolean isFull(int i, int j) {  
		validate(i, j);
		return isOpen(i, j) 
				&& uf.connected(to1D(i, j), 
						to1D(dim + 1, dim + 1));	
	}

	public boolean percolates() {    // system percolate?
		return uf.connected(to1D(dim + 1, dim + 1), 
				to1D(dim + 2, dim + 1));
	}

	private void validate(int i, int j) {
		if (i < 0 || j < 0 || i > dim + 2 || j > dim + 2) {
			throw new IndexOutOfBoundsException("Index");
		}
	}

	private int to1D(int i, int j) {
		return dim*i + j;
	}

	// test client
	public static void main(String[] args) {

	}
}	


