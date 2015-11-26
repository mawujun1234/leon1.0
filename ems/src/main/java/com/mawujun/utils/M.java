package com.mawujun.utils;
public final class M {
public static final class Adjust {
	public static final String id="id";
	public static final String status="status";
	public static final String returnStatus="returnStatus";
	public static final String str_out_id="str_out_id";
	public static final String str_out_date="str_out_date";
	public static final String str_out_oper_id="str_out_oper_id";
	public static final String str_in_id="str_in_id";
	public static final String str_in_date="str_in_date";
	public static final String str_in_oper_id="str_in_oper_id";
	public static final String memo="memo";
	public static final String adjustType="adjustType";
	public static final String adjust_id_borrow="adjust_id_borrow";
	public static final String project_id="project_id";
}
public static final class AdjustList {
	public static final String adjust_id="adjust_id";
	public static final String ecode="ecode";
	public static final String isnew="isnew";
	public static final String adjustListStatus="adjustListStatus";
	public static final String indate="indate";
	public static final String isReturn="isReturn";
	public static final String adjustlist_id_returnback="adjustlist_id_returnback";
	public static final String ecode_returnback="ecode_returnback";
	public static final String prod_id="prod_id";
	public static final String id="id";
}
public static final class Area {
	public static final String name="name";
	public static final String memo="memo";
	public static final String workunit_id="workunit_id";
	public static final String workunit_name="workunit_name";
	public static final String id="id";
}
public static final class Brand {
	public static final String id="id";
	public static final String name="name";
	public static final String status="status";
}
public static final class Customer {
	public static final String name="name";
	public static final String type="type";
	public static final String parent_id="parent_id";
	public static final String memo="memo";
	public static final String status="status";
	public static final String id="id";
}
public static final class CustomerContact {
	public static final String contact="contact";
	public static final String phone="phone";
	public static final String mobile="mobile";
	public static final String position="position";
	public static final String email="email";
	public static final String fax="fax";
	public static final String address="address";
	public static final String postcode="postcode";
	public static final String customer_id="customer_id";
	public static final String id="id";
}
public static final class Equipment {
	public static final String ecode="ecode";
	public static final String subtype_id="subtype_id";
	public static final String prod_id="prod_id";
	public static final String brand_id="brand_id";
	public static final String supplier_id="supplier_id";
	public static final String style="style";
	public static final String orderlist_id="orderlist_id";
	public static final String isnew="isnew";
	public static final String memo="memo";
	public static final String status="status";
	public static final String place="place";
	public static final String unitPrice="unitPrice";
	public static final String unitPrice_nav="unitPrice_nav";
	public static final String first_install_date="first_install_date";
	public static final String fisData="fisData";
	public static final String last_installIn_id="last_installIn_id";
	public static final String last_borrow_id="last_borrow_id";
	public static final String last_workunit_id="last_workunit_id";
	public static final String last_install_date="last_install_date";
	public static final String last_task_id="last_task_id";
	public static final String last_pole_id="last_pole_id";
	public static final String currt_task_id="currt_task_id";
}
public static final class EquipmentCycle {
	public static final String ecode="ecode";
	public static final String operateDate="operateDate";
	public static final String operater_id="operater_id";
	public static final String operater_name="operater_name";
	public static final String operateType="operateType";
	public static final String type_id="type_id";
	public static final String target_id="target_id";
	public static final String target_name="target_name";
	public static final String memo="memo";
	public static final String id="id";
}
public static final class EquipmentPole {
	public static final String ecode="ecode";
	public static final String pole_id="pole_id";
	public static final String num="num";
	public static final String inDate="inDate";
	public static final String type="type";
	public static final String type_id="type_id";
	public static final String from_id="from_id";
}
public static final class EquipmentProd {
	public static final String id="id";
	public static final String name="name";
	public static final String status="status";
	public static final String subtype_id="subtype_id";
	public static final String memo="memo";
	public static final String unit="unit";
	public static final String spec="spec";
	public static final String style="style";
	public static final String brand_id="brand_id";
	public static final String quality_month="quality_month";
	public static final String lock_style="lock_style";
	public static final String id_suffix="id_suffix";
}
public static final class EquipmentRepair {
	public static final String ecode="ecode";
	public static final String repair_id="repair_id";
	public static final String num="num";
	public static final String type="type";
	public static final String type_id="type_id";
	public static final String from_id="from_id";
	public static final String inDate="inDate";
}
public static final class EquipmentStore {
	public static final String ecode="ecode";
	public static final String store_id="store_id";
	public static final String num="num";
	public static final String type="type";
	public static final String type_id="type_id";
	public static final String from_id="from_id";
	public static final String inDate="inDate";
}
public static final class EquipmentSubtype {
	/**
	* 这里一般是集合属性，返回的是prodes
	*/
	public static final String prodes="prodes";
	public static final String id="id";
	public static final String name="name";
	public static final String status="status";
	public static final String parent_id="parent_id";
	public static final String memo="memo";
	public static final String leaf="leaf";
}
public static final class EquipmentType {
	/**
	* 这里一般是集合属性，返回的是subtypes
	*/
	public static final String subtypes="subtypes";
	public static final String id="id";
	public static final String name="name";
	public static final String status="status";
	public static final String parent_id="parent_id";
	public static final String memo="memo";
	public static final String leaf="leaf";
}
public static final class EquipmentWorkunit {
	public static final String ecode="ecode";
	public static final String workunit_id="workunit_id";
	public static final String num="num";
	public static final String inDate="inDate";
	public static final String type="type";
	public static final String type_id="type_id";
	public static final String from_id="from_id";
	public static final String project_id="project_id";
}
public static final class Pole {
	public static final String code="code";
	public static final String name="name";
	public static final String province="province";
	public static final String city="city";
	public static final String area="area";
	public static final String address="address";
	public static final String poleType="poleType";
	public static final String longitude="longitude";
	public static final String latitude="latitude";
	public static final String longitude_orgin="longitude_orgin";
	public static final String latitude_orgin="latitude_orgin";
	public static final String lngLatIsTrans="lngLatIsTrans";
	public static final String customer_id="customer_id";
	public static final String area_id="area_id";
	public static final String project_id="project_id";
	public static final String status="status";
	public static final String id="id";
}
public static final class Project {
	public static final String name="name";
	public static final String sname="sname";
	public static final String memo="memo";
	public static final String status="status";
	public static final String id="id";
}
public static final class Store {
	public static final String name="name";
	public static final String type="type";
	public static final String memo="memo";
	public static final String status="status";
	public static final String id="id";
}
public static final class Supplier {
	public static final String id="id";
	public static final String name="name";
	public static final String sname="sname";
	public static final String website="website";
	public static final String memo="memo";
	public static final String status="status";
}
public static final class SupplierContact {
	public static final String contact="contact";
	public static final String phone="phone";
	public static final String mobile="mobile";
	public static final String position="position";
	public static final String email="email";
	public static final String fax="fax";
	public static final String address="address";
	public static final String postcode="postcode";
	public static final String supplier_id="supplier_id";
	public static final String id="id";
}
public static final class WorkUnit {
	public static final String name="name";
	public static final String memo="memo";
	public static final String status="status";
	public static final String loginName="loginName";
	public static final String password="password";
	public static final String id="id";
}
public static final class WorkUnitContact {
	public static final String contact="contact";
	public static final String phone="phone";
	public static final String mobile="mobile";
	public static final String position="position";
	public static final String email="email";
	public static final String fax="fax";
	public static final String address="address";
	public static final String postcode="postcode";
	public static final String workunit_id="workunit_id";
	public static final String id="id";
}
public static final class B2INotify {
	public static final String ecode="ecode";
	public static final String store_id="store_id";
	public static final String borrow_id="borrow_id";
	public static final String workunit_id="workunit_id";
	public static final String pole_id="pole_id";
	public static final String task_id="task_id";
	public static final String createDate="createDate";
	public static final String ishandled="ishandled";
	public static final String id="id";
}
public static final class Borrow {
	public static final String id="id";
	public static final String store_id="store_id";
	public static final String operater="operater";
	public static final String operateDate="operateDate";
	public static final String workUnit_id="workUnit_id";
	public static final String project_id="project_id";
	public static final String status="status";
	public static final String memo="memo";
}
public static final class BorrowList {
	public static final String borrow_id="borrow_id";
	public static final String ecode="ecode";
	public static final String isReturn="isReturn";
	public static final String isnew="isnew";
	public static final String returnDate="returnDate";
	public static final String borrowListType="borrowListType";
	public static final String pole_id="pole_id";
	public static final String installOutType_id="installOutType_id";
	public static final String installOutType_content="installOutType_content";
	public static final String memo="memo";
	public static final String id="id";
}
public static final class InstallIn {
	public static final String id="id";
	public static final String store_id="store_id";
	public static final String operater="operater";
	public static final String operateDate="operateDate";
	public static final String workUnit_id="workUnit_id";
	public static final String type="type";
	public static final String memo="memo";
}
public static final class InstallInList {
	public static final String installIn_id="installIn_id";
	public static final String ecode="ecode";
	public static final String isBad="isBad";
	public static final String isnew="isnew";
	public static final String installInListType="installInListType";
	public static final String installout_id="installout_id";
	public static final String project_id="project_id";
	public static final String id="id";
}
public static final class InstallOut {
	public static final String id="id";
	public static final String store_id="store_id";
	public static final String operater="operater";
	public static final String operateDate="operateDate";
	public static final String workUnit_id="workUnit_id";
	public static final String project_id="project_id";
	public static final String status="status";
	public static final String requestnum="requestnum";
	public static final String memo="memo";
}
public static final class InstallOutList {
	public static final String installOut_id="installOut_id";
	public static final String ecode="ecode";
	public static final String isnew="isnew";
	public static final String installOutListType="installOutListType";
	public static final String pole_id="pole_id";
	public static final String isReturn="isReturn";
	public static final String returnDate="returnDate";
	public static final String installOutType_id="installOutType_id";
	public static final String installOutType_content="installOutType_content";
	public static final String id="id";
}
public static final class InstallOutType {
	public static final String name="name";
	public static final String memo="memo";
	public static final String id="id";
}
public static final class Day_sparepart {
	public static final String daykey="daykey";
	public static final String store_id="store_id";
	public static final String prod_id="prod_id";
	public static final String monthkey="monthkey";
	public static final String todaynum="todaynum";
	public static final String yesterdaynum="yesterdaynum";
	public static final String purchasenum="purchasenum";
	public static final String oldnum="oldnum";
	public static final String installoutnum="installoutnum";
	public static final String repairinnum="repairinnum";
	public static final String scrapoutnum="scrapoutnum";
	public static final String repairoutnum="repairoutnum";
	public static final String borrownum="borrownum";
	public static final String borrowreturnnum="borrowreturnnum";
	public static final String createDate="createDate";
}
public static final class MetaVersion {
	public static final String clasName="clasName";
	public static final String version="version";
}
public static final class Geolocation {
	public static final String sessionId="sessionId";
	public static final String uuid="uuid";
	public static final String loginName="loginName";
	public static final String longitude="longitude";
	public static final String latitude="latitude";
	public static final String radius="radius";
	public static final String direction="direction";
	public static final String speed="speed";
	public static final String distance="distance";
	public static final String loc_time="loc_time";
	public static final String gps_interval="gps_interval";
	public static final String createDate="createDate";
	public static final String id="id";
}
public static final class GpsConfig {
	public static final String id="id";
	public static final String interval="interval";
}
public static final class Message {
	public static final String task_id="task_id";
	public static final String workunit_id="workunit_id";
	public static final String type="type";
	public static final String isNew="isNew";
	public static final String content="content";
	public static final String createDate="createDate";
	public static final String readDate="readDate";
	public static final String id="id";
}
public static final class HandleMethod {
	public static final String name="name";
	public static final String id="id";
}
public static final class HitchReasonTpl {
	public static final String id="id";
	public static final String name="name";
	public static final String tpl="tpl";
	public static final String hitchType_id="hitchType_id";
}
public static final class HitchType {
	public static final String id="id";
	public static final String name="name";
}
public static final class LockEquipment {
	public static final String ecode="ecode";
	public static final String lockType="lockType";
	public static final String type_id="type_id";
	public static final String createDate="createDate";
}
public static final class Overtime {
	public static final String read="read";
	public static final String handling="handling";
	public static final String id="id";
}
public static final class Task {
	public static final String id="id";
	public static final String memo="memo";
	public static final String type="type";
	public static final String createrType="createrType";
	public static final String status="status";
	public static final String pole_id="pole_id";
	public static final String pole_name="pole_name";
	public static final String pole_address="pole_address";
	public static final String workunit_id="workunit_id";
	public static final String workunit_name="workunit_name";
	public static final String customer_id="customer_id";
	public static final String customer_name="customer_name";
	public static final String hitchDate="hitchDate";
	public static final String createDate="createDate";
	public static final String startHandDate="startHandDate";
	public static final String submitDate="submitDate";
	public static final String completeDate="completeDate";
	public static final String hitchType_id="hitchType_id";
	public static final String hitchType="hitchType";
	public static final String hitchReasonTpl_id="hitchReasonTpl_id";
	public static final String hitchReason="hitchReason";
	public static final String handleMethod_id="handleMethod_id";
	public static final String handle_contact="handle_contact";
}
public static final class TaskEquipmentList {
	public static final String task_id="task_id";
	public static final String ecode="ecode";
	public static final String scanDate="scanDate";
	public static final String type="type";
	public static final String installoutType="installoutType";
	public static final String id="id";
}
public static final class Repair {
	public static final String id="id";
	public static final String ecode="ecode";
	public static final String prod_id="prod_id";
	public static final String str_out_id="str_out_id";
	public static final String rpa_id="rpa_id";
	public static final String str_in_id="str_in_id";
	public static final String installIn_id="installIn_id";
	public static final String repair_date="repair_date";
	public static final String workunit_id="workunit_id";
	public static final String task_id="task_id";
	public static final String broken_memo="broken_memo";
	public static final String str_out_oper_id="str_out_oper_id";
	public static final String str_out_date="str_out_date";
	public static final String rpa_user_id="rpa_user_id";
	public static final String rpa_in_oper_id="rpa_in_oper_id";
	public static final String rpa_in_date="rpa_in_date";
	public static final String rpa_out_oper_id="rpa_out_oper_id";
	public static final String rpa_out_date="rpa_out_date";
	public static final String str_in_oper_id="str_in_oper_id";
	public static final String str_in_date="str_in_date";
	public static final String rpa_type="rpa_type";
	public static final String status="status";
	public static final String scrapDate="scrapDate";
	public static final String broken_reson="broken_reson";
	public static final String handler_method="handler_method";
	public static final String memo="memo";
	public static final String send_date="send_date";
	public static final String sendno="sendno";
	public static final String receive_date="receive_date";
	public static final String repairFactory="repairFactory";
}
public static final class Scrap {
	public static final String id="id";
	public static final String ecode="ecode";
	public static final String reason="reason";
	public static final String residual="residual";
	public static final String store_id="store_id";
	public static final String rpa_id="rpa_id";
	public static final String scrpReqOper="scrpReqOper";
	public static final String scrpReqDate="scrpReqDate";
	public static final String operater="operater";
	public static final String operateDate="operateDate";
	public static final String repair_id="repair_id";
	public static final String status="status";
}
public static final class Barcode {
	public static final String ecode="ecode";
	public static final String orderlist_id="orderlist_id";
	public static final String ymd="ymd";
	public static final String type_id="type_id";
	public static final String subtype_id="subtype_id";
	public static final String prod_id="prod_id";
	public static final String brand_id="brand_id";
	public static final String supplier_id="supplier_id";
	public static final String store_id="store_id";
	public static final String isnew="isnew";
	public static final String randomStr="randomStr";
	public static final String createDate="createDate";
	public static final String status="status";
}
public static final class Barcode_MaxNum {
	public static final String subtype_id="subtype_id";
	public static final String prod_id="prod_id";
	public static final String brand_id="brand_id";
	public static final String supplier_id="supplier_id";
	public static final String ymd="ymd";
	public static final String num="num";
	public static final String id="id";
}
public static final class InStore {
	public static final String id="id";
	public static final String store_id="store_id";
	public static final String operater="operater";
	public static final String operateDate="operateDate";
	public static final String memo="memo";
}
public static final class InStoreList {
	public static final String inStore_id="inStore_id";
	public static final String ecode="ecode";
	public static final String isnew="isnew";
	public static final String orderlist_id="orderlist_id";
	public static final String id="id";
}
public static final class Order {
	public static final String orderNo="orderNo";
	public static final String store_id="store_id";
	public static final String orderDate="orderDate";
	public static final String operater="operater";
	public static final String status="status";
	public static final String project_id="project_id";
	public static final String createDate="createDate";
	public static final String supplier_id="supplier_id";
	public static final String orderType="orderType";
	public static final String id="id";
}
public static final class OrderList {
	public static final String order_id="order_id";
	public static final String type_id="type_id";
	public static final String subtype_id="subtype_id";
	public static final String prod_id="prod_id";
	public static final String brand_id="brand_id";
	public static final String unitPrice="unitPrice";
	public static final String quality_month="quality_month";
	public static final String orderNum="orderNum";
	public static final String totalNum="totalNum";
	public static final String printNum="printNum";
	public static final String id="id";
}
public static final class DataRole {
	public static final String text="text";
	public static final String leaf="leaf";
	public static final String memo="memo";
	public static final String parentId="parentId";
	public static final String cls="cls";
	public static final String iconCls="iconCls";
	public static final String checked="checked";
	public static final String id="id";
}
public static final class FunRole {
	public static final String text="text";
	public static final String leaf="leaf";
	public static final String memo="memo";
	public static final String parentId="parentId";
	public static final String cls="cls";
	public static final String iconCls="iconCls";
	public static final String checked="checked";
	public static final String id="id";
}
public static final class Navigation {
	public static final String text="text";
	public static final String link="link";
	public static final String parentId="parentId";
	public static final String leaf="leaf";
	public static final String memo="memo";
	public static final String reportCode="reportCode";
	public static final String cls="cls";
	public static final String iconCls="iconCls";
	public static final String checked="checked";
	public static final String id="id";
}
public static final class User {
	public static final String username="username";
	public static final String password="password";
	public static final String name="name";
	public static final String phone="phone";
	public static final String email="email";
	public static final String address="address";
	public static final String type="type";
	public static final String loginDate="loginDate";
	public static final String status="status";
	public static final String isWorkunit="isWorkunit";
	public static final String sessionId="sessionId";
	public static final String gps_interval="gps_interval";
	public static final String id="id";
}
public static final class UserStore {
	public static final String user_id="user_id";
	public static final String store_id="store_id";
	public static final String look="look";
	public static final String edit="edit";
	public static final String store_name="store_name";
	public static final String store_status="store_status";
	public static final String store_type="store_type";
	public static final String id="id";
}
}
