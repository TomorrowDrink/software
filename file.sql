/*
Navicat MySQL Data Transfer

Source Server         : �˽���
Source Server Version : 50133
Source Host           : localhost:3306
Source Database       : user

Target Server Type    : MYSQL
Target Server Version : 50133
File Encoding         : 65001

Date: 2018-01-10 13:47:44
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `file`
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(11) NOT NULL,
  `filename` varchar(100) NOT NULL,
  `astype` varchar(50) NOT NULL,
  `state` varchar(50) CHARACTER SET gb2312 NOT NULL,
  `taskname` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('1512190425', '20180107174554_paper1512190425zaq.doc', 'Paper', '未审核', 'lll');
INSERT INTO `file` VALUES ('1512190425', '20180107174610_paper1512190425zxc.doc', 'Paper', '未审核', 'lll');
INSERT INTO `file` VALUES ('1512190425', '20180107174616_paper1512190425zaq.doc', 'Paper', '未审核', 'lll');
INSERT INTO `file` VALUES ('1512190425', '20180107174633_assignment1512190425dfv.doc', 'Assignment', '未审核', 'rrr');
INSERT INTO `file` VALUES ('1512190425', '20180107174741_assignment1512190425asd.doc', 'Assignment', '未审核', 'rrr');
INSERT INTO `file` VALUES ('1512190425', '20180107174803_review1512190425paper.doc', 'Review', '未审核', 'www');
INSERT INTO `file` VALUES ('1512190425', '20180107175331_openingreport1512190425dfv.doc', 'OpeningReport', '未审核', 'kkk');
INSERT INTO `file` VALUES ('1512190425', '20180107175357_openingreport1512190425zaq.doc', 'OpeningReport', '未审核', 'kkk');
INSERT INTO `file` VALUES ('1512190425', '20180107175422_literature1512190425asd.doc', 'Literature', '未审核', 'fff');
INSERT INTO `file` VALUES ('1512190425', '20180107175448_process1512190425process.doc', 'Process', '未审核', 'ggg');
INSERT INTO `file` VALUES ('1512190425', '20180107175456_midterm1512190425zxc.doc', 'Midterm', '未审核', 'zzz');
INSERT INTO `file` VALUES ('1512190425', '20180110133739_assignment1512190425zaq.doc', 'Assignment', '未审核', 'rrr');
