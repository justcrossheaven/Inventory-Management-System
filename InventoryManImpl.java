package inventoryman;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Comparator;
import java.util.LinkedHashSet;

/**
 * This InventoryManImpl provides the implementation of all methods
 * provided in the API InventoryMan. 
 * Including: addBook, addMusic, getItemToDisplay, getAll, getItemsAcquiredInYear, 
 * getCreators, and getFlatReport.
 * @author chenh
 *
 */
public class InventoryManImpl implements InventoryMan {
	private List<Item> _ItemList = new ArrayList<Item>();
	private String _flatName;
	
	/**
	 * Create an InventoryManImpl object for the specified flat.
	 * @param flatName The name of the flat whose inventory items are being managed.
	 */
	public InventoryManImpl(String flatName) {
		_flatName = flatName;
	}

	/**
	 * Add an item representing book to the inventory with the specified values.  
	 * @param author The author of the book
	 * @param title The title of the book
	 * @param publicationYear The year the book was published (String with 4 digits)
	 * @param publisher The publisher
	 * @param acquisitionDateStr The date the book was acquired (ISO8601 format)
	 * @param owner The owner of the book 
	 * @param costStr The cost of the book (format "$" dollars "." cents, where dollars is a sequence of 1 or more digits and
	 * cents is always 2 digits)
	 * @param formatStr The format of the book, either "Hardcover" or "Paperback"
	 * @return A string, either "Success" if there are no problems or a string beginning with "ERROR" and the rest of the string
	 * providing some details as to what went wrong.
	 * 
	 */
	public String addBook(String author, String title, String publicationYear, String publisher,
			String acquisitionDateStr, String owner, String costStr, String formatStr) {
		
		Item book = null;
		
		//Catch checked exceptions, including self-created custom exceptions and general exceptions.
		try {
			book = new Book(author, title, publicationYear, acquisitionDateStr, owner, costStr, formatStr, publisher);
		}catch (FormatException e1) {
			return "ERROR" + "Date is wrong!";
		}catch (CostException e2) {
			return "ERROR" + "Cost is wrong!";
		}catch (Exception e) {
			return "ERROR" + "Date or Cost is wrong!";
		}
		_ItemList.add(book);
		return "Success";
	}
	/**
	 * Add an item representing music to the inventory with the specified values.  
	 * @param artist The artist who made the music
	 * @param title The title of the musical piece
	 * @param releaseDateStr The date the piece was released (ISO8601 format)
	 * @param acquisitionDateStr The date the music was acquired (ISO8601 format)
	 * @param owner The owner of the music
	 * @param costStr The cost of the music (format "$" dollars "." cents, where dollars is a sequence of 1 or more digits and
	 * cents is always 2 digits)
	 * @param formatStr The format of the music, either "CD" or "LP"
	 * @return A string, either "Success" if there are no problems or a string beginning with "ERROR" and the rest of the string
	 * providing some details as to what went wrong.
	 */
	
	public String addMusic(String artist, String title, String releaseDateStr, String acquisitionDateStr, 
			String owner, String costStr, String formatStr) {
		Item music = null;
		
		//Catch checked exceptions, including self-created custom exceptions and general exceptions.
		try {
			music = new Music(artist, title, releaseDateStr, acquisitionDateStr, owner, costStr, formatStr);
		}catch (FormatException e1) {
			return "ERROR" + "Date is wrong!";
		}catch (CostException e2) {
			return "ERROR" + "Cost is wrong!";
		}catch (Exception e) {
			return "ERROR" + "Date or Cost is wrong!";
		}
			_ItemList.add(music);
		return "Success";
	}
	
	/**
	 * Provide a string showing the details of the item with the specified details.
	 * The format of the string depends on whether it is a book or music. The formats are:<br>
	 * Book - creator ", '" title "'. (" publication year ", " publisher "). [" format ", " owner ", " acquisition date + ", " cost "]"<br>
	 * Music - "'" title "' by " creator ", " release date ". (" format ", " owner ", " acquisition date ", " cost ")"<br>
	 * 
	 * @param creator The artist or author of the item
	 * @param title The title of the item
	 * @param formatStr The format of the item
	 * @return A string for the details of the specified item.
	 */
	
	public String getItemToDisplay(String creator, String title, String formatStr) {
		for (int i = 0; i< _ItemList.size(); i++) {
			Item CurrentItem = _ItemList.get(i);
			if (formatStr.equals(CurrentItem._formatStr) && CurrentItem._creator.equals(creator) && CurrentItem._title.equals(title)) {
				if (CurrentItem instanceof Book) {
					return ((Book)CurrentItem).asString();
				}else if (CurrentItem instanceof Music) {
					return ((Music)CurrentItem).asString();
				}
			}
		}
		return "ERROR";
	}
	
	/**
	 * Return a list of of all items in the specified order as a list of strings,
	 * where each string provides the details of the item using the same format
	 * as used by {@link #getItemToDisplay(String, String, String)}. 
	 * Possible orders are: Creator - in alphabetical order of author or artist,
	 * Title - in alphabetical order of item title, Acquisition - in date order
	 * that the item was acquired.
	 * @param order The order to list the items
	 * @return A list of strings providing the details of all items in the specified
	 * order.
	 */
	
