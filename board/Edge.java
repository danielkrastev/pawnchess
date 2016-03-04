package board;

public class Edge extends Square {

	public Edge (int column, int row){
		this.column = column;
		this.row = row;
	}
	
	public String toString() {
		return "#";
	}
}
