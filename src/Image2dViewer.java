import javax.swing.JFrame;
import java.awt.FlowLayout;
//import java.awt.Point;

//Frame for the vizualization
class Image2dViewer extends JFrame {

	private static final long serialVersionUID = -7498525833438154949L;

	public Image2dViewer() {
		setLayout(new FlowLayout());
	}
	public Image2dViewer(Image2d img) {
		setLayout(new FlowLayout());
		this.add(img);
		this.pack();
		this.setVisible(true);
	}
	public void addImage(Image2d img){
		this.add(img);
		this.pack();
		this.setVisible(true);
	}
	public void addImages(Iterable<Image2d> images){
		for (Image2d img : images){
			this.add(img);
		}
		this.pack();
		this.setVisible(true);
	}
	public void addImages(Image2d[] images){
		for (int i=0; i<= images.length; i++){
			this.add(images[i]);
		}
		this.pack();
		this.setVisible(true);
	}
	public void addPolyomino(Polyomino polyomino, Point center){
		this.add(new Image2d(polyomino, center));
		this.pack();
		this.setVisible(true);
	}
	public void addPolyominoes(
			Iterable<Polyomino> polyominoes, Point center){
		for (Polyomino polyomino : polyominoes){
			this.addPolyomino(polyomino, center);
		}
		this.pack();
		this.setVisible(true);
	}
	public void addPolyominoes(Polyomino[] polyominoes, Point center){
		for (int i=0; i < polyominoes.length; i++){
			this.addPolyomino(polyominoes[i], center);
		}
		this.pack();
		this.setVisible(true);
	}
}
