import java.awt.Color;
import java.util.HashSet;




// Here we defined some test funtions, which can be used to make tests.

public abstract class Test {

	// Returns the number of free polyominos of size n (using the naive algorithm), with the runtime.

	static void numberOfFreePolyominosNaive(int n) {
		long startTime = System.nanoTime();
		HashSet<Polyomino> polyominos = Polyomino.naiveGenerateFree(n);
		long endTime = System.nanoTime();
		System.out.println("Using Naive : There are " + polyominos.size() + " free polyominoes of size " + n + ".");
		System.out.println("Took "+ (endTime - startTime) + " ns");
		System.out.println("Approximately "+ (endTime - startTime)/1000000000 + " s");
	}
	
	
	
	
	
	// Returns the number of fixed polyominos of size n (using the naive algorithm), with the runtime
	
	static void numberOfFixedPolyominosNaive(int n) {
		long startTime = System.nanoTime();
		HashSet<Polyomino> polyominos = Polyomino.naiveGenerateFixed(n);
		System.out.println("Using Naive : There are " + polyominos.size() + " fixed polyominoes of size " + n + ".");
		long endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");
		System.out.println("Approximately "+ (endTime - startTime)/1000000000 + " s");
	}
	
	
	
	
	
	
	// A useless function I used to do some debugging
	
	static void neighborsof2 () {
		Image2d img = new Image2d(20,20);
		HashSet<Polyomino> Start = Polyomino.naiveGenerateFixed(1);

		if(!Start.iterator().hasNext()) {
			return;
		}
		Polyomino p = Start.iterator().next();
		HashSet<Polyomino> End = p.neighbors();

		for (Polyomino e : End) {
			Polyomino D = e.dilateBy(50);
			End.remove(e);
			End.add(D); 
			//polyominos.add(i,Polyomino.symmetry(polyominos.get(i)));
		}
		int k = 20;
		for (Polyomino e : End) {
			int[][][] polyo = Polyomino.creator(e, k, 0);
			k = k + 20 + e.width;
			for (int j = 0; j < e.n; j++) {
			    img.addPolygon(polyo[j][0], polyo[j][1], new Color(250,0,0));
			}
		}
		new Image2dViewer(img);
	}
	
	
	
	
	// This function prints what is contained in the file 'INF421'
	
	static void inf421printer() {
		Image2d img = new Image2d(600,300);
		Polyomino[] polyominos = Polyomino.fileReader("assets/Figure2.txt");
		int k = 2;

		for (int i = 0; i < polyominos.length; i++) {
			int[][][] polyo = Polyomino.creator(polyominos[i], k, 3);
			k = k + 2 + 2*polyominos[i].width;
			for (int j = 0; j < polyominos[i].n; j++) {
				//Color color = new Color((int)(Math.random() * ((254 - 0) + 1)),(int)(Math.random() * ((254 - 0) + 1)),(int)(Math.random() * ((254 - 0) + 1)));
			    //img.addPolygon(polyo[j][0], polyo[j][1], color);
			    img.addPolygon(polyo[j][0], polyo[j][1], new Color(250,0,0));
			}
		}
		new Image2dViewer(img);
	}
	
	
	
	
	
	// This function uses the naive algorithm to represent all fixed polyominos of size n for a given n
	
