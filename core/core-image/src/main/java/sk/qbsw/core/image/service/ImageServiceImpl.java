package sk.qbsw.core.image.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import sk.qbsw.core.image.model.ImageSize;
import sk.qbsw.core.image.repository.ImageRepository;

/**
 * The image service implementation.
 *
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@Service
public class ImageServiceImpl implements ImageService, ResourceLoaderAware
{
	/** The image repository. */
	@Autowired
	private ImageRepository imageRepository;

	/** The resource loader. */
	private ResourceLoader resourceLoader;

	/* (non-Javadoc)
	 * @see org.springframework.context.ResourceLoaderAware#setResourceLoader(org.springframework.core.io.ResourceLoader)
	 */
	@Override
	public void setResourceLoader (ResourceLoader resourceLoader)
	{
		this.resourceLoader = resourceLoader;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.service.ImageService#saveImage(java.lang.String, java.lang.String, java.io.InputStream)
	 */
	@Override
	public String saveImage (String destinationUri, String originalFileName, InputStream inputStream) throws IOException
	{
		return imageRepository.saveImage(destinationUri, originalFileName, inputStream);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.service.ImageService#resizeImage(java.lang.String, java.lang.String, java.io.InputStream, sk.qbsw.lomnicagallery.image.model.ImageSize)
	 */
	@Override
	public String resizeImage (String destinationUri, String originalFileName, InputStream inputStream, ImageSize requiredSize) throws IOException
	{
		return imageRepository.resizeImage(destinationUri, originalFileName, inputStream, requiredSize);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.service.ImageService#deleteImageQuietly(java.lang.String)
	 */
	@Override
	public void deleteImageQuietly (String fileUri)
	{
		imageRepository.deleteImageQuietly(fileUri);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.service.ImageService#openImageInputStream(java.lang.String)
	 */
	@Override
	public InputStream openImageInputStream (String fileUri) throws IOException
	{
		return imageRepository.openImageInputStream(fileUri);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.service.ImageService#convertImageToByteArray(java.lang.String)
	 */
	@Override
	public byte[] convertImageToByteArray (String fileUri) throws IOException
	{
		return imageRepository.convertImageToByteArray(fileUri);
	}

	@Override
	public Resource getResource (String location)
	{
		return resourceLoader.getResource(location);
	}
}
