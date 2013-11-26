package sk.qbsw.android.utils;

/**
 * Class to work with location and conversion of them
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
public class CLocation
{
	/** convert float to 1E6 value uses for set longitude and latitude in google maps
	 *@param numberToConvert float number to format
	 * @return converted int
	 */
	public static int get1E6Number (Double numberToConvert)
	{
		return (int) (numberToConvert * 1E6);

	}
	
	/**
	 * convert position info string which represent degrees.
	 * @param position to convert
	 * @return converted string in HH° MM'' SS' format
	 */
	public static String formatToDegrees(double position){	
		int hours = (int)position%60;
		position-= hours;
		position *= 60;
		int minutes = (int)position%60;
		position-= minutes;
		position *=60;
		int seconds = (int)position%60;
		
		return hours+"° "+minutes+"'' "+seconds+"' ";
	}
}
