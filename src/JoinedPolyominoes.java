import javax.swing.JComponent;

import java.util.HashSet;

import java.awt.Rectangle;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class JoinedPolyominoes extends JComponent{
	public HashSet<Polyomino> polyominoes;

	public static int scaling = Polyomino.scaling;
	public static float strokeSize = Polyomino.strokeSize;

	public JoinedPolyominoes(HashSet<Polyomino> polyominoes){
		this.polyominoes = polyominoes;
	}
	public JoinedPolyominoes(
			HashSet<Polyomino> polyominoes, String coloringMethod){
		for (Polyomino polyomino : polyominoes){
			if(coloringMethod == "random") polyomino.randomColor();
			if(coloringMethod == "bw") polyomino.randomGray();
		}
		this.polyominoes = polyominoes;
	}
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(strokeSize, getHeight() - strokeSize);
		g2.scale(scaling, -scaling);
		g2.setStroke(new BasicStroke((float) strokeSize/scaling));

		for (Polyomino polyomino : this.polyominoes){
			g2.setColor(polyomino.color);
			g2.fill(polyomino.getArea());
			g2.setColor(Color.BLACK);
			g2.draw(polyomino.getArea());
		}
	}
	public Dimension getPreferredSize(){
		Rectangle bounds = new Rectangle();
		for (Polyomino polyomino : this.polyominoes){
			bounds.add(polyomino.bounds);
		}
		
		bounds.width += 1;
		bounds.height += 1;
		bounds.width *= scaling;
		bounds.height *= scaling;

		bounds.width += 2*strokeSize;
		bounds.height += 2*strokeSize;

		return bounds.getSize();
	}
}

