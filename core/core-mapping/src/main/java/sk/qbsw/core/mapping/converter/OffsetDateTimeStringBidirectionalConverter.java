package sk.qbsw.core.mapping.converter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * The OffsetDateTime to/from String bidirectional converter.
 *
 * @author Tomas Lauro
 * 
 * @version 1.0.0
 * @since 1.0.0
 */
public class OffsetDateTimeStringBidirectionalConverter extends BidirectionalConverter<OffsetDateTime, String>
{
	/* (non-Javadoc)
	 * @see ma.glasnost.orika.converter.BidirectionalConverter#convertFrom(java.lang.Object, ma.glasnost.orika.metadata.Type)
	 */
	@Override
	public OffsetDateTime convertFrom (String source, Type<OffsetDateTime> destinationType)
	{
		return OffsetDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	/* (non-Javadoc)
	 * @see ma.glasnost.orika.converter.BidirectionalConverter#convertTo(java.lang.Object, ma.glasnost.orika.metadata.Type)
	 */
	@Override
	public String convertTo (OffsetDateTime source, Type<String> destinationType)
	{
		return source.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
}
