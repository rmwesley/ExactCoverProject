import java.awt.Point;
import java.awt.Dimension;
import java.util.HashSet;

public class Main {
	
	public static void main(String[] args) {

		Polyomino p = new Polyomino("[(0,0), (0,1), (1,0)]");
		p.show();
		//Test.minimalPolygon();
		Test.inf421printer();
		//Test.numberOfFreePolyominosNaive(3);
		//Test.numberOfFixedPolyominosNaive(3);
		Test.freeGenerator(6);
		Test.fixedGenerator(4);
		//Test.ExactcoverExample();
		//Test.TestDancingLinks();
		//Test.testTiling();
		//Test.countDilateFixed(2, 4);
		//Test.dilateRepresentFixed(1, 2);
	}
}

