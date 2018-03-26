/**
 * Generate the co-primes from (1,1) to (m,n)
 * 
 * @author Nick Foster
 * @date August 2017
 */

public class CoPrimes {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Must be passed two integer values > 0");
			
			System.exit(0);
		}
		
		int m = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);
		
		for (int i = m; i >= 1; i--) {
			for (int j = 1; j <= n; j++) {
				isCoPrime(i,j);
				
				if (isCoPrime(i,j)) System.out.print("*");
				else				System.out.print(" ");
				
				System.out.print(" ");
			}
			
			System.out.println();
		}
	}
	
	public static boolean isCoPrime(int x, int y) {
		int max = (x >= y) ? x : y;
		int count = 0;
		
		for (int i = 2; i <= max; ++i) {
			if ((x % i == 0) && (y % i == 0)) ++count;
		}
		
		if (count == 0) return true;
		else			return false;
	}

}
