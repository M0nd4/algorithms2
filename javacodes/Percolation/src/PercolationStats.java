
public class PercolationStats {
	private double [] probs;
	private double runs;
	
	public PercolationStats(int N, int T) {	
		double dim = N;
		runs = T;
		probs = new double[T];
		int r;
		int c;
		double runsTillPerc;
		for (int i = 0; i < T; i++) {		
			runsTillPerc = 0.0;
			Percolation perc = new Percolation(N);
			while (!perc.percolates()) {  
				r = StdRandom.uniform(1, N + 1);
				c = StdRandom.uniform(1, N + 1);
				if (!perc.isOpen(r, c)) {
					perc.open(r, c);
					runsTillPerc += 1;
				}
			}
			probs[i] = runsTillPerc/(dim * dim);
		}	
	}
	
	public double mean()  {   
		return StdStats.mean(probs);
	}
	
	public double stddev() {    
		return StdStats.stddev(probs);
	}
	
	public double confidenceLo() {  
		return (mean() - (1.96 * stddev())/Math.sqrt(runs));
	}
	
	public double confidenceHi() { 
		return (mean() + (1.96 * stddev())/Math.sqrt(runs));
	}
		
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats out = new PercolationStats(N, T);
		StdOut.println("mean = " + out.mean());
		StdOut.println("stddev = " + out.stddev());
		StdOut.println("95% confidence interval = " 
		+ out.confidenceLo() + " " + out.confidenceHi());
	}

}
