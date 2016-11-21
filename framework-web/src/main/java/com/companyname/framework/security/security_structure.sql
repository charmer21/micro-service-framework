SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sec_log`
-- ----------------------------
DROP TABLE IF EXISTS `sec_log`;
CREATE TABLE `sec_log` (
  `ID` varchar(38) NOT NULL COMMENT '日志标识',
  `USER_ID` varchar(38) NOT NULL COMMENT '用户标识',
  `LOG_TYPE` varchar(10) NOT NULL DEFAULT 'login' COMMENT '日志类型（login、operation、auth）',
  `USER_NAME` varchar(50) NOT NULL COMMENT '用户名称',
  `OPERATION_NAME` varchar(50) NOT NULL COMMENT '操作名称',
  `OPERATION_TIME` datetime NOT NULL COMMENT '操作时间',
  `DESCRIPTION` varchar(3000) DEFAULT NULL COMMENT '日志描述',
  PRIMARY KEY (`ID`),
  KEY `IDX_SEC_LOG_TYPE` (`LOG_TYPE`),
  KEY `IDX_SEC_LOG_USERID` (`USER_ID`),
  KEY `IDX_SEC_LOG_USERNAME` (`USER_NAME`),
  KEY `IDX_SEC_LOG_OPERATIONNAME` (`OPERATION_NAME`),
  KEY `IDX_SEC_LOG_OPERATIONTIME` (`OPERATION_TIME`),
  CONSTRAINT `FK_REFERENCE_24` FOREIGN KEY (`USER_ID`) REFERENCES `sec_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='安全日志(包括登录日志、操作日志和授权日志)';

-- ----------------------------
--  Table structure for `sec_org_role`
-- ----------------------------
DROP TABLE IF EXISTS `sec_org_role`;
CREATE TABLE `sec_org_role` (
  `ORG_ID` varchar(38) NOT NULL COMMENT '组织标识',
  `ROLE_ID` varchar(38) NOT NULL COMMENT '具备的角色（只能是管理类角色和混合类角色）',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`ORG_ID`,`ROLE_ID`),
  KEY `INX_ORGROLE_ORG` (`ORG_ID`),
  KEY `INX_ORGROLE_ROLE` (`ROLE_ID`),
  CONSTRAINT `FK_REFERENCE_14` FOREIGN KEY (`ROLE_ID`) REFERENCES `sec_role` (`ID`),
  CONSTRAINT `FK_SEC_ORGR_REFERENCE_SEC_ORG2` FOREIGN KEY (`ORG_ID`) REFERENCES `sec_organization` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='组织角色表，存储组织在组织上充当的角色。';

-- ----------------------------
--  Table structure for `sec_organization`
-- ----------------------------
DROP TABLE IF EXISTS `sec_organization`;
CREATE TABLE `sec_organization` (
  `ID` varchar(38) NOT NULL COMMENT '组织标识',
  `PATH` varchar(300) NOT NULL,
  `PARENT` varchar(38) DEFAULT NULL COMMENT '父组织标识',
  `NAME` varchar(150) DEFAULT NULL COMMENT '名称',
  `CODE` varchar(1000) DEFAULT NULL COMMENT '编码',
  `LEVEL` int(11) DEFAULT NULL COMMENT '层级',
  `ORDER` int(11) DEFAULT NULL COMMENT '顺序',
  `TYPE` int(11) DEFAULT NULL COMMENT '类型（公司、子公司、部门、科室等）',
  `STATUS` varchar(10) NOT NULL DEFAULT 'enabled' COMMENT '状态',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`ID`),
  KEY `INX_SEC_INDEX_STATUS` (`STATUS`),
  KEY `FK_REFERENCE_11` (`PARENT`),
  CONSTRAINT `FK_REFERENCE_11` FOREIGN KEY (`PARENT`) REFERENCES `sec_organization` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统组织表';

