public class Scope {
	public static void main(String[] args) {
		int[][][] x = new int[5][5][5];
		x[0][0][1] = 10;
		System.out.println("x1 = " + x[0][1][0]);
	}
}
