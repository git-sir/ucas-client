-- ----------------------------                                                                                                                                        
-- Records of manage_config modify date 2017/5/16                                                                                                                                           
-- ----------------------------                                                                                                                                        
INSERT INTO `manage_config` (`id`, `param_key`, `param_value`, `param_desc`, `status`) VALUES
	('111', 'DEFAULT_ACCOUNT_GROUP', '0', '默认用户组的主键', NULL),
	('2c9182e2-5bfb7ba5-015b-fb7ba52b', 'ALL_GRANT_TYPE', 'authorization_code,password,client_credentials,proxy_authorization_code,user', '统一认证的所有授权类型,若有多个则用","分开', NULL),
	('2c9182f7-5b9e165c-015b-9e7f6703-0002', 'DEFAULT_CLIENT_GROUP', '2c9182f7-5ba9323d-015b-a9323dfa', '默认应用组的主键', NULL),
	('ff808081-5bd811a4-015b-e5c2ed5b-0003', 'USER_CORE_URL', 'https://172.17.22.38:8444/ucas_core/', '调用统一认证外部接口地址', NULL);
-- ----------------------------                                                                                                                                                                 
-- Records of manage_ip_schedule_task                                                                                                                                                       
-- ----------------------------                                                                                                                                                             
INSERT INTO `manage_ip_schedule_task` VALUES ('DEFAULT', 'DEFAULT', 'DEFAULT', '0/50 * * * * ?', 'com.ucsmy.ucas.config.quartz.scan.DefaultScanJob', '172.17.21.59', '0', 'DEFAULT');

-- ----------------------------                                                                                                                                                                    
-- Records of manage_module                                                                                                                                                                        
-- ----------------------------                                                                                                                                                                    
INSERT INTO `manage_module` (`module_id`, `description`, `priority`, `sn`, `url`, `parent_id`, `name`, `leaf`, `image`) VALUES
	('08c3b96c-c0f4-4214-86fe-08bfb77147e9', '组织管理描述', 4, '', 'pages/organization/index.js', '2', '组织管理', '0', NULL),
	('1', '统一认证管理系统', 1, '0', '', '0', '统一认证管理系统', '0', ''),
	('2', '系统管理', 1, '0', '2', '1', '系统管理', '0', 'icon-menu-icon'),
	('2c9182f7-5b8e8b0d-015b-8e8b0df6-0000', '账号管理', 2, '', '', '1', '账号管理', '0', 'icon-user-icon'),
	('2c9182f7-5b8e8b0d-015b-8e8b5efc-0001', '账号管理', 1, '', 'pages/account/index.js', '2c9182f7-5b8e8b0d-015b-8e8b0df6-0000', '账号管理', '0', NULL),
	('2c9182f7-5b8e8b0d-015b-8e8e7ba8-0003', '账号组管理', 2, '', 'pages/accountGroup/index.js', '2c9182f7-5b8e8b0d-015b-8e8b0df6-0000', '账号组管理', '0', ''),
	('2c9182f7-5b8e8b0d-015b-8e8ee4f6-0004', '应用管理', 3, '', '', '1', '应用管理', '0', 'icon-edit-icon'),
	('2c9182f7-5b8e8b0d-015b-8e8f1d4e-0005', '应用管理', 2, '', 'pages/client_info/index.js', '2c9182f7-5b8e8b0d-015b-8e8ee4f6-0004', '应用管理', '0', ''),
	('2c9182f7-5b8e8b0d-015b-8e8f5145-0006', '应用组管理', 1, '', 'pages/clientgroup/index.js', '2c9182f7-5b8e8b0d-015b-8e8ee4f6-0004', '应用组管理', '0', NULL),
	('2c9182f7-5b8e8b0d-015b-8e8f7591-0007', '应用资源管理', 3, '', 'pages/clientResource/index.js', '2c9182f7-5b8e8b0d-015b-8e8ee4f6-0004', '应用资源管理', '0', NULL),
	('2c9182f7-5b8e8b0d-015b-8e904324-000a', '授权管理', 4, '', '', '1', '授权管理', '0', 'icon-menu-icon1'),
	('2c9182f7-5b8e8b0d-015b-8e90b6a9-000c', '帐号应用授权管理', 2, '', 'pages/userRel/index.js', '2c9182f7-5b8e8b0d-015b-8e904324-000a', '账号应用授权', '0', ''),
	('2c9182f7-5b8e8b0d-015b-8e90da43-000d', 'Token的策略管理', 3, '', 'pages/tokenStrategy/index.js', '2c9182f7-5b8e8b0d-015b-8e904324-000a', 'Token策略', '0', ''),
	('2c9182f7-5b8e8b0d-015b-8e90f717-000e', 'Ticket策略管理', 4, '', 'pages/tickettactics/index.js', '2c9182f7-5b8e8b0d-015b-8e904324-000a', 'Ticket策略', '0', ''),
	('3', '用户管理', 1, '0', 'pages/user/index.js', '2', '用户管理', '1', ''),
	('4', '菜单管理', 3, '0', 'pages/module/index.js', '2', '菜单管理', '1', NULL),
	('5', '角色管理', 2, '0', 'pages/role/index.js', '2', '角色管理', '1', NULL),
	('6', '权限管理', 4, '0', 'pages/permission/index.js', '2', '权限管理', '1', NULL),
	('7', '参数管理', 4, '0', 'pages/config/index.js', '2', '参数管理', '1', NULL),
	('def06628-a916-47bd-97a2-31b4768fc0a3', '定时管理', 6, '', 'pages/schedule/index.js', '2', '定时管理', '0', NULL),
	('ff808081-5bb3e055-015b-b3e0557a-0000', '资源组管理', 4, NULL, 'pages/resourceGroup/index.js', '2c9182f7-5b8e8b0d-015b-8e8ee4f6-0004', '资源组管理', '0', NULL);
