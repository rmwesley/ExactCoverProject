import java.util.HashSet;

public class DancingLinks {
	Column[] Row;
	Node[][] Nodes;

	//Constructor of a Dancing Links data structure from an exact cover problem matrix
	
	public DancingLinks(int[][] M) {

		this.Row = new Column[M[0].length + 1];
		this.Nodes = new Node[M.length][M[0].length];

		int h = M.length;
		int l = M[0].length;

		for (int j = 0; j < l + 1; j++) {
			this.Row[j] = new Column();
			this.Row[j].C = this.Row[j];
			this.Row[j].N = "" + j;
		}

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < l; j++) {
				this.Nodes[i][j] = new Node();
				this.Nodes[i][j].C = this.Row[j];
				Nodes[i][j].locator = i;
			}
		}

		for (int j = 0; j < l - 1; j++) {
			createLRLink(Row[j], Row[j + 1]);

			for (int i = 0; i < h - 1; i++) {
				createLRLink(Nodes[i][j], Nodes[i][j + 1]);
				createUDLink(Nodes[i][j], Nodes[i + 1][j]);
			}
			createUDLink(Nodes[h - 1][j], Row[j]);
			createUDLink(Row[j], Nodes[0][j]);
			createLRLink(Nodes[h - 1][j], Nodes[h - 1][j + 1]);
		}

		// The last loop didn't consider j = l - 1 and j = l
		createLRLink(Row[l - 1], Row[l]);
		createLRLink(Row[l], Row[0]);

		for (int i = 0; i < h - 1; i++) {
			createLRLink(Nodes[i][l - 1], Nodes[i][0]);
			createUDLink(Nodes[i][l - 1], Nodes[i + 1][l - 1]);
		}
		createUDLink(Nodes[h - 1][l - 1], Row[l - 1]);
		createUDLink(Row[l - 1], Nodes[0][l - 1]);
		createLRLink(Nodes[h - 1][l - 1], Nodes[h - 1][0]);

		createUDLink(Row[l], Row[l]);
		Row[l].S = h + 1;

		for (int j = 0; j < l; j++) {
			for (int i = 0; i < h; i++) {
				if (M[i][j] != 0) {
					Row[j].S++;
				}
				else {
					Node n = Nodes[i][j];

					n.removeLR();
					n.removeUD();
				}
			}
		}
	}

	//Create a Left-Right link between Nodes
	
	public void createLRLink(Node a, Node b) {
		a.R = b;
		b.L = a;
	}

	//Create a Up-Down link between Nodes
	
	public void createUDLink(Node a, Node b) {
		a.D = b;
		b.U = a;
	}
	
	//Covers a given column of this
	
	void cover(Column c) {
		c.removeLR();
		for (Node x = c.D; x != c; x = x.D) {
			for (Node y = x.R; y != x; y = y.R) {
				y.removeUD();
				y.C.S--;
			}
		}
	}

	//Uncovers a given column of this
	
	void uncover(Column c) {
		for (Node x = c.U; x != c; x = x.U) {
			for (Node y = x.L; y != x; y = y.L) {
				y.restoreUD();
				y.C.S++;
			}
		}
		c.restoreLR();
	}

	//Searches the column with the least amount of possible cover Nodes
	
	public Column minColumn() {
		Column m = new Column();
		m.S = this.Nodes.length + 2;

		for (Column x = (Column) Row[Row.length - 1].R; x != Row[Row.length - 1]; x = (Column) x.R) {
			if (x.S < m.S) {
				m = x;
			}
		}
		return m;
	}

	//Return a LinkedList of all Solutions of the problem, where each solution is comprised of a LinkedList of Nodes
	
	HashSet<HashSet<Node>> exactCoverNode() {
		HashSet<HashSet<Node>> P = new HashSet<HashSet<Node>>();

		Column best = minColumn();
		cover(best);

		for (Node row = best.D; row != best; row = row.D) {

			for (Node x = row.R; x != row; x = x.R) {
				cover(x.C);
			}

			if (isSolved()) {
				HashSet<Node> p = new HashSet<Node>();
				p.add(row);

				P.add(p);
				for (Node x = row.L; x != row; x = x.L) {
					uncover(x.C);
				}

				uncover(best);
				return P;
			}

			HashSet<HashSet<Node>> e = exactCoverNode();
			if (!e.isEmpty()) {
				P.addAll(e);
				for (HashSet<Node> p : e) {
					p.add(row);
				}
			}

			for (Node x = row.L; x != row; x = x.L) {
				uncover(x.C);
			}
		}
		uncover(best);
		return P;
	}

	//Verifies if this is solved
	
	boolean isSolved() {
		return Row[Row.length - 1].R == Row[Row.length - 1];
	}

}
