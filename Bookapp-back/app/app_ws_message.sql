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
-- Table structure for table `ws_message`
--

DROP TABLE IF EXISTS `ws_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ws_message` (
  `msgId` varchar(45) NOT NULL,
  `msgContent` varchar(100) NOT NULL,
  `fromName` varchar(30) NOT NULL,
  `toName` varchar(30) NOT NULL,
  `msgDate` date NOT NULL,
  `msgStatus` int(11) NOT NULL,
  PRIMARY KEY (`msgId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ws_message`
--

LOCK TABLES `ws_message` WRITE;
/*!40000 ALTER TABLE `ws_message` DISABLE KEYS */;
INSERT INTO `ws_message` VALUES ('1607072699880','sdfs','jack','rose','2020-12-04',1),('1607072708896','1213','rose','jack','2020-12-04',1),('1607072754100','s','rose','jack','2020-12-04',1),('1607072764359','ss','rose','jack','2020-12-04',1),('1607072768859','sssss','jack','rose','2020-12-04',1),('1607076287220','555\n','rose','jack','2020-12-04',1),('1607076298687','222\n','jack','rose','2020-12-04',1),('1607188397583','dfg','rose','jack','2020-12-06',1),('1607188479319','sdf','rose','jack','2020-12-06',1),('1607237432439','hello','rose','jack','2020-12-06',1),('1607237467539','hello','jack','rose','2020-12-06',0),('1607237480686','heo','jack','rose','2020-12-06',0);
/*!40000 ALTER TABLE `ws_message` ENABLE KEYS */;
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
