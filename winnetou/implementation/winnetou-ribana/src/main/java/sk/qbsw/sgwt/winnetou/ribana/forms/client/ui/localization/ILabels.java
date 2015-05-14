package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

/**
 * Labels on the client
 * 
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
public interface ILabels extends Constants
{
	public class Factory
	{
		private static ILabels instance;

		public static ILabels getInstance ()
		{
			if (instance == null)
			{
				instance = GWT.create(ILabels.class);
			}

			return instance;
		}
	}

	@Key ("button.add")
	public String button_add ();

	@Key ("button.admins")
	public String button_admins ();

	@Key ("button.approve")
	public String button_approve ();

	@Key ("button.approvedecline")
	public String button_approvedecline ();

	@Key ("button.browse")
	public String button_browse ();

	@Key ("button.cancel")
	public String button_cancel ();

	@Key ("button.decline")
	public String button_decline ();

	@Key ("button.details")
	public String button_details ();

	@Key ("button.help")
	public String button_help ();

	@Key ("button.info")
	public String button_info ();

	@Key ("button.login")
	public String button_login ();

	@Key ("button.new_registration")
	public String button_new_registration ();

	@Key ("button.print")
	public String button_print ();

	@Key ("button.pwd")
	public String button_pwd ();

	@Key ("button.refresh")
	public String button_refresh ();

	@Key ("button.renew_password")
	public String button_renew_password ();

	@Key ("button.revert")
	public String button_revert ();

	@Key ("button.save")
	public String button_save ();

	@Key ("button.search")
	public String button_search ();

	@Key ("button.submit")
	public String button_submit ();

	@Key ("button.timesheet")
	public String button_timesheet ();

	@Key ("button.tree")
	public String button_tree ();

	@Key ("button.unlock")
	public String button_unlock ();

	@Key ("item.abort")
	public String item_abort ();

	@Key ("item.activity")
	public String item_activity ();

	@Key ("item.activity_id")
	public String item_activity_id ();

	@Key ("item.city")
	public String item_city ();

	@Key ("item.client.id_no")
	public String item_client_idno ();

	@Key ("item.client.tax_no")
	public String item_client_taxno ();

	@Key ("item.client.vat_no")
	public String item_client_vatno ();

	@Key ("item.country")
	public String item_country ();

	@Key ("item.created_by")
	public String item_created_by ();

	@Key ("item.date")
	public String item_date ();

	@Key ("item.date.from")
	public String item_date_from ();

	@Key ("item.date.to")
	public String item_date_to ();

	@Key ("item.day.calendar")
	public String item_day_calendar ();

	@Key ("item.day_part")
	public String item_day_part ();

	@Key ("item.day.working")
	public String item_day_working ();

	@Key ("item.email")
	public String item_email ();

	@Key ("item.employee_code")
	public String item_employee_code ();

	@Key ("item.employee")
	public String item_employees ();

	@Key ("item.finish")
	public String item_finish ();

	@Key ("item.flag.activated")
	public String item_flag_activated ();

	@Key ("item.flag.active")
	public String item_flag_active ();

	@Key ("item.flag.hide_inactive")
	public String item_flag_hide_inactive ();

	@Key ("item.flag.main")
	public String item_flag_main ();

	@Key ("item.flag.show_inactive")
	public String item_flag_show_inactive ();

	@Key ("item.flag.working")
	public String item_flag_working ();

	@Key ("item.changed_by")
	public String item_changed_by ();

	@Key ("item.changedatetime")
	public String item_changedatetime ();

	@Key ("item.legacy_form")
	public String item_legacy_form ();

	@Key ("item.level")
	public String item_level ();

	@Key ("item.login_name")
	public String item_login_name ();

	@Key ("item.name")
	public String item_name ();

	@Key ("item.name_surname")
	public String item_name_surname ();

	@Key ("item.note")
	public String item_note ();

	@Key ("item.order")
	public String item_order ();

	@Key ("item.organization_name")
	public String item_organization_name ();

	@Key ("item.password")
	public String item_password ();

	@Key ("item.password_check")
	public String item_password_check ();

	@Key ("item.password_new")
	public String item_password_new ();

	@Key ("item.password_original")
	public String item_password_original ();

	@Key ("item.phase")
	public String item_phase ();

	@Key ("item.phone.fix")
	public String item_phone_fix ();

	@Key ("item.phone.mobile")
	public String item_phone_mobile ();

	@Key ("item.photo")
	public String item_photo ();

	@Key ("item.place")
	public String item_place ();

	@Key ("item.project")
	public String item_project ();

	@Key ("item.project_id")
	public String item_project_id ();

	@Key ("item.request_owner")
	public String item_request_owner ();

	@Key ("item.request.state")
	public String item_request_state ();

	@Key ("item.request.type")
	public String item_request_type ();

	@Key ("item.search.attribute")
	public String item_search_attribute ();

	@Key ("item.search.value")
	public String item_search_value ();

	@Key ("item.start")
	public String item_start ();

	@Key ("item.street")
	public String item_street ();

	@Key ("item.street_number")
	public String item_street_number ();

	@Key ("item.surname")
	public String item_surname ();

	@Key ("item.time")
	public String item_time ();

	@Key ("item.title")
	public String item_title ();

	@Key ("item.tree.move_action")
	public String item_tree_move_action ();

	@Key ("item.user_position")
	public String item_user_position ();

	@Key ("item.user_type")
	public String item_user_type ();

	@Key ("item.zip")
	public String item_zip ();

	@Key ("label.address")
	public String label_address ();

	@Key ("label.basic_info")
	public String label_basic_info ();

	@Key ("label.continue_work")
	public String label_continue_work ();

	@Key ("label.holiday")
	public String label_holiday ();

	@Key ("label.links")
	public String label_links ();

	@Key ("label.login")
	public String label_login ();

	@Key ("label.lost_password")
	public String label_lost_password ();

	@Key ("label.org.address")
	public String label_org_address ();

	@Key ("label.org.details")
	public String label_org_details ();

	@Key ("label.org.manger")
	public String label_org_manager ();

	@Key ("label.outside_workplace")
	public String label_outside_workplace ();

	@Key ("label.paid_free")
	public String label_paid_free ();

	@Key ("label.search.tree.login")
	public String label_serach_tree_login ();

	@Key ("label.search.tree.name")
	public String label_serach_tree_name ();

	@Key ("label.search.tree.surname")
	public String label_serach_tree_surname ();

	@Key ("label.sick_day")
	public String label_sick_day ();

	@Key ("label.superior_details")
	public String label_superior ();

	@Key ("label.system_name")
	public String label_system_name ();

	@Key ("label.treeorg.cancel")
	public String label_treeorg_cancel ();

	@Key ("label.treeorg.move_sub")
	public String label_treeorg_move_sub ();

	@Key ("label.treeorg.move_with_sub")
	public String label_treeorg_move_with_sub ();

	@Key ("label.treeorg.move_wo_sub")
	public String label_treeorg_move_wo_sub ();

	@Key ("label.treeorg.switch")
	public String label_treeorg_switch ();

	@Key ("label.work_trip")
	public String label_work_trip ();

	@Key ("label.agreement")
	public String labelAgreement ();

	@Key ("label.licence")
	public String labelLicence ();

	@Key ("window.menu.minimize")
	public String window_menu_minimize ();

	@Key ("window.menu.restore")
	public String window_menu_restore ();

	@Key ("window.menu.maximize")
	public String window_menu_maximize ();

	@Key ("window.menu.close")
	public String window_menu_close ();
}
