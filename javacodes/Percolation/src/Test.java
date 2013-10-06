
public class Test {

	
	public static void main(String[] args) {
		
		int N = 100;
		int T = 100;
		double [] probs = new double[T];
		int r = 0;
		int c = 0;
		double runsTillPerc;
		for(int i=0;i<T;i++) {		
			runsTillPerc = 0.0;
			Percolation perc = new Percolation(N);
			while(!perc.percolates()) {  		// Choose random coordinate to open until site percolates			
				r = StdRandom.uniform(1,N+1);
				c = StdRandom.uniform(1,N+1);
				if(!perc.isOpen(r,c)) {
					perc.open(r,c);
					runsTillPerc++;
				}
			}
			probs[i] = (runsTillPerc/(N*N));
		}
		StdArrayIO.print(probs);
		StdOut.println(StdStats.mean(probs));
		StdOut.println(StdStats.stddev(probs));
	}
}
