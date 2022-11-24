import java.awt.Color;
//import java.awt.Point;
import java.awt.Polygon;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.LinkedList;

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

	static void minimalPolygon(){
		Image2d img = new Image2d();
		int[] x = new int[3],y = new int[3];
		y[1]=50;
		x[2]=50;
		img.addColoredPolygon(new Polygon(x,y,3), new Color(0,0,0));
		new Image2dViewer(img);
	}
	// This function prints what is contained in the file 'INF421'
	static void inf421printer() {
		Image2dViewer frame = new Image2dViewer();	
		Polyomino[] polyominoes = Polyomino.fileReader("assets/Figure2.txt");
		frame.addPolyominoes(polyominoes, new Point(2,0));
	}

	// This function uses the naive algorithm to represent all fixed polyominoes of size n for a given n
	static void fixedGenerator(int n) {
		Image2dViewer frame = new Image2dViewer();	
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFixed(n);
		Point center = new Point(2,10);
		frame.addPolyominoes(polyominoes, center);
	}

	// This function prints the free polyominoes using the naive algorithm
	static void freeGenerator(int n) {
		Image2dViewer frame = new Image2dViewer();	
		frame.setPreferredSize(new Dimension(500,500));
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFree(n);
		Point center = new Point(2,10);
		frame.addPolyominoes(polyominoes, center);
		frame.revalidate();
		frame.repaint();
	}
	static void GenericECExample() {
		Polyomino p = new Polyomino("[(0,0), (0,1), (1,0)]");
		//p = new Polyomino("[(0,0), (0,1)]");
		Polyomino bigP = p.dilateBy(3);
		//System.out.println(p1.getFittings(bigP).size());
		HashSet<Polyomino> equivalent = p.getEquivalent();
		Image2dViewer frame = new Image2dViewer(bigP.draw(new Point()));
		frame.addPolyominoes(equivalent, new Point(0, 6));

		HashSet<Polyomino> pieces = new HashSet<Polyomino>();
		//HashSet<Polyomino> pieces = p.getFittings(bigP);
		for (Polyomino temp : equivalent){
			pieces.addAll(temp.getFittings(bigP));
		}

		GenericsEC<Point> problem = bigP.getEC(pieces);
		System.out.println("Solutions: ");
		for (HashSet<HashSet<Point>> solution :  problem.covers(true)){
			System.out.println("\n" + solution);
		}
	}

	// A test for the exact cover problem
	static void ExactcoverExample() {
		int[][] M = new int[6][7];
		HashSet<HashSet<Node>> NodeList = new HashSet<HashSet<Node>>();

		// Hint: Change M[1] so that it becomes equal to M[3] to visualize the case with 2 different solutions
		M[0] = new int[] {0, 0, 1, 0, 1, 1, 0};
		M[1] = new int[] {1, 0, 0, 1, 0, 0, 1};
		M[2] = new int[] {0, 1, 1, 0, 0, 1, 0};
		M[3] = new int[] {1, 0, 0, 1, 0, 0, 0};
		M[4] = new int[] {0, 1, 0, 0, 0, 0, 1};
		M[5] = new int[] {0, 0, 0, 1, 1, 0, 1};

		System.out.println('\n' + visualizeMatrix(M));

		// Naive solution:
		long startTime = System.nanoTime();
		HashSet<HashSet<Integer>> Solutions = ExactCover.cover(new int[M[0].length], M);
		long endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");


		for (HashSet<Integer> solution : Solutions) {
			System.out.print("\nSolution: ");
			for (Integer i : solution) {
				System.out.print(" " + i);
			}
		}
		System.out.println("\nNumber of solutions: " + Solutions.size());

		DancingLinks DL = new DancingLinks(M);

		// Checking if DL was correctly constructed
		M = new int[6][7];
		for (Column x = (Column) DL.Row[DL.Row.length - 1].R; x != DL.Row[DL.Row.length - 1]; x = (Column) x.R) {

			for (Node y = x.D; y != x; y = y.D) {
				M[y.locator][Integer.parseInt(y.C.N)] = 1;
			}
		}
		// System.out.print(visualizeMatrix(M));

		startTime = System.nanoTime();
		NodeList = DL.exactCoverNode();
		endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");

		System.out.print("\nNumber of solutions: " + NodeList.size());
	}

	// A test of the Dancing links algorithm
	static void TestDancingLinks() {
		int[][] M = new int[6][7];
		HashSet<HashSet<Node>> NodeList = new HashSet<HashSet<Node>>();

		// Hint: Change M[1] so that it becomes equal to M[3] to visualize the case with 2 different solutions

		M[0] = new int[] {0, 0, 1, 0, 1, 1, 0};
		M[1] = new int[] {1, 0, 0, 1, 0, 0, 0};
		M[2] = new int[] {0, 1, 1, 0, 0, 1, 0};
		M[3] = new int[] {1, 0, 0, 1, 0, 0, 0};
		M[4] = new int[] {0, 1, 0, 0, 0, 0, 1};
		M[5] = new int[] {0, 0, 0, 1, 1, 0, 1};

		System.out.println('\n' + visualizeMatrix(M));

		// Naive solution:
		long startTime = System.nanoTime();
		HashSet<HashSet<Integer>> Solutions = ExactCover.cover(new int[M[0].length], M);
		long endTime = System.nanoTime();

		for (HashSet<Integer> solution : Solutions) {
			System.out.print("\nSolution: ");
			for (Integer i : solution) {
				System.out.print(" " + i);
			}
		}
		System.out.println("\nNumber of solutions: " + Solutions.size());
		System.out.println("Took "+ (endTime - startTime) + " ns");

		DancingLinks DL = new DancingLinks(M);

		//Checking if DL was correctly constructed:

		/*		M = new int[6][7];
				for (Column x = (Column) DL.Row[DL.Row.length - 1].R; x != DL.Row[DL.Row.length - 1]; x = (Column) x.R) {

				for (Node y = x.D; y != x; y = y.D) {
				M[y.locator][Integer.parseInt(y.C.N)] = 1;
				}
				}*/

		//		System.out.print(visualizeMatrix(M));

		startTime = System.nanoTime();
		NodeList = DL.exactCoverNode();
		endTime = System.nanoTime();

		for (HashSet<Node> solution : NodeList) {
			System.out.print("\nSolution: ");
			for (Node x : solution) {
				System.out.print(" " + x.locator);
			}
		}

		System.out.println("\nNumber of solutions: " + NodeList.size());
		System.out.println("Took "+ (endTime - startTime) + " ns");
	}

	// Function to visualize a matrix
	public static String visualizeMatrix(int[][] M) {
		String S = new String();
		for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < M[0].length; j++) {
				S += String.valueOf(M[i][j]) + " ";
			}
			S += "\n";
		}
		return S;
	}

	// Function to visualize a matrix
	public static String visualizeMatrix(boolean[][] M) {
		String S = new String();
		for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < M[0].length; j++) {
				S += String.valueOf(M[i][j]) + " ";
			}
			S += "\n";
		}
		return S;
	}

	// Function that counts for n and k all the polyominoes of size n that can cover their own dilate by k, allowing rotations and symmetries.
	public static void countDilateFixed(int n, int k) {
		HashSet<Polyomino> polyominoes = Polyomino.dilateCoverFixed(n, k);
		System.out.print("There are " + polyominoes.size() + " fixed polyominoes of size " + n + " that cover their " + k + "-dilation");
	}

	// Function that counts for n and k all the polyominoes of size n that can cover their own dilate by k, without rotations and symmetries
	public static void countDilateFree(int n, int k) {
		HashSet<Polyomino> polyominoes = Polyomino.dilateCoverFree(n, k);
		System.out.print("There are " + polyominoes.size() + " free polyominoes of size " + n + " that cover their " + k + "-dilation");
	}

	// Function that determines for n, and k all the polyominoes of size n that can cover their own k-dilation, and represents them
	public static void dilateRepresentFixed(int n, int k) {
		Image2dViewer frame = new Image2dViewer();
		HashSet<Polyomino> polyominoes = Polyomino.dilateCoverFixed(n, k);

		Point center = new Point();

		frame.addPolyominoes(polyominoes, center);
	}

	// Function that determines for n, and k all the polyominoes of size n that can cover their own k-dilation, and represents them
	public static void dilateRepresentFree(int n, int k) {
		Image2dViewer frame = new Image2dViewer();
		HashSet<Polyomino> polyominoes = Polyomino.dilateCoverFree(n, k);
		frame.addPolyominoes(polyominoes, new Point(0,0));
	}	
}
