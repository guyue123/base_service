/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.8
Source Server Version : 50152
Source Host           : 192.168.1.8:3306
Source Database       : wp_admin

Target Server Type    : MYSQL
Target Server Version : 50152
File Encoding         : 65001

Date: 2015-07-30 16:32:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `SCHEDULER_CONFIG`
-- ----------------------------
DROP TABLE IF EXISTS `SCHEDULER_CONFIG`;
CREATE TABLE `SCHEDULER_CONFIG` (
  `JOB_ID` varchar(64) NOT NULL COMMENT '任务ID',
  `TRIGGER_ID` varchar(64) NOT NULL COMMENT '触发ID',
  `GROUP_ID` varchar(64) NOT NULL COMMENT '任务组ID',
  `JOB_CLASS` varchar(256) NOT NULL COMMENT '任务类路径',
  `CRON` varchar(64) NOT NULL COMMENT '执行周期，整数或cron格式',
  `DES` varchar(256) DEFAULT NULL COMMENT '任务描叙',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '任务状态1有效 0 无效',
  `RUN_GROUP` varchar(32) NOT NULL DEFAULT 'RUN' COMMENT '运行分组，用于区分在某个点是否运行此任务',
  `RUN_PROPS` varchar(4000) DEFAULT NULL COMMENT '运行参数，运行属性键值对使用换行符分割',
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `CREATEUSER` varchar(100) DEFAULT NULL COMMENT '创建人',
  `MODIFYTIME` datetime DEFAULT NULL COMMENT '修改时间',
  `MODIFYUSER` varchar(100) DEFAULT NULL COMMENT '修改人',
  `REMARK` varchar(2000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`JOB_ID`,`TRIGGER_ID`,`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度配置表';

-- ----------------------------
-- Records of SCHEDULER_CONFIG
-- ----------------------------
INSERT INTO `SCHEDULER_CONFIG` VALUES ('TEST_JOB_ID', 'TRIGGER_TEST_JOB_ID', 'TEST', 'com.base.job.test.TestJob', '*/5 * * * * ?', '任务', '1', 'RUN', null, '2015-07-06 16:40:13', 'admin', '2015-07-06 17:40:13', 'admin', null);