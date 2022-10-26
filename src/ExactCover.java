import java.util.HashSet;

public class ExactCover {

	
	
	// Returns a LinkedList, containing each solution to the exact cover problem
	
	public static HashSet<HashSet<Integer>> cover(int[] X, int[][] C) {
		HashSet<HashSet<Integer>> P = new HashSet<HashSet<Integer>>();

//		Use m = (first i such that X[i] = 0) for naive implementation
		int m = minX(X, C);
		if(m == C[0].length + 1) {
			return P;
		}

		for (int t = 0; t < C.length; t++){
			if(C[t][m] != 0){
				int[] Y = X.clone();
				int[][] D = C.clone();

				for (int y = 0; y < C[0].length; y++) {
					if(C[t][y] != 0) {
						Y[y] = 1;
						for (int i = 0; i < C.length; i++) {
							if(C[i][y] != 0) {
								D[i] = new int[D[0].length];
							}
						}
					}
				}

				int c = 0;
				for (int j = 0; j < D[0].length; j++) {
					if (Y[j] == 1) {
						c++;
					}
				}
				// Stopping condition (Solution found)
				if(c == Y.length) {
					HashSet<Integer> p = new HashSet<Integer>();
					p.add(t);

					P.add(p);
					return P;
				}

				HashSet<HashSet<Integer>> e = cover(Y, D);

				// An empty return means no solution found after the search, so recursively, we continue with an empty subcollection P and return it
				// If we found a solution in later calls, we add the current row as part of the solution in all possible solutions found considering it, so we add it to each entry of P
				if(!e.isEmpty()) {
					P.addAll(e);
					for (HashSet<Integer> solution : e) {
						solution.add(t);
					}
				}
			}
		}

		return P;
		}

	//Searches the column with the least amount of possible covers
	
	public static int minX(int[] X, int[][] C) {
		int m = C[0].length + 1;
		int c = 0;
		int M = C.length + 1;

		for (int t = 0; t < C[0].length; t++) {
			if(X[t] == 1) continue;

			c = 0;
			for (int s = 0; s < C.length; s++) {
				if(C[s][t] != 0) {
					c++;
				}
			}
			if(c < M) {
				M = c;
				m = t;
			}
		}
		return m;
	}
}
