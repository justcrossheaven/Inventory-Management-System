package inventoryman;
/**
 * FormatException is an exception that would be thrown
 * when the format of the date is different from the required format,
 * which is ISO8601 format.
 * @author chenh
 *
 */
public class FormatException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Construct an exception object.
	 * @param message The error message.
	 */
	public FormatException(String message) {
		super(message);
	}

}
