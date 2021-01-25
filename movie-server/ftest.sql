/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Schema         : ftest

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 24/01/2021 19:44:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for common_file
-- ----------------------------
DROP TABLE IF EXISTS `common_file`;
CREATE TABLE `common_file`  (
  `file_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `file_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of common_file
-- ----------------------------
INSERT INTO `common_file` VALUES (1, 'sadsdsd', 'sadsdsd', 'C:\\Users\\Dell\\Pictures\\_20210114212806.png');
INSERT INTO `common_file` VALUES (4, '6c52a15669742d17d8c130e5099c59b7dc2539faf88dcf1862dcecaa6a46ffe4', 'l2Q5-ks85K1oT1kSfk-sg.jpg', 'D://fileTemp//2021/1/6c52a15669742d17d8c130e5099c59b7dc2539faf88dcf1862dcecaa6a46ffe4.jpg');

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `ima_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `movie_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ima_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie
-- ----------------------------
INSERT INTO `movie` VALUES (1, '1526711273653046.jpg', '哥谭 第四季 Gotham', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (2, '1526726844323744.jpg', '游戏之夜', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (3, '1526727108393284.jpg', '环太平洋：雷霆再起', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (4, '1526727155977248.jpg', '头号玩家', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (5, '1527405023383089.jpg', '良种动物', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (7, '1527405918883501.jpg', '悲惨世界', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (8, '1527410387608938.jpg', '猛龙怪客', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (9, '1527410913000043.jpg', '绿野仙踪', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (10, '1527414992530668.jpg', '苍穹浩瀚', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (11, '1527415066008906.jpg', '犯罪现场调查', '/common/file/view/sadsdsd');
INSERT INTO `movie` VALUES (12, '1527415725066605.jpg', '西部世界', '/common/file/view/sadsdsd');

-- ----------------------------
-- Table structure for rate
-- ----------------------------
DROP TABLE IF EXISTS `rate`;
CREATE TABLE `rate`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `movie_id` bigint(0) NOT NULL,
  `rate` double NOT NULL,
  `user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of rate
-- ----------------------------
INSERT INTO `rate` VALUES (1, 1, 2.5, 1000);
INSERT INTO `rate` VALUES (2, 2, 3.5, 1000);
INSERT INTO `rate` VALUES (3, 3, 4.5, 1000);
INSERT INTO `rate` VALUES (4, 4, 3, 1000);
INSERT INTO `rate` VALUES (5, 5, 2.5, 1000);
INSERT INTO `rate` VALUES (12, 1, 2, 1003);
INSERT INTO `rate` VALUES (13, 2, 3, 1003);
INSERT INTO `rate` VALUES (14, 3, 3, 1003);
INSERT INTO `rate` VALUES (15, 4, 3, 1003);
INSERT INTO `rate` VALUES (16, 5, 3, 1003);
INSERT INTO `rate` VALUES (17, 7, 3, 1003);
INSERT INTO `rate` VALUES (18, 8, 3.5, 1003);
INSERT INTO `rate` VALUES (19, 9, 2, 1003);
INSERT INTO `rate` VALUES (20, 10, 2.5, 1003);
INSERT INTO `rate` VALUES (21, 11, 2.5, 1003);
INSERT INTO `rate` VALUES (22, 12, 3, 1003);
INSERT INTO `rate` VALUES (23, 1, 3.5, 1004);
INSERT INTO `rate` VALUES (24, 2, 3.5, 1004);
INSERT INTO `rate` VALUES (25, 3, 4, 1004);
INSERT INTO `rate` VALUES (26, 4, 5, 1004);
INSERT INTO `rate` VALUES (27, 5, 4, 1004);
INSERT INTO `rate` VALUES (28, 8, 3.5, 1004);
INSERT INTO `rate` VALUES (29, 9, 3.5, 1004);
INSERT INTO `rate` VALUES (30, 7, 2, 1004);
INSERT INTO `rate` VALUES (31, 10, 3.5, 1004);
INSERT INTO `rate` VALUES (32, 11, 3.5, 1004);
INSERT INTO `rate` VALUES (33, 12, 3, 1004);
INSERT INTO `rate` VALUES (34, 7, 5, 1000);
INSERT INTO `rate` VALUES (35, 12, 5, 1000);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1011 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1003, '12345', 'yang1');
INSERT INTO `user` VALUES (1004, '12345', 'aaa');
INSERT INTO `user` VALUES (1000, '12345', '123');

SET FOREIGN_KEY_CHECKS = 1;
