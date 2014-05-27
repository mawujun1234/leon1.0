
#就按这个规范写，不过要先导出生成表的sql
INSERT INTO LEON_USER(ID, CREATEDATE, DELETED, DELETEDDATE, ENABLE, EXPIREDATE, LASTIP, LASTLOGINDATE, LOCKED, LOGINNAME, NAME, PASSWORD) VALUES
('admin', NULL, 'N', NULL, 'Y', NULL, '0:0:0:0:0:0:0:1', TIMESTAMP '2014-05-27 15:37:55.245', 'N', 'admin', 'admin', 'admin');

INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('fun_manager', '功能管理','/desktop/fun/FunApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('menu_manager', '菜单管理','/desktop/menu/MenuApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('role_manager', '角色管理','/desktop/role/RoleApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('user_manager', '用户管理','/desktop/user/UserApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('onlineuser_manager', '在线用户管理','/desktop/user/OnlineUser.jsp','Y');

INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('constant_manager', '常数管理','/desktop/constant/ConstantApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('group_manager', '用户组管理','/desktop/group/GroupApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('org_manager', '组织管理','/desktop/org/OrgApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('org_type_manager', '组织类型管理','/desktop/org/OrgTypeApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('dimension_manager', '维度管理','/desktop/org/OrgDimenssionApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('parameter_manager', '参数管理','/desktop/parameter/ParameterApp.jsp','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('sys_parameter_manager', '系统参数管理','/desktop/parameter/SystemParameterSetPanel.jsp','Y');

INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('toolbar_preference_manager', '工具栏设置','/desktop/preferences/ToolbarSettings.js','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('wallpaper_preference_manager', '背景设置','/desktop/preferences/WallpaperSettings.js','Y');
INSERT INTO LEON_FUN(id,text,url,isenable) VALUES('window_preference_manager', 'window设置','/desktop/preferences/WindowSetting.js','Y');


INSERT INTO LEON_MENU(id,text) VALUES('default', '默认菜单');


INSERT INTO LEON_MENUITEM(ID, CODE, ICONCLS, ICONCLS32, JAVACLASS, LEAF, REPORTCODE, SCRIPTS, TEXT, FUN_ID, MENU_ID, PARENT_ID) VALUES
('2c908384463c40a701463c5f24810000', '', 'menu-category-default-16', 'menu-category-default-32', '', 'N', 'aaa', '', '权限管理', NULL, 'default', NULL),
('2c908384463c40a701463c6228eb0003', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aaa-aac', '', '角色管理', 'role_manager', 'default', '2c908384463c40a701463c5f24810000'),
('2c908384463c40a701463c628bde0004', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aaa-aad', '', '用户管理', 'user_manager', 'default', '2c908384463c40a701463c5f24810000'),
('2c908384463c40a701463c62fa2d0006', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aab-aaa', '', '常数管理', 'constant_manager', 'default', '2c908384463c40a701463c618c9b0002'),
('2c908384463c40a701463c6324530007', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aab-aab', '', '用户组管理', 'group_manager', 'default', '2c908384463c40a701463c618c9b0002'),
('2c908384463c40a701463c6327910008', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aab-aac', '', '组织管理', 'org_manager', 'default', '2c908384463c40a701463c618c9b0002'),
('2c908384463c40a701463c632bb60009', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aab-aad', '', '组织类型管理', 'org_type_manager', 'default', '2c908384463c40a701463c618c9b0002'),
('2c908384463c40a701463c632df0000a', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aab-aae', '', '维度管理', 'dimension_manager', 'default', '2c908384463c40a701463c618c9b0002'),
('2c908384463c40a701463c633009000b', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aab-aaf', '', '参数管理', 'parameter_manager', 'default', '2c908384463c40a701463c618c9b0002'),
('2c908384463c40a701463c633228000c', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aab-aag', '', '系统参数管理', 'sys_parameter_manager', 'default', '2c908384463c40a701463c618c9b0002'),
('2c908384463c40a701463c638134000e', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aac-aaa', '', '工具栏设置', 'toolbar_preference_manager', 'default', '2c908384463c40a701463c6348c5000d'),
('2c908384463c40a701463c638437000f', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aac-aab', '', '背景设置', 'wallpaper_preference_manager', 'default', '2c908384463c40a701463c6348c5000d'),
('2c908384463c40a701463c6386920010', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aac-aac', '', 'window设置', 'window_preference_manager', 'default', '2c908384463c40a701463c6348c5000d'),
('2c908384463c40a701463c618c9b0002', '', 'menu-category-default-16', 'menu-category-default-32', '', 'N', 'aab', '', '基础配置', NULL, 'default', NULL),
('2c908384463c40a701463c6348c5000d', '', 'menu-category-default-16', 'menu-category-default-32', '', 'N', 'aac', '', '个性化设置', NULL, 'default', NULL),
('fun_manager', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aaa-aaa', '', '功能管理', 'fun_manager', 'default', '2c908384463c40a701463c5f24810000'),
('menu_manager', '', 'menu-category-default-16', 'menu-category-default-32', '', 'Y', 'aaa-aab', '', '菜单管理', 'menu_manager', 'default', '2c908384463c40a701463c5f24810000');


