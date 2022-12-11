import java.util.HashSet;

public class Main {
	
	public static void main(String[] args) throws Exception {
		HashSet<Integer> options = new HashSet<Integer>();
        for(int i = 0; i < args.length; i++) {
			options.add(Integer.parseInt(args[i]));
        }
		for (Integer choice : options) test(choice);
	}
	private static void test(int choice) throws Exception{
		switch(choice){
			case 0:
				Polyomino P = new Polyomino("[(0,0), (1,0), (0,1)]");
				P.showThis();
				break;
			case 1:
				Test.inf421printer();
				break;
			case 2:
				Test.numberOfFreePolyominosNaive(3);
				break;
			case 3:
				Test.numberOfFixedPolyominosNaive(3);
				break;
			case 4:
				Test.freeGenerator(6);
				break;
			case 5:
				Test.fixedGenerator(4);
				break;
			case 6:
				Test.ECPolyominoExample();
				break;
			case 7:
				Test.ECPolyominoExample2();
				break;
			case 8:
				Test.GenericECExample();
				break;
			case 9:
				Test.ExactcoverExample();
				break;
			case 10:
				Test.DancingLinks();
				break;
			case 11:
				Test.DancingLinksImage();
				break;
			case 12:
				Test.countDilateFixed(4, 3);
				break;
			case 13:
				Test.countDilateFree(4, 3);
				break;
			case 14:
				Test.dilateRepresentFixed(4, 3);
				break;
			case 15:
				Test.dilateRepresentFree(4, 3);
				break;
		}
	}
}

