import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Column extends Node {
	private int size;
	private String name;
	private boolean isCovered;

	public Column() {
		super();
		this.setColumn(this);
		size = 0;
		name = new String();
		this.isCovered = false;
	}

	public Column(int length) {
		this();
		Column currColumn = this;
		for(int i = 0; i < length; i++){
			Column nextColumn = new Column();
			currColumn.linkR(nextColumn);
			currColumn = nextColumn;
			currColumn.setName("" + i);
		}
		currColumn.linkR(this);
	}

	public Column(int[][] matrix) throws Exception {
		this(matrix[0].length);
		for(int i = 0; i < matrix.length; i++){
			this.addRow(matrix[i]);
		}
	}

	public void addRow(int[] vector) throws Exception {
		Column currColumn = this;
		this.setRowNumber(this.getRowNumber() + 1);

		Node firstNode = new Node();
		Node currNode = firstNode;
		Node prevNode = currNode;

		for(int index=0; index < vector.length; index++){
			currColumn = currColumn.getR();
			currColumn.setRowNumber(this.getRowNumber() + 1);
			if(vector[index] == 0) continue;

			currColumn.increment();

			currColumn.getU().linkD(currNode);
			currNode.linkD(currColumn);
			currNode.setRowNumber(this.getRowNumber() - 1);
			currNode.setColumn(currColumn);

			prevNode = currNode;
			currNode = new Node();
			prevNode.linkR(currNode);
		}
		currColumn = currColumn.getR();
		prevNode.linkR(firstNode);
		if(currColumn != this){
			throw new Exception("Differ in length");
		}
	}

	@Override
	public Column getR(){
		return (Column) super.getR();
	}

	@Override
	public Column getL(){
		return (Column) super.getL();
	}

	@Override
	public String toString(){
		if (this.isCovered) return "Column " + getName()  + " is covered!";
		if (this.getName() != "") return this.getName();

		String str = "";
		for (Node currColumn : this) {
			str += ((Column) currColumn).getName();
		}
		return str;
	}

	public String getName(){
		return this.name;
	}

	public int getSize(){
		return this.size;
	}

	public void setSize(int size){
		this.size = size;
	}

	public void setName(String name){
		this.name = name;
	}

	public void increment(){
		this.size++;
	}

	public void decrement(){
		this.size--;
	}

	public void cover(){
		this.isCovered = true;
		this.removeHoriz();
		for (Node x = this.getD(); x != this; x = x.getD()) {
			for (Node y = x.getR(); y != x; y = y.getR()) {
				y.removeVert();
				y.getColumn().size--;
			}
		}
	}

	public void uncover(){
		for (Node x = this.getU(); x != this; x = x.getU()) {
			for (Node y = x.getL(); y != x; y = y.getL()) {
				y.restoreVert();
				y.getColumn().size++;
			}
		}
		this.restoreHoriz();
		this.isCovered = false;
	}

	// Searches the column with the least amount of possible cover nodes
	public Column leastCovers() {
		Column argmin = this.getR();

		for (Node currColumn : this){
			if (((Column) currColumn).getSize() < argmin.getSize()) {
				argmin = (Column) currColumn;
			}
		}
		return argmin;
	}

	// Return a set of all Solutions to the problem,
	// where each solution comprises a set of nodes
	HashSet<HashSet<Node>> exactCover() {
		HashSet<HashSet<Node>> setOfSolutions =
		   	new HashSet<HashSet<Node>>();

		if (isSolved()) return setOfSolutions;
		//System.out.println(this);
		Column best = leastCovers();
		//System.out.println(best);
		best.cover();
		//System.out.println(best);

		for (Node row = best.getD(); row != best; row = row.getD()) {
			for (Node currNode : row) {
				currNode.getColumn().cover();
			}
			if (isSolved()) {
				HashSet<Node> solution = new HashSet<Node>();
				solution.add(row);

				setOfSolutions.add(solution);
				for (Node currNode = row.getL(); currNode != row; currNode = currNode.getL()) {
					currNode.getColumn().uncover();
				}

				best.uncover();
				return setOfSolutions;
			}

			HashSet<HashSet<Node>> recursionSolutions = exactCover();
			if (!recursionSolutions.isEmpty()) {
				for (HashSet<Node> solution : recursionSolutions) {
					solution.add(row);
				}
				setOfSolutions.addAll(recursionSolutions);
			}

			for (Node currNode = row.getL(); currNode != row; currNode = currNode.getL()) {
				currNode.getColumn().uncover();
			}
		}
		best.uncover();
		return setOfSolutions;
	}

	// Verifies if problem is solved
	boolean isSolved() {
		return this.getR() == this;
	}
}
