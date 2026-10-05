// File provided by Polytechnique's INF421 course by professor Vincent Pilaud
package polyomino;

import javax.swing.JFrame;

public // Frame for the vizualization
class Image2dViewer extends JFrame {

	private static final long serialVersionUID = -7498525833438154949L;
	static int xLocation = 0;
	Image2d img;
	public Image2dComponent imgComponent;

	public Image2dViewer() {
		Image2d img = new Image2d(200, 200);
		new Image2dViewer(img);
	}

	public Image2dViewer(Image2d img) {
		this.img = img;
		this.setLocationRelativeTo(null);
		this.setLocation(xLocation, 0);
		setTitle("Projet d'INF421 : pavages de polyominos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		imgComponent = new Image2dComponent(img);
		add(imgComponent);
		pack();
		setVisible(true);
		xLocation += img.getWidth();
	}
}