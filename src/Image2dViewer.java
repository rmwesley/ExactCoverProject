import javax.swing.JFrame;
//import javax.swing.JScrollPane;
//import javax.swing.JPanel;
//import javax.swing.ScrollPaneLayout;
import java.awt.FlowLayout;
//import java.awt.GridLayout;
import java.awt.Point;

//Frame for the vizualization
class Image2dViewer extends JFrame {

	private static final long serialVersionUID = -7498525833438154949L;

	public Image2dViewer() {
		//this.panel = new JPanel();
		//JScrollPane scrollPanel = new JScrollPane(panel,
		//		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		//		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		////getContentPane().add(scrPane);
		//scrollPanel.setLayout(new ScrollPaneLayout());
		//this.add(scrollPanel);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		//setLayout(new GridLayout());
	}
	public Image2dViewer(Image2d img) {
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.panel = new JPanel();
		//panel.setLayout(new FlowLayout());
		//JScrollPane scrPane = new JScrollPane(panel);
		//getContentPane().add(scrPane);
		//scrPane.setLayout(new ScrollPaneLayout());
		setLayout(new FlowLayout());
		//setLayout(new GridLayout());
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
			//xLocation += img.dimension.width;
			//this.setLocation(xLocation, 0);
		}
		this.pack();
		this.setVisible(true);
	}
	public void addImages(Image2d[] images){
		for (int i=0; i<= images.length; i++){
			this.add(images[i]);
			//xLocation += img.dimension.width;
			//this.setLocation(xLocation, 0);
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
			//xLocation += polyomino.width;
			//this.setLocation(xLocation, 0);
			//if(horizontal) center.translate(4+p.width, 0);
			//else center.translate(0, 4+p.width);
		}
		this.pack();
		this.setVisible(true);
	}
	public void addPolyominoes(Polyomino[] polyominoes, Point center){
		for (int i=0; i < polyominoes.length; i++){
			this.addPolyomino(polyominoes[i], center);
			//xLocation += polyomino.width;
			//this.setLocation(xLocation, 0);
			//if(horizontal) center.translate(4+p.width, 0);
			//else center.translate(0, 4+p.width);
		}
		this.pack();
		this.setVisible(true);
	}
}
