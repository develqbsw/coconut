package sk.qbsw.core.mapping.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * The LocalDate to/from String bidirectional converter.
 *
 * @author Tomas Lauro
 * 
 * @version 1.0.0
 * @since 1.0.0
 */
public class LocalDateStringBidirectionalConverter extends BidirectionalConverter<LocalDate, String>
{
	/* (non-Javadoc)
	 * @see ma.glasnost.orika.converter.BidirectionalConverter#convertFrom(java.lang.Object, ma.glasnost.orika.metadata.Type)
	 */
	@Override
	public LocalDate convertFrom (String source, Type<LocalDate> destinationType)
	{
		return LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
	}

	/* (non-Javadoc)
	 * @see ma.glasnost.orika.converter.BidirectionalConverter#convertTo(java.lang.Object, ma.glasnost.orika.metadata.Type)
	 */
	@Override
	public String convertTo (LocalDate source, Type<String> destinationType)
	{
		return source.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
}
