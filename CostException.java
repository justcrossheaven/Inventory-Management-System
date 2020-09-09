package inventoryman;

/**
 * CostException is an exception that would be thrown
 * when the format of the cost is different from the required format.
 * @author chenh
 *
 */
public class CostException extends Exception {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Construct the exception
	 * @param message The error message shown. 
	 */
	public CostException(String message) {
		super(message);
	}
}
