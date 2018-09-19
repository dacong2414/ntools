
-- ----------------------------
-- Records of login_info
-- ----------------------------
DELETE FROM `logon_info`;
INSERT INTO `logon_info` VALUES (null,'1', 'admin', '5d69188f019d0f0a9f83fb4b98411739', '1', null, null);
COMMIT;

-- ----------------------------
-- Records of user_info
-- ----------------------------
DELETE FROM `user_info`;
INSERT INTO `user_info` VALUES (null, 'admin', null, null, 1, 1, null,null);
COMMIT;

-- ----------------------------
-- 属性视图
-- ----------------------------
DROP VIEW IF EXISTS  `config_view`;
CREATE VIEW `config_view` AS
SELECT
	`ci`.`mac_address` AS `mac_address`,
	`ci`.`client_id` AS `client_id`,
	`ci`.`client_name` AS `client_name`,
	`cp`.`property_value` AS `property_value`,
	`pi`.`property_id` AS `property_id`,
	`pi`.`property_name_en` AS `property_name_en`,
	`pi`.`property_name_cn` AS `property_name_cn`,
	`pi`.`property_desc` AS `property_desc`,
	`pi`.`default_value` AS `default_value`,
	`pi`.`disable_flag` AS `disable_flag`,
	`pi`.`global_flag` AS `global_flag`,
	`pi`.`customize_flag` AS `customize_flag`,
	`pi`.`display_order` AS `display_order`,
	`pi`.`html_type` AS `html_type`
FROM
	(
		(
			`client_info` `ci`
			JOIN `client_property` `cp`
		)
		JOIN `property_info` `pi`
	)
WHERE
	(
		(
			`ci`.`client_id` = `cp`.`client_id`
		)
		AND (
			`cp`.`property_id` = `pi`.`property_id`
		)
	);
	

DELETE FROM `sys_resources`;
INSERT INTO `sys_resources` VALUES (null, 'url', '缓存资源', '', '/cache/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '统计超声会诊控制', 'ownPartakeConsultList', '/countSuperSoundConsult/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '文件管理（获取最新的版本）', 'updateConfigList', '/file/method=getLastVersion*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '列表框架', '', '/frame/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '分组信息', 'groupInfoList', '/group/*', '1', '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '会议资源', 'meetingInfoList', '/meeting/*', '1', '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '基本属性', 'propertyInfoList', '/property/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '资源管理', 'resourceManageList', '/resource/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '角色管理', 'roleManageList', '/sysRole/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '用户管理', 'userInfoList', '/user/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '用户角色关系管理信息控制', 'userRightsManageList', '/userRoleRelation/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '文件管理（上传文件）', 'updateConfigList', '/file/method=upload*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '用户自定义属性', 'userDefinedPropertyList', '/defined/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '监控端监控的诊室', 'monitorUsers', '/monitor/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '客户端管理（mac地址配置）', 'clientInfoList', '/client/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '联动管理', 'regularLinkageList', '/property/*', '1',  '1', '0',null,null);
INSERT INTO `sys_resources` VALUES (null, 'url', '自定义配置列表', 'groupCustomRelationalList', '/relational/*', '1',  '1', '0',null,null);


DELETE FROM `sys_roles`;
INSERT INTO `sys_roles` VALUES (null, '管理员', '管理员', '1',  '1', '0');

DELETE FROM `sys_roles_resources`;
INSERT INTO `sys_roles_resources` VALUES (null, '1', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '2', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '3', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '4', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '5', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '6', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '7', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '8', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '9', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '10', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '11', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '12', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '13', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '14', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '15', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '16', '1');
INSERT INTO `sys_roles_resources` VALUES (null, '17', '1');




DELETE FROM `sys_users_roles`;
INSERT INTO `sys_users_roles` VALUES (null, '1', '1');

DELETE FROM `access_key_info`;
INSERT INTO access_key_info VALUES(null,'44CF9590006BF252F707','OtxrzxIsfpFjA7SwPzILwy8Bw21TLhquhboDYROV');


DELETE FROM `group_info`;
INSERT INTO `group_info` VALUES (null, '中国', '0', '中国', '', '2017-12-11 13:53:37', null);


