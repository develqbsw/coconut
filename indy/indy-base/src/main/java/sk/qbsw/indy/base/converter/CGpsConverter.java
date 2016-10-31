package sk.qbsw.indy.base.converter;

/**
 * Converter for gps coordinates
 * 
 * @author Tomas Leken
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class CGpsConverter
{
	/**
	 * Convert from degrees, minutes, seconds to decimal degrees
	 * 
	 * @param degrees
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public static Float convertToDecimalDegrees (String coordinates)
	{
		coordinates = coordinates.replace(" ", "");
		
		Integer degrees = Integer.valueOf(coordinates.substring(0, coordinates.indexOf("°")));
		Integer minutes = Integer.valueOf(coordinates.substring(coordinates.indexOf("°") + 1, coordinates.indexOf("'")));
		Float seconds = Float.valueOf(coordinates.substring(coordinates.indexOf("'") + 1, coordinates.indexOf("\"")));

		Float decimalMinutes = minutes + (seconds / 60);
		Float decimalDegrees = degrees + (decimalMinutes / 60);

		return decimalDegrees;
	}

	/**
	 * Convert from decimal degrees to degrees, minutes, seconds
	 * 
	 * @param decimalDegrees
	 * @return
	 */
	public static String convertFromDecimalDegrees (Float decimalDegrees, Boolean latitute)
	{
		boolean negative = false;

		if (decimalDegrees < 0)
		{
			decimalDegrees *= -1.0f;
			negative = true;
		}

		Integer degrees = decimalDegrees.intValue();
		Float decimal = (float) (decimalDegrees - decimalDegrees.intValue());
		decimal = (float) (Math.round(decimal * 100000.0) / 100000.0);
		Float decimalMinutes = decimal * 60;

		Integer minutes = decimalMinutes.intValue();
		decimal = (float) (decimalMinutes - decimalMinutes.intValue());
		decimal = (float) (Math.round(decimal * 100000.0) / 100000.0);
		Float seconds = decimal * 60;

		seconds = (float) (Math.round(seconds * 1000.0) / 1000.0);

		String gpsLocation = degrees + "° " + minutes + "' " + seconds + "\" ";

		if (latitute)
		{
			gpsLocation = gpsLocation.concat(negative ? "S" : "N");
		}
		else
		{
			gpsLocation = gpsLocation.concat(negative ? "W" : "E");
		}
		
		return gpsLocation;
	}
}
