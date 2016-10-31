package sk.qbsw.core.api.client.jackson.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * Date Serializer to JSon for jackson.
 * 
 * @author Dalibor Rak
 * @version 1.2.0
 * @since 1.2.0
 */
public class CDateJSonSerializer extends JsonSerializer<Date>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object,
	 * org.codehaus.jackson.JsonGenerator,
	 * org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize (Date date, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException
	{
		generator.writeString("" + date.getTime());
	}
}
