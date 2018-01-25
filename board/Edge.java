package board;

public class Edge extends Field {

	public Edge (int row, int column){
		this.row = row;
		this.column = column;
	}
	
	public String toString() {
		if (this.row == 9 || this.row == 0){
			return "===";
		} else {
			return "||";
		}	
	}
}
