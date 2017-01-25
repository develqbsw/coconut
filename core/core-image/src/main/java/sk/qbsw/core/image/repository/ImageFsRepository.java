package sk.qbsw.core.image.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.image.model.ImageSize;
import sk.qbsw.core.image.util.ImageProcessable;

/**
 * The image file system repository.
 *
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@Repository
public class ImageFsRepository implements ImageRepository
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageFsRepository.class);

	/** The image processor. */
	@Autowired
	private ImageProcessable imageProcessor;

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.repository.ImageRepository#saveImage(java.lang.String, java.lang.String, java.io.InputStream)
	 */
	@Override
	public String saveImage (String destinationUri, String originalFileName, InputStream inputStream) throws IOException
	{
		//generate new name
		String generatedFileName = imageProcessor.generateUniqueFileName(originalFileName);
		String generatedFileUri = destinationUri + generatedFileName;

		//save image
		saveImage(generatedFileUri, inputStream, null);

		//return new name
		return generatedFileName;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.repository.ImageRepository#resizeImage(java.lang.String, java.lang.String, java.io.InputStream, sk.qbsw.lomnicagallery.image.model.ImageSize)
	 */
	@Override
	public String resizeImage (String destinationUri, String originalFileName, InputStream inputStream, ImageSize requiredSize) throws IOException
	{
		String generatedFileUri = destinationUri + originalFileName;

		saveImage(generatedFileUri, inputStream, requiredSize);

		return originalFileName;
	}

	/**
	 * Save image.
	 *
	 * @param fileUri the uri
	 * @param inputStream the input stream
	 * @param requiredSize the required size
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void saveImage (String fileUri, InputStream inputStream, ImageSize requiredSize) throws IOException
	{
		OutputStream imageOutputStream = null;
		ImageReader imageReader = null;
		try
		{
			//get image
			imageReader = imageProcessor.getImageReader(inputStream);
			BufferedImage originalImage = imageReader.read(0);

			//create new image container
			imageOutputStream = FileUtils.openOutputStream(new File(fileUri));

			//resize image if needed
			BufferedImage imageToSave;
			if (requiredSize != null)
			{
				imageToSave = imageProcessor.resizeImage(originalImage, requiredSize);
			}
			else
			{
				imageToSave = originalImage;
			}

			//set image format
			imageProcessor.writeImageToOutputStream(imageToSave, imageOutputStream, imageReader);
		}
		finally
		{
			if (imageOutputStream != null)
			{
				imageOutputStream.close();
			}
			if (imageReader != null)
			{
				imageReader.dispose();
			}
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.repository.ImageRepository#deleteImageQuietly(java.lang.String)
	 */
	@Override
	public void deleteImageQuietly (String fileUri)
	{
		File fileToDelete = new File(fileUri);

		try
		{
			if (fileToDelete.delete())
			{
				LOGGER.debug("File " + fileUri + " has been deleted");
			}
			else
			{
				LOGGER.debug("File " + fileUri + " cannot be deleted");
			}
		}
		catch (SecurityException ex)
		{
			LOGGER.error("The file can not be deleted - ignores the error", ex);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.repository.ImageRepository#openImageInputStream(java.lang.String)
	 */
	@Override
	public InputStream openImageInputStream (String fileUri) throws IOException
	{
		return FileUtils.openInputStream(new File(fileUri));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.repository.ImageRepository#convertImageToByteArray(java.lang.String)
	 */
	@Override
	public byte[] convertImageToByteArray (String fileUri) throws IOException
	{
		try (InputStream fis = openImageInputStream(fileUri))
		{
			return IOUtils.toByteArray(fis);
		}
	}
}
