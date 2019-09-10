package sk.qbsw.core.filesystem.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

/**
 * The Class FileSystemRepositoryImpl.
 *
 * @author farkas.roman
 * @version 02.3.0
 * @since 2.3.0
 */
@Repository
public class FileSystemRepositoryImpl implements FileSystemRepository
{
	@Override
	public void save (String destFilePath, InputStream sourceIS) throws IOException
	{
		try (FileOutputStream destFileOutputStream = FileUtils.openOutputStream(new File(destFilePath)))
		{
			IOUtils.copy(sourceIS, destFileOutputStream);
		}
	}

	@Override
	public InputStream readAsInputStream (String filePath) throws IOException
	{
		return new FileInputStream(filePath);
	}
	
	@Override
	public byte[] readAsByteArray (String filePath) throws IOException
	{	
		InputStream is = readAsInputStream(filePath);
		return IOUtils.toByteArray(is);
	}
}
