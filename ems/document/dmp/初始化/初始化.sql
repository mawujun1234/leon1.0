delete ems_barcode_maxnum;
delete ems_barcode;
delete ems_order;
delete ems_instorelist;
delete ems_instore;
delete ems_installoutlist;
delete ems_installout;
delete ems_installinlist;
delete ems_installin;
delete ems_adjustlist;
delete ems_adjust;
delete ems_repair;
delete ems_scrap;

------------------------------------
delete ems_area;
delete ems_customer_contact;

delete ems_message;
delete sys_userstore;----用户可访问的仓库
delete ems_store;
delete ems_taskequipmentlist;
delete ems_task;
delete ems_workunit_contact;
delete ems_workunit;
-------------------------------------------
delete ems_equipment;
delete ems_equipmentprod;
delete ems_equipmentsubtype;
delete ems_equipmenttype;
delete ems_brand;
delete ems_pole;
delete ems_supplier_contact;
delete ems_supplier;
delete ems_customer;

delete ems_hitchtype;
delete ems_hitchreasontpl;

delete ems_message;
delete ems_overtime;

------------------------------------
delete sys_datarole_user;
delete sys_datarole;
delete sys_funrole_user;
delete sys_funrole;
delete sys_navigation_funrole;
--sys_navigation 这个事不能删除的，是菜单
delete sys_navigation_user;
delete sys_user;


delete ems_taskequipmentlist;
delete ems_task;
delete ems_geolocation;
delete ems_hitchreasontpl;
delete ems_hitchtype;
delete ems_message;
delete ems_metaversion;


delete REPORT_BUILDDAYREPORT;
delete REPORT_BUILDMONTHREPORT;
commit;



---初始化数据
alter table SYS_USER disable all triggers;
delete from SYS_USER;
commit;
insert into SYS_USER (id, address, email, logindate, name, password, phone, type, username, status)
values ('admin', null, null, null, 'admin', 'admin', null, 0, 'admin', 'Y');
commit;



