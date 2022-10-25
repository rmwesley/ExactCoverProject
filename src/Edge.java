
// an Edge is simply a segment : it begins in x, ends in y, and has a given width
public class Edge {
	
	int x1;
	int y1;
	int x2;
	int y2;
	int width;
	
	public Edge(int x1, int y1, int x2, int y2, int width) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = width;
	}
	
	
	// Dilates an edge by a factor n
	
	public Edge dilatationEdge(int n) {
		int x2bis = this.x2*n;
		int y2bis = this.y2*n;
		return new Edge(x1,y1,x2bis,y2bis,n);
	}

}