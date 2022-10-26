import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

//Image2d component
class Image2dComponent extends JComponent {

	private static final long serialVersionUID = -7710437354239150390L;
	private Image2d img;

	public Image2dComponent(Image2d img) {
		this.img = img;
		setPreferredSize(img.dimension);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// set the background color
		Dimension d = getSize();
		//g2.setBackground(Color.white);
		//g2.clearRect(0,0,d.width,d.height);
		int s=10;
		g2.translate(0,200);
		g2.scale(s, -s);

		// draw the polygons
		synchronized (img.coloredPolygons) {
			for (ColoredPolygon coloredPolygon : img.coloredPolygons) {
				g2.setColor(coloredPolygon.color);
				g2.fillPolygon(coloredPolygon.polygon);
				g2.drawPolygon(coloredPolygon.polygon);
			}
		}
		
		// draw the edges
		g2.setColor(Color.white);
		synchronized (img.edges) {
			for (Edge edge : img.edges) {
				g2.setStroke(new BasicStroke(edge.width));
				g2.drawLine(edge.x1, edge.y1, edge.x2, edge.y2);
			}
		}
	}
}
