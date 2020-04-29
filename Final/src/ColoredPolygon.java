import java.awt.Color;
import java.awt.Polygon;



// creates a ColoredPolygon. It is a Polygon, only with another variable : its color. IT'S THE POLYOMINOS
public class ColoredPolygon {
	
	int[] xcoords;
	int[] ycoords;
	Color color;
	Polygon polygon; 
	
	public ColoredPolygon(int[] x, int[] y, Color col) {
		this.xcoords = x;
		this.ycoords = y;
		this.color = col; // a color is defines using a builder that takes 3 integers as an input.
		this.polygon = new Polygon(x, y, x.length); // The definition is intuitive : a polygon contains a set of x and y coordinates which defines
	}                                               // its summits (and which are automatically linked together in the order in which they are given).
	                                                // here x.length simply defines the number of coordinates.	

}
