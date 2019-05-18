/*
Navicat MySQL Data Transfer

Source Server         : 腾讯云
Source Server Version : 50560
Source Host           : 62.234.110.157:3306
Source Database       : hyway

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2019-05-17 17:06:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_comm_region
-- ----------------------------
DROP TABLE IF EXISTS `t_comm_region`;
CREATE TABLE `t_comm_region` (
  `REGION_ID` double NOT NULL,
  `REGION_CODE` varchar(100) NOT NULL,
  `REGION_NAME` varchar(100) NOT NULL,
  `PARENT_ID` double NOT NULL,
  `REGION_LEVEL` double NOT NULL,
  `REGION_ORDER` double NOT NULL,
  `REGION_NAME_EN` varchar(100) NOT NULL,
  `REGION_SHORTNAME_EN` varchar(10) NOT NULL,
  PRIMARY KEY (`REGION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE `t_sys_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `modify_by` varchar(255) DEFAULT NULL,
  `del_state` int(4) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Table structure for t_sys_dept_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dept_relation`;
CREATE TABLE `t_sys_dept_relation` (
  `ancestor` int(11) DEFAULT NULL COMMENT '祖先节点',
  `descendant` int(11) DEFAULT NULL COMMENT ' 后代节点'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_type` varchar(50) DEFAULT NULL,
  `dict_code` varchar(50) DEFAULT NULL,
  `dict_code_desc` varchar(50) DEFAULT NULL,
  `dict_type_desc` varchar(50) DEFAULT NULL,
  `dict_lang` varchar(50) DEFAULT NULL,
  `dictSort` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统字典表';

-- ----------------------------
-- Table structure for t_sys_oper_record
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_oper_record`;
CREATE TABLE `t_sys_oper_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `data_id` int(11) DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `modify_by` varchar(255) DEFAULT NULL,
  `del_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统操作记录';

-- ----------------------------
-- Table structure for t_sys_resources
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_resources`;
CREATE TABLE `t_sys_resources` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `permission` varchar(255) DEFAULT NULL COMMENT '菜单权限标识',
  `url` varchar(255) DEFAULT NULL COMMENT 'url',
  `method` varchar(255) DEFAULT NULL COMMENT '请求方法',
  `parent_id` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `component` varchar(255) DEFAULT NULL COMMENT 'VUE页面',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `type` varchar(255) DEFAULT NULL COMMENT '菜单类型 （0菜单 1按钮）',
  `path` varchar(255) DEFAULT NULL COMMENT '前端URL',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `modify_by` varchar(255) DEFAULT NULL,
  `del_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `role_code` varchar(255) DEFAULT NULL,
  `role_desc` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `modify_by` varchar(255) DEFAULT NULL,
  `del_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for t_sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_dept`;
CREATE TABLE `t_sys_role_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_resources`;
CREATE TABLE `t_sys_role_resources` (
  `role_id` int(11) DEFAULT NULL,
  `resources_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_route_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_route_config`;
CREATE TABLE `t_sys_route_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` varchar(64) DEFAULT NULL COMMENT '路由id',
  `forward_uri` varchar(64) DEFAULT NULL COMMENT '转发目标uri',
  `route_order` int(11) DEFAULT NULL COMMENT '顺序',
  `route_predicates` varchar(1000) DEFAULT NULL COMMENT '断言字符窜字符串结构：[{\\r\\n                "name":"Path",\\r\\n                "args":{\\r\\n                   "pattern" : "/zy/**"\\r\\n                }\\r\\n              }]''',
  `route_filter` varchar(1000) DEFAULT NULL COMMENT '过滤器字符串集合，字符串结构：{\\r\\n              	"name":"StripPrefix",\\r\\n              	 "args":{\\r\\n              	 	"_genkey_0":"1"\\r\\n              	 }\\r\\n              }''',
  `route_enable` tinyint(11) DEFAULT NULL COMMENT '启用',
  `delete_state` tinyint(11) DEFAULT NULL COMMENT '删除',
  `create_by` varchar(30) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_by` varchar(30) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_route_version
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_route_version`;
CREATE TABLE `t_sys_route_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键、自动增长、版本号',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `modify_by` varchar(255) DEFAULT NULL,
  `del_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL COMMENT '账户名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `true_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `state` varchar(255) DEFAULT NULL COMMENT '用户状态',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `version` int(255) unsigned zerofill DEFAULT NULL COMMENT '0',
  `del_state` varchar(255) DEFAULT NULL COMMENT '删除状态',
  PRIMARY KEY (`id`),
  KEY `up` (`user_name`,`password`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基础表';
