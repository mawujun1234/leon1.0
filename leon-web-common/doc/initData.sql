DROP TABLE IF EXISTS LEON_FUN;
#就按这个规范写，不过要先导出生成表的sql
insert into LEON_USER (id,loginname,name,password,DELETED,ENABLE,LOCKED) values('admin','admin','admin','admin','N','Y','N')

INSERT INTO LEON_FUN(id,text,url) VALUES('fun_manager', '功能管理','/desktop/fun/FunApp.jsp');
INSERT INTO LEON_FUN(id,text,url) VALUES('menu_manager', '菜单管理','/desktop/menu/MenuApp.jsp');

INSERT INTO LEON_MENU(id,text) VALUES('default', '默认菜单');

INSERT INTO LEON_MENUITEM(id,text,reportCode,menu_id,fun_id) VALUES('fun_manager', '功能管理','aaa','default','fun_manager');
INSERT INTO LEON_MENUITEM(id,text,reportCode,menu_id,fun_id) VALUES('menu_manager', '菜单管理','aaa','default','menu_manager');



