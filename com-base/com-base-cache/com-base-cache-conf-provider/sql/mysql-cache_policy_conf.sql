/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : zzz

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2017-03-31 17:40:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cache_policy_conf
-- ----------------------------
DROP TABLE IF EXISTS `CACHE_POLICY_CONF`;
CREATE TABLE `CACHE_POLICY_CONF` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CACHE_ID` varchar(255) DEFAULT NULL COMMENT '缓存ID',
  `CACHE_GROUP` varchar(255) DEFAULT NULL COMMENT '缓存组',
  `CACHE_DESC` varchar(255) DEFAULT NULL COMMENT '描述',
  `POLICY_TYPE` varchar(255) DEFAULT NULL COMMENT '策略类型：sql,sql_row,table,class',
  `POLICY` varchar(500) DEFAULT NULL COMMENT '策略内容',
  `POLICY_STATUS` int(1) DEFAULT NULL COMMENT '状态：1有效，0无效',
  `CREATE_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `DB_TYPE` varchar(255) DEFAULT NULL COMMENT '数据库类型：1mysql，2 oracle',
  `DB_INFO` varchar(255) DEFAULT NULL COMMENT '数据库信息：key=value#key=value',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cache_policy_conf
-- ----------------------------
INSERT INTO `CACHE_POLICY_CONF` VALUES ('1', 'ZZZ.CACHE_POLICY_CONF', 'CIS', '缓存配置表信息', 'table', 'SELECT * FROM cache_policy_conf', '0', '2017-03-29 15:35:17', '2017-03-29 15:35:17', '1', 'driver=com.mysql.jdbc.Driver#url=jdbc:mysql://localhost:3306/zzz?useUnicode=true&characterEncoding=UTF-8#username=root#password=admin');
