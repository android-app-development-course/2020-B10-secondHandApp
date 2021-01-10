-- MySQL dump 10.13  Distrib 5.7.32, for Linux (x86_64)
--
-- Host: localhost    Database: app
-- ------------------------------------------------------
-- Server version	5.7.32-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods` (
  `goodsid` varchar(45) NOT NULL,
  `publisherid` varchar(45) NOT NULL,
  `images` varchar(255) DEFAULT NULL,
  `publishTime` varchar(45) NOT NULL,
  `price` varchar(15) NOT NULL,
  `title` varchar(45) NOT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `sort` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `university` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`goodsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES ('1605285021990','4','/home/wu/appimages/16052850219651.png','1605285021990','23.5','这是标题2','这是详情2','计算机类','广州','华南师范大学'),('1605285263368','4','/home/wu/appimages/16052852633151.png;/home/wu/appimages/1605285263337mvp.png','1605285263368','23.5','这是标题2','这是详情2',NULL,NULL,NULL),('1605443565035','4','/home/wu/appimages/1605443564753JPEG_20201115_140227.jpg;/home/wu/appimages/1605443564983Screenshot_20201022-130408.png','1605443565035','12.3','ssassdf','sldkjfklasj',NULL,NULL,NULL),('1605444412465','4','/home/wu/appimages/1605444412411Screenshot_20201022-130408.png','1605444412465','213','alkdfjasj','sakjdflkajsf',NULL,NULL,NULL),('1605451776256','4','/home/wu/appimages/16054517760441605451674310.png','1605451776256','33','meiyouzhongwen','meiyou',NULL,NULL,NULL),('1605460193349','4','/home/wu/appimages/16054601931221605451674310.png','1605460193349','13.4','imtitle','imdetail',NULL,NULL,NULL),('1605461381847','4','/home/wu/appimages/16054613818241605452286892.png','1605461381847','12.8','imtitle2','imdetail2','计算机类',NULL,NULL),('1605500204410','4','/home/wu/appimages/1605500204381Screenshot_20201116-121541.png','1605500204410','12.3','imtitle3','imdetail3',NULL,'广州','华南师范大学'),('3','4','/home/wu/appimages/16052551333811.png','1605255133403','这是标题','这是详情',NULL,NULL,'',NULL),('7','4','/home/wu/appimages/16052802025031.png','1605280202524','23.5','这是标题2','这是详情2','计算机类','广州','暨南大学');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-06 17:32:28
