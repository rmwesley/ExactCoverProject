import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Polygon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Collections;

//The class Polyomino, the heart of the subject
public class Polyomino {

	public HashSet<Point> tiles; // Set of tiles of the Polyomino
	public int n; // Number of tiles in the polyomino
	public Point corner; // Upper left corner of the Polyomino
	public int width, height; // Rectangular dimensions of the polyomino
	public Color color;

	// Initializes an empty Polyomino
	public Polyomino() {
		this.tiles = new HashSet<Point>();
		this.n = 0;
		this.corner = new Point();
		this.width = 0;
		this.height = 0;
		this.color = new Color(255, 0, 0);
		//this.color = new Color((int)(Math.random() * ((254 - 0) + 1)),(int)(Math.random() * ((254 - 0) + 1)),(int)(Math.random() * ((254 - 0) + 1)));
	}
	// Constructs a polyomino from a set of tiles
	public Polyomino(HashSet<Point> tiles) {
		this.tiles = new HashSet<Point>();
		for (Point p : tiles){
			this.addTile(new Point(p.x, p.y));
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

	// Adds a point to a Polyomino's tiling
	public void addTile(Point p){
		if(this.tiles.contains(p)) return;

		this.tiles.add(p);
		this.n++;
		if(this.n == 1){
			this.corner = new Point(p.x, p.y);
			this.width=1;
			this.height=1;
			return;
		}
		if(p.x < this.corner.x) {
			this.width += this.corner.x - p.x;
			this.corner.x = p.x;
		}
		if(p.y < this.corner.y) {
			this.height += this.corner.y - p.y;
			this.corner.y = p.y;
		}
		if(p.x >= this.corner.x + width) width = p.x - this.corner.x + 1;
		if(p.y >= this.corner.y + height) height = p.y - this.corner.y + 1;
	}
	public void addTile(int x, int y){
		this.addTile(new Point(x, y));
	}
	public void addTile(int[] v){
		this.addTile(new Point(v[0], v[1]));
	}

	public HashSet<Polygon> getPolygons() {
		HashSet<Polygon> polygons = new HashSet<Polygon>();
		Polygon currTile = new Polygon();
		Point vertex = new Point();

		for(Point p : this.tiles){
			currTile = new Polygon();
			vertex = new Point(2*p.x, 2*p.y);
			currTile.addPoint(vertex.x, vertex.y);
			vertex.translate(0, 1);
			currTile.addPoint(vertex.x, vertex.y);
			vertex.translate(1, 0);
			currTile.addPoint(vertex.x, vertex.y);
			vertex.translate(0, -1);
			currTile.addPoint(vertex.x, vertex.y);
			polygons.add(currTile);
		}
		return polygons;
	}
	// Determines the coordinates of the
	// vertices of the tiles composing the polyomino
	public HashSet<Polygon> getPolygons(Point center) {
		HashSet<Polygon> polygons = new HashSet<Polygon>();
		Polygon currTile = new Polygon();
		Point vertex = new Point();

		for(Point p : this.tiles){
			currTile = new Polygon();
			vertex = new Point(2*(p.x - this.corner.x) + center.x,
					2*(p.y - this.corner.y) + center.y);
			currTile.addPoint(vertex.x, vertex.y);
			vertex.translate(0, 1);
			currTile.addPoint(vertex.x, vertex.y);
			vertex.translate(1, 0);
			currTile.addPoint(vertex.x, vertex.y);
			vertex.translate(0, -1);
			currTile.addPoint(vertex.x, vertex.y);
			polygons.add(currTile);
		}
		return polygons;
	}

	// Dilates a polyomino by a factor f.
	public Polyomino dilateBy(int f){
		Polyomino polyomino = new Polyomino(this.tiles);
		for(Point p : this.tiles){
			for(int i=0; i<f; i++){
				for(int j=0; j<f; j++){
					polyomino.addTile(f*p.x+i, f*p.y+j);
				}
			}
		}
		return polyomino;
	}

	public void translate(int x, int y){
		this.corner.translate(x, y);
		for (Point p : this.tiles){
			p.translate(x, y);
		}
		this.tiles = new HashSet<Point>(this.tiles);
	}

	public void recenter(){
		this.translate(-this.corner.x, -this.corner.y);
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
			polyomino.addTile(corner.x + (p.y - corner.y),
					corner.y + width-1 - (p.x - corner.x));
		}
		return polyomino;
	}
	public Polyomino counterTurn(){
		Polyomino polyomino = new Polyomino();
		for(Point p : this.tiles){
			polyomino.addTile(corner.x + height-1 - (p.y - corner.y),
					corner.y + (p.x - corner.x));
		}
		return polyomino;
	}
	public Polyomino halfTurn(){
		Polyomino polyomino = new Polyomino();
		for(Point p : this.tiles){
			polyomino.addTile(corner.x + width-1 - (p.x - corner.x),
					corner.y + height-1 - (p.y - corner.y));
		}
		return polyomino;
	}

	// Returns the symmetric polyomino of this with respect to the Y axis
	public Polyomino symmetricalY() {
		Polyomino polyomino = new Polyomino();
		for(Point p : this.tiles){
			polyomino.addTile(corner.x + width-1 - p.x, p.y);
		}
		return polyomino;
	}

	// Returns the symmetric polyomino of this with respect to the X axis
	public Polyomino symmetricalX() {
		Polyomino polyomino = new Polyomino();
		for(Point p : this.tiles){
			polyomino.addTile(p.x, corner.y + this.height-1 - p.y);
		}
		return polyomino;
	}

	// A function that determines whether or not two Polyominoes are equal
	@Override
	public boolean equals(Object obj) {
		Polyomino polyomino = (Polyomino) obj;
		if(this == polyomino) return true;
		if(this.n != polyomino.n) return false;
		if(!this.corner.equals(polyomino.corner)) return false;
		if(!(this.width == polyomino.width)
				|| !(this.height == polyomino.height)){
			return false;
		}
		return this.tiles.equals(polyomino.tiles);
	}
	@Override
	public int hashCode() {
		return this.n*this.width+this.height*this.corner.x-this.corner.y;
	}

	// A method that reads an array of polyominoes from a file
	public static Polyomino[] fileReader(String filename) {
		LinkedList<Polyomino> polyominoList = new LinkedList<Polyomino>();
		BufferedReader reader;
		int xSpacing = 0, ySpacing = 0;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				Polyomino polyo = new Polyomino(line);
				polyo.translate(xSpacing, ySpacing);
				xSpacing += polyo.width;

				polyominoList.add(polyo);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return polyominoList.toArray(new Polyomino[0]);
	}

	// Returns all fixed Polyominoes associated to a given Polyomino
	// i.e. all the equivalence classes
	public HashSet<Polyomino> getEquivalent() {
		HashSet<Polyomino> polyominoes = new HashSet<Polyomino>();
		for(int i=0; i<4; i++){
			polyominoes.add(this.rotation(i));
			//Polyomino P = this.rotation(i);
			//if (!polyominoes.contains(P)) {
			//	polyominoes.add(P);
			//}
		}

		Polyomino temp = this.symmetricalY();
		for(int i=0; i<4; i++){
			polyominoes.add(temp.rotation(i));
			//Polyomino P = temp.rotation(i);
			//if (!polyominoes.contains(P)) {
			//	polyominoes.add(P);
			//}
		}
		return polyominoes;
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
					child.addTile(neighbor.x, neighbor.y);
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

		// Verifies if two Polyominoes are equivalent
		public boolean isEquivalentTo(Polyomino polyomino) {
			return this.getEquivalent().contains(polyomino);
		}

		// Returns the exact cover problem of this and a set of polyominoes, not allowing repetitions of equivalent elements from pieces
		public int[][] getUniqueECMatrix(HashSet<Polyomino> pieces) {
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
			//HashMap<Polyomino, int[]> matrixVectors = new HashMap<Polyomino, int[]>();
			for (Polyomino polyomino : pieces){
				for (Polyomino rep : representants){
					if(polyomino.isEquivalentTo(rep)){
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

		// Creates covering problem for this polyomino
		public GenericsEC<Point> getEC(HashSet<Polyomino> polyominoPieces){
			HashSet<Point> groundSet = this.tiles;
			HashSet<HashSet<Point>> pieces = new HashSet<HashSet<Point>>();
			for (Polyomino polyomino : polyominoPieces){
				pieces.add(polyomino.tiles);
			}
			return new GenericsEC<Point>(groundSet, pieces);
		}

		// Returns the exact cover problem of this and a set of polyominoes, allowing repetitions of elements
		public int[][] getECMatrix(HashSet<Polyomino> pieces) {
			int[][] matrix = new int[pieces.size()][this.n];
			int i=0, j=0;
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
		// Draws a Polyomino in a new Image2d component
		public Image2d draw(Point center){
			return new Image2d(this, center);
		}
		// Adds a Polyomino drawing to a new container
		public void show(){
			new Image2dViewer(this.draw(new Point(0, 0)));
		}

		// Returns, for a given n and k, all the polyominoes of size n that can cover their k-dilation (with rotation)
		public static HashSet<Polyomino> dilateCoverFree(int n, int k) {
			HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFixed(n);
			HashSet<Polyomino> solutionPolyominoes = new HashSet<Polyomino>();
			for (Polyomino originalP : polyominoes) {
				Polyomino dilatedP = originalP.dilateBy(k);
				if(!dilatedP.getEC(originalP.getEquivalent())
						.covers(true).isEmpty()) {
					solutionPolyominoes.add(originalP);
						}
			}
			return solutionPolyominoes;
		}

		// Returns, for a given n and k, all the polyominoes of size n that can cover their k-dilation (without rotations)
		public static HashSet<Polyomino> dilateCoverFixed(int n, int k) {
			HashSet<Polyomino> polyominoes = Polyomino.naiveGenerateFixed(n);
			HashSet<Polyomino> solutionPolyominoes = new HashSet<Polyomino>();
			for (Polyomino originalP : polyominoes) {
				Polyomino dilatedP = originalP.dilateBy(k);
				HashSet<Polyomino> pieces = new HashSet<Polyomino>();
				pieces.add(originalP);

				if(!dilatedP.getEC(pieces).covers(true).isEmpty()) {
					solutionPolyominoes.add(originalP);
				}
			}
			return solutionPolyominoes;
		}
	}