	static void fixedGenerator(int n) {
		Image2d img = new Image2d(200,220);
		HashSet<Polyomino> polyominos = Polyomino.naiveGenerateFixed(n);
		HashSet<Polyomino> dilated_polyominoes = new HashSet<Polyomino>();
		for (Polyomino P : polyominos) {
			Polyomino D = P.dilateBy(60);  //put 5
			dilated_polyominoes.add(D);
			//polyominos.add(i,Polyomino.symmetry(polyominos.get(i)));
		}
		int k = 20;
		int l = 0;
		int[] l1 = new int[50];
		int i = 0;

		for (Polyomino P : dilated_polyominoes) {
			l1[i%50] = P.height;
			if (i % 50 == 0) {
				l = Polyomino.max(l1) + l + 100;
				k = 20;
			}
			
			int[][][] polyo = Polyomino.creator(P, k, l);
			//int[][][] edges = Polyomino.edgeGenerator(polyominos.get(i), k, l);
			k = k + 20 + P.width;
			for (int j = 0; j < P.n; j++) {
			    img.addPolygon(polyo[j][0], polyo[j][1], new Color(250,0,0));
			    /*for (int r = 0; r < 4; r++) {
			    	int x1 = edges[j][r][0];
			    	int x2 = edges[j][r][1];
			    	int y1 = edges[j][r][2];
			    	int y2 = edges[j][r][3];
			    	img.addEdge(x1,x2,y1,y2,1);
					}	*/
			}
			i++;
		}
		new Image2dViewer(img);	
	}
	
