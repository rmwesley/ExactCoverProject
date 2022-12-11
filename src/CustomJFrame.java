import javax.swing.JFrame;
//import javax.swing.ScrollPaneLayout;
import java.awt.FlowLayout;
//import java.awt.Point;

//Frame for the vizualization
class CustomJFrame extends JFrame {

	private static final long serialVersionUID = -7498525833438154949L;

	public CustomJFrame() {
		//setLayout(new ScrollPaneLayout());
		setLayout(new FlowLayout());
	}
	public CustomJFrame(Polyomino polyomino) {
		//setLayout(new ScrollPaneLayout());
		setLayout(new FlowLayout());
		this.add(polyomino);
		this.pack();
		this.setVisible(true);
	}
	//public void add(Polyomino p){
	//	super.add(p);
	//	this.pack();
	//	this.setVisible(true);
	//}
	public void add(Iterable<Polyomino> polyominoes){
		this.add(polyominoes, "normal");
	}
	public void add(Iterable<Polyomino> polyominoes, String coloring){
		for (Polyomino polyomino : polyominoes){
			if(coloring == "random") polyomino.randomColor();
			if(coloring == "bw") polyomino.randomGray();
			this.add(polyomino);
		}
		this.pack();
		this.setVisible(true);
	}
	public void add(Polyomino[] polyominoes){
		for (int i=0; i < polyominoes.length; i++){
			this.add(polyominoes[i]);
		}
		this.pack();
		this.setVisible(true);
	}
}
