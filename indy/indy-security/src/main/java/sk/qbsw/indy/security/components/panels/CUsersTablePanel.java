package sk.qbsw.indy.security.components.panels;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.minis.behavior.mootip.MootipSettings;

import sk.qbsw.indy.security.model.dataprovider.CUsersDataProvider;
import sk.qbsw.indy.security.model.enums.EFlagEnabled;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.User;

public abstract class CUsersTablePanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private CUsersDataProvider dataProvider;



	@SuppressWarnings ({"rawtypes", "unchecked"})
	public CUsersTablePanel (String id, Group group, Boolean enabled, Organization organization)
	{
		super(id);

		List<IColumn<?, ?>> columns = new ArrayList<IColumn<?, ?>>();

		columns.add(new PropertyColumn<User, String>(new StringResourceModel("header.name", null), "name"));
		columns.add(new PropertyColumn<User, String>(new StringResourceModel("header.surname", null), "surname"));
		columns.add(new PropertyColumn<User, String>(new StringResourceModel("header.login", null), "login"));
		columns.add(new AbstractColumn<User, String>(new StringResourceModel("header.role", null))
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem (Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> model)
			{
				User user = model.getObject();
				String groupLabel = "group." + user.getGroups().iterator().next().getCode().toLowerCase();
				cellItem.add(new Label(componentId, new StringResourceModel(groupLabel, null)));
			}
		});

		columns.add(new AbstractColumn<User, Boolean>(new StringResourceModel("header.enabled", null))
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem (Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> model)
			{
				User user = model.getObject();
				cellItem.add(new Label(componentId, new StringResourceModel(EFlagEnabled.parse(user.getFlagEnabled()).getLabel(), null)));
			}
		});

		columns.add(new AbstractColumn<User, String>(new StringResourceModel("header.operations", null))
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem (Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> model)
			{
				User user = model.getObject();
				cellItem.add(new OperationPanel(componentId, user));


			}
		});

		dataProvider = new CUsersDataProvider(group, enabled, organization);
		DataTable table = new DataTable("table", columns, dataProvider, dataProvider.size());
		table.addTopToolbar(new HeadersToolbar(table, null));
		add(table);
	}

	private class OperationPanel extends Panel
	{

		private static final long serialVersionUID = 1L;

		private Image detailImage;
		private Image passwdImage;

		public OperationPanel (String id, final User user)
		{
			super(id);

			MootipSettings mootipSettings = new MootipSettings();
			mootipSettings.setShowDelay(0);
			mootipSettings.setHideDelay(0);
			mootipSettings.setOffsets(new Point(10, 10));

			Link<WebPage> link = new Link<WebPage>("link")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick ()
				{
					navigateToEditPage(user);
				}
			};
			add(link);

			detailImage = new Image("detail", new PackageResourceReference(CUsersTablePanel.class, "detail.png"));
			link.add(detailImage);

			detailImage.add(new AttributeModifier("title", new StringResourceModel("tooltip.detail", null)));

			Link<WebPage> passwdLink = new Link<WebPage>("passwdLink")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick ()
				{
					navigateToPasswordPage(user);
				}
			};
			add(passwdLink);
			passwdImage = new Image("passwd", new PackageResourceReference(CUsersTablePanel.class, "password.png"));
			passwdLink.add(passwdImage);

			passwdImage.add(new AttributeModifier("title", new StringResourceModel("tooltip.passwd", null)));
		}


	}

	public void setForGroup (Group forGroup)
	{
		dataProvider.setGroup(forGroup);
	}

	public void setForFlagEnabled (Boolean enabled)
	{
		dataProvider.setEnabled(enabled);
	}

	protected abstract void navigateToEditPage (User user);

	protected abstract void navigateToPasswordPage (User user);

}
