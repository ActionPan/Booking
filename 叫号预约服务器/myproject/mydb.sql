/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.6.10 : Database - mydb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mydb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mydb`;

/*Table structure for table `busin` */

DROP TABLE IF EXISTS `busin`;

CREATE TABLE `busin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `room1` int(11) DEFAULT '0',
  `room2` int(11) DEFAULT '0',
  `start_time` varchar(45) DEFAULT NULL,
  `over_time` varchar(45) DEFAULT NULL,
  `rese_sum` int(11) DEFAULT '0',
  `room1_num` int(11) DEFAULT '0',
  `room2_num` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `busin` */

insert  into `busin`(`id`,`username`,`room1`,`room2`,`start_time`,`over_time`,`rese_sum`,`room1_num`,`room2_num`) values (1,'linpansen',30,15,'2014/5/18','2014/6/18',23,13,10),(2,'panruigang',30,15,'2014/5/18','2014/6/18',0,0,0);

/*Table structure for table `reserve` */

DROP TABLE IF EXISTS `reserve`;

CREATE TABLE `reserve` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personalname` varchar(45) DEFAULT NULL,
  `companyname` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `reserve` */

insert  into `reserve`(`id`,`personalname`,`companyname`,`time`) values (1,'linpansen','linpansen','2014/5/29'),(2,'panruigang','linpansen','2012/6/2');

/*Table structure for table `user1` */

DROP TABLE IF EXISTS `user1`;

CREATE TABLE `user1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(18) DEFAULT NULL,
  `password` varchar(18) DEFAULT NULL,
  `name` varchar(18) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `user1` */

insert  into `user1`(`id`,`username`,`password`,`name`,`sex`) values (1,'linpansen','linpansen','林潘森','男'),(5,'er2','asd','lkjlk','asdf'),(6,'er2','asd','lkjlk','asdf'),(7,'1321','undefined','asdf','asd');

/*Table structure for table `user2` */

DROP TABLE IF EXISTS `user2`;

CREATE TABLE `user2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(18) DEFAULT NULL,
  `password` varchar(18) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user2` */

insert  into `user2`(`id`,`username`,`password`,`name`,`address`) values (1,'linpansen','linpansen','马沥必胜客','广东机电职业技术学院北校');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
