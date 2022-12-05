import java.util.HashSet;

public class ECPolyomino {

	public Polyomino groundSet;
	public HashSet<Polyomino> pieces;

	public ECPolyomino(Polyomino groundSet, HashSet<Polyomino> pieces){
		this.groundSet = new Polyomino(groundSet.tiles);
		this.pieces = new HashSet<Polyomino>(pieces);
	}

	public HashSet<HashSet<Polyomino>> covers(boolean repeatable){
		return this.covers(repeatable, 0);
	}
	public HashSet<HashSet<Polyomino>> covers(
			boolean repeatable, int type) {
			//boolean repeatable, int type, int depth) {
		HashSet<HashSet<Polyomino>> setOfSolutions =
		   	new HashSet<HashSet<Polyomino>>();
		//depth++;
		//if(depth == 10) return setOfSolutions;

		// Naive approach, get any piece to cover
		// Point toBeCovered = this.groundSet.iterator().next()
		Point toBeCovered = this.leastCovers();
		//System.out.println("\n\n\n" + groundSet);
		//System.out.println(pieces);
		//System.out.println("Best: " + toBeCovered);
		//System.out.println(depth);
		if(toBeCovered == null) return setOfSolutions;

		//int index = 0;
		for (Polyomino piece : pieces){
		//	System.out.println("Index: " + index);
		//	System.out.println("Element to be covered: " + toBeCovered);
		//	System.out.println(piece);
		//	System.out.println(groundSet);
		//	index++;
			ECPolyomino remaining =
				new ECPolyomino(this.groundSet, this.pieces);

			if(!piece.tiles.contains(toBeCovered)
					|| !groundSet.tiles.containsAll(piece.tiles)) continue;
			for (Point elementCovered : piece.tiles) {
				remaining.groundSet.tiles.remove(elementCovered);
				for(Polyomino otherPiece : pieces){
					if(otherPiece.tiles.contains(elementCovered)) {
						remaining.pieces.remove(otherPiece);
					}
					//if(!repeatable &&
					//		otherPiece.isEquivalentTo(piece, type)){
					//	remaining.pieces.remove(otherPiece);	
					//};
				}
			}
			//System.out.println("Remaining: " + remaining);

			// Stopping condition (Solution found)
			if(remaining.groundSet.tiles.isEmpty()) {
				HashSet<Polyomino> solution = new HashSet<Polyomino>();
				solution.add(piece);

				setOfSolutions.add(solution);
				return setOfSolutions;
			}

			HashSet<HashSet<Polyomino>> recursiveAllSolutions =
			   	remaining.covers(repeatable, type);
			   	//remaining.covers(repeatable, type, depth);
			//System.out.println("\n\nRecursion: " + recursiveAllSolutions);

			// If a solution is found in later calls, we add the current piece as part of the solution to each entry of setOfSolutions
			for (HashSet<Polyomino> solution : recursiveAllSolutions) {
				solution.add(piece);
			}
			setOfSolutions.addAll(recursiveAllSolutions);
		}

		return setOfSolutions;
	}

	//Searches the element with the least amount of possible covers
	public Point leastCovers() {
		Point argmin = null;
		int min = pieces.size() + 1;

		for (Point element : groundSet.tiles){
			int nCovers = 0;
			for (Polyomino piece : pieces) {
				if(piece.tiles.contains(element)) nCovers++;
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
