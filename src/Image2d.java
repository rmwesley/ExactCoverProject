import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


// Manipulation for images. Here we define an image
public class Image2d {
	Dimension dimension; // dimension of the image
	public LinkedList<ColoredPolygon> coloredPolygons; // colored polygons in the image (which is a list of Polygons)
	public LinkedList<Edge> edges; // edges to add to separate polygons (ie a list of Edges)

	// Constructor that instantiates an image of a specified width and height
	public Image2d(Dimension dimension){
		this.dimension = dimension;
		this.coloredPolygons = new LinkedList<ColoredPolygon>();
		this.edges = new LinkedList<Edge>();
	}
	public Image2d(int width, int height) {
		dimension = new Dimension(width, height);
		coloredPolygons = new LinkedList<ColoredPolygon>();
		edges = new LinkedList<Edge>();
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
		coloredPolygons = new LinkedList<ColoredPolygon>();
		edges = new LinkedList<Edge>();
	}
}
