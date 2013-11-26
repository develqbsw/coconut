package sk.qbsw.android.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.zip.GZIPOutputStream;


/**
 * Class to work with files
 * @author Michal Lacko
 * @since 7.0
 * @version 0.1
 */
public class CFile
{
	/**
	 * utility to copy files from one file to another
	 * @param sourceFile from which is content copied
	 * @param destFile where is content copied
	 * @throws IOException if any I/O error occurs
	 */
	public static void copyFile (File sourceFile, File destFile) throws IOException
	{
		if (!destFile.exists())
		{
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try
		{
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		}
		finally
		{
			if (source != null)
			{
				source.close();
			}
			if (destination != null)
			{
				destination.close();
			}
		}
	}

	/**
	 * compress file from input file to output file.
	 * @param inFile input file which is input to packaging
	 * @param outFile which is file created after compress
	 * @throws IOException when is error on disc access
	 */
	public static void compressFile (File inFile, File outFile) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(outFile);
		GZIPOutputStream gzos = new GZIPOutputStream(fos);
		
		FileInputStream fin = new FileInputStream(inFile);
		BufferedInputStream in = new BufferedInputStream(fin);
		
		try
		{
			byte[] buffer = new byte[1024];
			int i;
			while ( (i = in.read(buffer)) >= 0)
			{
				gzos.write(buffer, 0, i);
			}
			in.close();
			gzos.close();
		}
		finally
		{
			if (fos != null)
			{
				fos.flush();
				fos.close();
			}
			if (gzos != null)
			{
				gzos.flush();
				gzos.close();
			}
			if (fin != null)
			{
				fin.close();
			}
			if (in != null)
			{
				in.close();
			}
		}

	}
}