-- ----------------------------                                                                                                                         
-- Records of manage_organization                                                                                        
-- ----------------------------                                                                                          
INSERT INTO `manage_organization` VALUES ('abdb6fe7ee9e11e6bfa2005056adf4cb', '超级管理组', '超级管理组', '0', '', null);

-- ----------------------------
-- Records of manage_permission modify date 2017/5/16  
-- ----------------------------
INSERT INTO `manage_permission` VALUES ('18a4d221-c245-4fa9-b770-08d4633a7026', '查询角色', '查询', 'role_query', '5', '/role.role.pgflow/queryRoleList*');
INSERT INTO `manage_permission` VALUES ('1f759941-e52d-426a-aace-b6fa99ef68c8', '角色绑定权限', '绑定权限', 'role_bind_permission', '5', '/rolePermission.addRolePermission.pgflow/addRolePermission*');
INSERT INTO `manage_permission` VALUES ('1fdd8fcf-ed8d-46fd-9509-7a4187e7ea38', '删除用户', '删除', 'user_delete', '3', '/user.user.pgflow/deleteUser*');
INSERT INTO `manage_permission` VALUES ('2380627f-7ae7-48bf-a3e8-3d66e3549509', '删除参数', '删除', 'config_delete', '7', '/config.config.pgflow/delete_config*');
INSERT INTO `manage_permission` VALUES ('26935107-cf4d-42cd-a05e-acecee79fc24', '修改菜单', '修改', 'module_update', '4', '/module.module.pgflow/updateModule*');
INSERT INTO `manage_permission` VALUES ('29f373da-cb7f-42e4-830c-1a1240b32fd7', '组织解绑用户', '解绑用户', 'org_unbind_user', '08c3b96c-c0f4-4214-86fe-08bfb77147e9', '/organization.organization.pgflow/unbindOrganization*');
INSERT INTO `manage_permission` VALUES ('2c9182c3-5ba43be5-015b-a43be503-0000', '', '资源添加', 'resource_add', '2c9182f7-5b8e8b0d-015b-8e8f7591-0007', 'clientResource/addResource');
INSERT INTO `manage_permission` VALUES ('2c9182c3-5ba43be5-015b-a43c63d0-0001', '', '资源修改', 'resource_update', '2c9182f7-5b8e8b0d-015b-8e8f7591-0007', 'clientResource/editResource');
INSERT INTO `manage_permission` VALUES ('2c9182c3-5ba43be5-015b-a43d05fe-0002', '', '资源删除', 'resource_delete', '2c9182f7-5b8e8b0d-015b-8e8f7591-0007', 'clientResource/deleteResource');
INSERT INTO `manage_permission` VALUES ('2c9182c3-5bad4630-015b-ad4630e4-0000', '', '绑定资源组', 'client_info_bindResgr', '2c9182f7-5b8e8b0d-015b-8e8f1d4e-0005', 'clientResgrRel/addResgrRel');
INSERT INTO `manage_permission` VALUES ('2c9182c3-5bae6a6b-015b-ae6a6b2b-0000', '', '解绑资源组', 'client_info_unbindResgr', '2c9182f7-5b8e8b0d-015b-8e8f1d4e-0005', 'clientResgrRel/deleteResgrRel');
INSERT INTO `manage_permission` VALUES ('2c9182c3-5bc8619f-015b-c8619fa1-0000', '', '资源展示', 'resource_show', '2c9182f7-5b8e8b0d-015b-8e8f7591-0007', 'clientResource/showResource');
INSERT INTO `manage_permission` VALUES ('2c9182e2-5b9db977-015b-9db977e4-0000', '查询应用', '查询', 'client_info_query', '2c9182f7-5b8e8b0d-015b-8e8f1d4e-0005', '-');
INSERT INTO `manage_permission` VALUES ('2c9182e2-5b9db977-015b-9dbace10-0001', '新增应用', '新增', 'client_info_add', '2c9182f7-5b8e8b0d-015b-8e8f1d4e-0005', '-');
INSERT INTO `manage_permission` VALUES ('2c9182e2-5b9db977-015b-9dbb3741-0002', '修改应用', '修改', 'client_info_update', '2c9182f7-5b8e8b0d-015b-8e8f1d4e-0005', '-');
INSERT INTO `manage_permission` VALUES ('2c9182e2-5b9db977-015b-9dbbe67d-0003', '删除应用', '删除', 'client_info_delete', '2c9182f7-5b8e8b0d-015b-8e8f1d4e-0005', '-');
INSERT INTO `manage_permission` VALUES ('2c9182e2-5bc8b555-015b-c8b555d2-0000', '查看应用详情', '查看', 'client_info_view', '2c9182f7-5b8e8b0d-015b-8e8f1d4e-0005', '-');
INSERT INTO `manage_permission` VALUES ('2c9182e5-5bccd342-015b-ccd342dd-0000', '修改账号组', '修改账号组', 'update_account_group', '2c9182f7-5b8e8b0d-015b-8e8e7ba8-0003', 'accountGroup');
INSERT INTO `manage_permission` VALUES ('2c9182e5-5bccd80d-015b-ccd80d41-0000', '删除账号组', '删除账号组', 'delete_account_group', '2c9182f7-5b8e8b0d-015b-8e8e7ba8-0003', 'delete_account_group');
INSERT INTO `manage_permission` VALUES ('2c9182e5-5bccd80d-015b-ccd85b51-0001', '绑定用户', '绑定用户', 'bind_account', '2c9182f7-5b8e8b0d-015b-8e8e7ba8-0003', 'bind_account');
INSERT INTO `manage_permission` VALUES ('2c9182e5-5bccd80d-015b-ccd886f2-0002', '解绑用户', '解绑用户', 'unbind_account', '2c9182f7-5b8e8b0d-015b-8e8e7ba8-0003', 'unbind_account');
INSERT INTO `manage_permission` VALUES ('2c9182e5-5bccd80d-015b-ccd8afb7-0003', '绑定应用组', '绑定应用组', 'bind_client_group', '2c9182f7-5b8e8b0d-015b-8e8e7ba8-0003', 'bind_client_group');
INSERT INTO `manage_permission` VALUES ('2c9182e5-5bccd80d-015b-ccd8d3cf-0004', '解绑应用组', '解绑应用组', 'unbind_client_group', '2c9182f7-5b8e8b0d-015b-8e8e7ba8-0003', 'unbind_client_group');
INSERT INTO `manage_permission` VALUES ('2c9182e5-5bccd80d-015b-ccd90338-0005', '应用组查看', '应用组查看', 'view_client_group', '2c9182f7-5b8e8b0d-015b-8e8e7ba8-0003', 'view_client_group');
INSERT INTO `manage_permission` VALUES ('2c9182e5-5bccd80d-015b-ccd93030-0006', '用户查看', '用户查看', 'view_account', '2c9182f7-5b8e8b0d-015b-8e8e7ba8-0003', 'view_account');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5b9e165c-015b-9edcd286-0003', '应用组添加', '添加', 'client_group_add', '2c9182f7-5b8e8b0d-015b-8e8f5145-0006', 'clientGroup/add');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5b9e165c-015b-9eddc0c5-0004', '应用组修改', '修改', 'client_group_edit', '2c9182f7-5b8e8b0d-015b-8e8f5145-0006', 'clientGroup/update');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5b9e165c-015b-9ede1935-0005', '资源组删除', '删除', 'client_group_delete', '2c9182f7-5b8e8b0d-015b-8e8f5145-0006', 'clientGroup/delete');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5b9e165c-015b-9ee8cca4-0006', '绑定应用', '绑定应用', 'client_group_bind', '2c9182f7-5b8e8b0d-015b-8e8f5145-0006', 'clientGroup/manageClient');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5b9e165c-015b-9ee9314d-0007', '解绑应用', '解绑应用', 'client_group_unbind', '2c9182f7-5b8e8b0d-015b-8e8f5145-0006', 'clientGroup/unbind');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5ba3a6de-015b-a3a6de47-0000', '查看授权应用', '查看授权应用', 'user_rel_client', '2c9182f7-5b8e8b0d-015b-8e90b6a9-000c', 'userRel/userList');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5ba3cff9-015b-a3cff944-0000', '应用管理', '应用管理', 'client_group_manage', '2c9182f7-5b8e8b0d-015b-8e8f5145-0006', 'clientGroup/manageClient');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5ba3cff9-015b-a3f31039-0001', '取消应用授权', '取消应用授权', 'user_rel_remove_client', '2c9182f7-5b8e8b0d-015b-8e90b6a9-000c', 'userRel/removeClient');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5bf58244-015b-f5824428-0000', '查询', '查询', 'token_strategy_query', '2c9182f7-5b8e8b0d-015b-8e90da43-000d', '-');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5bf58244-015b-f58285ef-0001', '修改', '修改', 'token_strategy_edit', '2c9182f7-5b8e8b0d-015b-8e90da43-000d', '-');
INSERT INTO `manage_permission` VALUES ('2c9182f7-5bf58244-015b-f582be59-0002', '删除', '删除', 'token_strategy_delete', '2c9182f7-5b8e8b0d-015b-8e90da43-000d', '-');
INSERT INTO `manage_permission` VALUES ('3595c7a4-1ec1-4361-925c-9ad8e3380221', '密码修改', '密码修改', 'user_update_password', '3', '/user.user.pgflow/updateUserPassword*');
INSERT INTO `manage_permission` VALUES ('36e05dd5-79f8-49e1-bb13-14e81203d547', '删除', '删除', 'org_delete', '08c3b96c-c0f4-4214-86fe-08bfb77147e9', '/organization.organization.pgflow/deleteOrganization*');
INSERT INTO `manage_permission` VALUES ('3c7c931b-114f-4ca3-9220-f9118e1060ed', '修改', '修改', 'org_update', '08c3b96c-c0f4-4214-86fe-08bfb77147e9', '/organization.organization.pgflow/editOrganization*');
INSERT INTO `manage_permission` VALUES ('3d57b77d-b148-4fea-9fa0-71619fe33f5a', '新增用户', '新增', 'user_add', '3', '/user.user.pgflow/addUser*');
INSERT INTO `manage_permission` VALUES ('4b72e940-3647-4cc4-8b56-45761432984e', '解绑用户对应角色', '解绑角色', 'role_unbind_user', '5', '/role.user_role.pgflow/unbindUser*');
INSERT INTO `manage_permission` VALUES ('54105a72-8433-478e-b878-70fff83b46ed', '删除权限', '删除', 'role_delete', '5', '/role.role.pgflow/deleteRole*');
INSERT INTO `manage_permission` VALUES ('56d1d066-1ef6-4ad4-8ea7-d1591f18a51f', '修改角色', '修改', 'role_update', '5', '/role.role.pgflow/updateRole*');
INSERT INTO `manage_permission` VALUES ('58c54c9a-c4ae-454d-b05c-576de1975a42', '用户查询', '查询', 'user_query', '3', '/user.user.pgflow/queryUserList*');
INSERT INTO `manage_permission` VALUES ('5c5a71a1-7f27-452c-9e34-9d424be48850', '新增子节点', '新增', 'org_add', '08c3b96c-c0f4-4214-86fe-08bfb77147e9', '/organization.organization.pgflow/addOrganization*');
INSERT INTO `manage_permission` VALUES ('6658ffb8-8556-4d5d-bd9f-890d6eca508b', '组织绑定用户', '绑定用户', 'org_bind_user', '08c3b96c-c0f4-4214-86fe-08bfb77147e9', '/organization.organization.pgflow/bindOrganization*');
INSERT INTO `manage_permission` VALUES ('6cdc5d4e-bba6-4187-a5f3-908276aa41f2', '绑定用户对应角色', '绑定角色', 'role_bind_user', '5', '/role.user_role.pgflow/bind_user*');
INSERT INTO `manage_permission` VALUES ('6f5edffe-83a5-4bcf-907a-6e10c2c1245f', '组织查询', '查询', 'org_query', '08c3b96c-c0f4-4214-86fe-08bfb77147e9', '/organization.organization.pgflow/queryOrganization*');
INSERT INTO `manage_permission` VALUES ('70ceae1c-573c-42c1-8e09-4b6979ff47da', '删除菜单', '删除', 'module_delete', '4', '/module.module.pgflow/delModule*');
INSERT INTO `manage_permission` VALUES ('8fc06f62-633d-47a2-8345-3ec0ee7d94b0', '权限查询', '查询', 'permission_query', '6', '/permission.permission.pgflow/getPermissionList*');
INSERT INTO `manage_permission` VALUES ('9707e828-3422-41c5-937a-a4afd4f9b849', '权限修改s', '修改', 'permission_update', '6', '/permission.permission.pgflow/updatePermission*');
INSERT INTO `manage_permission` VALUES ('99daa3fe-5b64-4dce-9dee-1e9693434210', '新增子节点', '新增', 'module_add', '4', '/module.module.pgflow/addModule*');
INSERT INTO `manage_permission` VALUES ('b0dac8d0-4367-41b4-b965-e7a7f0ae0c8c', '参数查询', '查询', 'config_query', '7', '/config.config.pgflow/query_config*');
INSERT INTO `manage_permission` VALUES ('bdf5deec-3d3d-47d3-aca2-474f8c6f1a38', '新增参数', '新增', 'config_add', '7', '/config.config.pgflow/add_config*');
INSERT INTO `manage_permission` VALUES ('c25361e4-e891-41f0-9585-9fbf4dc0199d', '新增角色', '新增', 'role_add', '5', '/role.role.pgflow/addRole*');
INSERT INTO `manage_permission` VALUES ('c9f62ced-e67c-4438-af54-8f99a490fec0', '修改用户', '修改', 'user_update', '3', '/user.user.pgflow/updateUser*');
INSERT INTO `manage_permission` VALUES ('d2852505-4e70-40c6-9c8d-ddfe72520952', '修改参数', '修改', 'config_update', '7', '/config.config.pgflow/edit_config*');
INSERT INTO `manage_permission` VALUES ('d7386557-360a-444d-b71b-3b071afead51', '菜单查询', '查询', 'module_query', '4', '/module.module.pgflow/moduleList*');
INSERT INTO `manage_permission` VALUES ('e36a76d6-2426-4e5a-a876-06dfc031311c', '新增权限', '新增', 'permission_add', '6', '/permission.permission.pgflow/addPermission*');
INSERT INTO `manage_permission` VALUES ('ff808081-5bb3e055-015b-b3e14680-0001', '资源组查询', '查询', 'resource_group_query', 'ff808081-5bb3e055-015b-b3e0557a-0000', '-');
INSERT INTO `manage_permission` VALUES ('ff808081-5bb3e055-015b-b3eabce7-0002', '添加资源组', '添加资源组', 'client_info_addResgr', '2c9182f7-5b8e8b0d-015b-8e8f1d4e-0005', '-');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c7fd4273-0012', '修改', '修改', 'resource_group_edit', 'ff808081-5bb3e055-015b-b3e0557a-0000', '-');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c7fd80fe-0013', '删除', '删除', 'resource_group_delete', 'ff808081-5bb3e055-015b-b3e0557a-0000', '-');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c80fc86e-0014', '修改基本信息', '修改基本信息', 'account_update', '2c9182f7-5b8e8b0d-015b-8e8b5efc-0001', '/account/upInfo*');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c8107eb0-0015', '删除', '删除', 'account_delete', '2c9182f7-5b8e8b0d-015b-8e8b5efc-0001', '/account/delete*');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c810bed5-0016', '冻结', '冻结', 'account_freeze', '2c9182f7-5b8e8b0d-015b-8e8b5efc-0001', '/account/freeze*');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c811022e-0017', '解冻', '解冻', 'account_unfreeze', '2c9182f7-5b8e8b0d-015b-8e8b5efc-0001', '/account/unfreeze*');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c815320f-0018', '修改邮箱', '修改邮箱', 'account_mod_email', '2c9182f7-5b8e8b0d-015b-8e8b5efc-0001', '/account/upEmail*');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c8158033-0019', '修改手机号', '修改手机号', 'account_mod_mobile', '2c9182f7-5b8e8b0d-015b-8e8b5efc-0001', 'account/upPhone*');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c815bfbc-001a', '密码修改', '密码修改', 'account_update_password', '2c9182f7-5b8e8b0d-015b-8e8b5efc-0001', 'account/upPassword*');
INSERT INTO `manage_permission` VALUES ('ff808081-5bc6c065-015b-c815fd22-001b', '详情', '详情', 'account_detail', '2c9182f7-5b8e8b0d-015b-8e8b5efc-0001', '*');

