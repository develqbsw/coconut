package sk.qbsw.core.filesystem.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;

/**
 * The Class FileUtils.
 * 
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 2.3.0
 * @since 2.3.0
 *
 */
public class FileUtils
{

	/**
	 * Hide constructor
	 */
	private FileUtils ()
	{
	}

	/**
	 * Generate unique file name by original file name.
	 *
	 * @param originalFileName the original file name
	 * @return the string
	 */
	public static String generateUniqueFileName (String originalFileName)
	{
		String fileExtension = FilenameUtils.getExtension(originalFileName);
		return generateUniqueFileNameByExtension(fileExtension);
	}

	/**
	 * Generate unique file name by extension.
	 *
	 * @param fileExtension the file extension
	 * @return the string
	 */
	public static String generateUniqueFileNameByExtension (String fileExtension)
	{
		String randomFileName = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss.SSS").format(new Date()) + "_" + RandomStringUtils.randomAlphanumeric(5);

		if (fileExtension != null && !fileExtension.isEmpty())
		{
			return String.format("%s.%s", randomFileName, fileExtension);
		}
		else
		{
			return String.format("%s", randomFileName);

		}
	}
}
