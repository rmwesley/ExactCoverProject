import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.List;


// Manipulation for images. Here we define an image
public class Image2d {
	Dimension dimension;
	public LinkedList<ColoredPolygon> coloredPolygons;

	public Image2d(Dimension dimension){
		this.dimension = dimension;
		this.coloredPolygons = new LinkedList<ColoredPolygon>();
	}
	public Image2d(int width, int height) {
		dimension = new Dimension(width, height);
		coloredPolygons = new LinkedList<ColoredPolygon>();
	}

	// Adds a single Polyomino to the current image (this)
	public void addPolyomino(Polyomino P, Point center) {
		HashSet<Polygon> polygons = P.getPolygons(center);
		Color color = new Color((int)(Math.random() * ((254 - 0) + 1)),(int)(Math.random() * ((254 - 0) + 1)),(int)(Math.random() * ((254 - 0) + 1)));
		for(Polygon p : polygons){
			this.addColoredPolygon(p, color);
		}
	}
	// Adds an array of Polyominoes to the current image (this)
	public void addPolyominoes(Polyomino[] polyominoes, Point center, boolean horizontal){
		for(int i=0; i < polyominoes.length; i++){
			this.addPolyomino(polyominoes[i], center);
			if(horizontal) center.translate(4+polyominoes[i].width, 0);
			else center.translate(0, 4+polyominoes[i].width);
		}
	}

	// Adds a HashSet of Polyominoes to the current image (this)
	public void addPolyominoes(HashSet<Polyomino> polyominoes, Point center, boolean horizontal){
		for(Polyomino p : polyominoes){
			this.addPolyomino(p, center);
			if(horizontal) center.translate(4+p.width, 0);
			else center.translate(0, 4+p.width);
		}
	}

	public void addColoredPolygon(Polygon polygon, Color color) {
		coloredPolygons.add(new ColoredPolygon(polygon, color));
	}

	// Clear the picture
	public void clear() {
		coloredPolygons = new LinkedList<ColoredPolygon>();
	}
}
