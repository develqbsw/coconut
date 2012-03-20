/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package sk.qbsw.indy.base.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.EnumeratedType;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;

/** Filed for setting date including timezone
 * @author lacko
 *
 */
public class CTimeExtendedField extends FormComponentPanel<Calendar>
{

	/**
	 * Enumerated type for represent type of timeZones
	 */
	private static class EtcTimeZones extends EnumeratedType
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public static List<String> values ()
		{
			return Arrays.asList(new String[] {"Etc/GMT-12", "Etc/GMT-11", "Etc/GMT-10", "Etc/GMT-9", "Etc/GMT-8", "Etc/GMT-7", "Etc/GMT-6", "Etc/GMT-5", "Etc/GMT-4", "Etc/GMT-3", "Etc/GMT-2", "Etc/GMT-1", "Etc/GMT+0", "Etc/GMT+1", "Etc/GMT+2", "Etc/GMT+3", "Etc/GMT+4", "Etc/GMT+5", "Etc/GMT+6", "Etc/GMT+7", "Etc/GMT+8", "Etc/GMT+9", "Etc/GMT+10", "Etc/GMT+11", "Etc/GMT+12"});

		}

		private EtcTimeZones (final String name)
		{
			super(name);
		}
	}

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private TextField<Integer> hoursField;
	private TextField<Integer> minutesField;
	private DropDownChoice<String> timeZomeChoice;

	private Integer hours;
	private Integer minutes;
	private String timeZone;

	private String fieldName;

	public CTimeExtendedField (String id)
	{
		super(id);

		this.fieldName = id;
		init();
	}

	public CTimeExtendedField (String id, IModel<Calendar> model)
	{
		super(id, model);

		this.fieldName = id;
		init();
	}

	public void setFieldName (String fieldName)
	{
		this.fieldName = fieldName;
	}

	private void init ()
	{
		setType(Calendar.class);
		add(hoursField = new TextField<Integer>("hours", new PropertyModel<Integer>(this, "hours"), Integer.class)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void validate ()
			{
				List<ValidationError> errors = checkInputHours();
				if (errors.size() > 0)
				{
					for (ValidationError error : errors)
					{
						error(error);
					}
				}
				else
				{
					if (isValid())
					{
						convertInput();
						if (isValid())
						{
							validateValidators();
						}
					}
				}
			}
		});
		hoursField.add(new RangeValidator<Integer>(0, 23)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void validate (IValidatable<Integer> validatable)
			{
				Integer value = validatable.getValue();
				final Integer min = getMinimum();
				final Integer max = getMaximum();
				if (value.compareTo(min) < 0 || value.compareTo(max) > 0)
				{
					ValidationError error = new ValidationError();
					error.addMessageKey("error.range_hours_error_in_field");
					error.setVariable("fieldName", fieldName);
					error.setVariable("minimum", min);
					error.setVariable("maximum", max);
					validatable.error(error);
				}
			}

		});
		hoursField.setLabel(new Model<String>("hours"));

		add(minutesField = new TextField<Integer>("minutes", new PropertyModel<Integer>(this, "minutes"), Integer.class)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void validate ()
			{
				List<ValidationError> errors = checkInputMinutes();
				if (errors.size() > 0)
				{
					for (ValidationError error : errors)
					{
						error(error);
					}
				}
				else
				{
					if (isValid())
					{
						convertInput();
						if (isValid())
						{
							validateValidators();
						}
					}
				}
			}
		});
		minutesField.add(new RangeValidator<Integer>(0, 59)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void validate (IValidatable<Integer> validatable)
			{
				Integer value = validatable.getValue();
				final Integer min = getMinimum();
				final Integer max = getMaximum();
				if (value.compareTo(min) < 0 || value.compareTo(max) > 0)
				{
					ValidationError error = new ValidationError();
					error.addMessageKey("error.range_minutes_error_in_field");
					error.setVariable("fieldName", fieldName);
					error.setVariable("minimum", min);
					error.setVariable("maximum", max);
					validatable.error(error);
				}
			}

		});
		minutesField.setLabel(new Model<String>("minutes"));

		add(timeZomeChoice = new DropDownChoice<String>("timezone", new PropertyModel<String>(this, "timeZone"), EtcTimeZones.values()));
		// initial one value
		timeZomeChoice.setModelObject("Etc/GMT+1");

		hoursField.setRequired(true);
		minutesField.setRequired(true);
	}

	private List<ValidationError> checkInputHours ()
	{
		List<ValidationError> errors = new ArrayList<ValidationError>();
		String hoursValue = hoursField.getInput();
		if (isRequired())
		{
			if (hoursValue == null || "".equals(hoursValue))
			{
				ValidationError error = new ValidationError();
				error.addMessageKey("error.required_hours_field");
				error.setVariable("fieldName", fieldName);
				errors.add(error);
			}
		}
		if (hoursValue != null && !"".equals(hoursValue))
		{
			try
			{
				Integer.parseInt(hoursValue);
			}
			catch (NumberFormatException e)
			{
				ValidationError error = new ValidationError();
				error.addMessageKey("error.parse_hours_field");
				error.setVariable("fieldName", fieldName);
				errors.add(error);
			}
		}
		return errors;
	}

	private List<ValidationError> checkInputMinutes ()
	{
		List<ValidationError> errors = new ArrayList<ValidationError>();
		String minutesValue = minutesField.getInput();
		if (isRequired())
		{
			if (minutesValue == null || "".equals(minutesValue))
			{
				ValidationError error = new ValidationError();
				error.addMessageKey("error.required_minutes_field");
				error.setVariable("fieldName", fieldName);
				errors.add(error);
			}
		}
		if (minutesValue != null && !"".equals(minutesValue))
		{
			try
			{
				Integer.parseInt(minutesValue);
			}
			catch (NumberFormatException e)
			{
				ValidationError error = new ValidationError();
				error.addMessageKey("error.parse_minutes_field");
				error.setVariable("fieldName", fieldName);
				errors.add(error);
			}
		}
		return errors;
	}

	/**
	 * Sets the converted input, which is an instance of {@link Date}, possibly null. It combines
	 * the inputs of the nested date, hours, minutes and am/pm fields and constructs a date from it.
	 * <p>
	 * Note that overriding this method is a better option than overriding {@link #updateModel()}
	 * like the first versions of this class did. The reason for that is that this method can be
	 * used by form validators without having to depend on the actual model being updated, and this
	 * method is called by the default implementation of {@link #updateModel()} anyway (so we don't
	 * have to override that anymore).
	 * </p>
	 * 
	 * @see org.apache.wicket.markup.html.form.FormComponent#convertInput()
	 */
	@Override
	protected void convertInput ()
	{

		Integer hoursValue = hoursField.getConvertedInput();
		Integer minutesValue = minutesField.getConvertedInput();
		String timeZoneValue = timeZomeChoice.getConvertedInput();

		Calendar calendar = null;

		try
		{

			if ( (hoursValue != null))
			{
				calendar = (calendar == null) ? Calendar.getInstance() : calendar;
				calendar.set(Calendar.HOUR_OF_DAY, hoursValue);
			}
			if ( (minutesValue != null))
			{
				calendar = (calendar == null) ? Calendar.getInstance() : calendar;
				calendar.set(Calendar.MINUTE, minutesValue);
			}
			if ( (timeZoneValue != null))
			{
				calendar = (calendar == null) ? Calendar.getInstance() : calendar;
				calendar.setTimeZone(TimeZone.getTimeZone(timeZoneValue));
			}

		}
		catch (RuntimeException e)
		{
			CTimeExtendedField.this.error(e.getMessage());
			invalid();
		}

		setConvertedInput(calendar);

	}

	/**
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	@Override
	protected void onBeforeRender ()
	{
		hoursField.setRequired(isRequired());
		minutesField.setRequired(isRequired());
		timeZomeChoice.setRequired(isRequired());

		Calendar d = (Calendar) getDefaultModelObject();

		if (d != null)
		{
			timeZone = d.getTimeZone().getID();
			hours = d.get(Calendar.HOUR_OF_DAY);
			minutes = d.get(Calendar.MINUTE);
		}

		super.onBeforeRender();
	}

	public TextField<Integer> getHoursField ()
	{
		return hoursField;
	}

	public void setHoursField (TextField<Integer> hoursField)
	{
		this.hoursField = hoursField;
	}

	public TextField<Integer> getMinutesField ()
	{
		return minutesField;
	}

	public void setMinutesField (TextField<Integer> minutesField)
	{
		this.minutesField = minutesField;
	}

	public DropDownChoice<String> getTimeZomeChoice ()
	{
		return timeZomeChoice;
	}

	public void setTimeZomeChoice (DropDownChoice<String> timeZomeChoice)
	{
		this.timeZomeChoice = timeZomeChoice;
	}

	public Integer getHours ()
	{
		return hours;
	}

	public void setHours (Integer hours)
	{
		this.hours = hours;
	}

	public Integer getMinutes ()
	{
		return minutes;
	}

	public void setMinutes (Integer minutes)
	{
		this.minutes = minutes;
	}

	public String getTimeZone ()
	{
		return timeZone;
	}

	public void setTimeZone (String timeZone)
	{
		this.timeZone = timeZone;
	}



}
