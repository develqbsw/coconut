package sk.qbsw.core.mapping.converter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * The OffsetDateTime to/from String bidirectional converter.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.0.0
 */
public class OffsetDateTimeStringBidirectionalConverter extends BidirectionalConverter<OffsetDateTime, String>
{
	@Override
	public OffsetDateTime convertFrom (String source, Type<OffsetDateTime> destinationType, MappingContext mappingContext)
	{
		return OffsetDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	@Override
	public String convertTo (OffsetDateTime source, Type<String> destinationType, MappingContext mappingContext)
	{
		return source.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
}
