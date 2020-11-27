/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.7.30 : Database - fastanswer
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fastanswer` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `fastanswer`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(512) DEFAULT NULL COMMENT '评论内容',
  `question_id` bigint(20) unsigned DEFAULT NULL COMMENT '问题id',
  `root_id` bigint(20) DEFAULT NULL COMMENT '根评论id（一级评论为空）',
  `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '父评论id（一级评论为空）',
  `commentator` bigint(20) unsigned DEFAULT NULL COMMENT '评论人id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评论表';

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `question_id` bigint(20) unsigned DEFAULT NULL COMMENT '问题id',
  `parent_comment_id` bigint(20) unsigned DEFAULT NULL COMMENT '父评论id',
  `comment_id` bigint(20) unsigned DEFAULT NULL COMMENT '评论id',
  `notifier` bigint(20) unsigned DEFAULT NULL COMMENT '通知者',
  `receiver` bigint(20) unsigned DEFAULT NULL COMMENT '接收者',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否被阅读',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='通知表';

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '问题标题',
  `description` text COMMENT '问题描述',
  `tag` varchar(104) DEFAULT NULL COMMENT '问题标签',
  `creator` bigint(20) unsigned DEFAULT NULL COMMENT '问题发起人id',
  `view_count` int(11) unsigned DEFAULT NULL COMMENT '阅读数',
  `comment_count` int(11) unsigned DEFAULT NULL COMMENT '评论数',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='问题表';

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `github_id` bigint(20) unsigned DEFAULT NULL COMMENT 'GitHub ID',
  `github_name` varchar(50) DEFAULT NULL COMMENT 'GitHub name',
  `github_html_url` varchar(100) DEFAULT NULL COMMENT 'GitHub html url',
  `github_avatar_url` varchar(100) DEFAULT NULL COMMENT 'GitHub avatar url',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
