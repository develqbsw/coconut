package sk.qbsw.paypal.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CSimpleDateFormatSafe
{
	public CSimpleDateFormatSafe(String format){
		this.df = new SimpleDateFormat(format);
	}
	
	private SimpleDateFormat df;

	public String format (Date calendar)
	{
		String retVal = "";
		synchronized (df)
		{
			retVal = df.format(calendar);
		}

		return retVal;
	}

}
