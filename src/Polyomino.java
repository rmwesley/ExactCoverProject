import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Arrays;
import java.util.LinkedList;

//The class Polyomino, the heart of the subject
public class Polyomino {

	public boolean[][] grid; // Grid representing tiles of Polyomino
	public int n; // Number of tiles in the polyomino
	public int width, height; // Rectangular dimensions of the polyomino

	// Counts the number of squares of a Polyomino
	public int countSquares() {
		int counter = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j]) {
					counter++;
				}
			} 
		}
		return counter;
	}

	// Constructs a polyomino from a matrix of booleans
	public Polyomino(boolean[][] grid) {
		this.grid = grid;
		this.n = this.countSquares();
		this.width = this.grid.length;
		this.height = this.grid[0].length;
	}

	// Determines the coordinates of the vertices of the tiles composing the polyomino, relative to a given origin
	public HashSet<LinkedList<Point>> getVertices(Point origin) {

		// We define vertices, which will contain the coordinates of the vertices of the polygons composing the polyomino.
		HashSet<LinkedList<Point>> vertices = new HashSet<LinkedList<Point>>();
		LinkedList<Point> currTile = new LinkedList<Point>();
		Point vertex = new Point();

		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				// For square tiles
				if (this.grid[i][j]) {
					vertex = new Point(2*i+origin.x, 2*j+origin.y);
					//currTile.add(vertex);
					currTile.add((Point) vertex.clone());
					vertex.translate(0, 1);
					currTile.add((Point) vertex.clone());
					vertex.translate(1, 0);
					currTile.add((Point) vertex.clone());
					vertex.translate(0, -1);
					currTile.add((Point) vertex.clone());
					vertices.add(currTile);
					currTile = new LinkedList<Point>();
				}
			}
		}
		return vertices;
	}

	// Dilates a polyomino by a factor n. Extremely useful to draw polyominoes (because the Image.Scale method tends to deform them)
	public Polyomino dilateBy(int n) {
		boolean[][] newGrid = new boolean[n * this.width][n * this.height];
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				for (int k = 0; k < n; k++) {
					for (int l = 0; l < n; l++) {
						newGrid[n * i + k][n * j + l] = this.grid[i][j];
					}
				}
			}
		}
		return new Polyomino(newGrid);
	}

	// Returns the biggest element of an array
	public static int max(int[] x) {
		int maxVal = x[0];
		for(int i = 0; i < x.length; i++){
			if(x[i] > maxVal) {
				maxVal = x[i];
			}
		}
		return maxVal;
	}

	// Constructs a polyomino from a string of coordinates
	public Polyomino(String s) {

		HashSet<Integer[]> squares = new HashSet<Integer[]>();
		int i = 1; 

		// we get through the string, we begin just after '{'
		while (i < s.length() - 1) {

			i++;
			int x = 0, y = 0;
			while (s.charAt(i) != ',') {
				// while we've not reached ',', we add the character to x
				x = x * 10 + Integer.parseInt(Character.toString(s.charAt(i)));
				i++;
			}
			i++; 
			//when we've reached ',' we get in another loop
			while (s.charAt(i) != ')') {
				// while we've not reached ')', we add the character to y
				y = y * 10 + Integer.parseInt(Character.toString(s.charAt(i)));
				i++;
			}
			// we add the coordinates to our list of coordinates
			Integer[] point = { x, y };
			squares.add(point);
			i++;
			if (s.charAt(i) == ',')
				i = i + 2;
			else
				break;
		}
		// we convert our list of coordinates into a boolean grid
		int xmax = 0, ymax = 0;
		for (Integer[] tile : squares) {
			xmax = Math.max(xmax, tile[0]);
			ymax = Math.max(ymax, tile[1]);
		}
		boolean[][] tilesArray = new boolean[xmax + 1][ymax + 1];
		for (Integer[] tile : squares) {
			tilesArray[tile[0]][tile[1]] = true;
		}
		this.grid = tilesArray;
		this.n = this.countSquares();
		this.width = this.grid.length;
		this.height = this.grid[0].length;
	}

	// Here we define a method that returns an array of polyominoes from a file
	public static Polyomino[] fileReader(String filename) {
		LinkedList<Polyomino> polyominoList = new LinkedList<Polyomino>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				Polyomino polyo = new Polyomino(line);
				//polyominoList.add(polyo.symmetrical());
				polyominoList.add(polyo);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return polyominoList.toArray(new Polyomino[0]);
	}

	// Here we define a method that rotates a Polyomino, with respect to the origin, with n quarter turns in the trigonometric way. Here the origin
	// is the origin of the grid representing the polyomino. 
	public static Polyomino rotation(int n, Polyomino polyomino) {
		boolean[][] grid = polyomino.grid;
		int width = grid.length;
		int height = grid[0].length;
		if (n % 4 == 1) {
			boolean[][] newGrid = new boolean[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					newGrid[i][j] = grid[j][height - 1 - i];    // Here is a quarter-turn rotation
				}
			}
			return new Polyomino(newGrid);
		} 
		else if (n % 4 == 2) {
			boolean[][] newGrid = new boolean[width][height];
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					newGrid[i][j] = grid[width - 1 - i][height - 1 - j];   // Here, n % 4 == 2 so a half-turn rotation
				}
			}
			return new Polyomino(newGrid);
		} 
		else if (n % 4 == 3) {
			boolean[][] newGrid = new boolean[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					newGrid[i][j] = grid[width - 1 - j][i]; // Here, a three quarter-turn rotation
				}
			}
			return new Polyomino(newGrid);
		}
		return polyomino;
	}

	// Returns the symmetric polyomino of this with respect to the Y axis (ie the left side of the grid)
	// Used for the exact cover problem
	public Polyomino translate( int x, int y) {
		int width, height;
		width = this.width;
		height = this.height;
		boolean[][] newGrid = new boolean[width+x][height+y];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i >= x && j >= y) {
					newGrid[i][j] = this.grid[i - x][j - y];
				} else {
					newGrid[i][j] = false;
				}
			}
		}
		return new Polyomino(newGrid);
	}

	// A method that adapts a Polyomino P to this
	public Polyomino adaptTo(Polyomino P) {
		int width, height;
		width = this.width;
		height = this.height;
		boolean[][] newGrid = new boolean[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i <= P.width && j <= P.height) {
					newGrid[i][j] = P.grid[i][j];
				} else {
					newGrid[i][j] = false;
				}
			}
		}
		return new Polyomino(newGrid);
	}

	// Returns the symmetric polyomino of this with respect to the Y axis (ie the left side of the grid)
	public Polyomino symmetrical() {
		boolean[][] grid = this.grid;
		int width, height;
		width = grid.length;
		height = grid[0].length;
		boolean[][] newGrid = new boolean[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				newGrid[i][j] = grid[i][height - 1 - j];
			}
		}
		return new Polyomino(newGrid);
	}

	// A function that determines whether or not two Polyominoes are equal
	public boolean Equals(Polyomino P) {
		if (this.height != P.height || this.width != P.width || this.n != P.n) {
			return false;
		}
		for (int i = 0; i < this.width;i++) {
			for (int j = 0; j < this.height;j++) {
				if (this.grid[i][j] != P.grid[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	// Returns all fixed Polyominoes associated to a given Polyomino (ie all the equivalence class)
	public HashSet<Polyomino> returnFixed() {
		HashSet<Polyomino> polyominoes = new HashSet<Polyomino>();
		Polyomino P = rotation(0, this);

		if (!polyominoes.contains(P)) {
			polyominoes.add(P);
		}
		P = rotation(1, this);
		if (!polyominoes.contains(P)) {
			polyominoes.add(P);
		}
		P = rotation(2, this);
		if (!polyominoes.contains(P))  {
			polyominoes.add(P);
		}
		P = rotation(3, this);
		if (!polyominoes.contains(P))  {
			polyominoes.add(P);
		}

		Polyomino Q = this.symmetrical();
		P = this.symmetrical();
		if (!polyominoes.contains(P))  {
			polyominoes.add(P);
		}
		P = rotation(1, Q);
		if (!polyominoes.contains(P))  {
			polyominoes.add(P);
		}
		P = rotation(2, Q);
		if (!polyominoes.contains(P))  {
			polyominoes.add(P);
		}
		P = rotation(3, Q);
		if (!polyominoes.contains(P))  {
			polyominoes.add(P);
		}
		return polyominoes;
	}

	// Tells us whether a given square is contained inside the Polyomino
	public boolean containsSquare(int[] square) {
		if (square[0] >= this.width || square[1] >= this.height || square[0] < 0 || square[1] < 0) {
			return false;
		}
		else {
			return this.grid[square[0]][square[1]];
		}
	}

	// An auxiliary function that adds a square to a polyomino, and is key to implement an algorithm that generates polyominoes
	public Polyomino addSquare(int[] square) {
		if (this.containsSquare(square)) {
			return this;
		}		
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		if (square[0] < 0) { 
			a = 1;
		}
		if (square[0] >= this.width) {
			b = 1;
		}
		if (square[1] < 0) {
			c = 1;
		}
		if (square[1] >= this.height) {
			d = 1;
		}
		boolean[][] newGrid = new boolean[this.width + a + b][this.height + c + d];
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				newGrid[a + i][c + j] = this.grid[i][j];
			}
		}
		newGrid[square[0] + a][square[1] + c] = true;
		return new Polyomino(newGrid);
	}

	// An auxiliary function that returns a list containing all the polyominoes with one new square adjacent to them
	public HashSet<Polyomino> neighbors() {
		HashSet<Polyomino> polyominoes = new HashSet<Polyomino>();
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (this.grid[i][j]) {
					int[][] squares = {{i+1,j},{i-1,j},{i,j+1},{i,j-1}};
					for (int k = 0; k < 4; k++)
					{
						int[] square = squares[k];
						if(!this.containsSquare(square)) {
							polyominoes.add(this.addSquare(square));
						}
					}
				}
			}
		}
		return polyominoes;
	}

	// A naive algorithm that generates all fixed Polyominoes
	public static HashSet<Polyomino> naiveGenerateFixed(int n) {
		if (n == 1) {
			HashSet<Polyomino> polyominoes = new HashSet<Polyomino>();
			polyominoes.add(new Polyomino("[(0,0)]")); 
			return polyominoes;
		} 
		else {
			HashSet<Polyomino> polyominoes1 = naiveGenerateFixed(n - 1);
			HashSet<Polyomino> polyominoes = new HashSet<Polyomino>();
			for (Polyomino P : polyominoes1) {
				for (Polyomino P2 : P.neighbors()) {
					polyominoes.add(P2);
				}
			}
			return polyominoes;
		}
	}

	// A method useful for the next algorithm
	public static boolean intersectionEmpty(HashSet<Polyomino> polyominoes1, HashSet<Polyomino> polyominoes2) {
		for (Polyomino P1 : polyominoes1) {
			if (polyominoes2.contains(P1)) {
				return false;
			}
		}
		return true;
	}

	// A naive algorithm that generates all free polyominoes
	public static HashSet<Polyomino> naiveGenerateFree(int n) {
		if (n == 1) {
			HashSet<Polyomino> polys = new HashSet<Polyomino>();
			polys.add(new Polyomino("[(0,0)]"));
			return polys;
		} 
		else {
			HashSet<Polyomino> polys1 = naiveGenerateFree(n - 1);
			HashSet<Polyomino> polys = new HashSet<Polyomino>();
			for (Polyomino P : polys1) {
				for (Polyomino P2 : P.neighbors()) {
					if ((!polys.contains(P2)) && intersectionEmpty(polys, P2.returnFixed())) {
						polys.add(P2);
					}
				}
			}
			return polys;
		}
	}

	// Other methods designed to solve the exact cover problem
	public int[][] order() {
		int[][] M = new int[grid.length][grid[0].length];
		int c = 0;

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if(grid[i][j]) {
					c++;
					M[i][j] = c;
				}
			}
		}
		return M;
	}

	// Considering this and numbered matrix, returns which number positions a polyomino P covers when it is placed at the position (x, y) of this
	public int[] intersectionsWith(Polyomino P, int x, int y) {
		int[][] M = this.order();
		int[] v = new int[this.n];
		int c = 0;

		if(P.width + y > this.width || P.height + x > this.height) {
			return null;
		}

		for (int i = 0; i < P.grid.length; i++) {
			for (int j = 0; j < P.grid[0].length; j++) {
				if(P.grid[i][j] && grid[i + y][j + x]) {
					c++;
					v[M[i + y][j + x] - 1] = 1;
				}
			}
		}
		if(c == P.n) {
			return v;
		}
		return null;
	}

	// Appends two vectors
	private static int[] append(int[] x, int[] y) {
		if(x == null || y == null) {
			return x;
		}
		int[] z = new int[x.length + y.length];
		if(y.length == 0) {
			return x;
		}
		if(x.length == 0) {
			return y;
		}
		for (int i = 0; i < x.length; i++) {
			z[i] = x[i];
		}
		for (int i = 0; i < y.length; i++) {
			z[i + x.length] = y[i];
		}
		return z;
	}

	// Appends a new row at the end of a matrix
	private static int[][] append(int[][] M, int[] v) {
		if(M == null || v == null) {
			return M;
		}
		int[][] N = new int[M.length + 1][v.length];
		if(v.length == 0) {
			return M;
		}
		if(M.length == 0) {
			N[0] = v;
			return N;
		}
		if(M[0].length != v.length) {
			return null;
		}
		for (int i = 0; i < M.length; i++) {
			N[i] = M[i];
		}
		N[M.length] = v;
		return N;
	}

	// Returns the exact cover problem of this and a LinkedList of polyominoes L, without repetitions of elements of L
	public int[][] toUniqueMatrix(HashSet<Polyomino> L) {
		// Adds L.size() entries in the problem matrix's columns to identify each piece, which therefore can't be covered (used) more than once
		// We keep in mind that we should also deal with the case of not using a certain piece further in the code, as an piece could simply not be used, which isn't immediately considered
		int l = L.size();
		int[][] M = new int[0][this.n + l];
		int[] u = new int[l];
		int c = 0;
		int k = 0;

		for (Polyomino p : L) {
			u[c]++;
			k = 0;
			// Searching equivalent pieces to prohibit repetitions and storing them in u
			// If the user doesns't want the problem matrix to contain numbers different from 0 or 1, just substitute the if statement for "p.isEquivalentTo(q) && u[k] != 0"
			// For the sake of performance, I didn't bother with this detail, as the algorithm only accounts for entries equal or different to 0
			for (Polyomino q : L) {
				if(p.isEquivalentTo(q)) {
					u[k]++;
				}
				k++;
			}
			for (int x = 0; x < height; x++) {
				for (int y = 0; y < width; y++) {
					M = append(M, append(this.intersectionsWith(p, x, y), u));
				}
			}

			k = 0;
			for (Polyomino q : L) {
				if(p.isEquivalentTo(q)) {
					u[k]++;
				}
				k++;
			}
			// Allowing a piece to not be used (Creating a copy with only zeros in the first n entries of the vector, those which represent the spaces of a Polyomino to be covered, thus creating a pseudo-piece, that doesn't cover anything)
			// A piece necessarily needs to be used to cover the new "piece columns" that were created, which seems contradictory, but they are empty in their first n entries, so they don't cover anything of the Polyomino columns
			M = append(M, append(new int[this.n], u));
			u[c]--;
			c++;
		}
		return M;
	}

	// Returns the first non-zero entry of an int[] array (Useful to return the real position of a polyomino in a list, excluding equivalences)
	public static int FirstEntry(int[] v) {
		for (int i = 0; i < v.length; i++) {
			if(v[i] != 0) {
				return i;
			}
		}
		return v.length;
	}

	// Verifies if two Polyominoes are equivalent
	public boolean isEquivalentTo(Polyomino P) {
		return this.returnFixed().contains(P);
	}

	// Returns the exact cover problem of this and a HashSet of polyominoes L, allowing repetitions of elements of L
	public int[][] toMatrix(HashSet<Polyomino> L) {
		int[][] M = new int[0][this.n];
		//LinkedList<Polyomino> L = naiveGenerateFixed(k);

		for (Polyomino p : L) {
			for (int x = 0; x < height; x++) {
				for (int y = 0; y < width; y++) {
					M = append(M, this.intersectionsWith(p, x, y));
				}
			}
		}
		return M;
	}

	// toString method to visualize "this"
	@Override
	public String toString() {
		String S = new String();

		S += "\n";
		for (boolean[] row : grid) {
			for (boolean b : row) {
				S += " " + String.valueOf(b);
			}
			S += "\n";
		}
		return S;
	}

	// Returns a HashSet consisting of all solutions to the exact cover problem, each solution represented as a LinkedList of the grid of the polyominoes used in the solution
	// This function Tiles a Polyomino using a linkedlist of polyominoes with repetitions 
	public HashSet<HashSet<boolean[][]>> GridDLSolution(HashSet<Polyomino> L){
		int[][] N = this.order();
		int[][] M = this.toMatrix(L);
		DancingLinks DL = new DancingLinks(M);
		HashSet<HashSet<Node>> NodeList = DL.exactCoverNode();
		HashSet<HashSet<boolean[][]>> Solution = new HashSet<HashSet<boolean[][]>>();

		// Returning the representation of each Node (Row of the problem matrix) as a boolean matrix, which can be used to visualize each solution with different functions (toString or PrintTiling)
		for (HashSet<Node> solution : NodeList) {
			HashSet<boolean[][]> Set = new HashSet<boolean[][]>();
			for (Node x : solution) {
				boolean[][] S = PositiontoCoordinates(N, reduce(M[x.locator], this.n));
				if(S.length == 0) {
					continue;
				}
				Set.add(S);
			}
			Solution.add(Set);
		}

		return Solution;
	}

	// Returns a LinkedList consisting of all solutions to the exact cover problem, each solution represented as a LinkedList of the grid of the polyominoes used in the solution (without dancing links)
	// This function Tiles a Polyomino using a linkedlist of polyominoes with repetitions (without dancing links)
	public HashSet<HashSet<boolean[][]>> GridECSolution(HashSet<Polyomino> L){
		int[][] N = this.order();
		int[][] M = this.toMatrix(L);
		HashSet<HashSet<Integer>> List = ExactCover.cover(new int[M[0].length], M);
		HashSet<HashSet<boolean[][]>> Solution = new HashSet<HashSet<boolean[][]>>();

		for (HashSet<Integer> solution : List) {
			HashSet<boolean[][]> Set = new HashSet<boolean[][]>();
			for (Integer x : solution) {
				boolean[][] S = PositiontoCoordinates(N, reduce(M[x], this.n));
				if(S.length == 0) {
					continue;
				}
				Set.add(S);
			}
			Solution.add(Set);
		}

		return Solution;
	}

	// Returns a LinkedList consisting of all solutions to the exact cover problem, each solution represented as a LinkedList of the grid of the polyominoes used in the solution, without repetitions allowed in each solution
	// This function Tiles a Polyomino using a linkedlist of polyominoes without repetitions
	public HashSet<HashSet<boolean[][]>> GridDLSolutionUnique(HashSet<Polyomino> L){
		int[][] N = this.order();
		int[][] M = this.toUniqueMatrix(L);
		DancingLinks DL = new DancingLinks(M);
		HashSet<HashSet<Node>> NodeList = DL.exactCoverNode();
		HashSet<HashSet<boolean[][]>> Solution = new HashSet<HashSet<boolean[][]>>();

		for (HashSet<Node> solution : NodeList) {
			HashSet<boolean[][]> Set = new HashSet<boolean[][]>();
			for (Node x : solution) {
				boolean[][] S = PositiontoCoordinates(N, reduce(M[x.locator], this.n));
				if(S.length == 0) {
					continue;
				}
				Set.add(S);
			}
			Solution.add(Set);
		}

		return Solution;
	}

	// Reduces a vector size, erasing its last elements (Used to erase the added part a row of an exact cover problem without repetitions, leaving only the polyomino part)
	public static int[] reduce(int[] original, int size) {
		int[] row = new int[size];
		for (int i = 0; i < size; i++) {
			row[i] = original[i];
		}
		return row;
	}

	// Verifies if an int[] array has only zero entries
	public static boolean isZeros(int[] v) {
		for (int i = 0; i < v.length; i++) {
			if(v[i] != 0) {
				return false;
			}
		}
		return true;
	}

	// Given a numbered matrix of a Polyomino and a set of position numbers, returns a boolean matrix grid of a polyomino that covers these positions
	public static boolean[][] PositiontoCoordinates(int[][] order, int[] positions) {
		boolean[][] B = new boolean[order.length][order[0].length];
		int[] v = positions;

		if(isZeros(v)) {
			return new boolean[0][0];
		}

		for (int c = 0; c < v.length; c++) {
			if(v[c] == 1) {
				for (int i = 0; i < order.length; i++) {
					for (int j = 0; j < order[0].length; j++) {
						if(c + 1 == order[i][j]) {
							B[i][j] = true;
							break;
						}
					}
				}
			}
		}
		return B;
	}





	private static int[] append(int[] x, int y) {
		int[] z = new int[x.length + 1];
		for (int i = 0; i < x.length; i++) {
			z[i] = x[i];
		}
		z[x.length] = y;
		return z;
	}

	// Prints a Polyomino in a new Image2d element
	public void print(){
		Image2d img = new Image2d(800, 600);
		img.addPolyomino(this, new Point());
		new Image2dViewer(img);
	}

	// Returns, for a given n and k, all the polyominoes of size n that can cover their k-dilation (with rotation)
	public static HashSet<Polyomino> dilateCoverFixed(int n, int k) {
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFree(n);
		HashSet<Polyomino> newPolyominoes = new HashSet<Polyomino>();
		for (Polyomino P : polyominoes) {
			Polyomino P1 = P.dilateBy(k);
			if(!P1.GridDLSolution(P.returnFixed()).isEmpty()) {
				newPolyominoes.add(P);
			}
		}
		return newPolyominoes;
	}

	// Returns, for a given n and k, all the polyominoes of size n that can cover their k-dilation (without rotations)
	public static HashSet<Polyomino> dilateCoverFree(int n, int k) {
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFixed(n);
		HashSet<Polyomino> newPolyominoes = new HashSet<Polyomino>();
		for (Polyomino P : polyominoes) {
			Polyomino P1 = P.dilateBy(k);
			HashSet<Polyomino> newPolyominoes1 = new HashSet<Polyomino>();
			newPolyominoes1.add(P); 
			if(!P1.GridDLSolution(newPolyominoes1).isEmpty()) {
				newPolyominoes.add(P);						
			}
		}
		return newPolyominoes;
	}
}
