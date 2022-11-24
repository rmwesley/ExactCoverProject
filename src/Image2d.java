import java.util.LinkedList;
import java.util.HashSet;
import java.util.List;

//import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JComponent;

public class Image2d extends JComponent {
	private static int scaling = 10;
	public List<ColoredPolygon> coloredPolygons;

	public Image2d() {
		this.coloredPolygons = new LinkedList<ColoredPolygon>();
	}
	public Image2d(Polyomino polyomino, Point center) {
		this.coloredPolygons = new LinkedList<ColoredPolygon>();
		HashSet<Polygon> polygons = polyomino.getPolygons(center);
		for(Polygon polygon : polygons){
			this.addColoredPolygon(polygon, polyomino.color);
		}
	}

	public void addColoredPolygon(Polygon polygon, Color color) {
		coloredPolygons.add(new ColoredPolygon(polygon, color));
	}

	// Clear the picture
	public void clear() {
		coloredPolygons = new LinkedList<ColoredPolygon>();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.translate(0, getHeight() - scaling/2);
		g2.scale(scaling, -scaling);

		for (ColoredPolygon coloredPolygon : this.coloredPolygons) {
			g2.setColor(coloredPolygon.color);
			g2.fillPolygon(coloredPolygon.polygon);
			g2.drawPolygon(coloredPolygon.polygon);
		}
	}
	@Override
	public Dimension getPreferredSize(){
		Rectangle bounds = new Rectangle();
		for (ColoredPolygon coloredPolygon : this.coloredPolygons) {
			bounds.add(coloredPolygon.polygon.getBounds());
		}
		Dimension size = bounds.getSize();
		size.width += 1;
		size.height += 1;
		size.width *= scaling;
		size.height *= scaling;
		return size;
	}
}
