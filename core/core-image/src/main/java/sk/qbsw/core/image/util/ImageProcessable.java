package sk.qbsw.core.image.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageReader;

import sk.qbsw.core.image.model.ImageSize;


/**
 * The image processing interface.
 * 
 * @author Tomas Lauro
 *
 * @version 1.17.0
 * @since 1.17.0
 */
public interface ImageProcessable
{
	/**
	 * Encode image to base 64.
	 *
	 * @param imageStream the image stream
	 * @return the string
	 */
	String encodeImageToBase64 (InputStream imageStream);

	/**
	 * Encode image to base 64.
	 *
	 * @param imageBytes the image bytes
	 * @return the string
	 */
	String encodeImageToBase64 (byte[] imageBytes);

	/**
	 * Decode image from base 64 to stream.
	 *
	 * @param base64Image the base 64 image
	 * @return the input stream
	 */
	InputStream decodeImageFromBase64ToStream (String base64Image);

	/**
	 * Generate unique file name.
	 *
	 * @param originalFileName the original file name
	 * @return the string
	 */
	String generateUniqueFileName (String originalFileName);

	/**
	 * Resize image.
	 *
	 * @param originalImage the original image
	 * @param requiredSize the required size
	 * @return the buffered image
	 */
	BufferedImage resizeImage (BufferedImage originalImage, ImageSize requiredSize);

	/**
	 * Calculate image size.
	 *
	 * @param originalSize the original size
	 * @param requiredSize the required size
	 * @return the image size
	 */
	ImageSize calculateImageSize (ImageSize originalSize, ImageSize requiredSize);

	/**
	 * Gets the image reader.
	 *
	 * @param inputStream the input stream
	 * @return the image reader
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	ImageReader getImageReader (InputStream inputStream) throws IOException;

	/**
	 * Write image to output stream.
	 *
	 * @param image the image
	 * @param imageOutputStream the image output stream
	 * @param imageReader the image reader
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void writeImageToOutputStream (BufferedImage image, OutputStream imageOutputStream, ImageReader imageReader) throws IOException;
}
