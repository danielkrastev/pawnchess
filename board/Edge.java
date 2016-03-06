package board;

public class Edge extends Square {

	public Edge (int column, int row){
		this.column = column;
		this.row = row;
	}
	
	public String toString() {
		if (this.row == 9 || this.row == 0){
			return "===";
		} else {
			return "||";
		}	
	}
}
