import java.awt.Color;
//import java.awt.Point;
import java.awt.Polygon;
import java.awt.Dimension;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;

// Here we defined some test funtions, which can be used to make tests.
public abstract class Test {

	// Returns the number of free polyominoes of size n (using the naive algorithm), with the runtime.
	static void numberOfFreePolyominosNaive(int n) {
		long startTime = System.nanoTime();
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFree(n);
		long endTime = System.nanoTime();
		System.out.println("Using Naive : There are " + polyominoes.size() + " free polyominoes of size " + n + ".");
		System.out.println("Took "+ (endTime - startTime) + " ns");
		System.out.println("Approximately "+ (endTime - startTime)/1000000000 + " s");
	}

	// Returns the number of fixed polyominoes of size n (using the naive algorithm), with the runtime
	static void numberOfFixedPolyominosNaive(int n) {
		long startTime = System.nanoTime();
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFixed(n);
		System.out.println("Using Naive : There are " + polyominoes.size() + " fixed polyominoes of size " + n + ".");
		long endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");
		System.out.println("Approximately "+ (endTime - startTime)/1000000000 + " s");
	}

	// This function prints what is contained in the file 'INF421'
	static void inf421printer() {
		CustomJFrame frame = new CustomJFrame();	
		Polyomino[] polyominoes =
			Polyomino.fileReader("assets/Figure2.txt");
		for (int i=0; i < polyominoes.length; i++){
			frame.add(polyominoes[i]);
		}
		frame.pack();
		frame.setVisible(true);
		//frame.add(polyominoes, new Point(2,0));
	}

	// This function uses the naive algorithm to represent all fixed polyominoes of size n for a given n
	static void fixedGenerator(int n) {
		CustomJFrame frame = new CustomJFrame();	
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFixed(n);
		Point center = new Point(2,10);
		frame.add(polyominoes);
	}

	// This function prints the free polyominoes using the naive algorithm
	static void freeGenerator(int n) {
		CustomJFrame frame = new CustomJFrame();	
		frame.setPreferredSize(new Dimension(500,500));
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFree(n);
		Point center = new Point(2,10);
		frame.add(polyominoes);
		frame.revalidate();
		frame.repaint();
	}

	static void ECPolyominoExample() {
		Polyomino p = new Polyomino("[(0,0), (0,1), (1,0)]");
		Polyomino bigP = p.dilateBy(3);
		HashSet<Polyomino> equivalent = p.getEquivalent();
		HashSet<HashSet<Polyomino>> solutions =
			new HashSet<HashSet<Polyomino>>();

		HashSet<Polyomino> pieces = new HashSet<Polyomino>();
		for (Polyomino temp : equivalent){
			pieces.addAll(temp.getFittings(bigP));
		}
		ECPolyomino problem = new ECPolyomino(bigP, pieces);

		CustomJFrame frame = new CustomJFrame();
		for (HashSet<Polyomino> solution :  problem.covers(true, 0)){
			frame.add(new JoinedPolyominoes(solution, "random"));
		}
		frame.pack();
		frame.setVisible(true);
	}

	static void ECPolyominoExample2() {
		Polyomino bigP = new Polyomino("[(0,0)]");
		bigP = bigP.stretchX(4);
		bigP = bigP.stretchY(3);

		HashSet<Polyomino> originalPieces = Polyomino.naiveGenerateFixed(3);
		HashSet<Polyomino> pieces = new HashSet<Polyomino>();

		for (Polyomino temp : originalPieces){
			pieces.addAll(temp.getFittings(bigP));
		}

		ECPolyomino problem = new ECPolyomino(bigP, pieces);

		CustomJFrame frame = new CustomJFrame();
		for (HashSet<Polyomino> solution : problem.covers(true, 0)){
			frame.add(new JoinedPolyominoes(solution, "random"));
		}
		frame.pack();
		frame.setVisible(true);
	}

