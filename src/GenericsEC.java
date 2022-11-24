import java.util.HashSet;

public class GenericsEC<E> {

	public HashSet<E> groundSet;
	public HashSet<HashSet<E>> pieces;

	public GenericsEC(HashSet<E> groundSet, HashSet<HashSet<E>> pieces){
		this.groundSet = new HashSet<E>(groundSet);
		this.pieces = new HashSet<HashSet<E>>(pieces);
	}

	public HashSet<HashSet<HashSet<E>>> covers(
			boolean repeatable) {
			//boolean repeatable, int depth) {
		//depth++;
		HashSet<HashSet<HashSet<E>>> setOfSolutions =
		   	new HashSet<HashSet<HashSet<E>>>();

		// Naive approach, get any piece to cover
		// E toBeCovered = this.groundSet.iterator().next()
		E toBeCovered = this.leastCovers();
		//System.out.println("\n\n\n" + groundSet);
		//System.out.println(pieces);
		//System.out.println("Best: " + toBeCovered);
		//System.out.println(depth);
		if(toBeCovered == null) return setOfSolutions;
		//if(depth == 20) return setOfSolutions;

		//int index = 0;
		for (HashSet<E> piece : pieces){
			//System.out.println("Index: " + index);
			//System.out.println("Element to be covered: " + toBeCovered);
			//System.out.println(piece);
			//System.out.println(groundSet);
			//index++;
			GenericsEC<E> remaining =
				new GenericsEC<E>(this.groundSet, this.pieces);

			if(!piece.contains(toBeCovered)
					|| !groundSet.containsAll(piece)) continue;
			//System.out.println("\nPiece contains: " + piece);
			for (E elementCovered : piece) {
				remaining.groundSet.remove(elementCovered);
				if(repeatable) continue;
				for(HashSet<E> otherPiece : pieces){
					if(otherPiece.contains(elementCovered)) {
						remaining.pieces.remove(otherPiece);
					}
				}
			}
			//System.out.println("Remaining: " + remaining);

			// Stopping condition (Solution found)
			if(remaining.groundSet.isEmpty()) {
				HashSet<HashSet<E>> solution = new HashSet<HashSet<E>>();
				solution.add(piece);

				setOfSolutions.add(solution);
				return setOfSolutions;
			}

			HashSet<HashSet<HashSet<E>>> recursiveAllSolutions =
			   	remaining.covers(repeatable);
			   	//remaining.covers(repeatable, depth);
			//System.out.println("\n\nRecursion: " + recursiveAllSolutions);

			// An empty return means no solution found in further calls
			if(recursiveAllSolutions.isEmpty()) {
				continue;
			}
			// If a solution is found in later calls, we add the current piece as part of the solution to each entry of setOfSolutions
			for (HashSet<HashSet<E>> solution : recursiveAllSolutions) {
				solution.add(piece);
			}
			setOfSolutions.addAll(recursiveAllSolutions);
		}

		return setOfSolutions;
	}

	//Searches the element with the least amount of possible covers
	public E leastCovers() {
		E argmin = null;
		int min = pieces.size() + 1;

		for (E element : groundSet){
			int nCovers = 0;
			for (HashSet<E> piece : pieces) {
				if(piece.contains(element)) nCovers++;
			}
			if(nCovers < min) {
				min = nCovers;
				argmin = element;
			}
		}
		return argmin;
	}
	@Override
	public String toString(){
		String str = "";
		str += this.groundSet.toString();
		str += '\n' + this.pieces.toString();
		return str;
	}
}
