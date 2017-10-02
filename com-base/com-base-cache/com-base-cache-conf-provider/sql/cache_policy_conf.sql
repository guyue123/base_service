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
DROP TABLE IF EXISTS `cache_policy_conf`;
CREATE TABLE `cache_policy_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `chche_id` varchar(255) DEFAULT NULL COMMENT '缓存ID',
  `cache_group` varchar(255) DEFAULT NULL COMMENT '缓存组',
  `cache_desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `policy_type` varchar(255) DEFAULT NULL COMMENT '策略类型：sql,sql_row,table,class',
  `policy` varchar(500) DEFAULT NULL COMMENT '策略内容',
  `policy_status` int(1) DEFAULT NULL COMMENT '状态：1有效，0无效',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `db_type` varchar(255) DEFAULT NULL COMMENT '数据库类型：1mysql，2 oracle',
  `db_info` varchar(255) DEFAULT NULL COMMENT '数据库信息：key=value#key=value',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cache_policy_conf
-- ----------------------------
INSERT INTO `cache_policy_conf` VALUES ('1', 'ZZZ.CACHE_POLICY_CONF', 'CIS', '缓存配置表信息', 'table', 'SELECT * FROM cache_policy_conf', '1', '2017-03-29 15:35:17', '2017-03-29 15:35:17', '1', 'driver=com.mysql.jdbc.Driver#url=jdbc:mysql://localhost:3306/zzz?useUnicode=true&characterEncoding=UTF-8#username=root#password=admin');
