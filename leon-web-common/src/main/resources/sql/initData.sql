drop table  LEON_FUN_bak;
create table  LEON_FUN_bak as select * from LEON_FUN;
DELETE from LEON_FUN;
INSERT INTO LEON_FUN(c_id,c_text) VALUES('root', '功能管理');
INSERT INTO LEON_FUN(c_id,c_text,c_url,reportCode,parent_id) VALUES('fun_manager', '功能管理','/desktop/fun/FunApp.jsp','###','root');
INSERT INTO LEON_FUN(c_id,c_text,c_url,reportCode,parent_id) VALUES('menu_manager', '菜单管理','/desktop/menu/MenuApp.jsp','###','root');

drop table  LEON_MENU;
create table  LEON_MENU_bak as select * from LEON_MENU;
DELETE from LEON_MENU;
INSERT INTO LEON_MENU(c_id,c_text) VALUES('default', '默认菜单');

drop table  LEON_MENUITEM;
create table  LEON_MENUITEM_bak as select * from LEON_MENUITEM;
DELETE from LEON_MENUITEM;
INSERT INTO LEON_MENUITEM(c_id,c_text,menu_id,fun_id) VALUES('root', '菜单管理','default','root');
INSERT INTO LEON_MENUITEM(c_id,c_text,reportCode,menu_id,parent_id,fun_id) VALUES('fun_manager', '功能管理','###','default','root','fun_manager');
INSERT INTO LEON_MENUITEM(c_id,c_text,menu_id,reportCode,parent_id,fun_id) VALUES('menu_manager', '菜单管理','###','default','root','menu_manager');



