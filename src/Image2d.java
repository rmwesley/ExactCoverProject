// File provided by Polytechnique's INF421 course by professor Vincent Pilaud
// With Image2d() constructor by Wesley

import java.awt.Color;
import java.awt.Polygon;
import java.util.Collections;
import java.util.LinkedList;

class ColoredPolygon {
	Color color;
	Polygon polygon;

	public ColoredPolygon(int[] xcoords, int[] ycoords, Color color) {
		polygon = new Polygon(xcoords, ycoords, xcoords.length);
		this.color = color;
	}

}

// class Edge {
// 	int x1, y1, x2, y2;
// 	int width;

// 	public Edge(int x1, int y1, int x2, int y2, int width) {
// 		super();
// 		this.x1 = x1;
// 		this.y1 = y1;
// 		this.x2 = x2;
// 		this.y2 = y2;
// 		this.width = width;
// 	}
// }

// Manipulation for images
public class Image2d {
	private int width; // width of the image
	private int height; // height of the image
	private java.util.List<ColoredPolygon> coloredPolygons; // colored polygons
															// in the image
	private java.util.List<Edge> edges; // edges to add to separate polygons

	// Constructor that instantiates an image of a specified width and height
	public Image2d(int width, int height) {
		this.width = width;
		this.height = height;
		coloredPolygons = Collections.synchronizedList(new LinkedList<ColoredPolygon>());
		edges = Collections.synchronizedList(new LinkedList<Edge>());
	}

	public Image2d() {
		this(400, 600);
	}

	// Return the width of the image
	public int getWidth() {
		return width;
	}

	// Return the height of the image
	public int getHeight() {
		return height;
	}

	// Return the colored polygons of the image
	public java.util.List<ColoredPolygon> getColoredPolygons() {
		return coloredPolygons;
	}

	// Return the edges of the image
	public java.util.List<Edge> getEdges() {
		return edges;
	}

	// Create the polygon with xcoords, ycoords and color
	public void addPolygon(int[] xcoords, int[] ycoords, Color color) {
		coloredPolygons.add(new ColoredPolygon(xcoords, ycoords, color));
	}

	// Create the edge with coordinates x1, y1, x2, y2
	public void addEdge(int x1, int y1, int x2, int y2, int width) {
		edges.add(new Edge(x1, y1, x2, y2, width));
	}

	// Clear the picture
	public void clear() {
		coloredPolygons = Collections.synchronizedList(new LinkedList<ColoredPolygon>());
		edges = Collections.synchronizedList(new LinkedList<Edge>());
	}
}