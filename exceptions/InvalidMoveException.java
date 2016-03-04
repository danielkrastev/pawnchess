package exceptions;

public class InvalidMoveException extends Exception {
	
	private static final long serialVersionUID = 1L;//don't know what this is for

	public InvalidMoveException(String message) {
		super(message);
	}
}