-- ----------------------------                                                                                                                      
-- Records of manage_role                                                                           
-- ----------------------------                                                                     
INSERT INTO `manage_role` VALUES ('2c9182d4-5a1c3f70-015a-1c3f7089-0000', '超级管理', '超级管理员');

-- ----------------------------                                                                                                                                  
-- Records of manage_role_module                                                                                                                                 
-- ----------------------------                                                                                                                                  
INSERT INTO `manage_role_module` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0000', '1', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');                                   
INSERT INTO `manage_role_module` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0001', '2', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');                                   
INSERT INTO `manage_role_module` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0002', '3', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');                                   
INSERT INTO `manage_role_module` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0003', '08c3b96c-c0f4-4214-86fe-08bfb77147e9', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_module` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0004', '5', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');                                   
INSERT INTO `manage_role_module` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0005', '6', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');                                   
INSERT INTO `manage_role_module` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0006', '7', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');                                   
INSERT INTO `manage_role_module` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0007', '2c9182f7-5b8e8b0d-015b-8e8f7591-0007', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_module` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0008', '2c9182f7-5b8e8b0d-015b-8e8ee4f6-0004', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');            

-- ----------------------------                                                          
-- Records of manage_role_param                                                          
-- ----------------------------                                                          
INSERT INTO `manage_role_param` VALUES ('2c9182d4-5a1c3f70-015a-1c3f7089-0000', 'admin');

