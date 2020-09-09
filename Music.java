package inventoryman;
/**
 * This music class is a subclass of Item class that provides a music object.
 * It includes a constructor and an asString method implemented
 * from abstract class "Item". 
 * @author chenh
 *
 */
public class Music extends Item {
	
	/**
	 * Construct a music object.
	 * 
	 * @param creator The creator of the music.
	 * @param title The title of the music.
	 * @param releaseDate The release date of the musci.
	 * @param acquisitionDateStr The acquisiton date of the music.
	 * @param owner The owner of the music.
	 * @param costStr The cost of the music.
	 * @param formatStr The format of the music.
	 * @throws Exception CostException and FormatException. Both are custom exceptions.
	 */
	public Music(String creator, String title, String releaseDate, String acquisitionDateStr,
			String owner, String costStr, String formatStr) throws Exception {
		super(creator, title, releaseDate, acquisitionDateStr, owner, costStr, formatStr);
		stateType = "Music";
	}
	
	/**
	 * @return A string displaying of the book. 
	 */
	public String asString() {
		return ("'" + _title + "' by " + _creator + ", " + _publicationYear + ". (" + 
	_formatStr +  ", " + _owner + ", " +  _acquisitionDateStr + ", " + _costStr + ")");
	}
	
}
