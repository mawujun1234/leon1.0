DROP TABLE IF EXISTS LEON_FUN;
#就按这个规范写，不过要先导出生成表的sql
CREATE TABLE LEON_FUN(ID INT PRIMARY KEY, NAME VARCHAR(255));
INSERT INTO LEON_FUN(id,text) VALUES('root', '功能管理');
INSERT INTO LEON_FUN(id,text,url,reportCode,parent_id) VALUES('fun_manager', '功能管理','/desktop/fun/FunApp.jsp','###','root');
INSERT INTO LEON_FUN(id,text,url,reportCode,parent_id) VALUES('menu_manager', '菜单管理','/desktop/menu/MenuApp.jsp','###','root');

drop table  IF EXISTS  LEON_MENU;

INSERT INTO LEON_MENU(id,text,rootid) VALUES('default', '默认菜单','default');

drop table  IF EXISTS  LEON_MENUITEM;

INSERT INTO LEON_MENUITEM(id,text,menu_id,fun_id) VALUES('default', '菜单管理','default','root');
INSERT INTO LEON_MENUITEM(id,text,reportCode,menu_id,parent_id,fun_id) VALUES('fun_manager', '功能管理','###','default','default','fun_manager');
INSERT INTO LEON_MENUITEM(id,text,reportCode,menu_id,parent_id,fun_id) VALUES('menu_manager', '菜单管理','###','default','default','menu_manager');



