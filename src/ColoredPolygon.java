import java.awt.Color;
import java.awt.Polygon;

// A data structure containing a Polygon and its color
public class ColoredPolygon {

	Color color;
	Polygon polygon; 

	public ColoredPolygon(Polygon polygon, Color color) {
		this.color = color;
		this.polygon = polygon; 
	}
}
