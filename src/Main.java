//import java.awt.Point;
import java.awt.Dimension;
import java.util.HashSet;

public class Main {
	
	public static void main(String[] args) {

		//Test.minimalPolygon();
		Test.inf421printer();
		//Test.numberOfFreePolyominosNaive(3);
		//Test.numberOfFixedPolyominosNaive(3);
		//Test.freeGenerator(6);
		//Test.fixedGenerator(4);
		Test.GenericECExample();
		//Test.ExactcoverExample();
		//Test.TestDancingLinks();
		//Test.testTiling();
		//Test.countDilateFixed(2, 4);

		Test.dilateRepresentFixed(3, 3);
		Test.dilateRepresentFree(3, 3);
	}
}

