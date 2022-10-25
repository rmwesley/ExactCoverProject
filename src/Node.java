
public class Node {
	Node U, D, L, R;
	Column C;
	int locator;

	public Node() {
		U = null;
		D = null;
		L = null;
		R = null;
		C = null;
		int locator = -1;
	}

	@Override
	public String toString() {
		return String.valueOf(locator);
	}

	void removeLR() {
		this.R.L = this.L;
		this.L.R = this.R;
	}

	void removeUD() {
		this.D.U = this.U;
		this.U.D = this.D;
	}

	void restoreUD() {
		this.D.U = this;
		this.U.D = this;
	}

	void restoreLR() {
		this.R.L = this;
		this.L.R = this;
	}
}