-- ----------------------------                                                                                                                                      
-- Records of manage_role_permission                                                                                                                                 
-- ----------------------------                                                                                                                                      
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0009', '36e05dd5-79f8-49e1-bb13-14e81203d547', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-000a', '56d1d066-1ef6-4ad4-8ea7-d1591f18a51f', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-000b', '2c9182c3-5ba43be5-015b-a43be503-0000', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-000c', '18a4d221-c245-4fa9-b770-08d4633a7026', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-000d', '2380627f-7ae7-48bf-a3e8-3d66e3549509', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-000e', '2c9182c3-5ba43be5-015b-a43d05fe-0002', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-000f', '3c7c931b-114f-4ca3-9220-f9118e1060ed', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0010', 'b0dac8d0-4367-41b4-b965-e7a7f0ae0c8c', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0011', 'c9f62ced-e67c-4438-af54-8f99a490fec0', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0012', 'd2852505-4e70-40c6-9c8d-ddfe72520952', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0013', '3595c7a4-1ec1-4361-925c-9ad8e3380221', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0014', '3d57b77d-b148-4fea-9fa0-71619fe33f5a', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0015', '54105a72-8433-478e-b878-70fff83b46ed', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0016', '58c54c9a-c4ae-454d-b05c-576de1975a42', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0017', 'e36a76d6-2426-4e5a-a876-06dfc031311c', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0018', '29f373da-cb7f-42e4-830c-1a1240b32fd7', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0019', '6cdc5d4e-bba6-4187-a5f3-908276aa41f2', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-001a', '9707e828-3422-41c5-937a-a4afd4f9b849', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-001b', 'c25361e4-e891-41f0-9585-9fbf4dc0199d', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-001c', '5c5a71a1-7f27-452c-9e34-9d424be48850', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-001d', '4b72e940-3647-4cc4-8b56-45761432984e', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-001e', '2c9182c3-5ba43be5-015b-a43c63d0-0001', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-001f', '1fdd8fcf-ed8d-46fd-9509-7a4187e7ea38', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0020', '8fc06f62-633d-47a2-8345-3ec0ee7d94b0', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0021', '1f759941-e52d-426a-aace-b6fa99ef68c8', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0022', 'bdf5deec-3d3d-47d3-aca2-474f8c6f1a38', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');
INSERT INTO `manage_role_permission` VALUES ('2c9182c3-5ba43e31-015b-a43e3103-0023', '6f5edffe-83a5-4bcf-907a-6e10c2c1245f', '2c9182d4-5a1c3f70-015a-1c3f7089-0000');

