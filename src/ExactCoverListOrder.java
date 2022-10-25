import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExactCoverListOrder {

	public static LinkedList<Integer> cover(List<Integer> X, LinkedList<List<Integer>> C) {
		List<Integer> K = IntStream.rangeClosed(1, C.size()).boxed().collect(Collectors.toList());
		return cover(X, C, K);
	}

	public static LinkedList<Integer> cover(List<Integer> X, LinkedList<List<Integer>> C, List<Integer> K) {
		Integer M = minX(X, C);
		//Use M = X.get(0) for naive implementation

		LinkedList<Integer> P = new LinkedList<Integer>();
		int c = 0;

		for (List<Integer> S : C) {
			c++;
			if(S.contains(M)){
				List<Integer> Y = new ArrayList<Integer>(X);
				LinkedList<List<Integer>> D = (LinkedList<List<Integer>>) C.clone();
				List<Integer> L = new ArrayList<Integer>(K);

				for(Integer y : S) {
					Y.remove(y);
					int p = 0;
					LinkedList<List<Integer>> E = (LinkedList<List<Integer>>) D.clone();
					if(E.isEmpty()) {
						break;
					}
					for(List<Integer> T : E) {
						p++;
						if(D.isEmpty()) {
							break;
						}
						if(T.contains(y)) {
							L.remove(p - 1);
							D.remove(p - 1);
							p--;
						}
					}
				}

				if (Y.isEmpty()) {
					P.add(0);
					P.add(K.get(c - 1));
					return P;
				}

				LinkedList<Integer> e = cover(Y, D, L);

				if(!e.isEmpty()) {
					P.addAll(e);
					P.add(K.get(c - 1));
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
