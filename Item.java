package inventoryman;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This Item abstract class provides some common fields and methods that need to be tracked
 * during the implementation of the InventoryMan. 
 * It has two subclasses: Book and Music
 * Fields include creator, title, publication year, 
 * acquisition year, owner, cost, format and type of item. It also includes a method to construct
 * a comparator according to different use. There will also be an abstract method "asString" as both 
 * subclasses Book and Music would use it differently.
 * @author chenh
 *
 */
public abstract class Item {
	
	//Below are fields and states that need to be tracked.
	protected String _creator;
	protected String _title;
	protected String _publicationYear; //or releaseDate
	protected String _acquisitionDateStr;
	protected String _owner;
	protected String _costStr;
	protected String _formatStr;
	protected List<Item> _ItemList = new ArrayList<Item>();
	protected String stateType;
	
	
	/**
	 * Constructor creating an Item "object".
	 * Actually provides an general means of constructor of creating Book and Music class.
	 * Throws two custom exceptions inside of it.
	 * 
	 * @param creator The creator of the item.
	 * @param title The title of the item.
	 * @param publicationYear The publicationYear or the realeaseDate of the item.
	 * @param acquisitionDateStr The acquisiton date of the item.
	 * @param owner The owner of the item.
	 * @param costStr The cost of the item.
	 * @param formatStr The format of the item.
	 * @throws Exception CostException and FormatException. Both are custom exceptions.
	 */
		public Item(String creator, String title, String publicationYear, String acquisitionDateStr, String owner, String costStr, String formatStr) throws Exception {
		
		int year = Integer.parseInt(acquisitionDateStr.substring(0, 3));
		int month = Integer.parseInt(acquisitionDateStr.substring(5, 6));
		int date = Integer.parseInt(acquisitionDateStr.substring(8, 9));
		char symbol = costStr.charAt(0);
		String value = costStr.substring(costStr.indexOf('.'), costStr.length()-1);
		
		//Below are conditions of two custom exceptions throwing.
		
		if(acquisitionDateStr.length() != 10 || acquisitionDateStr.charAt(4)!= '-' || acquisitionDateStr.charAt(7)!='-'
				|| (year<0 || year>9999)||(month<0||month>99)||(date<0||date>99)) {
			throw new FormatException("ERROR");
		}
		if(symbol!='$'||costStr.indexOf('.')<1||(value.length()!=2)) {
			throw new CostException("ERROR");
		}
		_creator = creator;
		_title = title;
		_publicationYear = publicationYear;
		_owner = owner;
		_acquisitionDateStr = acquisitionDateStr;
		_costStr = costStr;
		_formatStr = formatStr;
	}
	
	/**
	 * An abstract method since it is used in both book and music and have different implementation.
	 * 
	 * @return A string, according to the type of item (either book or music) to return different display string.
	 */
	abstract String asString();
	
	/**
	 * 
	 * @param state An Enum type to switch the state to make different comparisons.
	 * @return a Comparator<Item> to make comparisons according to different state.
	 */
	public Comparator<Item> sorting(State state) {
		switch(state) {
		case CREATOR:
			return new Comparator<Item>(){
				@Override
				public int compare(Item I1, Item I2) {
					return I1._creator.compareTo(I2._creator);
				}
			};
		case TITLE:
			return new Comparator<Item>(){
				@Override
				public int compare(Item I1, Item I2) {
					return I1._title.compareTo(I2._title);
				}
			};
		case ACQUISITION:
			return new Comparator<Item>(){
				@Override
				public int compare(Item I1, Item I2) {
					return I1._acquisitionDateStr.compareTo(I2._acquisitionDateStr);
				}
			};
			default:
				return null;
		}
	}
}
