package sk.qbsw.indy.base.utils;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * Feedback message filter that accepts messages from WebMarkupContainer and components inside it
 * 
 * @author Tomas Leken
 * @version 1.0.0
 */
public class CFeedbackMessageFilter extends ComponentFeedbackMessageFilter
{

	private static final long serialVersionUID = -7481952798261887365L;

	private WebMarkupContainer container;

	public CFeedbackMessageFilter (WebMarkupContainer container)
	{
		super(container);

		this.container = container;
	}

	@Override
	public boolean accept (FeedbackMessage message)
	{
		return message.getReporter() == container || container.contains(message.getReporter(), true);
	}

}
