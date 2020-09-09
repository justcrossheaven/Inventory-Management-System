package inventoryman;

/**
 * This book class is a subclass of Item class that provides a book object.
 * It includes a constructor, setPublisher method and an asString method implemented
 * from abstract class "Item". 
 * @author chenh
 *
 */
public class Book extends Item {
	
	//Publisher is a new and unique state for book.
	protected String _publisher;
	
	/**
	 * Constructs a Book object.
	 * 
	 * @param creator The creator of the book.
	 * @param title The title of the book.
	 * @param publicationYear The publication year of the book.
	 * @param acquisitionDateStr The acquisiton date of the book.
	 * @param owner The owner of the book.
	 * @param costStr The cost of the book.
	 * @param formatStr The format of the book.
	 * @param publisher The publisher of the book.
	 * @throws Exception CostException and FormatException. Both are custom exceptions.
	 */
	public Book(String creator, String title, String publicationYear, String acquisitionDateStr,
			String owner, String costStr, String formatStr, String publisher) throws Exception {
		super(creator, title, publicationYear, acquisitionDateStr, owner, costStr, formatStr);
		this.setPublisher(publisher);
		stateType = "Book";
	}
	/**
	 * Set the publisher of the book.
	 * @param publisher
	 */
	public void setPublisher(String publisher) {
		this._publisher = publisher;
	}
	
	/**
	 * @return A string displaying of the book. 
	 */
	public String asString() {
		return (_creator + ", '" + _title + "'. (" + _publicationYear + ", " + _publisher 
				+ "). [" + _formatStr + ", " + _owner + ", " + _acquisitionDateStr + ", " + _costStr + "]");
	}
}
