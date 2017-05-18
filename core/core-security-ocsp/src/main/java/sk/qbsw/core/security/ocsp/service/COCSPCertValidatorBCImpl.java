package sk.qbsw.core.security.ocsp.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPReqBuilder;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.cert.ocsp.SingleResp;
import org.bouncycastle.cert.ocsp.UnknownStatus;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.security.ocsp.service.sha256.CSHA256CertificateID;
import sun.security.provider.certpath.OCSP.RevocationStatus;
import sun.security.provider.certpath.OCSP.RevocationStatus.CertStatus;

/**
 * OCSPCertificate validator implemented using Bouncy Castle library.
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 */
@SuppressWarnings("restriction")
@Service("ocspbcimpl")
public class COCSPCertValidatorBCImpl implements IOCSPCertValidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.qbsw.core.ocsp.IOCSPCertValidator#generateOCSPRequest(java.security
	 * .cert.X509Certificate, java.math.BigInteger)
	 */
	@Override
	public byte[] generateOCSPRequest(X509Certificate issuerCert, BigInteger serialNumber) {
		if (issuerCert == null) {
			throw new IllegalArgumentException("Issuer certificate cannot be empty");
		}
		if (serialNumber == null) {
			throw new IllegalArgumentException("Certificate to check cannot be empty");
		}

		byte[] retVal = null;

		try {
			// Add provider BC
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			// Generate the id for the certificate
			CertificateID id = new CSHA256CertificateID(issuerCert, serialNumber);

			// request generation
			OCSPReqBuilder gen = new OCSPReqBuilder();
			gen.addRequest(id);

			// create details
			BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
			Extension e = new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, false, new DEROctetString(nonce.toByteArray()));
			gen.setRequestExtensions(new Extensions(e));

			OCSPReq ocspRequest = gen.build();
			retVal = ocspRequest.getEncoded();
		} catch (CertificateEncodingException | OCSPException | IOException e) {
			throw new CSystemException("Cannot generate OCSP request.", e);
		}

		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.qbsw.core.ocsp.IOCSPCertValidator#generateOCSPRequest(java.security
	 * .cert.X509Certificate, java.security.cert.X509Certificate)
	 */
	@Override
	public byte[] generateOCSPRequest(X509Certificate issuerCert, X509Certificate checkCertificate) {
		if (checkCertificate == null) {
			throw new IllegalArgumentException("Certificate to check cannot be empty");
		}

		return this.generateOCSPRequest(issuerCert, checkCertificate.getSerialNumber());
	}

	/**
	 * Gets the certificate status.
	 *
	 * @param resp
	 *            the resp
	 * @return the certificate status
	 */
	private CertStatus getCertificateStatus(SingleResp resp) {
		Object status = resp.getCertStatus();
		if (status == CertificateStatus.GOOD) {
			return RevocationStatus.CertStatus.GOOD;
		} else if (status instanceof RevokedStatus) {
			return RevocationStatus.CertStatus.REVOKED;
		} else if (status instanceof UnknownStatus) {
			return RevocationStatus.CertStatus.UNKNOWN;
		}
		throw new CSystemException("Unable to identify OCSP Certificate status.");
	}

	/**
	 * Gets the OCSP call status.
	 *
	 * @param ocspResponse
	 *            the OCSP response
	 * @return the OCSP status
	 */
	private EOCSPResponseStatus getOCSPStatus(OCSPResp ocspResponse) {
		return EOCSPResponseStatus.valueOf(ocspResponse.getStatus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.qbsw.core.ocsp.IOCSPCertValidator#processResponse(java.io.InputStream)
	 */
	@Override
	public COCSPValidationResult processResponse(InputStream ocspResponseStream) {
		COCSPValidationResult retVal = new COCSPValidationResult();

		try {
			OCSPResp ocspResponse = new OCSPResp(ocspResponseStream);
			EOCSPResponseStatus ocspResponseStatus = getOCSPStatus(ocspResponse);
			retVal.setOcspStatus(ocspResponseStatus);

			if (EOCSPResponseStatus.SUCCESSFUL.equals(ocspResponseStatus)) {
				SingleResp[] responses;
				BasicOCSPResp basicResponse = (BasicOCSPResp) ocspResponse.getResponseObject();
				responses = (basicResponse == null) ? null : basicResponse.getResponses();

				if (responses != null && responses.length == 1) {
					SingleResp resp = responses[0];
					retVal.setCertificateStatus(getCertificateStatus(resp));
				}
				else {
					throw new CSystemException("Too many certificates");
				}
			}
		} catch (OCSPException | IOException e) {
			throw new CSystemException("Unable to process OCSP response.", e);
		}

		return retVal;
	}
}
