import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ExactCoverList {

	public static LinkedList<List<Integer>> cover(List<Integer> X, LinkedList<List<Integer>> C) {
		Integer M = minX(X, C);
		//Use M = X.get(0) for naive implementation

		// This isn't the correct code for our project, but we added as we still worked on it
		// Returns a LinkedList with the solutions to the exact cover problem, but it doesn`t take as entry an exact cover problem matrix, instead it takes a Ground Set and a Collection, represented by LinkedLists

		LinkedList<List<Integer>> P = new LinkedList<List<Integer>>();

		for (List<Integer> S : C) {
			if(S.contains(M)){
				List<Integer> Y = new ArrayList<Integer>(X);
				LinkedList<List<Integer>> D = (LinkedList<List<Integer>>) C.clone();

				for(Integer y : S) {
					Y.remove(y);
					for(List<Integer> T : C) {
						if(T.contains(y)) {
							D.remove(T);
						}
					}
				}

				if (Y.isEmpty()) {
					P.add(new ArrayList<Integer>(Arrays.asList(0)));
					P.add(S);
					return P;
				}

				LinkedList<List<Integer>> e = cover(Y,D);

				if(!e.isEmpty()) {
					P.addAll(e);
					P.add(S);
				}
			}
		}

		return P; 
		}

	public static Integer minX(List<Integer> X, LinkedList<List<Integer>> C) {
		Integer M = X.size();
		Integer i = 0;
		Integer m = C.size();

		for(Integer t : X) {
			i = 0;
			for(List<Integer> S : C) {
				if(S.contains(t)) {
					i++;
				}
			}
			if(i < m) {
				m = i;
				M = t;
			}
		}
		return M;
	}
}