-- ----------------------------                                                                                                                                                                          
-- Records of manage_user_account                                                                                                                                                                        
-- ----------------------------                                                                                                                                                                          
INSERT INTO `manage_user_account` VALUES ('7ebbb432-fbf8-44d2-8fad-e9faddae970c', '2017-03-09 11:28:06', 'e0cdf7e461423fb4c68709a7e50a05a0', 'geHDS', '0', 'admin', '13763340378@qq.com', '13763340378');

-- ----------------------------                                                                                                                                          
-- Records of manage_user_organization                                                                                                                                   
-- ----------------------------                                                                                                                                          
INSERT INTO `manage_user_organization` VALUES ('2c9182ec-5af401c0-015a-f401c076-0001', '7ebbb432-fbf8-44d2-8fad-e9faddae970c', 'abdb6fe7ee9e11e6bfa2005056adf4cb', null);

-- ----------------------------                                                                                                                                                                                           
-- Records of manage_user_profile                                                                                                                                                                                         
-- ----------------------------                                                                                                                                                                                           
INSERT INTO `manage_user_profile` VALUES ('7ebbb432-fbf8-44d2-8fad-e9faddae970c', '超级管理', null, '5102391', '13763340378', '2017-03-09 11:28:06', null, '13763340378@qq.com', null, '1', null, null, 'register', null);

