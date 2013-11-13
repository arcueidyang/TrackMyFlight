package exception;

/**
 * 
 * @author Richard Yang
 *
 */

public class WrongPredictionFormatException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4212693907474363474L;

	public WrongPredictionFormatException() {
		super();
	}
	
	public WrongPredictionFormatException(String str) {
		super(str);
	}
	
}