	static void GenericECExample() {
		Polyomino p = new Polyomino("[(0,0), (0,1), (1,0)]");
		Polyomino bigP = p.dilateBy(3);
		HashSet<Polyomino> equivalent = p.getEquivalent();

		//CustomJFrame frame =
		//	new CustomJFrame(bigP.draw(new Point()));
		//frame.add(equivalent, new Point(0, 6));
		HashSet<Polyomino> pieces = new HashSet<Polyomino>();
		for (Polyomino temp : equivalent){
			pieces.addAll(temp.getFittings(bigP));
		}

		GenericsEC<Point> problem = bigP.getEC(pieces);

		CustomJFrame frame = new CustomJFrame();
		for (HashSet<HashSet<Point>> solution :  problem.covers(true)){
			Image2d component = new Image2d();
			HashSet<Polyomino> polyominoesSol =	new HashSet<Polyomino>();

			for (HashSet<Point> tiles : solution){
				Polyomino temp = new Polyomino(tiles);
				temp.randomColor();
				polyominoesSol.add(temp);
			}
			frame.add(new JoinedPolyominoes(polyominoesSol));
		}
		frame.pack();
		frame.setVisible(true);
	}

	// A test for the exact cover problem
	static void TextualECExample() throws Exception {
		HashSet<HashSet<Node>> NodeList = new HashSet<HashSet<Node>>();

		int[][] matrix = new int[6][7];
		matrix[0] = new int[] {0, 0, 1, 0, 1, 1, 0};
		matrix[1] = new int[] {1, 0, 0, 1, 0, 0, 1};
		matrix[2] = new int[] {0, 1, 1, 0, 0, 1, 0};
		matrix[3] = new int[] {1, 0, 0, 1, 0, 0, 0};
		matrix[4] = new int[] {0, 1, 0, 0, 0, 0, 1};
		matrix[5] = new int[] {0, 0, 0, 1, 1, 0, 1};
		// Hint to test the case with 2 solutions:
		// Make matrix[1] = matrix[3]
		matrix[1] = matrix[3];

		System.out.println('\n' + visualizeMatrix(matrix));

		// Naive solution:
		long startTime = System.nanoTime();
		HashSet<HashSet<Integer>> Solutions =
			ExactCover.cover(new int[matrix[0].length], matrix);
		long endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");


		for (HashSet<Integer> solution : Solutions) {
			System.out.print("\nSolution: ");
			for (Integer i : solution) {
				System.out.print(" " + i);
			}
		}
		System.out.println("\nNumber of solutions: " + Solutions.size());

		Column DL = new Column(matrix);

		// Checking if DL was correctly constructed
		matrix = new int[6][7];
		// System.out.print(visualizeMatrix(matrix));

		startTime = System.nanoTime();
		NodeList = DL.exactCover();
		endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");

		System.out.print("\nNumber of solutions: " + NodeList.size());
	}

