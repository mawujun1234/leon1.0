INSERT INTO leon_fun(ID, BUSSINESSTYPE, HELPCONTENT, ISENABLE, TEXT, URL) VALUES ('panera_customerProperty_manager', null, '', 'Y', '客户属性', '/panera/customerProperty/CustomerPropertyApp.jsp');
INSERT INTO leon_fun(ID, BUSSINESSTYPE, HELPCONTENT, ISENABLE, TEXT, URL) VALUES('panera_customersource_manager', null, '', 'Y', '客户来源管理', '/panera/customerSource/CustomerSourceApp.jsp');
INSERT INTO leon_fun(ID, BUSSINESSTYPE, HELPCONTENT, ISENABLE, TEXT, URL) VALUES('panera_country_manager', null, '', 'Y', '国家管理', '/panera/continents/CountryApp.jsp');
INSERT INTO leon_fun(ID, BUSSINESSTYPE, HELPCONTENT, ISENABLE, TEXT, URL) VALUES('panera_customer_manager', null, '', 'Y', '客户管理', '/panera/customer/CustomerApp.jsp');
INSERT INTO leon_fun(ID, BUSSINESSTYPE, HELPCONTENT, ISENABLE, TEXT, URL) VALUES('panera_customer_remind', null, '', 'Y', '跟进计划提醒', '/panera/customer/Remind.js');
INSERT INTO leon_fun(ID, BUSSINESSTYPE, HELPCONTENT, ISENABLE, TEXT, URL) VALUES('panera_customer_report', null, '', 'Y', '客户统计', '/panera/customer/Report.jsp');


INSERT INTO leon_menuitem(ID, CODE, ICONCLS, ICONCLS32, JAVACLASS, LEAF, REPORTCODE, SCRIPTS, TEXT, FUN_ID, MENU_ID, PARENT_ID, AUTOSTART) VALUES
('2c9083844664ed04014664f80bb80000', '', 'menu-category-default-16', 'menu-category-default-32', '', 'N', 'aad', '','基础信息维护', NULL, 'default', NULL, 'N');
INSERT INTO leon_menuitem(ID, CODE, ICONCLS, ICONCLS32, JAVACLASS, LEAF, REPORTCODE, SCRIPTS, TEXT, FUN_ID, MENU_ID, PARENT_ID, AUTOSTART) VALUES
('2c9083844664ed04014664f8bcd30001', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aad-aaa', '','客户来源', 'panera_customersource_manager', 'default', '2c9083844664ed04014664f80bb80000', NULL);
INSERT INTO leon_menuitem(ID, CODE, ICONCLS, ICONCLS32, JAVACLASS, LEAF, REPORTCODE, SCRIPTS, TEXT, FUN_ID, MENU_ID, PARENT_ID, AUTOSTART) VALUES
('2c9083844673eae3014673f187550000', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aad-aac', '','国家管理', 'panera_country_manager', 'default', '2c9083844664ed04014664f80bb80000', NULL);
INSERT INTO leon_menuitem(ID, CODE, ICONCLS, ICONCLS32, JAVACLASS, LEAF, REPORTCODE, SCRIPTS, TEXT, FUN_ID, MENU_ID, PARENT_ID, AUTOSTART) VALUES
('2c90838446eab7800146eb3892030001', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aad-aaf', '','跟进计划提醒', 'panera_customer_remind', 'default', '2c9083844664ed04014664f80bb80000', 'Y');
INSERT INTO leon_menuitem(ID, CODE, ICONCLS, ICONCLS32, JAVACLASS, LEAF, REPORTCODE, SCRIPTS, TEXT, FUN_ID, MENU_ID, PARENT_ID, AUTOSTART) VALUES
('2c9083844665aacd014665ab62340000', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aad-aab', '','客户性质', 'panera_customerProperty_manager', 'default', '2c9083844664ed04014664f80bb80000', 'N');
INSERT INTO leon_menuitem(ID, CODE, ICONCLS, ICONCLS32, JAVACLASS, LEAF, REPORTCODE, SCRIPTS, TEXT, FUN_ID, MENU_ID, PARENT_ID, AUTOSTART) VALUES
('2c9083844710810c01471081a2180000', '', 'pngs_16_LiveMessengeralt3', 'pngs_32_LiveMessengeralt3', '', 'Y', 'aae', '','客户管理', 'panera_customer_manager', 'default', NULL, 'N');
INSERT INTO leon_menuitem(ID, CODE, ICONCLS, ICONCLS32, JAVACLASS, LEAF, REPORTCODE, SCRIPTS, TEXT, FUN_ID, MENU_ID, PARENT_ID, AUTOSTART) VALUES
('2c90838446f198d90146f19b20aa0000', '', 'pngs_16_20110213231948381', 'pngs_32_20110213231948381', '', 'Y', 'aaf', '','客户统计', 'panera_customer_report', 'default', NULL, 'N');


create  table leon_parameter(
    id varchar(255) not null,
    content varchar(200),
    desc varchar(200),
    name varchar(50),
    showmodel varchar(50),
    sort integer,
    subjects varchar(50),
    validation varchar(150),
    valueenum varchar(200)
);