-- ----------------------------
--  Table structure for `sec_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sec_permission`;
CREATE TABLE `sec_permission` (
  `ID` varchar(38) NOT NULL COMMENT '操作标识',
  `PATH` varchar(300) NOT NULL,
  `PARENT` varchar(38) DEFAULT NULL COMMENT '父操作标识',
  `CODE` varchar(50) NOT NULL COMMENT '操作代码',
  `NAME` varchar(150) NOT NULL COMMENT '名称',
  `CONTEXT` varchar(300) DEFAULT NULL,
  `URL` varchar(300) DEFAULT NULL COMMENT 'Page对应的URL，或者页面元素所在的URL',
  `TYPE` varchar(10) NOT NULL DEFAULT 'Page' COMMENT '模块、页面或者元素',
  `ELEMENT_ID` varchar(50) DEFAULT NULL COMMENT '受控页面元素的ID',
  `ELEMENT_BEHAVIOUR` varchar(30) DEFAULT NULL COMMENT '页面元素默认行为',
  `ORDER` int(11) DEFAULT '1000' COMMENT '顺序',
  `DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '描述',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_PERMISSIONS_CODE` (`CODE`),
  KEY `IDX_PERMISSIONS_URL` (`URL`(255)),
  KEY `LDX_PERMISSIONS_TYPE` (`TYPE`),
  KEY `FK_SEC_PERM_PARENTPER_SEC_PERM` (`PARENT`),
  CONSTRAINT `FK_SEC_PERM_PARENTPER_SEC_PERM` FOREIGN KEY (`PARENT`) REFERENCES `sec_permission` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='操作权限表';

-- ----------------------------
--  Table structure for `sec_permission_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sec_permission_resource`;
CREATE TABLE `sec_permission_resource` (
  `ID` varchar(38) NOT NULL COMMENT '操作资源标识',
  `PERMISSION_ID` varchar(38) NOT NULL COMMENT '操作标识',
  `TYPE` varchar(30) NOT NULL DEFAULT 'URL' COMMENT '资源类型，默认是Url，将来可能有其他类型',
  `RESOURCE` varchar(300) NOT NULL COMMENT '资源',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_PERMISSION_RESOURCE_01` (`RESOURCE`(255)),
  KEY `FK_REFERENCE_34` (`PERMISSION_ID`),
  CONSTRAINT `FK_REFERENCE_34` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `sec_permission` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='权限资源表';

-- ----------------------------
--  Table structure for `sec_permission_rule`
-- ----------------------------
DROP TABLE IF EXISTS `sec_permission_rule`;
CREATE TABLE `sec_permission_rule` (
  `ID` varchar(38) NOT NULL COMMENT '规则标识',
  `OPERATION_ID` varchar(38) NOT NULL COMMENT '操作标识',
  `NAME` varchar(150) NOT NULL COMMENT '名称',
  `PRIORITY` int(11) NOT NULL DEFAULT '100' COMMENT '优先级',
  `RULE` varchar(1000) NOT NULL COMMENT '规则',
  `BEHAVIOUR` varchar(30) DEFAULT NULL COMMENT '行为',
  `DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '描述',
  `IS_DEFAULT` int(11) NOT NULL DEFAULT '0' COMMENT '是否缺省规则',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`ID`),
  KEY `FK_SEC_SECU_BELONGTOO_SEC_PERM` (`OPERATION_ID`),
  CONSTRAINT `FK_SEC_SECU_BELONGTOO_SEC_PERM` FOREIGN KEY (`OPERATION_ID`) REFERENCES `sec_permission` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='权限规则表';

-- ----------------------------
--  Table structure for `sec_role`
-- ----------------------------
DROP TABLE IF EXISTS `sec_role`;
CREATE TABLE `sec_role` (
  `ID` varchar(38) NOT NULL COMMENT '角色标识',
  `CODE` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `NAME` varchar(150) NOT NULL COMMENT '角色名',
  `DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '描述',
  `TYPE` smallint(6) NOT NULL DEFAULT '0' COMMENT '角色类型（管理类、流转类、混合类）',
  `PARTICIPANT_TYPE` smallint(6) DEFAULT NULL COMMENT '参与者类型（用户、部门）',
  `BELONGTO_SYS_ID` smallint(6) DEFAULT '1' COMMENT '所属系统1客服中心2呼叫中心',
  `BELONGTO_SYS_NAME` varchar(50) DEFAULT NULL COMMENT '所属系统名称',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`ID`),
  KEY `INX_ROLES_TYPE` (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统角色表，对工作流而言是角色类型';

-- ----------------------------
--  Table structure for `sec_role_inheritance`
-- ----------------------------
DROP TABLE IF EXISTS `sec_role_inheritance`;
CREATE TABLE `sec_role_inheritance` (
  `PARENT_ROLE_ID` varchar(38) NOT NULL COMMENT '父角色标识',
  `CHILD_ROLE_ID` varchar(38) NOT NULL COMMENT '子角色标识',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`PARENT_ROLE_ID`,`CHILD_ROLE_ID`),
  KEY `FK_SEC_ROLE_INHERI_CHILD_ROLE` (`CHILD_ROLE_ID`),
  CONSTRAINT `FK_SEC_ROLE_INHERI_CHILD_ROLE` FOREIGN KEY (`CHILD_ROLE_ID`) REFERENCES `sec_role` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `FK_SEC_ROLE_INHERI_ROLE` FOREIGN KEY (`PARENT_ROLE_ID`) REFERENCES `sec_role` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色继承表，记录角色的父子关系';

-- ----------------------------
--  Table structure for `sec_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sec_role_permission`;
CREATE TABLE `sec_role_permission` (
  `ROLE_ID` varchar(38) NOT NULL COMMENT '角色标识',
  `OPERATION_ID` varchar(38) NOT NULL COMMENT '操作标识',
  `RULE_ID` varchar(38) DEFAULT NULL COMMENT '规则标识',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`ROLE_ID`,`OPERATION_ID`),
  KEY `FK_SEC_ROLE_BELONGTOP_SEC_PERM` (`OPERATION_ID`),
  KEY `FK_SEC_ROLE_PERMISSIO_SEC_SECU` (`RULE_ID`),
  CONSTRAINT `FK_ASSIGNTOROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `sec_role` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `FK_SEC_ROLE_BELONGTOP_SEC_PERM` FOREIGN KEY (`OPERATION_ID`) REFERENCES `sec_permission` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `FK_SEC_ROLE_PERMISSIO_SEC_SECU` FOREIGN KEY (`RULE_ID`) REFERENCES `sec_permission_rule` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色权限表';

-- ----------------------------
--  Table structure for `sec_user`
-- ----------------------------
DROP TABLE IF EXISTS `sec_user`;
CREATE TABLE `sec_user` (
  `ID` varchar(38) NOT NULL COMMENT '用户标识',
  `NAME` varchar(50) NOT NULL COMMENT '姓名',
  `TYPE` varchar(10) NOT NULL DEFAULT 'local' COMMENT '类型',
  `LOGIN_ID` varchar(30) NOT NULL COMMENT '账户',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '密码',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '邮件',
  `MOBILE_PHONE` varchar(15) DEFAULT NULL COMMENT '手机',
  `IM` varchar(50) DEFAULT NULL COMMENT '即时通讯号',
  `TELEPHONE` varchar(15) DEFAULT NULL COMMENT '办公电话',
  `SEX` char(1) NOT NULL DEFAULT 'U' COMMENT '性别',
  `BIRTHDAY` datetime DEFAULT NULL COMMENT '生日',
  `STATUS` varchar(10) NOT NULL DEFAULT 'enabled' COMMENT '状态',
  `ORG_ID` varchar(38) NOT NULL COMMENT '所属组织',
  `RANK` varchar(50) DEFAULT NULL COMMENT '职位',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`ID`),
  KEY `FK_SEC_USER_REFERENCE_SEC_ORG1` (`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统用户表';

-- ----------------------------
--  Table structure for `sec_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sec_user_role`;
CREATE TABLE `sec_user_role` (
  `USER_ID` varchar(38) NOT NULL COMMENT '用户标识',
  `ROLE_ID` varchar(38) NOT NULL COMMENT '角色标识（只能是管理类角色和混合类角色）',
  `CREATED_BY` varchar(38) NOT NULL COMMENT '创建人',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATED_BY` varchar(38) NOT NULL COMMENT '最后修改人',
  `LAST_UPDATED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `INX_USERROLE_USER` (`USER_ID`),
  KEY `INX_USERROLE_ROLE` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色表，存储用户在组织上充当的角色。';

SET FOREIGN_KEY_CHECKS = 1;
