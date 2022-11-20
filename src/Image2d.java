//import java.awt.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.LinkedList;
//import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JComponent;

public class Image2d extends JComponent {
	//private static final long serialVersionUID = -7710437354239150390L;
	private static int scaling = 10;
	public List<ColoredPolygon> coloredPolygons;
	//public LinkedList<ColoredPolygon> coloredPolygons;
	//private Rectangle bounds;

	public Image2d() {
		//this.coloredPolygons = Collections.synchronizedList(new LinkedList<ColoredPolygon>());
		this.coloredPolygons = new LinkedList<ColoredPolygon>();
		//this.bounds = new Rectangle();
		//this.bounds = new Rectangle(0,0,200,200);
	}
	// Adds a single Polyomino to the current image (this)
	public Image2d(Polyomino polyomino, Point center) {
		this.coloredPolygons = new LinkedList<ColoredPolygon>();
		//this.coloredPolygons = Collections.synchronizedList(new LinkedList<ColoredPolygon>());
		//this.bounds = new Rectangle(2*scaling*center.x, 2*scaling*center.y,
		//		2*scaling*polyomino.width, 2*scaling*polyomino.height);

		//this.size = new Dimension(100,100);
		HashSet<Polygon> polygons = polyomino.getPolygons(center);
		for(Polygon polygon : polygons){
			this.addColoredPolygon(polygon, polyomino.color);
		}
	}

	public void addColoredPolygon(Polygon polygon, Color color) {
		coloredPolygons.add(new ColoredPolygon(polygon, color));

		//this.bounds = new Rectangle(2*scaling*center.x, 2*scaling*center.y,
		//		2*scaling*polyomino.width, 2*scaling*polyomino.height);
		//this.bounds.add(polygon.getBounds());
	}

	// Clear the picture
	public void clear() {
		coloredPolygons = new LinkedList<ColoredPolygon>();
		//this.bounds = new Rectangle();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		//g2.setBackground(Color.white);
		g2.translate(0,250);
		g2.scale(scaling, -scaling);

		//synchronized (this.coloredPolygons) {
		for (ColoredPolygon coloredPolygon : this.coloredPolygons) {
			g2.setColor(coloredPolygon.color);
			g2.fillPolygon(coloredPolygon.polygon);
			g2.drawPolygon(coloredPolygon.polygon);
		}
		//}
	}
	@Override
	public Dimension getPreferredSize(){
		Rectangle bounds = new Rectangle(0,0,4,0);
		for (ColoredPolygon coloredPolygon : this.coloredPolygons) {
			bounds.add(coloredPolygon.polygon.getBounds());
		}
		Dimension size = bounds.getSize();
		size.width += 1;
		size.width *= scaling;
		size.height *= scaling;
		return size;
	}
}
