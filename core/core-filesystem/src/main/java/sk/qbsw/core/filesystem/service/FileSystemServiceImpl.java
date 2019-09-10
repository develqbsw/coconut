package sk.qbsw.core.filesystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.filesystem.exception.FilesystemError;
import sk.qbsw.core.filesystem.repository.FileSystemRepository;
import sk.qbsw.core.filesystem.util.FileUtils;

import java.io.IOException;
import java.io.InputStream;


/**
 * The Class FileSystemServiceImpl.
 *
 * @author farkas.roman
 * @version 2.3.0
 * @since 2.3.0
 */
@Service
public class FileSystemServiceImpl implements FileSystemService
{
	private FileSystemRepository repository;

	private ResourceLoader resourceLoader;

	@Autowired
	public FileSystemServiceImpl (FileSystemRepository repository, ResourceLoader resourceLoader)
	{
		this.repository = repository;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public String save (String destFileDirectoryPath, String originalFileName, InputStream sourceIS) throws IOException
	{
		String destFileName = FileUtils.generateUniqueFileName(originalFileName);
		String destFilePath = destFileDirectoryPath + destFileName;

		repository.save(destFilePath, sourceIS);

		return destFileName;
	}

	@Override
	public InputStream readAsInputStream (String fileDirectoryPath, String fileName) throws IOException
	{
		String filePath = fileDirectoryPath + fileName;
		return repository.readAsInputStream(filePath);
	}

	@Override
	public byte[] readAsByteArray (String fileDirectoryPath, String fileName) throws IOException
	{
		String filePath = fileDirectoryPath + fileName;
		return repository.readAsByteArray(filePath);
	}

	@Override
	public Resource getResource (String fileDirectoryPath, String fileName) throws CBusinessException
	{
		Resource resource = resourceLoader.getResource("file:" + fileDirectoryPath + fileName);

		if (!resource.exists())
		{
			throw new CBusinessException(FilesystemError.FILE_NOT_FOUND);
		}
		return resource;
	}
}
