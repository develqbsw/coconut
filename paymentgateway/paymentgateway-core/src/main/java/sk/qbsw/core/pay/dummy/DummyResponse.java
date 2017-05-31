/**
 * 
 */
package sk.qbsw.core.pay.dummy;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import sk.qbsw.core.pay.base.response.AbstractBankResponse;

/**
 * Object wraps bank responses to our payment
 * 
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0
 *
 */
public class DummyResponse extends AbstractBankResponse {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
