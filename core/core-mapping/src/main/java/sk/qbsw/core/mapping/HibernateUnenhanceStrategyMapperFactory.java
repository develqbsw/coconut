package sk.qbsw.core.mapping;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import ma.glasnost.orika.converter.builtin.EnumConverter;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.unenhance.HibernateUnenhanceStrategy;
import sk.qbsw.core.mapping.converter.LocalDateStringBidirectionalConverter;
import sk.qbsw.core.mapping.converter.OffsetDateTimeStringBidirectionalConverter;

/**
 * A factory for creating HibernateUnenhanceStrategyMapper objects.
 * 
 * @author farkas.roman
 * @since 1.18.0
 */
public class HibernateUnenhanceStrategyMapperFactory extends DefaultMapperFactory
{

	/**
	 * Instantiates a new hibernate unenhance strategy mapper factory.
	 */
	protected HibernateUnenhanceStrategyMapperFactory ()
	{
		super(new Builder().unenhanceStrategy(new HibernateUnenhanceStrategy()));

		getConverterFactory().registerConverter(new EnumConverter());
		getConverterFactory().registerConverter(new OffsetDateTimeStringBidirectionalConverter());
		getConverterFactory().registerConverter(new LocalDateStringBidirectionalConverter());
		getConverterFactory().registerConverter(new PassThroughConverter(LocalDate.class));
		getConverterFactory().registerConverter(new PassThroughConverter(OffsetDateTime.class));
		registerConcreteType(Set.class, LinkedHashSet.class);
	}
}