	public List<String> getAll(String order){
		List<String> result = new ArrayList<String>();
		if(_ItemList.isEmpty()) {
			return result;
		}
		Item C = _ItemList.get(0);
		
		//Use Enum to do different comparison.
		if (order == "Creator") {
			Collections.sort(_ItemList, C.sorting(State.CREATOR));
		}else if (order == "Title") {
			Collections.sort(_ItemList, C.sorting(State.TITLE));
		}else if (order == "Acquisition") {
			Collections.sort(_ItemList, C.sorting(State.ACQUISITION));
		}
		for (int i = 0; i<_ItemList.size(); i++) {
			Item CurrentItem = _ItemList.get(i);
			String current = getItemToDisplay(CurrentItem._creator, CurrentItem._title, CurrentItem._formatStr);
			result.add(current);
		}
		return result;
	}
	
	/**
	 * Return a list of all items acquired in the specified year in order of 
	 * date of acquisition as a list of strings, where each string provides 
	 * the details of the item using the same format
	 * as used by {@link #getItemToDisplay(String, String, String)}. 
	 * @param year The year in which the item should have been acquired
	 * @return The list of items acquired in the year in order of date of acquisition.
	 */
	
	public List<String> getItemsAcquiredInYear(String year){
		List<String> result = new ArrayList<String>();
		List<Item> ItemAc = new ArrayList<Item>();
		
		//Get items with specific acquisition Year.
		for(int i = 0; i<_ItemList.size(); i++) {
			String releaseYear = _ItemList.get(i)._acquisitionDateStr;
			if (releaseYear.indexOf(year) != -1) {
				ItemAc.add(_ItemList.get(i));
			}
		}
		if(_ItemList.isEmpty()) {
			return result;
		}
		Item C = _ItemList.get(0);
		
		//Sort the item list.
		Collections.sort(ItemAc, C.sorting(State.ACQUISITION));
		for(int i = 0; i<ItemAc.size(); i++) {
			Item CurrentItem = ItemAc.get(i);
			String current = getItemToDisplay(CurrentItem._creator, CurrentItem._title, CurrentItem._formatStr);
			result.add(current);
		}
		return result;
	}
	
	/**
	 * Return a list of all creators of items in the inventory in alphabetical
	 * order. If there are multiple items with the same creator then that creator 
	 * should only appear once in the list. 
	 * @return The list of creators in alphabetical order.
	 */
	
	public List<String> getCreators(){
		List<String> result = new ArrayList<String>();
		
		//First get creators for all items including repeated ones.
		for (int i = 0; i<_ItemList.size(); i++) {
			result.add(_ItemList.get(i)._creator);
		}
		
		//Use linked hash set to remove the repeated creator.
		Set<String> set = new LinkedHashSet<String>();
		set.addAll(result);
		result.clear();
		result.addAll(set);
		return result;
	}
	
	/**
	 * Return a report of the items in the inventory. The report is a list of strings
	 * where the <em>first element</em> is the name of the flat, and the remaining elements
	 * are strings representing items. The format of the strings is:<br>
	 * Book - owner ": " creator ", '" title "'. (" format ")"<br>
	 * Music - owner ": '" title "' by " creator " (" format ")"<br>
	 * The order of the items is:
	 * <ol>
	 * <li>
	 * Items are ordered in alphabetical order of their owner.
	 * <li>
	 * Items owned by the same person are ordered with all books coming before all music.
	 * <li>
	 * All books owned by the same person are ordered first by creator, then by title.
	 * <li>
	 * All music owned by the same person are ordered first by creator, then by title.
	 * </ol>
	 * @return A list of items with the format and order specified. 
	 */
	
	public List<String> getFlatReport(){
		List<String> result = new ArrayList<String>();
		result.add(_flatName);
		
		//Sort the item at different order by using else if.
		Collections.sort(_ItemList, new Comparator<Item>() {
			@Override
			public int compare(Item I1, Item I2) {
				if (I1._owner!=I2._owner) {
					return I1._owner.compareTo(I2._owner);
				}else if ((I1 instanceof Book && I2 instanceof Music)||(I1 instanceof Music && I2 instanceof Book)) {
					return I1.stateType.compareTo (I2.stateType);
				}else if (I1._creator!=I2._creator) {
					return I1._creator.compareTo(I2._creator);
				}
				return I1._title.compareTo(I2._title);
			}
		});
		
		//For sorted itemlist, get a report of each elements.
		for (int i = 0; i < _ItemList.size(); i++) {
			Item CurrentItem = _ItemList.get(i);
			String currentString;
			if (CurrentItem instanceof Book) {
				currentString = CurrentItem._owner + ": "+ CurrentItem._creator + ", '" + CurrentItem._title + "'. (" + CurrentItem._formatStr + ")";
			}else {
				currentString = CurrentItem._owner + ": '" + CurrentItem._title + "' by " + CurrentItem._creator + " (" + CurrentItem._formatStr + ")";
			}
			result.add(currentString);
		}
		return result;
	}
}
