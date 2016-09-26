CREATE DATABASE  IF NOT EXISTS `peertopeer` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `peertopeer`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: peertopeer
-- ------------------------------------------------------
-- Server version	5.6.24-log

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
-- Table structure for table `songs`
--

DROP TABLE IF EXISTS `songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `songs` (
  `idsongs` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `artist` varchar(45) DEFAULT NULL,
  `album` varchar(45) DEFAULT NULL,
  `genre` varchar(45) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `hash` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`idsongs`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songs`
--

LOCK TABLES `songs` WRITE;
/*!40000 ALTER TABLE `songs` DISABLE KEYS */;
INSERT INTO `songs` VALUES (1,'requiem','mozart','unknown','classical',1791,'9a1be396c886d97a60f5d4f9d6cd1f08'),(2,'four seasons','vivaldi','unknown','classical',1725,'425f51ff63ff6621819dedb448704dba'),(3,'temple of the king','\"\"','ritchie blackmore\'s rainbow','classical  rock',1975,'a6369ea7a1fd7fb1fc7c055218f894ac'),(4,'holy diver','dio','holy diver','heavy metal',1983,'5cc1847e0788eb9aaf7cab43460a22b5'),(5,'vodka','korpiklaani','karkelo','folk metal',NULL,'97ffd653375a1d2aadda9206c92b03c0'),(6,'\"\"','miles davis','\"\"','\"\"',NULL,'82f7d372319db9b5f0b54e2c8517dd81'),(7,'i don\'t want to set the world on fire','the ink spots','smoothie','jazz',1941,'8539ef14ef46a611d346ad15f32d784d');
/*!40000 ALTER TABLE `songs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-20  3:00:31
