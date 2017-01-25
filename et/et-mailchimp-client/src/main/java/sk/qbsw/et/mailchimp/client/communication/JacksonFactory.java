package sk.qbsw.et.mailchimp.client.communication;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The base rest template factory.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public class JacksonFactory
{
	/**
	 * Creates a new Jackson object.
	 *
	 * @return the object mapper
	 */
	public static ObjectMapper createJacksonObjectMapper ()
	{
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

		builder.indentOutput(true);
		builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS, SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);

		return builder.build();
	}

	/**
	 * Instantiates a new jackson factory.
	 */
	private JacksonFactory ()
	{
		//empty
	}
}