	// This function prints the free polyominos using the naive algorithm
	static void freeGenerator(int n) {
		Image2d img = new Image2d(1000,200);
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFree(n);
		HashSet<Polyomino> dilated_polyominoes = new HashSet<Polyomino>();
		int k = 20;
		int l = 0;
		int[] l1 = new int[40];
		int i = 0;

		for (Polyomino P : polyominoes) {
			l1[i%40] = P.height;
			if (i % 40 == 0) {
				l = Polyomino.max(l1) + l + 100;
				k = 20;
			}
			int[][][] polyo = Polyomino.creator(P, k, l);
			k = k + 20 + P.width;
			for (int j = 0; j < P.n; j++) {
			    img.addPolygon(polyo[j][0], polyo[j][1], new Color(250,0,0));
			}
		}
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

		System.out.println(toString(M));


		// Naif solution:

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
		// System.out.print(toString(M));

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

		System.out.println(toString(M));


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

//		System.out.print(toString(M));

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
	
	
	
	
	
	// A test that takes 3 different polyominos and prints their tilings using different sets of pieces
	
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
		System.out.println(toString(P.order()));
		int[][] M = P.toMatrix(Polyomino.naiveGenerateFixed(2));

		Polyomino Q = new Polyomino(B);
		System.out.println(toString(Q.order()));
		int[][] N = Q.toMatrix(Polyomino.naiveGenerateFixed(2));

		Polyomino R = new Polyomino(C);
		System.out.println(toString(R.order()));
		int[][] O = R.toMatrix(Polyomino.naiveGenerateFixed(3));

		System.out.println(toString(M) + "\n");

		System.out.println(toString(N) + "\n");

		System.out.println(toString(O) + "\n");

		O = R.toUniqueMatrix(Polyomino.naiveGenerateFixed(3)); 
	
		System.out.println(toString(O) + "\n");

		System.out.println(R);
		
		System.out.println("Until now we only verified our entries, we now execute the covering algorithm tests and their execution time:\n");

		
		
		System.out.println(toString(P.toUniqueMatrix(Polyomino.naiveGenerateFixed(2))));
		
		long startTime = System.nanoTime();
		HashSet<HashSet<boolean[][]>> Sols = P.GridDLSolutionUnique(Polyomino.naiveGenerateFixed(2));
		//LinkedList<LinkedList<boolean[][]>> Sols = Q.GridECSolution(Polyomino.naiveGenerateFixed(3));
		long endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");


		startTime = System.nanoTime();
	
		for (HashSet<boolean[][]> solution : Sols) {
			Polyomino.PrintTiling(3, 4, solution);
		}
		endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime) + " ns");


		for (HashSet<boolean[][]> is : Sols) {
			for (boolean[][] is2 : is) {
				System.out.println(toString(is2));
			}
			System.out.println("-------------------------------------");
		}
	}
	

	// toString function to visualize a matrix

	public static String toString(int[][] M) {
		String S = new String();
		for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < M[0].length; j++) {
				S += String.valueOf(M[i][j]) + " ";
			}
			S += "\n";
		}
		return S;
	}

    // toString function to visualize a matrix

	public static String toString(boolean[][] M) {
		String S = new String();
		for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < M[0].length; j++) {
				S += String.valueOf(M[i][j]) + " ";
			}
			S += "\n";
		}
		return S;
	 }


	
	
	
	// Function that counts for n and k all the polyominos of size n that can cover their own dilate by k, allowing rotations and symmetries.
		
	public static void countDilateFixed(int n, int k) {
		HashSet<Polyomino> polyominos = Polyomino.dilateCoverFixed(n, k);
		System.out.print("There are " + polyominos.size() + " fixed polyominos of size " + n + " that cover their " + k + "-dilation");
	}
	
	
	
	
	
	
	// Function that counts for n and k all the polyominos of size n that can cover their own dilate by k, without rotations and symmetries
	
	public static void countDilateFree(int n, int k) {
		HashSet<Polyomino> polyominos = Polyomino.dilateCoverFree(n, k);
		System.out.print("There are " + polyominos.size() + " free polyominos of size " + n + " that cover their " + k + "-dilation");
	}
		
	
	
	
	
		
	// Function that determines for n, and k all the polyominos of size n that can cover their own k-dilation, and represents them
		
	public static void dilateRepresentFixed(int n, int k) {
		Image2d img = new Image2d(30,30);
		HashSet<Polyomino> polyominos = Polyomino.dilateCoverFixed(n, k);
		for (Polyomino P : polyominos) {
			Polyomino D = P.dilateBy(10);
			polyominos.remove(P);
			polyominos.add(D);
			//polyominos.add(i,Polyomino.symmetry(polyominos.get(i)));
		}
		int z = 20;
		int l = 0;
		int[] l1 = new int[40];
		int i = 0;

		for (Polyomino P : polyominos) {
			l1[i%40] = P.height;
			if (i % 40 == 0) {
				l = Polyomino.max(l1) + l + 100;
				z = 20;
			}
			int[][][] polyo = Polyomino.creator(P, z, l);
			z= z + 20 + P.width;
			for (int j = 0; j < P.n; j++) {
			    img.addPolygon(polyo[j][0], polyo[j][1], new Color(250,0,0));
			}
			i++;
		}
		new Image2dViewer(img);	
	}
	
	 
	
	
	
	
	// Function that determines for n, and k all the polyominos of size n that can cover their own k-dilation, and represents them
	
	public static void dilateRepresentFree(int n, int k) {
		Image2d img = new Image2d(30,30);
		HashSet<Polyomino> polyominos = Polyomino.dilateCoverFree(n, k);

		for (Polyomino P : polyominos) {
			Polyomino D = P.dilateBy(10);
			polyominos.remove(P);
			polyominos.add(D);
			//polyominos.add(i,Polyomino.symmetry(polyominos.get(i)));
		}
		int z = 20;
		int l = 0;
		int[] l1 = new int[40];
		int i = 0;

		for (Polyomino P : polyominos) {
			l1[i%40] = P.height;
			if (i % 40 == 0) {
				l = Polyomino.max(l1) + l + 100;
				z = 20;
			}
			int[][][] polyo = Polyomino.creator(P, z, l);
			z = z + 20 + P.width;
			for (int j = 0; j < P.n; j++) {
			    img.addPolygon(polyo[j][0], polyo[j][1], new Color(250,0,0));
			    /*img.addEdge(polyo[j][0][0], polyo[j][1][0], polyo[j][0][1], polyo[j][1][1], 1);
			    img.addEdge(polyo[j][0][2], polyo[j][1][1], polyo[j][0][2], polyo[j][1][2], 1);
			    img.addEdge(polyo[j][0][3], polyo[j][1][2], polyo[j][0][3], polyo[j][1][3], 1);
			    img.addEdge(polyo[j][0][0], polyo[j][1][3], polyo[j][0][0], polyo[j][1][0], 1);*/
			}
			i++;
		}
		new Image2dViewer(img);	
	}	
		
		
		
		
		
}
