/**
 * 
 */
package sk.qbsw.core.pay.base.csob;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class CsobUtils
{
	private static final String SIG_HASH = "SHA256withRSA";
	private static final String CIPHER = "RSA/ECB/PKCS1Padding";
	private static final String CIPHER_PROVIDER = "BC";
	private static final Logger LOGGER = LoggerFactory.getLogger(CsobUtils.class);

	

	public static byte[] signWithPrivateKeyBC (byte[] data, Certificate crt, PrivateKey pkey)
	{
		try
		{
			//ASN1ObjectIdentifier identifier = ContentInfo.signedData;

			Security.addProvider(new BouncyCastleProvider());

			X509Certificate cert = (X509Certificate) crt;
			List certList = new ArrayList();
			certList.add(new X509CertificateHolder(cert.getEncoded()));
			Store certs = new JcaCertStore(certList);

			CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
			JcaSimpleSignerInfoGeneratorBuilder builder = new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setDirectSignature(true);
			gen.addSignerInfoGenerator(builder.build("SHA256withRSA", pkey, cert));
			gen.addCertificates(certs);

			CMSTypedData msg = new CMSProcessableByteArray(data);
			CMSSignedData s = gen.generate(msg, true);

			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			DEROutputStream dOut = new DEROutputStream(bOut);
			dOut.writeObject(s.toASN1Structure().toASN1Primitive());
			dOut.close();
			return bOut.toByteArray();

		}
		catch (IOException | CMSException | CertificateEncodingException | OperatorCreationException e)
		{
			throw new SecurityException(e);
		}
	}

	public static byte[] extractAndVerifyData (byte[] data, Certificate crt)
	{
		try
		{

			Security.addProvider(new BouncyCastleProvider());

			CMSSignedData cms = new CMSSignedData(Base64.getDecoder().decode(data));
			Store store = cms.getCertificates();
			SignerInformationStore signers = cms.getSignerInfos();
			Collection c = signers.getSigners();
			Iterator it = c.iterator();
			while (it.hasNext())
			{
				SignerInformation signer = (SignerInformation) it.next();
				//Collection certCollection = store.getMatches(signer.getSID());
				//Iterator certIt = certCollection.iterator();
				//X509CertificateHolder certHolder = (X509CertificateHolder) certIt.next();
				//X509Certificate cert = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolder);
				X509Certificate cert = (X509Certificate) crt;
				if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(cert)))
				{
					LOGGER.info("CSOB SIGNATURE VERIFIED");
					byte[] bs = (byte[]) cms.getSignedContent().getContent();
					return bs;
				}
			}
			return null;

		}
		catch (CMSException | OperatorCreationException e)
		{
			throw new SecurityException(e);
		}
	}

	
	/**
	 * 
	 * @author martinkovic
	 * @version 1.15.0
	 * @return 
	 * @since 1.15.0 
	 */
	public static byte[] encryptWithPublicKey (byte[] xmlEnvelope, PublicKey publicKey)
	{
		try
		{
			Security.addProvider(new BouncyCastleProvider());

			RSAPublicKey k = (RSAPublicKey) publicKey;
			int modulusLength = (k.getModulus().bitLength() / 8) - 11;

			int offset = 0;
			byte[] data = xmlEnvelope;

			Cipher cipher = Cipher.getInstance(CIPHER, CIPHER_PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			ByteArrayOutputStream ms = new ByteArrayOutputStream();
			while (offset < data.length)
			{

				int length = Math.min(modulusLength, data.length - offset);

				byte[] chunkBuffer = Arrays.copyOfRange(data, offset, offset + length);
				offset += length;
				byte[] enc = cipher.doFinal(chunkBuffer);
				ms.write(enc);
			}
			return ms.toByteArray();

		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | NoSuchProviderException e)
		{
			LOGGER.error("Problem with CSOB encryption. ", e);
			throw new SecurityException(e);
		}

	}

	/**
	 * 
	 * @author martinkovic
	 * @version 1.15.0
	 * @return 
	 * @since 1.15.0 
	 */
	public static byte[] encryptWithPrivateKey (byte[] xmlEnvelope, PrivateKey key)
	{
		//RSA/ECB/PKCS1Padding (1024, 2048)
		//		RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
		//		RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
		try
		{
			Security.addProvider(new BouncyCastleProvider());
			RSAPrivateCrtKey k = (RSAPrivateCrtKey) key;
			int modulusLength = (k.getModulus().bitLength() / 8) - 11;

			int offset = 0;
			byte[] data = xmlEnvelope;

			Cipher cipher = Cipher.getInstance(CIPHER, CIPHER_PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			ByteArrayOutputStream ms = new ByteArrayOutputStream();
			while (offset < data.length)
			{
				int length = Math.min(modulusLength, data.length - offset);

				byte[] chunkBuffer = Arrays.copyOfRange(data, offset, offset + length);
				offset += length;
				byte[] enc = cipher.doFinal(chunkBuffer);
				ms.write(enc);
			}
			return ms.toByteArray();

		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | NoSuchProviderException e)
		{
			LOGGER.error("Problem with CSOB encryption. ", e);
			throw new SecurityException(e);
		}
	}

	/**
	 * @param msg
	 * @param privateVODCert
	 * @author martinkovic
	 * @version 1.15.0
	 * @return 
	 * @since 1.15.0 
	 */
	public static byte[] decryptWithPrivateKey (byte[] msg, PrivateKey privateVODCert)
	{
		byte[] decodedMsg = msg;

		//RSA/ECB/PKCS1Padding (1024, 2048)
		//		RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
		//		RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
		try
		{
			Security.addProvider(new BouncyCastleProvider());
			RSAPrivateCrtKey k = (RSAPrivateCrtKey) privateVODCert;
			int modulusLength = (k.getModulus().bitLength() / 8);

			int offset = 0;
			byte[] data = decodedMsg;

			Cipher cipher = Cipher.getInstance(CIPHER, CIPHER_PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateVODCert);

			ByteArrayOutputStream ms = new ByteArrayOutputStream();
			while (offset < data.length)
			{
				int length = Math.min(modulusLength, data.length - offset);

				byte[] chunkBuffer = Arrays.copyOfRange(data, offset, offset + length);
				offset += length;
				byte[] enc = cipher.doFinal(chunkBuffer);
				ms.write(enc);
			}
			return ms.toByteArray();

		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | NoSuchProviderException e)
		{
			LOGGER.error("Problem with CSOB encryption. ", e);
			throw new SecurityException(e);
		}

	}

	/**
	 * @param msg
	 * @param privateVODCert
	 * @author martinkovic
	 * @version 1.15.0
	 * @return 
	 * @since 1.15.0 
	 */
	public static byte[] decryptWithPublicKey (byte[] msg, PublicKey key)
	{
		byte[] decodedMsg = msg;

		//RSA/ECB/PKCS1Padding (1024, 2048)
		//		RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
		//		RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
		try
		{
			Security.addProvider(new BouncyCastleProvider());
			RSAPublicKey k = (RSAPublicKey) key;
			int modulusLength = (k.getModulus().bitLength() / 8);

			int offset = 0;
			byte[] data = decodedMsg;

			Cipher cipher = Cipher.getInstance(CIPHER, CIPHER_PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, key);

			ByteArrayOutputStream ms = new ByteArrayOutputStream();
			while (offset < data.length)
			{
				int length = Math.min(modulusLength, data.length - offset);

				byte[] chunkBuffer = Arrays.copyOfRange(data, offset, offset + length);
				offset += length;
				byte[] enc = cipher.doFinal(chunkBuffer);
				ms.write(enc);
			}
			return ms.toByteArray();

		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | NoSuchProviderException e)
		{
			LOGGER.error("Problem with CSOB encryption. ", e);
			throw new SecurityException(e);
		}

	}

}
