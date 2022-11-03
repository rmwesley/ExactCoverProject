import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
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
		Image2d img = new Image2d(150, 150);
		int[] x = new int[3],y = new int[3];
		y[1]=-10;
		x[2]=10;
		img.addColoredPolygon(new Polygon(x,y,3), new Color(0,0,0));
		new Image2dViewer(img);
	}
	// This function prints what is contained in the file 'INF421'
	static void inf421printer() {
		Image2d img = new Image2d(800, 400);
		Polyomino[] polyominoes = Polyomino.fileReader("assets/Figure2.txt");
		Point center = new Point(0,0);
		img.addPolyominoes(polyominoes, center, true);
		new Image2dViewer(img);
	}

	// This function uses the naive algorithm to represent all fixed polyominoes of size n for a given n
	static void fixedGenerator(int n) {
		Image2d img = new Image2d(1600,200);
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFixed(n);
		Point center = new Point(2,3);
		img.addPolyominoes(polyominoes, center, true);
		new Image2dViewer(img);	
	}

	// This function prints the free polyominoes using the naive algorithm
	static void freeGenerator(int n) {
		Image2d img = new Image2d(1600,200);
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFree(n);
		Point center = new Point(2,3);
		img.addPolyominoes(polyominoes, center, true);
		new Image2dViewer(img);	
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

		System.out.println(visualizeMatrix(M));

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

		System.out.println(visualizeMatrix(M));


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

	// A test that takes 3 different polyominoes and prints their tilings using different sets of pieces
	public static void testTiling() {
		boolean[][] A = new boolean[3][2];
		A[0] = new boolean[] {true, false};
		A[1] = new boolean[] {true, false};
		A[2] = new boolean[] {true, true};

		boolean[][] B = new boolean[4][3];
		B[0] = new boolean[] {true, false, false};
		B[1] = new boolean[] {true, false, false};
		B[2] = new boolean[] {true, false, true};
		B[3] = new boolean[] {false, true, true};

		boolean[][] C = new boolean[4][3];
		C[0] = new boolean[] {true, false, false};
		C[1] = new boolean[] {true, true, true};
		C[2] = new boolean[] {true, true, true};
		C[3] = new boolean[] {false, true, true};

		Polyomino P = new Polyomino(A);
		System.out.println(visualizeMatrix(P.order()));
		int[][] M = P.toMatrix(Polyomino.naiveGenerateFixed(2));

		Polyomino Q = new Polyomino(B);
		System.out.println(visualizeMatrix(Q.order()));
		int[][] N = Q.toMatrix(Polyomino.naiveGenerateFixed(2));

		Polyomino R = new Polyomino(C);
		System.out.println(visualizeMatrix(R.order()));
		int[][] O = R.toMatrix(Polyomino.naiveGenerateFixed(3));

		System.out.println(visualizeMatrix(M) + "\n");

		System.out.println(visualizeMatrix(N) + "\n");

		System.out.println(visualizeMatrix(O) + "\n");

		O = R.toUniqueMatrix(Polyomino.naiveGenerateFixed(3)); 

		System.out.println(visualizeMatrix(O) + "\n");

		System.out.println(R);

		System.out.println("Until now we only verified our entries, we now execute the covering algorithm tests and their execution time:\n");



		System.out.println(visualizeMatrix(P.toUniqueMatrix(Polyomino.naiveGenerateFixed(2))));

		long startTime = System.nanoTime();
		HashSet<HashSet<boolean[][]>> Sols = P.GridDLSolutionUnique(Polyomino.naiveGenerateFixed(2));
		long endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");

		startTime = System.nanoTime();

		for (HashSet<boolean[][]> solution : Sols) {
			Point center = new Point();
			for(boolean[][] grid : solution){
				Polyomino p = new Polyomino(grid);
				img.addPolyomino(p, center);
				center.translate(2+p.width, 0);
			}
		}
		new Image2dViewer(img);
		endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");

		for (HashSet<boolean[][]> is : Sols) {
			for (boolean[][] is2 : is) {
				System.out.println(visualizeMatrix(is2));
			}
			System.out.println("-------------------------------------");
		}
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
		Image2d img = new Image2d(30,30);
		HashSet<Polyomino> polyominoes = Polyomino.dilateCoverFixed(n, k);
		for (Polyomino P : polyominoes) {
			Polyomino D = P.dilateBy(10);
			polyominoes.remove(P);
			polyominoes.add(D);
			//polyominoes.add(i,Polyomino.symmetry(polyominoes.get(i)));
		}
		Point center = new Point(20,0);
		int[] l1 = new int[40];
		int idx=0;

		for (Polyomino P : polyominoes) {
			l1[idx%40] = P.height;
			if (idx % 40 == 0) {
				center.translate(0, Polyomino.max(l1) + 100);
			}
			HashSet<LinkedList<Point>> vetices = P.getVertices(center);
			for (int j = 0; j < P.n; j++) {
				img.addPolyominoes(polyominoes, new Point(0,0), true);
			}
		}
		new Image2dViewer(img);	
	}

	// Function that determines for n, and k all the polyominoes of size n that can cover their own k-dilation, and represents them
	public static void dilateRepresentFree(int n, int k) {
		Image2d img = new Image2d(30,30);
		HashSet<Polyomino> polyominoes = Polyomino.dilateCoverFree(n, k);

		for (Polyomino P : polyominoes) {
			Polyomino D = P.dilateBy(10);
			polyominoes.remove(P);
			polyominoes.add(D);
			//polyominoes.add(i,Polyomino.symmetry(polyominoes.get(i)));
		}
		int z = 20;
		int l = 0;
		int[] l1 = new int[40];
		int i = 0;

		for (Polyomino P : polyominoes) {
			l1[i%40] = P.height;
			if (i % 40 == 0) {
				l = Polyomino.max(l1) + l + 100;
				z = 20;
			}
			Point center = new Point(z,l);
			HashSet<LinkedList<Point>> vetices = P.getVertices(center);
			for (int j = 0; j < P.n; j++) {
				img.addPolyominoes(polyominoes, new Point(0,0), true);
			}
			i++;
		}
		new Image2dViewer(img);	
	}	
}
