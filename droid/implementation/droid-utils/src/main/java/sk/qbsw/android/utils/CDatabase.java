package sk.qbsw.android.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

/**
 * Class to work with application database
 * @author Michal Lacko
 * @since 3.0
 * @version 0.1.0
 */
public class CDatabase
{

	/**
	 * Method to copy application database file to sdCard !!! THIS IS ONLY FOR DEBUG REASONS IN PRODUCTION THIS MUST BE REMOVED !!!
	 * @param telephoneNumber - number which is called
	 * @param context - applicationContext to call 
	 */
	public static void copyDatabaseToFile (Context applicationContext, String databaseName, File newLocation) throws IOException
	{

		//Open the empty db as the output stream
		File databaseFile = applicationContext.getDatabasePath(databaseName);

		FileInputStream inputStream = new FileInputStream(databaseFile);

		FileOutputStream outputStream = new FileOutputStream(newLocation);

		//create parents if is necessary
		newLocation.getParentFile().mkdirs();


		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ( (length = inputStream.read(buffer)) > 0)
		{
			outputStream.write(buffer, 0, length);
		}

		//Close the streams
		outputStream.flush();
		outputStream.close();
		inputStream.close();

	}
}
