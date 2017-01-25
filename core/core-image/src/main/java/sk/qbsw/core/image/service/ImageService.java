package sk.qbsw.core.image.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

import sk.qbsw.core.image.model.ImageSize;

/**
 * The image service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public interface ImageService
{
	/**
	 * Saves the image.
	 *
	 * @param destinationUri the uri
	 * @param originalFileName the original file name
	 * @param inputStream the input stream
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	String saveImage (String destinationUri, String originalFileName, InputStream inputStream) throws IOException;

	/**
	 * Resize image.
	 *
	 * @param destinationUri the destination uri
	 * @param originalFileName the original file name
	 * @param inputStream the input stream
	 * @param requiredSize the required size
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	String resizeImage (String destinationUri, String originalFileName, InputStream inputStream, ImageSize requiredSize) throws IOException;

	/**
	 * Delete image quietly.
	 *
	 * @param fileUri the uri
	 */
	void deleteImageQuietly (String fileUri);

	/**
	 * Open image input stream.
	 *
	 * @param fileUri the string
	 * @return the input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	InputStream openImageInputStream (String fileUri) throws IOException;

	/**
	 * Convert image to byte array.
	 *
	 * @param fileUri the uri
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	byte[] convertImageToByteArray (String fileUri) throws IOException;

	/**
	 * Gets the resource.
	 *
	 * @param location the location
	 * @return the resource
	 */
	Resource getResource (String location);
}