-- ----------------------------                                                                                                                                      
-- Records of manage_user_role                                                                                                                                       
-- ----------------------------                                                                                                                                      
INSERT INTO `manage_user_role` VALUES ('95c93c8b-956c-49b9-acc6-4de9ef73b4e4', '2c9182d4-5a1c3f70-015a-1c3f7089-0000', '7ebbb432-fbf8-44d2-8fad-e9faddae970c', null);

-- ----------------------------
-- Records of ucas_account_group
-- ----------------------------
INSERT INTO `ucas_account_group` VALUES ('0', '默认用户组', '默认用户组');
INSERT INTO `ucas_account_group` VALUES ('1', '网金内部用户组', '网金内部用户组');
INSERT INTO `ucas_account_group` VALUES ('2', '终端用户组', '只能终端用户');

-- ----------------------------
-- Records of ucas_client_group
-- ----------------------------
INSERT INTO `ucas_client_group` VALUES ('2c9182f7-5ba9323d-015b-a9323dfa', '默认应用组', '默认应用组', '0');

/* 初始化应用组 */
INSERT INTO `ucas_client_group` (`CLIG_UUID`, `GROUP_NAME`, `DESC_RIBE`, `IS_SSO`) VALUES('00000000000000000000000000000000','统一认证中心','认证中心接口','0');
INSERT INTO `ucas_client_group` (`CLIG_UUID`, `GROUP_NAME`, `DESC_RIBE`, `IS_SSO`) VALUES('00000000000000000000000000000001','统一认证客户端','认证中心客户端','0');
/* 初始化应用 */
INSERT INTO `ucas_client_info` (`CLIENT_ID`, `CLIG_UUID`, `CLIENT_SECRET`, `CLIENT_NAME`, `DESC_RIBE`, `GRANT_TYPE`, `CLIENT_URL`, `STATUS`, `CREATE_DATE`, `MODIFY_DATE`) VALUES('00000000000000000000000000000000','00000000000000000000000000000000','','统一认证接口','统一认证中心的客户端，不能改变clientid，因为代码里写死，进行配置内部资源接口',NULL,NULL,'0','2017-04-28 09:41:42','2017-04-28 09:41:45');
INSERT INTO `ucas_client_info` (`CLIENT_ID`, `CLIG_UUID`, `CLIENT_SECRET`, `CLIENT_NAME`, `DESC_RIBE`, `GRANT_TYPE`, `CLIENT_URL`, `STATUS`, `CREATE_DATE`, `MODIFY_DATE`) VALUES('00000000000000000000000000000001','00000000000000000000000000000001','000000000000000001','ucas_client','统一认证管理客户端','authorization_code,password,client_credentials,refresh_token,proxy_authorization_code',NULL,'0','2017-04-28 09:48:44','2017-04-28 09:48:46');
/* 初始化应用资源组 */
INSERT INTO `ucas_resource_group` (`RES_GROUP_UUID`, `CLIENT_ID`, `GROUP_NAME`, `DESC_RIBE`) VALUES('35db29376d544396b4065161d86070eb','00000000000000000000000000000000','client_usermanage_status','用户账号状态');
INSERT INTO `ucas_resource_group` (`RES_GROUP_UUID`, `CLIENT_ID`, `GROUP_NAME`, `DESC_RIBE`) VALUES('8d6cee00284948a1a8deceecfd0df1ee','00000000000000000000000000000000','client_ ticket','调用票券');
INSERT INTO `ucas_resource_group` (`RES_GROUP_UUID`, `CLIENT_ID`, `GROUP_NAME`, `DESC_RIBE`) VALUES('944ba9a59140473e9cf16138f6c7b9af','00000000000000000000000000000000','client_usermanage','用户账号');
INSERT INTO `ucas_resource_group` (`RES_GROUP_UUID`, `CLIENT_ID`, `GROUP_NAME`, `DESC_RIBE`) VALUES('ea352bb90ce040b0a2d1b024e0f47fd8','00000000000000000000000000000000','client_userinfo','用户个人信息');
INSERT INTO `ucas_resource_group` (`RES_GROUP_UUID`, `CLIENT_ID`, `GROUP_NAME`, `DESC_RIBE`) VALUES('fb9d65a3bac341fbb6b8fa68d36b86a1','00000000000000000000000000000000','ucas_base','统一认证中心基础资源组');
INSERT INTO `ucas_resource_group` (`RES_GROUP_UUID`, `CLIENT_ID`, `GROUP_NAME`, `DESC_RIBE`) VALUES('ceece6622fc311e7a576005056adf4cb', '00000000000000000000000000000000', 'client_usermanage_cur', '当前用户管理');
INSERT INTO `ucas_resource_group` VALUES ('8a8081b9-5c152a79-015c-1540fe94', '00000000000000000000000000000000', 'client_usermanage_accg', '账号组信息');
INSERT INTO `ucas_resource_group` VALUES ('8a8081b9-5c152a79-015c-1541ab08', '00000000000000000000000000000000', 'client_usermanage_fingerprint', '指纹信息');
/* 初始化应用授权资源组关系表 */
INSERT INTO `ucas_client_resgr_rel` (`UUID`, `CLIENT_ID`, `RES_GROUP_UUID`) VALUES('247b07332bbb11e7868e005056adf4cb','00000000000000000000000000000001','35db29376d544396b4065161d86070eb');
INSERT INTO `ucas_client_resgr_rel` (`UUID`, `CLIENT_ID`, `RES_GROUP_UUID`) VALUES('c1c730bf2bba11e7868e005056adf4cb','00000000000000000000000000000001','fb9d65a3bac341fbb6b8fa68d36b86a1');
INSERT INTO `ucas_client_resgr_rel` (`UUID`, `CLIENT_ID`, `RES_GROUP_UUID`) VALUES('c7eeeaff2bba11e7868e005056adf4cb','00000000000000000000000000000001','ea352bb90ce040b0a2d1b024e0f47fd8');
INSERT INTO `ucas_client_resgr_rel` (`UUID`, `CLIENT_ID`, `RES_GROUP_UUID`) VALUES('d86e66892bba11e7868e005056adf4cb','00000000000000000000000000000001','944ba9a59140473e9cf16138f6c7b9af');
/* 初始化应用资源表 */
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('0f8590ee2bba11e7868e005056adf4cb','944ba9a59140473e9cf16138f6c7b9af','/oauth/resource/user/get_user','获取用户详情','0','1','2017-04-28 10:26:05','2017-04-28 10:26:08','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('3cbf60cb2bb911e7868e005056adf4cb','35db29376d544396b4065161d86070eb','/oauth/resource/user/freeze_user','冻结用户帐号','0','1','2017-04-28 10:20:55','2017-04-28 10:20:58','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('49f59ccc2bb811e7868e005056adf4cb','ea352bb90ce040b0a2d1b024e0f47fd8','/oauth/resource/tokenUser/userInfo','获取用户个人信息','1','1','2017-04-28 10:14:45','2017-04-28 10:14:48','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('4d1977012bba11e7868e005056adf4cb','944ba9a59140473e9cf16138f6c7b9af','/oauth/resource/user/update_info','修改用户信息','0','1','2017-04-28 10:28:17','2017-04-28 10:28:19','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('538fec4d2bb911e7868e005056adf4cb','35db29376d544396b4065161d86070eb','/oauth/resource/user/refreeze_user','启用用户帐号','0','1','2017-04-28 10:21:35','2017-04-28 10:21:38','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('812bbd9d2bb911e7868e005056adf4cb','944ba9a59140473e9cf16138f6c7b9af','/oauth/resource/user/add_user','添加用户帐号','0','1','2017-04-28 10:22:32','2017-04-28 10:22:34','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('9095cab02bb911e7868e005056adf4cb','944ba9a59140473e9cf16138f6c7b9af','/oauth/resource/user/del_user','删除用户帐号','0','1','2017-04-28 10:23:13','2017-04-28 10:23:15','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('9878b58a2bb811e7868e005056adf4cb','8d6cee00284948a1a8deceecfd0df1ee','/oauth/resource/ticket/getTicket','获取接口调用票券','0','1','2017-04-28 10:17:03','2017-04-28 10:17:06','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('b216b8bf2bb911e7868e005056adf4cb','944ba9a59140473e9cf16138f6c7b9af','/oauth/resource/user/mod_password','修改用户密码','0','1','2017-04-28 10:23:47','2017-04-28 10:23:49','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('cd931b002bb911e7868e005056adf4cb','944ba9a59140473e9cf16138f6c7b9af','/oauth/resource/user/mod_email','修改邮箱帐号','0','1','2017-04-28 10:24:33','2017-04-28 10:24:36','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('e454e3032bb811e7868e005056adf4cb','8d6cee00284948a1a8deceecfd0df1ee','/oauth/resource/ticket/ticketHasAuthorize','判断凭证是否有调用权限','0','1','2017-04-28 10:18:29','2017-04-28 10:18:31','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('e4ce07382bb911e7868e005056adf4cb','944ba9a59140473e9cf16138f6c7b9af','/oauth/resource/user/mod_mobile','修改手机帐号','0','1','2017-04-28 10:25:13','2017-04-28 10:25:15','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('5eee7e3c2fc411e7a576005056adf4cb','ceece6622fc311e7a576005056adf4cb','/oauth/resource/tokenUser/update_info','修改当前用户个人信息','0','1','2017-05-03 13:51:21','2017-05-03 13:51:23','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('95316de22fc411e7a576005056adf4cb','ceece6622fc311e7a576005056adf4cb','/oauth/resource/tokenUser/mod_password','修改当前用户密码','0','1','2017-05-03 13:52:14','2017-05-03 13:52:17','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('b6ca42db2fc411e7a576005056adf4cb','ceece6622fc311e7a576005056adf4cb','/oauth/resource/tokenUser/mod_email','修改当前用户邮箱帐号','0','1','2017-05-03 13:53:56','2017-05-03 13:53:58','0');
INSERT INTO `ucas_client_resource` (`RES_UUID`, `RES_GROUP_UUID`, `RES_URI`, `DESC_RIBE`, `IS_PRIVACY`, `IS_DEFAULT`, `CREATE_DATE`, `MODIFY_DATE`, `STATUS`) VALUES('f9e8b0232fc411e7a576005056adf4cb','ceece6622fc311e7a576005056adf4cb','/oauth/resource/tokenUser/mod_mobile','修改当前用户手机帐号','0','1','2017-05-03 13:55:04','2017-05-03 13:55:06','0');
INSERT INTO `ucas_client_resource` VALUES ('8a8081a2-5c1428d9-015c-146c7d51', 'fb9d65a3bac341fbb6b8fa68d36b86a1', '/oauth/refreshToken', '刷新token', '0', '0', '2017-05-17 11:21:13', '2017-05-17 11:21:13', '0');
INSERT INTO `ucas_client_resource` VALUES ('8a8081a2-5c1428d9-015c-146ea371', 'fb9d65a3bac341fbb6b8fa68d36b86a1', '/oauth/resource/authToken', '校验认证令牌有效性', '0', '0', '2017-05-17 11:23:34', '2017-05-17 11:23:34', '0');
INSERT INTO `ucas_client_resource` VALUES ('8a8081a2-5c1428d9-015c-146fa3e6', 'fb9d65a3bac341fbb6b8fa68d36b86a1', '/oauth/resource/getScopes', '获取应用当前的授权列表', '0', '0', '2017-05-17 11:24:40', '2017-05-17 11:24:40', '0');
INSERT INTO `ucas_client_resource` VALUES ('8a8081b9-5c152a79-015c-154315ee', '8a8081b9-5c152a79-015c-1540fe94', '/oauth/resource/user/get_accg', '获取账号组数据', '0', '0', '2017-05-17 15:15:37', '2017-05-17 15:15:37', '0');
INSERT INTO `ucas_client_resource` VALUES ('8a8081b9-5c152a79-015c-1543bc47', '8a8081b9-5c152a79-015c-1541ab08', '/oauth/resource/user/set_fingerprint', '设置指纹标识', '0', '0', null, '2017-05-17 15:23:25', '0');
