package sk.qbsw.core.pay.base.csob.model;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Class SporoPayRequest.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CsobPayRequest
{

	private String merchantId;
	private String note;
	private String ss;
	private String vs;
	private String ks;
	private String sum;
	private String merchantBankNo;
	private String merchantAccount;
	/**
	 * url adresa obchodníka na, ktorú má byť klient po zaplatení sumy presmerovaný. K tejto adrese nie je pridávaná žiadna ďalšia informácia, napríklad o úspešnosti platby. Preto by mala obsahovať minimálne identifikátor objednávky.
	 */
	private String urlRedirect;

	public String createCompleteXML ()
	{
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document document = db.newDocument();
			Element zprava = document.createElement("zprava");
			zprava.setAttribute("ofce", "3111");
			document.appendChild(zprava);

			Element obchodnik = document.createElement("obchodnik");
			zprava.appendChild(obchodnik);

			Element id = document.createElement("id");
			id.appendChild(document.createTextNode(merchantId));
			obchodnik.appendChild(id);

			Element urlObchodnika = document.createElement("urlObchodnika");
			urlObchodnika.appendChild(document.createTextNode(urlRedirect));
			obchodnik.appendChild(urlObchodnika);

			Element data = document.createElement("data");
			zprava.appendChild(data);

			Element nProtiucet = document.createElement("nProtiucet");
			nProtiucet.appendChild(document.createTextNode(merchantAccount));
			data.appendChild(nProtiucet);

			Element chKodBankaProti = document.createElement("chKodBankaProti");
			chKodBankaProti.appendChild(document.createTextNode(merchantBankNo));
			data.appendChild(chKodBankaProti);

			Element nCastka = document.createElement("nCastka");
			nCastka.appendChild(document.createTextNode(sum));
			data.appendChild(nCastka);

			Element nKS = document.createElement("nKS");
			nKS.appendChild(document.createTextNode(ks));
			data.appendChild(nKS);

			Element chVS = document.createElement("chVS");
			chVS.appendChild(document.createTextNode(vs));
			data.appendChild(chVS);

			Element nSS = document.createElement("nSS");
			nSS.appendChild(document.createTextNode(ss));
			data.appendChild(nSS);

			Element vchPoleAV1 = document.createElement("vchPoleAV1");
			vchPoleAV1.appendChild(document.createTextNode("#" + merchantId));
			data.appendChild(vchPoleAV1);
			if (note != null)
			{

				String[] noteSplited = note.split(String.format("(?<=\\G.{%1$d})", 35));
				if (noteSplited.length > 3)
				{
					throw new RuntimeException("poznamka je dlhsia ako 105 znakov. CSOB podporuje len 3x35 znakov");
				}
				if (StringUtils.isNotBlank(noteSplited[0]))
				{

					Element vchPoleAV2 = document.createElement("vchPoleAV2");
					vchPoleAV2.appendChild(document.createTextNode(noteSplited[0]));
					data.appendChild(vchPoleAV2);
				}

				if (StringUtils.isNotBlank(noteSplited[1]))
				{
					Element vchPoleAV3 = document.createElement("vchPoleAV3");
					vchPoleAV3.appendChild(document.createTextNode(noteSplited[1]));
					data.appendChild(vchPoleAV3);
				}
				if (StringUtils.isNotBlank(noteSplited[2]))
				{
					Element vchPoleAV4 = document.createElement("vchPoleAV4");
					vchPoleAV4.appendChild(document.createTextNode(noteSplited[2]));
					data.appendChild(vchPoleAV4);
				}
			}

			String stringFromDocument = getStringFromDocument(document);
			return stringFromDocument;

		}
		catch (ParserConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}

	//method to convert Document to String
	public String getStringFromDocument (Document doc)
	{
		try
		{
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(domSource, result);
			return writer.toString();
		}
		catch (TransformerException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId ()
	{
		return merchantId;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId (String merchantId)
	{
		this.merchantId = merchantId;
	}

	/**
	 * @return the note
	 */
	public String getNote ()
	{
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote (String note)
	{
		this.note = note;
	}

	/**
	 * @return the ss
	 */
	public String getSs ()
	{
		return ss;
	}

	/**
	 * @param ss the ss to set
	 */
	public void setSs (String ss)
	{
		this.ss = ss;
	}

	/**
	 * @return the vs
	 */
	public String getVs ()
	{
		return vs;
	}

	/**
	 * @param vs the vs to set
	 */
	public void setVs (String vs)
	{
		this.vs = vs;
	}

	/**
	 * @return the ks
	 */
	public String getKs ()
	{
		return ks;
	}

	/**
	 * @param ks the ks to set
	 */
	public void setKs (String ks)
	{
		this.ks = ks;
	}

	/**
	 * @return the sum
	 */
	public String getSum ()
	{
		return sum;
	}

	/**
	 * @param sum the sum to set
	 */
	public void setSum (String sum)
	{
		this.sum = sum;
	}

	/**
	 * @return the merchantBankNo
	 */
	public String getMerchantBankNo ()
	{
		return merchantBankNo;
	}

	/**
	 * @param merchantBankNo the merchantBankNo to set
	 */
	public void setMerchantBankNo (String merchantBankNo)
	{
		this.merchantBankNo = merchantBankNo;
	}

	/**
	 * @return the merchantAccount
	 */
	public String getMerchantAccount ()
	{
		return merchantAccount;
	}

	/**
	 * @param merchantAccount the merchantAccount to set
	 */
	public void setMerchantAccount (String merchantAccount)
	{
		this.merchantAccount = merchantAccount;
	}

	/**
	 * @return the urlRedirect
	 */
	public String getUrlRedirect ()
	{
		return urlRedirect;
	}

	/**
	 * @param urlRedirect the urlRedirect to set
	 */
	public void setUrlRedirect (String urlRedirect)
	{
		this.urlRedirect = urlRedirect;
	}
}
