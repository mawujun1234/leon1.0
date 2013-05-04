drop table  LEON_FUN_bak;
create table  LEON_FUN_bak as select * from LEON_FUN;
DELETE from LEON_FUN;
INSERT INTO LEON_FUN(c_id,c_text) VALUES('root', '功能管理');

drop table  LEON_MENU;
create table  LEON_MENU_bak as select * from LEON_MENU;
DELETE from LEON_MENU;
INSERT INTO LEON_MENU(c_id,c_text) VALUES('default', '默认菜单');



