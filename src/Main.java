import java.awt.Point;
import java.awt.Dimension;
import java.util.HashSet;

public class Main {
	
	public static void main(String[] args) {

		//HashSet<Polyomino> elements = new HashSet<Polyomino>();
		//Polyomino P = new Polyomino("[(0,0)]");
		//P.addTile(1, 0);
		//P.addTile(1, 1);
		//System.out.println(P.getEquivalent());
		//Image2d drawing = new Image2d(new Dimension(2000,400));
		//drawing.addPolyomino(P, new Point(2, 10));
		//drawing.addPolyominoes(P.getEquivalent(), new Point(2, 0), true);
		//new Image2dViewer(drawing);
		//Polyomino polyomino = new Polyomino("[(0,0)]");
		//polyomino.addTile(new Point(-1,0));
		//polyomino.recenter();

		//System.out.println(polyomino.height);
		//System.out.println(P.width);
		//System.out.println(P.height);
		//System.out.println(polyomino.width);
		//System.out.println(P.equals(polyomino));
		//elements.add(P);
		//System.out.println(polyomino);
		//System.out.println(P.equals(polyomino));
		//System.out.println(P.tiles.equals(polyomino.tiles));
		//System.out.println(elements.contains(polyomino));
		//System.out.println(polyomino);
		//HashSet<Point> tiles = new HashSet<Point>();
		//tiles.add(new Point(0, 0));
		//tiles.add(new Point(-1,0));
		//System.out.println(tiles);
		//Polyomino P = new Polyomino("[(0,0), (0,1)]");
		//Polyomino P = new Polyomino("[(0,0), (0,1), (1,0)]");
		//Image2d drawing = P.draw(new Point(2, 10), new Dimension(2000,400));
		//Image2d drawing = polyomino.draw(new Point(5, 10), new Dimension(2000,400));
		//drawing.addPolyominoes(P.children(), new Point(2, 0), true);
		//drawing.addPolyomino(new Polyomino(tiles), new Point(20, 10));
		//new Image2dViewer(drawing);
		Test.minimalPolygon();
		Test.inf421printer();
		//Test.numberOfFreePolyominosNaive(3);
		//Test.numberOfFixedPolyominosNaive(3);
		Test.freeGenerator(6);
		Test.fixedGenerator(6);
		//Test.ExactcoverExample();
		//Test.TestDancingLinks();
		//Test.testTiling();
		//Test.countDilateFixed(2, 2);
		//Test.dilateRepresentFixed(8, 4);
	}
}

