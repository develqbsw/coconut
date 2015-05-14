package sk.qbsw.android.dao.jpa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sk.qbsw.android.CQBSWAndroidLoggerTag;
import sk.qbsw.android.exception.CExceptionHandler;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Helper class which creates/updates our database and provides the DAOs.
 * 
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 */
public abstract class CDatabaseHelper extends OrmLiteSqliteOpenHelper
{
	private final String DATABASE_NAME;
	private final int DATABASE_VERSION;
	private Context context;

	public CDatabaseHelper (Context context, int databaseVersion, String databaseName)
	{
		super(context, databaseName, null, databaseVersion);
		this.context = context;
		this.DATABASE_NAME = databaseName;
		this.DATABASE_VERSION = databaseVersion;

		try
		{

			logFilesInDbFolder("Before copiring database ");
			// creating database from file in assets folder
			createDataBase();
			logFilesInDbFolder("After copiring database ");

		}
		catch (IOException e)
		{
			Log.d(CQBSWAndroidLoggerTag.TAG, " error in copiring database from path:" + DATABASE_NAME + " to path:" + getDbFile().getAbsolutePath() + DATABASE_NAME);
			// database does't exist yet.
			CExceptionHandler.logException(this.getClass(), e);
		}
	}

	@Override
	public void onCreate (SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
	{
		Log.d(CQBSWAndroidLoggerTag.TAG, "Creating database:" + DATABASE_NAME + " Database version:" + DATABASE_VERSION);
	}

	@Override
	public void onUpgrade (SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion)
	{
		Log.d(CQBSWAndroidLoggerTag.TAG, "Upgrading database:" + DATABASE_NAME + " Old database version:" + oldVersion + " New database version:" + newVersion);

		// now is nothing to update because app is new
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase () throws IOException
	{

		boolean dbExist = checkDataBase();

		if (dbExist)
		{
			// do nothing - database already exist
		}
		else
		{
			try
			{
				Log.d(CQBSWAndroidLoggerTag.TAG, "database doesnt exist copiring path:" + DATABASE_NAME + " to path:" + getDbFile().getAbsolutePath() + DATABASE_NAME);
				copyDataBase();

			}
			catch (IOException e)
			{

				throw new Error("Error copying database");

			}

			// open database
			this.getReadableDatabase();
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase ()
	{

		SQLiteDatabase checkDB = null;

		try
		{
			String myPath = getDbFile().getAbsolutePath();
			Log.d(CQBSWAndroidLoggerTag.TAG, "checking database path: " + myPath);
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		}
		catch (SQLiteException e)
		{
			Log.d(CQBSWAndroidLoggerTag.TAG, "database path: " + getDbFile().getAbsolutePath() + " doesn't exists ");
			// database does't exist yet.

		}

		if (checkDB != null)
		{
			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase () throws IOException
	{

		// Open your local db as the input stream
		InputStream myInput = this.context.getAssets().open(DATABASE_NAME);

		// Open the empty db as the output stream
		File databaseFile = new File(getDbFile().getAbsolutePath());
		if (databaseFile.getParentFile().mkdirs() == false)
		{
			throw new IOException("Path to database: " + getDbFile().getAbsolutePath() + " cannot be created");
		}

		OutputStream myOutput = new FileOutputStream(databaseFile);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ( (length = myInput.read(buffer)) > 0)
		{
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	/**
	 * log to console list of database files
	 */
	private void logFilesInDbFolder (String prefix)
	{

		File databaseFolder = getDbFile().getParentFile();

		// get files in log directory
		File[] dbFiles = databaseFolder.listFiles();

		// if databaseFolder isn't directory then listFiles return null
		if (dbFiles != null)
		{
			Log.e(CQBSWAndroidLoggerTag.TAG, prefix + " Files in db path: " + databaseFolder.getAbsolutePath());
			for (File file : dbFiles)
			{
				Log.e(CQBSWAndroidLoggerTag.TAG, prefix + " files in db path: " + file.getAbsolutePath() + " size: " + file.length());
			}

		}
		else
		{
			Log.e(CQBSWAndroidLoggerTag.TAG, prefix + "No files in db path: " + databaseFolder.getAbsolutePath());
		}
	}

	/**
	 * get file of database on file system
	 * @return file of database on file system
	 * // The Android's default system path of your application database. This is
	 * // address of default package defined in androidManifest file
	 */
	public File getDbFile ()
	{
		return context.getDatabasePath(DATABASE_NAME);
	}

}
