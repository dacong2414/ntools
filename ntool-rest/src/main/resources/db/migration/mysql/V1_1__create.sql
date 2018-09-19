
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `logon_info`;
CREATE TABLE `logon_info` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `u_id` bigint(10) NOT NULL COMMENT '用户id对应userinfo的u_id',
  `login_name` varchar(255) NOT NULL COMMENT '用户登录名',
  `login_pwd` varchar(255) NOT NULL COMMENT '用户密码',
  `disable_flag` bigint(2) NOT NULL COMMENT '0禁用、1.使用',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `rec_id` (`rec_id`) USING BTREE,
  UNIQUE KEY `u_id` (`u_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `u_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `title` varchar(255) DEFAULT NULL COMMENT '用户中文名',
  `description` varchar(255) DEFAULT NULL COMMENT '用户描述',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `disable_flag` bigint(10) NOT NULL COMMENT '0禁用、1.使用',
  `user_type` bigint(10) NOT NULL COMMENT '是否是超级管理员0不是1是',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `u_id` (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `log_desc` varchar(255) NOT NULL COMMENT '日志描述',
  `log_type` bigint(10) NOT NULL  COMMENT '日志类型',
  `exception_code` varchar(255) NOT NULL COMMENT '用户中文名',
  `exception_detail` varchar(255) NOT NULL COMMENT '用户中文名',
  `request_ip` varchar(255) NOT NULL COMMENT '用户中文名',
  `method` varchar(255) NOT NULL COMMENT '用户中文名',
  `params` varchar(255) NOT NULL COMMENT '用户中文名',
  `operator_id` bigint(10) NOT NULL  COMMENT '日志类型',
  `operate_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `rec_id` (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `consult_detail`;
CREATE TABLE `consult_detail` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `link_id` bigint(10) NOT NULL COMMENT '会话ID',
  `u_id` bigint(10) NOT NULL COMMENT '操作发生人ID',
  `opra` bigint(10) NOT NULL COMMENT '操作：1.加入会话 2.离开会话 3.异常中断 4.断线重连',
  `inviter_id` bigint(10) DEFAULT NULL COMMENT '当操作为1.加入会话时，这里的值表示邀请人ID，其他情况为null',
  `gmt_create` datetime NOT NULL COMMENT '操作发生时间',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `rec_id` (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


-- ----------------------------
DROP TABLE IF EXISTS `consult_total`;
CREATE TABLE `consult_total` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `u_id` bigint(10) NOT NULL COMMENT '发起人ID',
  `bill_num` varchar(2000) NOT NULL COMMENT '单据号',
  `gmt_begin` datetime NOT NULL COMMENT '会诊开始时间',
  `gmt_end` datetime DEFAULT NULL COMMENT '会诊结束时间',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `rec_id` (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `video_play`;
CREATE TABLE `video_play` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `video_id` bigint(10) DEFAULT NULL COMMENT '视频ID',
  `monthe_play` bigint(20) DEFAULT NULL COMMENT '月点击数',
  `totle_play` bigint(20) DEFAULT NULL COMMENT '总点击数',
  `gmt_create` datetime DEFAULT NULL COMMENT '视频上传时间',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `video_id` (`video_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `video_pic`;
CREATE TABLE `video_pic` (
  `pic_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `video_id` bigint(10) DEFAULT NULL COMMENT '视频ID',
  `pic_type` bigint(10) DEFAULT NULL COMMENT '图片类型：1、大图2、小图',
  `pic_path` varchar(255) COMMENT '图片路径',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`pic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `video_category`;
CREATE TABLE `video_category` (
  `category_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '视频类别ID',
  `category_name` varchar(255) DEFAULT NULL COMMENT '类别名称',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL COMMENT '修改时间',
  `disable_flag` bigint(10) DEFAULT '1' COMMENT '有效标志:0无效1有效',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `name_check` (`category_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
-- ----------------------------
DROP TABLE IF EXISTS `video_info`;
CREATE TABLE `video_info` (
  `video_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '视频ID',
  `file_storage_path` varchar(255) DEFAULT NULL COMMENT '视频存储路径',
  `report_id` varchar(255) DEFAULT NULL COMMENT '报告ID',
  `user_id` bigint(10) DEFAULT NULL COMMENT '视频上传人ID',
  `device_id` bigint(10) DEFAULT NULL COMMENT '设备ID',
  `category_id` bigint(10) DEFAULT NULL COMMENT '视频类别ID',
  `video_time` bigint(20) DEFAULT NULL COMMENT '视频时长',
  `video_title` varchar(255) DEFAULT NULL COMMENT '视频标题',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  `disable_flag` bigint(10) DEFAULT '1' COMMENT '有效标志0.无效1.有效',
  PRIMARY KEY (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `report_info`;
CREATE TABLE `report_info` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `check_begin_time` datetime DEFAULT NULL COMMENT '检查开始时间',
  `check_end_time` datetime DEFAULT NULL COMMENT '检查结束时间',
  `check_position` varchar(255) DEFAULT NULL COMMENT '检查部位',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  `patient_id` varchar(255) DEFAULT NULL COMMENT '病人id',
  `report_id` varchar(255) DEFAULT NULL COMMENT '报告id',
  PRIMARY KEY (`rec_id`),
	UNIQUE KEY `report_id` (`report_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `patient_info`;
CREATE TABLE `patient_info` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` varchar(255) DEFAULT NULL COMMENT '年龄',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  `patient_id` varchar(255) DEFAULT NULL COMMENT '病人id',
  `patient_name` varchar(255) DEFAULT NULL COMMENT '病人名称',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `patient_id` (`patient_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `delete_back_up`;
CREATE TABLE `delete_back_up` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `check_begin_time` datetime DEFAULT NULL COMMENT '检查开始时间',
  `check_end_time` datetime DEFAULT NULL COMMENT '检查结束时间',
  `check_position` varchar(255) DEFAULT NULL COMMENT '检查部位',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `patient_id` varchar(255) DEFAULT NULL COMMENT '病人id',
  `report_id` varchar(255) DEFAULT NULL COMMENT '报告id',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `group_info`;
CREATE TABLE `group_info` (
  `group_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '分组ID',
  `content` varchar(2000) DEFAULT NULL COMMENT '分组说明',
  `f_group_id` bigint(10) DEFAULT NULL COMMENT '上级分组ID',
  `group_name` varchar(255) DEFAULT NULL COMMENT ' 分组名称',
  `control_flag` varchar(255) DEFAULT NULL COMMENT '所有人可见、同组可见，不递归下级、同组可见递归下级、所有人可见',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`group_id`),
   UNIQUE KEY `group_name` (`group_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `group_custom_relational`;
CREATE TABLE `group_custom_relational` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `left_id` bigint(10) NOT NULL COMMENT '左边对应id',
  `left_type` varchar(255) NOT NULL COMMENT ' 左边对应类型',
  `right_id` bigint(10) NOT NULL COMMENT '右边对应id',
  `right_type` varchar(255) NOT NULL COMMENT '右边对应类型',
  `recursive` varchar(255) NOT NULL COMMENT '是否递归',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `mac_address`;
CREATE TABLE `mac_address` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `mac_address` varchar(255) DEFAULT NULL COMMENT 'mac地址',
  `zip_name` varchar(1000) DEFAULT NULL COMMENT '压缩文件名称',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  `update_version` varchar(255) DEFAULT NULL COMMENT '更新版本',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `rec_id` (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `monitor_client_mapping`;
CREATE TABLE `monitor_client_mapping` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `client_uid` bigint(10) DEFAULT NULL COMMENT '诊室端的uId（对应user_info的u_id）',
  `monitor_uid` bigint(10) DEFAULT NULL COMMENT '监控端uId（对应user_info的u_id）',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources` (
  `resource_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '资源编号',
  `resource_type` varchar(100) DEFAULT NULL COMMENT '资源类型,（url或者模块对应用户模块表迁移过来）',
  `resource_name` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `resource_desc` varchar(200) DEFAULT NULL COMMENT '描述',
  `resource_path` varchar(200) DEFAULT NULL COMMENT '资源路径',
  `priority` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `use_flag` bigint(2) DEFAULT NULL COMMENT '有效标志',
  `sys_flag` bigint(2) DEFAULT NULL COMMENT '是否是系统默认资源(0不可以更改 1.可更改) ',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`resource_id`),
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles` (
  `role_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(200) DEFAULT NULL COMMENT '描述',
  `use_flag` bigint(2) DEFAULT NULL COMMENT '0不能使用、1使用',
  `role_issys` bigint(2) DEFAULT NULL COMMENT '是否是管理员 0不是  1是',
  `sys_flag` bigint(2) DEFAULT NULL COMMENT '是否是系统默认资源(0不可以更改 1.可更改) ',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_id` (`role_id`) USING BTREE ,
   UNIQUE KEY `role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `sys_roles_resources`;
CREATE TABLE `sys_roles_resources` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `resource_id` bigint(10) DEFAULT NULL COMMENT '资源id',
  `role_id` bigint(10) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `rec_id` (`rec_id`) USING BTREE,
   UNIQUE KEY `resourceIdRoleId` (`resource_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(10) DEFAULT NULL  COMMENT '角色id',
  `user_id` bigint(10) DEFAULT NULL  COMMENT '用户id',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `rec_id` (`rec_id`) USING BTREE 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `update_config`;
CREATE TABLE `update_config` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `update_version` varchar(255) NOT NULL COMMENT '更新版本',
  `update_path` varchar(255) NOT NULL COMMENT '更新文件',
  `update_type` varchar(255) DEFAULT NULL COMMENT '更新类型',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  `force_type` bigint(2) DEFAULT '0' COMMENT '默认为0 为不强制更新   1、强制更新',
  `sys_info` varchar(255) DEFAULT NULL COMMENT '系统信息',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `rec_id` (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `group_id` bigint(10) DEFAULT NULL COMMENT '分组ID',
  `u_id` bigint(10) DEFAULT NULL COMMENT '用户ID',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `doctor_info`;
CREATE TABLE `doctor_info` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `doctor_name` varchar(255) DEFAULT NULL COMMENT '医生姓名',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL COMMENT '修改时间',
  `kind_id` bigint(10) DEFAULT NULL COMMENT '医生类型对应umt_kind表的kind_id',
  `hospital_name` varchar(255) DEFAULT NULL COMMENT '医院名称',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;


-- 角色为meetingClient的用户才可以为会议主席
DROP TABLE IF EXISTS `meeting_info`;
CREATE TABLE `meeting_info` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会议ID',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  `meeting_name` varchar(255) DEFAULT NULL COMMENT '会议名称',
  `meeting_pass_word` varchar(255) DEFAULT NULL COMMENT '会议密码',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `meetingName` (`meeting_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- 客户端
DROP TABLE IF EXISTS `client_info`;
CREATE TABLE `client_info` (
  `client_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '客户端ID',
  `client_name` varchar(255) DEFAULT NULL COMMENT '客户端名称',
  `mac_address` varchar(255) DEFAULT NULL COMMENT 'mac地址 唯一',
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `mac_address` (`mac_address`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- 客户端属性表

DROP TABLE IF EXISTS `client_property`;
CREATE TABLE `client_property` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_id` bigint(20) NOT NULL COMMENT '客户端ID',
  `property_id` bigint(20) NOT NULL COMMENT '属性ID',
  `property_value` varchar(255) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



-- 属性名称表
DROP TABLE IF EXISTS `property_info`;
CREATE TABLE `property_info` (
  `property_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `property_name_en` varchar(255) NOT NULL COMMENT '属性英文名称',
  `property_name_cn` varchar(255) NOT NULL COMMENT '属性中文名称',
  `property_desc` varchar(255) DEFAULT NULL COMMENT '属性描述',
  `default_value` varchar(255) DEFAULT NULL COMMENT '属性默认值',
  `tag_type` varchar(255) DEFAULT NULL COMMENT '标签分类',
  `disable_flag` bigint(2) NOT NULL COMMENT '使用标志0 失效 1生效',
  `global_flag` bigint(2) NOT NULL COMMENT '是否公共属性配置  0私有配置 1是公共配置 2.都是',
  `customize_flag` bigint(2) NOT NULL COMMENT '能否用户自定义 0 不能 1能',
  `display_order` bigint(10) NOT NULL COMMENT '显示优先级',
  `html_type` bigint(20) NOT NULL COMMENT 'radio,select,checkbox,text',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_mod` datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`property_id`),
  UNIQUE KEY `property_name_en` (`property_name_en`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


-- 属性扩展表extend
DROP TABLE IF EXISTS `property_extend`;
CREATE TABLE `property_extend` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `property_id` bigint(20) NOT NULL COMMENT '属性ID',
  `show_name`varchar(255) DEFAULT NULL COMMENT '显示名称',
  `show_value` varchar(255) DEFAULT NULL COMMENT '显示值',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


-- 联动规则

DROP TABLE IF EXISTS `regular_linkage`;
CREATE TABLE `regular_linkage` (
  `function_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '规则id',
  `property_id` bigint(20) NOT NULL COMMENT '属性ID',
  `property_value`varchar(255) DEFAULT NULL COMMENT '属性值',
  `f_function_id` bigint(20) NOT NULL COMMENT '触发规则ID，0表示当前属性是父节点，是触发条件，其他ID表示当前属性子节点，是执行联动，且ID是父节点的ID',
  PRIMARY KEY (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



-- head签名信息表
DROP TABLE IF EXISTS `access_key_info`;
CREATE TABLE `access_key_info` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `access_key_id` varchar(255) NOT NULL COMMENT '事先知道的一个密匙key',
  `access_key_secret` varchar(255) NOT NULL COMMENT '得到密匙',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- 用户自定义表 用户属性表(用户自定义配置customize_flag=1 global_flag=0，2 控制)
DROP TABLE IF EXISTS `user_defined`;
CREATE TABLE `user_defined` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `u_id` bigint(20) NOT NULL COMMENT '用户的u_id',
  `property_id` bigint(20) NOT NULL COMMENT '属性id',
  `property_value` varchar(255) NOT NULL COMMENT '属性value',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


DROP TABLE IF EXISTS `rcsss_table_stats`;
CREATE TABLE `rcsss_table_stats` (
  `table_name`       VARCHAR(64) DEFAULT NULL COMMENT '表名',
  `counter`          BIGINT (10) DEFAULT '0' COMMENT '更新计数器',
  `last_cols_update` datetime    DEFAULT NULL COMMENT '表结构最近操作时间',
  `last_row_update`  datetime    DEFAULT NULL COMMENT '表数据最近更新时间',
  `last_num_rows`    BIGINT (10) DEFAULT NULL COMMENT '表数据最近统计总数（增删）'
) ENGINE =InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `http_send_failed_resend`;
CREATE TABLE `http_send_failed_resend` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `data` varchar(255) NOT NULL COMMENT '发送的数据内容',
  `flag` bigint(20)   NOT NULL COMMENT '发送成功或者失败标志1->失败  0->成功',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`rec_id`)
) ENGINE =InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `mac_address_login_name`;
CREATE TABLE `mac_address_login_name` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_name` varchar(255) NOT NULL COMMENT '登录名',
  `mac_address` varchar(255) NOT NULL COMMENT 'mac地址',
  PRIMARY KEY (`rec_id`)
) ENGINE =InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `mac_address_login_name`;
CREATE TABLE `mac_address_login_name` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_name` varchar(255) NOT NULL COMMENT '登录名',
  `mac_address` varchar(255) NOT NULL COMMENT 'mac地址',
  PRIMARY KEY (`rec_id`)
) ENGINE =InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `equipment_info`;
CREATE TABLE `equipment_info` (
  `rec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `equipment_sys_name` varchar(255) NOT NULL COMMENT '设备系统名称  pc ios android',
  `equipment_id` varchar(255) NOT NULL COMMENT 'mac地址(或唯一码)',
  `equipment_name` varchar(255) NOT NULL COMMENT '设备名称 华为 iphone',
  `version_info` varchar(255) NOT NULL COMMENT '客户端版本信息  ',
  PRIMARY KEY (`rec_id`)
) ENGINE =InnoDB DEFAULT CHARSET=gbk;


