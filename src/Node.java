public class Node implements Iterable<Node> {
	private Node up;
	private Node down;
	private Node left;
	private Node right;
	private Column column;
	private int rowNumber;

	public Node() {
		up = this;
		down = this;
		left = this;
		right = this;
		column = null;
		rowNumber = 0;
	}

	public boolean equals(Node other){
		return rowNumber == other.rowNumber && column == other.column;
	}

	public int hashcode(){
		return rowNumber + column.hashCode();
	}

	@Override
	public String toString() {
		return this.getRowNumber() + "," + this.column.getName();
		//return this.getRowNumber() + "," + this.column.getName();
		//String str = "";
		//for (Node node : this){
		//	str += node.column.getName() + ","
		//		+ node.getRowNumber();
		//}
		//return str;
	}

	@Override
	public java.util.Iterator<Node> iterator(){
		return new java.util.Iterator<Node>(){
			private Node current = Node.this;

			@Override
			public boolean hasNext(){
				return current.getR() != Node.this;
			}
			@Override
			public Node next(){
				if (!hasNext()) {
					throw new java.util.NoSuchElementException();
				}
				current = current.getR();
				return current;
			}
		};
	}

	public int getRowNumber(){
		return this.rowNumber;
	}

	public void setRowNumber(int n){
		this.rowNumber = n;
	}

	public Column getColumn(){
		return this.column;
	}

	public void setColumn(Column column){
		this.column = column;
	}

	public Node getR(){
		return this.right;
	}

	public Node getD(){
		return this.down;
	}

	public Node getL(){
		return this.left;
	}

	public Node getU(){
		return this.up;
	}

	void removeHoriz() {
		this.right.left = this.left;
		this.left.right = this.right;
	}

	void removeVert() {
		this.down.up = this.up;
		this.up.down = this.down;
	}
	
	void restoreVert() {
		this.down.up = this;
		this.up.down = this;
	}

	void restoreHoriz() {
		this.right.left = this;
		this.left.right = this;
	}

	//Create an horizontal link between nodes
	public void linkD(Node other) {
		this.down = other;
		other.up = this;
	}

	//Create a vertical link between nodes
	public void linkR(Node other) {
		this.right = other;
		other.left = this;
	}
	void addHoriz(Node other) {
		other.right = this.right;
		other.left = this;
	}

	void addVert(Node other) {
		other.down = this.down;
		other.up = this;
	}
}