	// A test of the Dancing links algorithm
	static void TextualDancingLinks() throws Exception{
		HashSet<HashSet<Node>> NodeList = new HashSet<HashSet<Node>>();

		int[][] matrix = new int[6][7];
		matrix[0] = new int[] {0, 0, 1, 0, 1, 1, 0};
		matrix[1] = new int[] {1, 0, 0, 1, 0, 0, 1};
		matrix[2] = new int[] {0, 1, 1, 0, 0, 1, 0};
		matrix[3] = new int[] {1, 0, 0, 1, 0, 0, 0};
		matrix[4] = new int[] {0, 1, 0, 0, 0, 0, 1};
		matrix[5] = new int[] {0, 0, 0, 1, 1, 0, 1};
		// Hint to test the case with 2 solutions:
		// Make matrix[1] = matrix[3]
		matrix[1] = matrix[3];

		System.out.println('\n' + visualizeMatrix(matrix));
		// Naive solution:

		long startTime = System.nanoTime();
		HashSet<HashSet<Integer>> Solutions = ExactCover.cover(new int[matrix[0].length], matrix);
		long endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");

		int counter = 0;
		for (HashSet<Integer> solution : Solutions) {
			System.out.print("\nSolution " + counter + ": " + solution);
			counter++;
		}

		System.out.println("\nNumber of solutions: " + Solutions.size());

		Column column = new Column(matrix);

		System.out.println(visualizeMatrix(matrix));

		startTime = System.nanoTime();
		NodeList = column.exactCover();
		endTime = System.nanoTime();

		System.out.println("Took "+ (endTime - startTime) + " ns");

		counter = 0;
		for(HashSet<Node> solution : NodeList){
			System.out.print("\nSolution " + counter + ": " + solution);
			counter++;
		}
		System.out.println("\nNumber of solutions: " + NodeList.size());
	}
	static void DancingLinks() throws Exception{
		Polyomino bigP = new Polyomino("[(0,0)]");

		//bigP = bigP.stretchX(4);
		//bigP = bigP.stretchY(3);
		//HashSet<Polyomino> originalPieces =
		//Polyomino.naiveGenerateFixed(3);

		bigP = bigP.stretchX(6);
		bigP = bigP.stretchY(4);
		HashSet<Polyomino> originalPieces =
			Polyomino.naiveGenerateFixed(4);
		ArrayList<Polyomino> pieces = new ArrayList<Polyomino>();

		for (Polyomino temp : originalPieces){
			pieces.addAll(temp.getFittings(bigP));
		}

		Column column = new Column(bigP.getECMatrix(pieces));

		CustomJFrame frame = new CustomJFrame();
		for (HashSet<Node> solution : column.exactCover()){
			HashSet<Polyomino> solutionPolyominoes =
			   new HashSet<Polyomino>();

			for (Node node : solution){
				solutionPolyominoes.add(pieces.get(node.getRowNumber()));
			}
			frame.add(new JoinedPolyominoes(solutionPolyominoes, "random"));
		}
		frame.pack();
		frame.setVisible(true);
	}

	// Function to visualize a matrix
	public static String visualizeMatrix(int[][] matrix) {
		String S = new String();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				S += String.valueOf(matrix[i][j]) + " ";
			}
			S += "\n";
		}
		return S;
	}

	// Function to visualize a matrix
	public static String visualizeMatrix(boolean[][] matrix) {
		String S = new String();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				S += String.valueOf(matrix[i][j]) + " ";
			}
			S += "\n";
		}
		return S;
	}

	// Function that counts for n and k all the polyominoes of size n that can cover their own dilate by k, allowing rotations and symmetries.
	public static void countDilateFixed(int n, int k) {
		LinkedList<HashSet<Polyomino>> polyominoes =
			Polyomino.dilateCoverFixed(n, k);
		System.out.println("\nThere are " + polyominoes.size()
				+ " fixed polyominoes of size " + n
				+ " that cover their " + k + "-dilation");
	}

	// Function that counts for n and k all the polyominoes of size n that can cover their own dilate by k, without rotations and symmetries
	public static void countDilateFree(int n, int k) {
		LinkedList<HashSet<Polyomino>> polyominoes =
			Polyomino.dilateCoverFree(n, k);
		System.out.println("\nThere are " + polyominoes.size()
				+ " free polyominoes of size " + n
				+ " that cover their " + k + "-dilation");
	}

	// Function that determines for n, and k all the polyominoes of size n that can cover their own k-dilation, and represents them
	public static void dilateRepresentFixed(int n, int k) {
		CustomJFrame frame = new CustomJFrame();
		LinkedList<HashSet<Polyomino>> coverings =
			Polyomino.dilateCoverFixed(n, k);

		for (HashSet<Polyomino> solution : coverings){
			//frame.add(solution);
			frame.add(new JoinedPolyominoes(solution, "random"));
		}
		frame.pack();
		frame.setVisible(true);
	}

	// Function that determines for n, and k all the polyominoes of size n that can cover their own k-dilation, and represents them
	public static void dilateRepresentFree(int n, int k) {
		CustomJFrame frame = new CustomJFrame();
		LinkedList<HashSet<Polyomino>> coverings =
			Polyomino.dilateCoverFree(n, k);

		for (HashSet<Polyomino> solution : coverings){
			frame.add(new JoinedPolyominoes(solution, "random"));
		}
		frame.pack();
		frame.setVisible(true);
	}	
}
