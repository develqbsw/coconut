package sk.qbsw.core.image.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.image.configuration.ImageExtensionConfiguration;
import sk.qbsw.core.image.exception.ImageErrorResponse;
import sk.qbsw.core.image.exception.ImageException;
import sk.qbsw.core.image.model.ImageSize;

/**
 * The image processor.
 *
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@Component
public class ImageProcessor implements ImageProcessable
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcessor.class);

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.ImageProcessable#encodeImageToBase64(java.io.InputStream)
	 */
	@Override
	public String encodeImageToBase64 (InputStream imageStream)
	{
		try
		{
			return encodeImageToBase64(IOUtils.toByteArray(imageStream));
		}
		catch (IOException ex)
		{
			LOGGER.error("The file operation error", ex);
			throw new ImageException(ImageErrorResponse.FILE_OPERATION_ERROR);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.ImageProcessable#encodeImageToBase64(byte[])
	 */
	@Override
	public String encodeImageToBase64 (byte[] imageBytes)
	{
		return Base64.encodeBase64String(imageBytes);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.ImageProcessable#decodeImageFromBase64ToStream(java.lang.String)
	 */
	@Override
	public InputStream decodeImageFromBase64ToStream (String base64Image)
	{
		return new ByteArrayInputStream(Base64.decodeBase64(base64Image));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.ImageProcessable#generateUniqueFileName(java.lang.String, java.lang.String)
	 */
	@Override
	public String generateUniqueFileName (String originalFileName)
	{
		String randomFileName = new SimpleDateFormat("dd.MM.YYYY-HH.mm.ss.SSS").format(new Date()) + "_" + RandomStringUtils.randomAlphanumeric(5);
		String fileExtension = FilenameUtils.getExtension(originalFileName);

		if (fileExtension != null && !fileExtension.isEmpty())
		{
			return String.format("%s.%s", randomFileName, fileExtension);
		}
		else
		{
			return String.format("%s", randomFileName);

		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.util.ImageProcessable#resizeImage(java.awt.image.BufferedImage, sk.qbsw.lomnicagallery.image.model.ImageSize)
	 */
	@Override
	public BufferedImage resizeImage (BufferedImage originalImage, ImageSize requiredSize)
	{
		//calculate new image size
		ImageSize calculatedImageSize = calculateImageSize(new ImageSize(originalImage.getWidth(), originalImage.getHeight()), requiredSize);

		//get image type
		int imageType = (originalImage.getType() == BufferedImage.TYPE_CUSTOM) ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		BufferedImage resizedImage = new BufferedImage(calculatedImageSize.getWidth(), calculatedImageSize.getHeight(), imageType);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, calculatedImageSize.getWidth(), calculatedImageSize.getHeight(), null);
		g.dispose();

		return resizedImage;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.util.ImageProcessable#calculateImageSize(sk.qbsw.lomnicagallery.image.model.ImageSize, sk.qbsw.lomnicagallery.image.model.ImageSize)
	 */
	@Override
	public ImageSize calculateImageSize (ImageSize originalSize, ImageSize requiredSize)
	{
		if (originalSize.getWidth() == 0 || originalSize.getHeight() == 0 || //
			(requiredSize.getWidth() == 0 && requiredSize.getHeight() == 0))
		{
			throw new ImageException(ImageErrorResponse.INVALID_PARAMETERS);
		}

		//if the image is smaller than requested size, return original
		if (originalSize.getWidth() <= requiredSize.getWidth() || originalSize.getHeight() <= requiredSize.getHeight())
		{
			return originalSize;
		}

		//calculate rates
		float rateX = (float) requiredSize.getWidth() / (float) originalSize.getWidth();
		float rateY = (float) requiredSize.getHeight() / (float) originalSize.getHeight();

		//create new size
		if (Float.compare(rateX, 0f) == 0 || (Float.compare(rateY, 0f) != 0 && rateY < rateX))
		{
			return new ImageSize((int) (originalSize.getWidth() * rateY), (int) (originalSize.getHeight() * rateY));
		}
		else
		{
			return new ImageSize((int) (originalSize.getWidth() * rateX), (int) (originalSize.getHeight() * rateX));
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.ImageProcessable#getImageReader(java.io.InputStream)
	 */
	@Override
	public ImageReader getImageReader (InputStream inputStream) throws IOException
	{
		// Create an image input stream on the image
		ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
		if (imageInputStream == null)
		{
			throw new CSystemException("Can not create imageInputStream", ImageErrorResponse.FILE_OPERATION_ERROR);
		}

		// Find all image readers that recognize the image format
		Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInputStream);
		if (!iter.hasNext())
		{
			throw new CSystemException("The image readers not found", ImageErrorResponse.INVALID_FILE_TYPE);
		}

		ImageReader reader = iter.next();
		reader.setInput(imageInputStream);

		return reader;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.image.ImageProcessable#writeImageToOutputStream(java.awt.image.BufferedImage, java.io.OutputStream, javax.imageio.ImageReader)
	 */
	@Override
	public void writeImageToOutputStream (BufferedImage image, OutputStream imageOutputStream, ImageReader imageReader) throws IOException
	{
		if (isProcessingJpgImage(imageReader))
		{
			ImageIO.write(setWhiteBackGroundToImage(image), imageReader.getFormatName(), imageOutputStream);
		}
		else
		{
			ImageIO.write(image, imageReader.getFormatName(), imageOutputStream);
		}
	}

	/**
	 * Sets the white back ground to image.
	 *
	 * @param originalImage the original image
	 * @return the buffered image
	 */
	private BufferedImage setWhiteBackGroundToImage (BufferedImage originalImage)
	{
		BufferedImage image = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		image.createGraphics().drawImage(originalImage, 0, 0, Color.WHITE, null);

		return image;
	}

	/**
	 * Checks if is processing jpg image.
	 *
	 * @param imageReader the image reader
	 * @return true, if is processing jpg image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private boolean isProcessingJpgImage (ImageReader imageReader) throws IOException
	{
		return imageReader.getFormatName().equalsIgnoreCase(ImageExtensionConfiguration.JPEG_TYPE_EXTENSION) || imageReader.getFormatName().equalsIgnoreCase(ImageExtensionConfiguration.JPG_TYPE_EXTENSION);
	}
}
