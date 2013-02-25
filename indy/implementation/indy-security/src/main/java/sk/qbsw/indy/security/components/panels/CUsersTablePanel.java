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

import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.indy.security.model.dataprovider.CUsersDataProvider;
import sk.qbsw.indy.security.model.enums.EFlagEnabled;

public abstract class CUsersTablePanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private CUsersDataProvider dataProvider;



	@SuppressWarnings ({"rawtypes", "unchecked"})
	public CUsersTablePanel (String id, CGroup group, Boolean enabled, COrganization organization)
	{
		super(id);

		List<IColumn<?,?>> columns = new ArrayList<IColumn<?,?>>();

		columns.add(new PropertyColumn<CUser, String>(new StringResourceModel("header.name", null), "name"));
		columns.add(new PropertyColumn<CUser, String>(new StringResourceModel("header.surname", null), "surname"));
		columns.add(new PropertyColumn<CUser, String>(new StringResourceModel("header.login", null), "login"));
		columns.add(new AbstractColumn<CUser, String>(new StringResourceModel("header.role", null))
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem (Item<ICellPopulator<CUser>> cellItem, String componentId, IModel<CUser> model)
			{
				CUser user = model.getObject();
				String groupLabel = "group." + user.getGroups().iterator().next().getCode().toLowerCase();
				cellItem.add(new Label(componentId, new StringResourceModel(groupLabel, null)));
			}
		});

		columns.add(new AbstractColumn<CUser, Boolean>(new StringResourceModel("header.enabled", null))
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem (Item<ICellPopulator<CUser>> cellItem, String componentId, IModel<CUser> model)
			{
				CUser user = model.getObject();
				cellItem.add(new Label(componentId, new StringResourceModel(EFlagEnabled.parse(user.getFlagEnabled()).getLabel(), null)));
			}
		});

		columns.add(new AbstractColumn<CUser, String>(new StringResourceModel("header.operations", null))
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem (Item<ICellPopulator<CUser>> cellItem, String componentId, IModel<CUser> model)
			{
				CUser user = model.getObject();
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

		public OperationPanel (String id, final CUser user)
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
			link.add(detailImage = new Image("detail", new PackageResourceReference(CUsersTablePanel.class, "detail.png")));

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
			passwdLink.add(passwdImage = new Image("passwd", new PackageResourceReference(CUsersTablePanel.class, "password.png")));

			passwdImage.add(new AttributeModifier("title", new StringResourceModel("tooltip.passwd", null)));
		}


	}

	public void setForGroup (CGroup forGroup)
	{
		dataProvider.setGroup(forGroup);
	}

	public void setForFlagEnabled (Boolean enabled)
	{
		dataProvider.setEnabled(enabled);
	}

	protected abstract void navigateToEditPage (CUser user);

	protected abstract void navigateToPasswordPage (CUser user);

}
