package sk.qbsw.core.mapping.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * The LocalDate to/from String bidirectional converter.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.0.0
 */
public class LocalDateStringBidirectionalConverter extends BidirectionalConverter<LocalDate, String>
{
	@Override
	public LocalDate convertFrom (String source, Type<LocalDate> destinationType, MappingContext mappingContext)
	{
		return LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
	}

	@Override
	public String convertTo (LocalDate source, Type<String> destinationType, MappingContext mappingContext)
	{
		return source.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
}
