import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneLayout;

//Frame for the vizualization
class Image2dViewer extends JFrame {

	private static final long serialVersionUID = -7498525833438154949L;
	static int xLocation = 0;
	Image2d img;

	public Image2dViewer(Image2d img) {
		this.img = img;
		this.setLocation(xLocation, 0);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Image2dComponent img2d = new Image2dComponent(img);

		JPanel container = new JPanel();
		JScrollPane scrPane = new JScrollPane(container);
		getContentPane().add(scrPane);
		scrPane.setLayout(new ScrollPaneLayout());

		container.add(img2d);

		pack();
		setVisible(true);
		xLocation += img.dimension.width;
	}
}