alter table SYS_NAVIGATION disable all triggers;
delete from SYS_NAVIGATION;
commit;
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('60b66baf-8966-4dc6-bc90-90e83f6295e4', 'Y', '/adjust/NewAdjustApp.jsp', null, '9f1b4ba5-0d1f-44f2-a75d-0fc69071e473', 'aah-aaa', '调拨出库');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('f8db4523-ee8d-4488-88c7-cdf4b9ce7b7b', 'Y', '/adjust/AdjustApp.jsp', null, '9f1b4ba5-0d1f-44f2-a75d-0fc69071e473', 'aah-aac', '调拨单查询');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('ba44722c-adb9-4136-b460-12ef480419c9', 'N', null, null, null, 'aab', '系统管理');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('9a4f1de9-dce5-4b95-9f48-8df90fcc3cf2', 'Y', '/mgr/navigation.jsp', null, 'ba44722c-adb9-4136-b460-12ef480419c9', 'aab-aab', '菜单管理');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('9b22214d-a376-43fb-b75c-440b5168eef6', 'Y', '/store/BarcodeApp.jsp', null, 'd7624421-6323-4bc7-a28f-6f011f771a57', 'aac-aaf', '条码导出');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('1aacef02-7c01-4464-a02b-f164f97ffa2e', 'Y', '/baseinfo/EquipmentTypeApp.jsp', null, 'aeb4c40f-e8d4-414d-b2a1-f7b1dea80aaf', 'aaa-aab', '设备类型');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('aeb4c40f-e8d4-414d-b2a1-f7b1dea80aaf', 'N', null, null, null, 'aaa', '基础信息维护');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('09fcb219-41d7-44b3-a70c-71ef061342d2', 'Y', '/mgr/funRole.jsp', null, 'ba44722c-adb9-4136-b460-12ef480419c9', 'aab-aac', '功能角色');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('2d512aa7-9fa4-4e0c-ae51-2258e476b89e', 'Y', '/baseinfo/StoreApp.jsp', null, 'aeb4c40f-e8d4-414d-b2a1-f7b1dea80aaf', 'aaa-aaa', '库房信息');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('9a4359ea-8050-4f71-b433-e31f19284294', 'Y', '/baseinfo/WorkUnitApp.jsp', null, 'aeb4c40f-e8d4-414d-b2a1-f7b1dea80aaf', 'aaa-aac', '作业单位');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('83293cbe-071f-4902-a15d-9c52120cfe7a', 'Y', '/baseinfo/SupplierApp.jsp', null, 'aeb4c40f-e8d4-414d-b2a1-f7b1dea80aaf', 'aaa-aad', '供应商');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('5ac203f7-8d5b-40ea-8654-e951594db6a8', 'Y', '/baseinfo/CustomerApp.jsp', null, 'aeb4c40f-e8d4-414d-b2a1-f7b1dea80aaf', 'aaa-aae', '客户信息');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('f4912aac-52ae-4f23-a017-c8f495eaccab', 'Y', '/mgr/userApp.jsp', null, 'ba44722c-adb9-4136-b460-12ef480419c9', 'aab-aae', '用户管理');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('d7624421-6323-4bc7-a28f-6f011f771a57', 'N', null, null, null, 'aac', '新设备入库');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('3b3f70b6-621c-4026-b2f5-57e5451fa6cd', 'Y', '/store/InStoreApp.jsp', null, 'd7624421-6323-4bc7-a28f-6f011f771a57', 'aac-aaa', '新设备入库');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('ca8be663-0055-4786-8590-ea788c065be0', 'Y', '/baseinfo/AreaApp.jsp', null, 'aeb4c40f-e8d4-414d-b2a1-f7b1dea80aaf', 'aaa-aaf', '片区管理');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('6424df74-c571-4a10-aa39-d358e9743781', 'N', null, null, null, 'aae', '任务调度管理');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('373d4584-3767-4311-9825-9ab95c031db8', 'Y', '/task/TaskSendApp.jsp', null, '6424df74-c571-4a10-aa39-d358e9743781', 'aae-aaa', '任务下发');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('dd2f0178-bc59-422e-ae96-0af92bc6cc0c', 'Y', '/task/TaskQueryApp.jsp', null, '6424df74-c571-4a10-aa39-d358e9743781', 'aae-aab', '任务查询管理');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('f0861a7e-597e-4886-9a14-b83a37d74150', 'Y', null, null, 'f54d8775-74fa-43c0-b2ff-df5549be60b4', 'aae-aac-aab', 'eeee');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('b96d7f0c-d4f4-42b3-b0be-4db2f200245a', 'Y', '/baseinfo/BrandApp.jsp', null, 'aeb4c40f-e8d4-414d-b2a1-f7b1dea80aaf', 'aaa-aag', '品牌管理');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('763ce28f-13d0-491d-b7c9-ef859fbcc7c1', 'N', null, null, null, 'aaf', '外勤借用');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('fd84ac77-d4a5-4615-adaa-3336198e82ca', 'N', null, null, null, 'aag', '维    修');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('9f1b4ba5-0d1f-44f2-a75d-0fc69071e473', 'N', null, null, null, 'aah', '调    拨');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('43ea18c3-4d58-4ad0-9817-28db7199993c', 'Y', '/install/InstallOutApp.jsp', null, '763ce28f-13d0-491d-b7c9-ef859fbcc7c1', 'aaf-aaa', '外勤借用');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('3f70509f-8ec2-4d6b-b4ba-499eaa99a4e5', 'Y', '/install/InstallInApp.jsp', null, '763ce28f-13d0-491d-b7c9-ef859fbcc7c1', 'aaf-aab', '外勤借用返回');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('a690500f-8b13-4fd9-8385-0fd9c6608092', 'Y', '/repair/NewRepairApp.jsp', null, 'fd84ac77-d4a5-4615-adaa-3336198e82ca', 'aag-aaa', '生成维修单');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('a2e03a62-3d0c-438c-96ef-d9ffc0d11a0c', 'Y', '/repair/MgrRepairApp.jsp', null, 'fd84ac77-d4a5-4615-adaa-3336198e82ca', 'aag-aab', '维修单管理');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('c8786484-7c1b-47a5-8350-98174d23ce1a', 'Y', '/repair/RMgrRepairApp.jsp', null, 'fd84ac77-d4a5-4615-adaa-3336198e82ca', 'aag-aac', '维修单管理(维)');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('a8cd3a69-fcab-40b3-a4b0-5743ac53be46', 'Y', '/store/OrderApp.jsp', null, 'd7624421-6323-4bc7-a28f-6f011f771a57', 'aac-aag', '订单录入');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('40794406-4ac1-4712-a6d6-f825f22b5114', 'Y', '/repair/RepairInApp.jsp', null, 'fd84ac77-d4a5-4615-adaa-3336198e82ca', 'aag-aad', '维修入库(维)');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('d272130c-8d1a-4043-8baf-24a8b465473e', 'Y', '/adjust/AdjustInApp.jsp', null, '9f1b4ba5-0d1f-44f2-a75d-0fc69071e473', 'aah-aad', '调拨入库');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('e25d2f00-41f8-488f-9d97-7c88fb909a7e', 'Y', '/store/OrderQueryApp.jsp', null, 'd7624421-6323-4bc7-a28f-6f011f771a57', 'aac-aah', '订单历史查询');
insert into SYS_NAVIGATION (id, leaf, link, memo, parentid, reportcode, text)
values ('9b918511-5837-4d4f-a4af-7abc87a60a91', 'Y', '/task/OvertimeApp.jsp', null, '6424df74-c571-4a10-aa39-d358e9743781', 'aae-aac', '超时设置');
insert into SYS_NAVIGATION (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('4b59ebbd-c5fa-47e5-8932-1385b0912f1a', 'Y', '/task/HitchReasonTplApp.jsp', null, '6424df74-c571-4a10-aa39-d358e9743781', 'aae-aad', '故障类型/原因模板维护');
insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('019ba77f-8348-46e9-911a-d2786e0b8f36', 'Y', '/store/InStoreQueryApp.jsp', null, 'd7624421-6323-4bc7-a28f-6f011f771a57', 'aac-aai', '入库记录查询');
insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('5458a38a-734d-4952-b24a-b4f32ce09ff7', 'Y', '/install/InOutQueryApp.jsp', null, '763ce28f-13d0-491d-b7c9-ef859fbcc7c1', 'aaf-aac', '外勤借用记录查询');
insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('7291c55a-15ed-4205-959f-2065b0ee54c7', 'N', null, null, null, 'aai', '报表');
insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('c0485251-d40b-49fd-954b-cb9591c53775', 'Y', '/report/PropertyStatusApp.jsp', null, '7291c55a-15ed-4205-959f-2065b0ee54c7', 'aai-aaa', '仓库资产情况');
insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('d90c8adb-35da-45b1-886d-c765e3848384', 'Y', '/report/EquipmentStatusApp.jsp', null, '7291c55a-15ed-4205-959f-2065b0ee54c7', 'aai-aab', '设备状态表');
insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('298b0161-205b-4871-abb3-68c3a6e65983', 'Y', '/report/BuildDayReportApp.jsp', null, '7291c55a-15ed-4205-959f-2065b0ee54c7', 'aai-aaf', '在建工程仓库盘点月报表');
insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('135f608e-3161-4680-8e73-f2f6b4a69359', 'Y', '/report/PatrolTaskMonitorApp.jsp', null, '7291c55a-15ed-4205-959f-2065b0ee54c7', 'aai-aae', '巡检任务监控报表');
	insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('3516a585-b9d3-4b31-bd27-d6c244e26848', 'Y', '/report/RepairTaskMonitorApp.jsp', null, '7291c55a-15ed-4205-959f-2065b0ee54c7', 'aai-aad', '维修任务监控报表');
insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('d3a9d8f7-399d-4f40-b9e8-1c258422c84f', 'Y', '/report/NewInstallTaskMonitorApp.jsp', null, '7291c55a-15ed-4205-959f-2065b0ee54c7', 'aai-aac', '安装任务监控报表');
insert into sys_navigation (ID, LEAF, LINK, MEMO, PARENTID, REPORTCODE, TEXT)
values ('13117a83-edab-4a12-87f4-f5ead730166d', 'Y', '/report/SparepartMonthReportApp.jsp', null, '7291c55a-15ed-4205-959f-2065b0ee54c7', 'aai-aag', '备品备件仓管盘点月报表');


commit;
alter table SYS_NAVIGATION enable all triggers;


alter table EMS_OVERTIME disable all triggers;
delete from EMS_OVERTIME;
commit;
insert into EMS_OVERTIME (id, handling, read)
values ('overtime', 1440, 1440);
commit;
alter table EMS_OVERTIME enable all triggers;








