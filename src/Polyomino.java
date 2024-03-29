import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Collection;

import java.util.Random;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import java.awt.geom.Area;

import javax.swing.JComponent;

//The class Polyomino, the heart of the subject
public class Polyomino extends JComponent {

	public HashSet<Point> tiles; // Set of tiles of the Polyomino
	public int n; // Number of tiles in the polyomino
	public Rectangle bounds; // Rectangle with bounds of the polyomino
	public Color color;
	private static Random random = new Random(0);
	public static int scaling = 20;
	public static float strokeSize = 5;

	// Initializes an empty Polyomino
	public Polyomino() {
		this.tiles = new HashSet<Point>();
		this.n = 0;
		this.bounds = new Rectangle();
		this.color = new Color(255, 0, 0);
	}
	// Constructs a polyomino from a set of tiles
	public Polyomino(HashSet<Point> tiles) {
		this.tiles = new HashSet<Point>();
		for (Point p : tiles){
			this.addTile((Point) p.clone());
		}
		this.color = new Color(255, 0, 0);
	}
	// Constructs a polyomino from a line-string of coordinates
	public Polyomino(String str) {
		this.tiles = new HashSet<Point>();

		Pattern pattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
		Matcher matcher = pattern.matcher(str);

		int x,y;
		while (matcher.find()){
			x = Integer.parseInt(matcher.group(1));
			y = Integer.parseInt(matcher.group(2));
			this.addTile(new Point(x, y));
		}
		this.color = new Color(255, 0, 0);
	}
	public void randomGray(){
		int temp = random.nextInt(255);
		color = new Color(temp, temp, temp);
	}
	public void randomColor(){
		color = new Color(
				random.nextInt(255),
				random.nextInt(255),
				random.nextInt(255));
	}
	public void setColor(Color color){
		this.color = color;
	}
	// Adds a point to a Polyomino's tiling
	public void addTile(Point p){
		if(this.tiles.contains(p)) return;

		this.tiles.add(p);
		this.n++;
		if(this.n == 1){
			this.bounds = new Rectangle(p.x, p.y, 0, 0);
			return;
		}
		this.bounds.add(p);
	}
	public void addTile(int x, int y){
		this.addTile(new Point(x, y));
	}
	public void addTile(int[] v){
		this.addTile(new Point(v[0], v[1]));
	}

	public Area getArea(){
		Area surface = new Area();
		Dimension tileSize = new Dimension(1, 1);
		for(Point p : this.tiles){
			surface.add(new Area(new Rectangle(p, tileSize)));
		}
		return surface;
	}
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;

		g2.translate(strokeSize, getHeight() - strokeSize);
		g2.scale(scaling, -scaling);
		g2.setColor(this.color);
		g2.fill(this.getArea());
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) strokeSize/scaling));
		g2.draw(this.getArea());
	}
	@Override
	public Dimension getPreferredSize(){
		Dimension size = bounds.getSize();
		size.width += 1;
		size.height += 1;
		size.width *= scaling;
		size.height *= scaling;

		size.width += 2*strokeSize;
		size.height += 2*strokeSize;
		return size;
	}

	// Stretches a polyomino by a factor f in the X direction
	public Polyomino stretchX(int f){
		Polyomino polyomino = new Polyomino(this.tiles);
		for(Point p : this.tiles){
			for(int i=0; i<f; i++){
				polyomino.addTile(f*(p.x - bounds.x) + i + bounds.x, p.y);
			}
		}
		return polyomino;
	}
	// Stretches a polyomino by a factor f in the Y direction
	public Polyomino stretchY(int f){
		Polyomino polyomino = new Polyomino(this.tiles);
		for(Point p : this.tiles){
			for(int j=0; j<f; j++){
				polyomino.addTile(p.x, f*(p.y - bounds.y) + j + bounds.y);
			}
		}
		return polyomino;
	}
	// Dilates a polyomino by a factor f.
	public Polyomino dilateBy(int f){
		Polyomino polyomino = new Polyomino(this.tiles);
		for(Point p : this.tiles){
			for(int i=0; i<f; i++){
				for(int j=0; j<f; j++){
					polyomino.addTile(f*(p.x - bounds.x) + i + bounds.x,
						f*(p.y - bounds.y) + j + bounds.y);
				}
			}
		}
		return polyomino;
	}

	public Polyomino translated(int x, int y){
		Polyomino polyomino = new Polyomino();
		polyomino.bounds = new Rectangle(this.bounds);
		polyomino.bounds.translate(x, y);
		polyomino.n = this.n;

		for (Point p : this.tiles){
			polyomino.tiles.add(new Point(p.x + x, p.y + y));
		}
		return polyomino;
	}
	public void translate(int x, int y){
		this.bounds.translate(x, y);
		for (Point p : this.tiles){
			p.translate(x, y);
		}
		this.tiles = new HashSet<Point>(this.tiles);
	}

	public Polyomino recentered(){
		return this.translated(-this.bounds.x, -this.bounds.y);
	}
	public void recenter(){
		this.translate(-this.bounds.x, -this.bounds.y);
	}
	public void recenter(Point center){
		this.translate(center.x - this.bounds.x, center.y - this.bounds.y);
	}

	public Polyomino rotation(int n){
		if(n%4==1) return this.turn();
		if(n%4==2) return this.halfTurn();
		if(n%4==3) return this.counterTurn();
		return this;
	}

	public Polyomino turn(){
		Polyomino polyomino = new Polyomino();
		for(Point p : this.tiles){
			polyomino.addTile(bounds.x + (p.y - bounds.y),
					bounds.y + bounds.width - (p.x - bounds.x));
		}
		return polyomino;
	}
	public Polyomino counterTurn(){
		Polyomino polyomino = new Polyomino();
		for(Point p : this.tiles){
			polyomino.addTile(bounds.x + bounds.height - (p.y - bounds.y),
					bounds.y + (p.x - bounds.x));
		}
		return polyomino;
	}
	public Polyomino halfTurn(){
		Polyomino polyomino = new Polyomino();
		for(Point p : this.tiles){
			polyomino.addTile(bounds.x + bounds.width - (p.x - bounds.x),
					bounds.y + bounds.height - (p.y - bounds.y));
		}
		return polyomino;
	}

	// Returns the symmetric polyomino of this with respect to the Y axis
	public Polyomino symmetricalY() {
		Polyomino polyomino = new Polyomino();
		for(Point p : this.tiles){
			polyomino.addTile(bounds.x + bounds.width - p.x, p.y);
		}
		return polyomino;
	}

	// Returns the symmetric polyomino of this with respect to the X axis
	public Polyomino symmetricalX() {
		Polyomino polyomino = new Polyomino();
		for(Point p : this.tiles){
			polyomino.addTile(p.x, bounds.y + bounds.height - p.y);
		}
		return polyomino;
	}

	// A function that determines whether or not two Polyominoes are equal
	@Override
	public boolean equals(Object obj) {
		Polyomino polyomino = (Polyomino) obj;
		if(this == polyomino) return true;
		if(this.n != polyomino.n) return false;
		if(!this.bounds.equals(polyomino.bounds)) return false;
		return this.tiles.equals(polyomino.tiles);
	}
	@Override
	public int hashCode() {
		return n*bounds.width+bounds.height*bounds.x-bounds.y;
	}

	// A method that reads an array of polyominoes from a file
	public static Polyomino[] fileReader(String filename) {
		LinkedList<Polyomino> polyominoList = new LinkedList<Polyomino>();
		BufferedReader reader;
		//int xSpacing = 0, ySpacing = 0;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				Polyomino polyo = new Polyomino(line);
				//polyo.translate(xSpacing, ySpacing);
				//xSpacing += polyo.bounds.width;

				polyominoList.add(polyo);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return polyominoList.toArray(new Polyomino[0]);
	}

	public HashSet<Polyomino> getFittings(Polyomino polyomino){
		//System.out.println("\n" + this);
		//System.out.println(polyomino);
		HashSet<Polyomino> fittingPolyominoes =	new HashSet<Polyomino>();

		Point fixedPoint = (Point) this.tiles.iterator().next().clone();
		this.translate(-fixedPoint.x, -fixedPoint.y);

		for(Point p: polyomino.tiles){
			this.translate(p.x, p.y);
			if(polyomino.tiles.containsAll(this.tiles)){

				Polyomino currPolyomino = new Polyomino();
				for(Point temp: this.tiles){
					currPolyomino.addTile((Point) temp.clone());
				}
				//System.out.println(currPolyomino);

				fittingPolyominoes.add(currPolyomino);
			}
			this.translate(-p.x, -p.y);
		}
		this.translate(fixedPoint.x, fixedPoint.y);
		//System.out.println("\nAfter\n" + this + "\n\n");
		return fittingPolyominoes;
	}

	////Get all the ways "this" can fit inside specified Polyomino
	//public HashSet<HashSet<Point>> getFittings(Polyomino polyomino){
	//	HashSet<HashSet<Point>> fittingTiles =
	//		new HashSet<HashSet<Point>>();

	//	Point fixedPoint = this.tiles.iterator().next();
	//	this.translate(-fixedPoint.x, -fixedPoint.y);

	//	for(Point p: polyomino.tiles){
	//		this.translate(p.x, p.y);
	//		if(polyomino.tiles.containsAll(this.tiles)){
	//			//System.out.println("\n" + this);
	//			//System.out.println(polyomino);

	//			HashSet<Point> currTiles = new HashSet<Point>();
	//			for(Point temp: this.tiles){
	//				currTiles.add((Point) temp.clone());
	//			}
	//			//System.out.println(currTile);

	//			fittingTiles.add(currTiles);
	//			//fittingTiles.add(new HashSet<Point>(this.tiles));
	//		}
	//		this.translate(-p.x, -p.y);
	//	}
	//	this.translate(fixedPoint.x, fixedPoint.y);
	//	return fittingTiles;
	//}

	// Returns all fixed Polyominoes associated to a given Polyomino
	// i.e. all the equivalence classes
	public HashSet<Polyomino> getEquivalent() {
		return this.getEquivalent(0);
	}
	public HashSet<Polyomino> getEquivalent(int equivalenceType) {
		HashSet<Polyomino> equivalent = this.getRotations();
		if (equivalenceType == 1) return equivalent;
		equivalent.addAll(this.getSymmetries());
		return equivalent;
	}
	public HashSet<Polyomino> getRotations() {
		HashSet<Polyomino> equivalent = new HashSet<Polyomino>();
		for(int i=0; i<4; i++){
			equivalent.add(this.rotation(i));
		}
		return equivalent;
	}
	public HashSet<Polyomino> getSymmetries(){
		HashSet<Polyomino> equivalent = new HashSet<Polyomino>();
		Polyomino temp = this.symmetricalY();
		for(int i=0; i<4; i++){
			equivalent.add(temp.rotation(i));
		}
		return equivalent;
	}

	// Returns the polyominoes equal to this
	// but with one new square added
	public LinkedList<Polyomino> children() {
		LinkedList<Polyomino> children = new LinkedList<Polyomino>();
		for(Point p : this.tiles){
			for(int i=-2; i<2; i++){
				Point neighbor = new Point(p.x + (i%2), p.y + ((i+1)%2));
				if(!this.tiles.contains(neighbor)) {
					Polyomino child = new Polyomino(this.tiles);
					child.addTile((Point) neighbor.clone());
					child.recenter();
					if(children.contains(child)) {
						continue;
					}
					children.add(child);
				}
			}
		}
		return children;
	}

	// A naive algorithm that generates all fixed Polyominoes
	public static HashSet<Polyomino> naiveGenerateFixed(int n) {
		HashSet<Polyomino> polyominoes = new HashSet<Polyomino>();
		if (n == 1) {
			polyominoes.add(new Polyomino("[(0,0)]")); 
			return polyominoes;
		} 
		else {
			for (Polyomino polyomino : naiveGenerateFixed(n - 1)) {
				polyominoes.addAll(polyomino.children());
			}
			return polyominoes;
		}
	}

	// A naive algorithm that generates all free polyominoes
	public static HashSet<Polyomino> naiveGenerateFree(int n) {
		if (n == 1) {
			HashSet<Polyomino> polyominoes = new HashSet<Polyomino>();
			polyominoes.add(new Polyomino("[(0,0)]"));
			return polyominoes;
		} 
		else {
			HashSet<Polyomino> polyominoes = new HashSet<Polyomino>();
			for (Polyomino P : naiveGenerateFixed(n - 1)) {
				for (Polyomino temp : P.children()){
					if ((!polyominoes.contains(temp)) && Collections
							.disjoint(polyominoes, temp.getEquivalent())) {
						polyominoes.add(temp);
							}
				}
			}
			return polyominoes;
		}
	}

	public boolean isEquivalentTo(Polyomino polyomino) {
		return this.isEquivalentTo(polyomino, 0);
	}
	// Verifies if two Polyominoes are equivalent
	public boolean isEquivalentTo(
			Polyomino polyomino, int equivalenceType) {

		HashSet<Polyomino> equivalent =
			this.getEquivalent(equivalenceType);

		for(Polyomino temp : equivalent){
			temp.recenter(new Point(polyomino.bounds.getLocation()));
		}
		return equivalent.contains(polyomino);
	}

	// Returns the exact cover problem of this and a set of polyominoes
	// not allowing repetitions of equivalent elements from pieces
	public int[][] getUniqueECMatrix(
			Collection<Polyomino> pieces, int equivalenceType) {
		int nPieces = pieces.size();
		//int[][] matrix = new int[nPieces][this.n + nPieces];
		//int[][] repetitions = new int[nPieces][nPieces];
		//int[] vector = new int[nPieces];
		boolean repeated = false;
		int i=0, j=0;
		int uniqueCounter = 0;

		j = this.n;
		HashSet<Polyomino> representants = new HashSet<Polyomino>();
		HashMap<Polyomino, Integer> identifiers = new HashMap<Polyomino, Integer>();
		//HashMap<Polyomino, int[]> matrixVectors =
		//new HashMap<Polyomino, int[]>();
		for (Polyomino polyomino : pieces){
			for (Polyomino rep : representants){
				if(polyomino.isEquivalentTo(rep, equivalenceType)){
					repeated = true;
					//vector = matrixVectors.get(rep);
					//vector.set(i, 1);
					//matrixVectors.put(rep, vector);
					identifiers.put(polyomino, identifiers.get(rep));
					j++;
				}
			}
			if (!repeated){
				//vector = new int[nPieces];
				//vector[i] = 1;
				//matrixVectors.put(polyomino, vector);
				identifiers.put(polyomino, uniqueCounter);
				representants.add(polyomino);
				uniqueCounter++;
			}
			i++;
		}
		i=0;
		int[][] matrix = new int[nPieces][this.n + uniqueCounter];
		for (Polyomino polyomino : pieces) {
			matrix[i][this.n + identifiers.get(polyomino)] = 1;
			i++;
		}
		i=0;j=0;
		for (Polyomino polyomino : pieces) {
			for (Point p : this.tiles) {
				if(polyomino.tiles.contains(p)) {
					matrix[i][j] = 1;
				}
				j++;
			}
			i++;
		}
		return matrix;
	}

	public ECPolyomino getECP(HashSet<Polyomino> pieces){
		return new ECPolyomino(this, pieces);
	}
	// Creates covering problem for this polyomino
	public GenericsEC<Point> getEC(Iterable<Polyomino> polyominoPieces){
		HashSet<Point> groundSet = this.tiles;
		HashSet<HashSet<Point>> pieces = new HashSet<HashSet<Point>>();
		for (Polyomino polyomino : polyominoPieces){
			pieces.add(polyomino.tiles);
		}
		return new GenericsEC<Point>(groundSet, pieces);
	}

	// Returns the exact cover problem of this and a set of polyominoes
	// Allows repetitions of elements
	public int[][] getECMatrix(Collection<Polyomino> pieces) {
		int[][] matrix = new int[pieces.size()][this.n];
		int i=0, j=0;
		for (Polyomino polyomino : pieces) {
			j = 0;
			for (Point p : this.tiles) {
				if(polyomino.tiles.contains(p)) {
					matrix[i][j] = 1;
				}
				j++;
			}
			i++;
		}
		return matrix;
	}

	// toString method to visualize the Polyomino as a String
	@Override
	public String toString() {
		String str = new String();

		str += "[";
		for (Point p : this.tiles) {
			str += "(" + p.x + "," + p.y + "), ";
		}
		str = str.substring(0, str.length() - 2);
		str += "]";
		return str;
	}

	public static int max(int[] list){
		int max = list[0];
		for(int i=1; i < list.length; i++){
			if(list[i] > max) max = list[i];
		}
		return max;
	}
	// Adds a Polyomino drawing to a new container
	public Image2dViewer showThis(){
		Image2dViewer frame = new Image2dViewer();
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		return frame;
	}

	// Returns, for a given n and k, all the polyominoes of size n that can cover their k-dilation (with rotation)
	public static LinkedList<HashSet<Polyomino>> dilateCoverFree(
			int n, int k) {
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFree(n);
		HashSet<Polyomino> satisfyingPolyominoes =
			new HashSet<Polyomino>();
		LinkedList<HashSet<Polyomino>> coverings =
			new LinkedList<HashSet<Polyomino>>();

		for (Polyomino originalP : polyominoes) {
			Polyomino dilatedP = originalP.dilateBy(k);
			HashSet<Polyomino> pieces = originalP.getFittings(dilatedP);
			HashSet<Polyomino> equivalent = originalP.getEquivalent();

			for(Polyomino temp : equivalent){
				pieces.addAll(temp.getFittings(dilatedP));
			}

			HashSet<HashSet<Polyomino>> solutions =
				dilatedP.getECP(pieces).covers(true);
			if(!solutions.isEmpty()
					&& Collections.disjoint(equivalent,
						satisfyingPolyominoes)) {
				satisfyingPolyominoes.add(originalP);
				coverings.add(solutions.iterator().next());
			}
		}
		return coverings;
	}
	// Returns, for a given n and k, all the polyominoes of size n that can cover their k-dilation (without rotations)
	public static LinkedList<HashSet<Polyomino>> dilateCoverFixed(
			int n, int k) {
		HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFree(n);
		LinkedList<HashSet<Polyomino>> coverings =
			new LinkedList<HashSet<Polyomino>>();
		for (Polyomino originalP : polyominoes) {
			Polyomino dilatedP = originalP.dilateBy(k);
			HashSet<Polyomino> pieces = originalP.getFittings(dilatedP);

			HashSet<HashSet<Polyomino>> solutions =
				dilatedP.getECP(pieces).covers(true);
			if(!solutions.isEmpty()) {
				coverings.add(solutions.iterator().next());
			}
		}
		return coverings;
	}
}
