-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: esientradas
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `espectaculo`
--

DROP TABLE IF EXISTS `espectaculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `espectaculo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `artista` varchar(255) DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `escenario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK17g74ktcpvkkg8ia2io7ti3jr` (`escenario_id`),
  CONSTRAINT `FK17g74ktcpvkkg8ia2io7ti3jr` FOREIGN KEY (`escenario_id`) REFERENCES `escenario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `espectaculo`
--

LOCK TABLES `espectaculo` WRITE;
/*!40000 ALTER TABLE `espectaculo` DISABLE KEYS */;
INSERT INTO `espectaculo` VALUES (1,'Banda Sinfónica Nacional','2026-03-01 20:00:00.000000',1),(2,'Orquesta Filarmónica','2026-03-05 19:30:00.000000',1),(3,'Compañía Teatro Clásico','2026-03-07 18:00:00.000000',2),(4,'Grupo Jazz Contemporáneo','2026-03-10 21:00:00.000000',3),(5,'Festival Indie Rock','2026-03-12 22:00:00.000000',4),(6,'Concierto Pop Internacional','2026-03-15 21:30:00.000000',4),(7,'Recital de Piano','2026-03-18 19:00:00.000000',1),(8,'Obra Experimental','2026-03-20 20:00:00.000000',3),(9,'Espectáculo Flamenco','2026-03-22 21:00:00.000000',2),(10,'Festival Electrónica','2026-03-25 23:00:00.000000',4),(11,'Monólogo Humorístico','2026-03-27 20:30:00.000000',2),(12,'Concierto Coral','2026-03-29 18:00:00.000000',1),(13,'Show Infantil','2026-04-02 17:00:00.000000',5),(14,'Concierto Blues','2026-04-05 21:00:00.000000',3),(15,'Festival Folklórico','2026-04-08 20:00:00.000000',5),(16,'Rock Clásico en Vivo','2026-04-12 21:30:00.000000',4),(17,'Teatro Moderno','2026-04-15 19:30:00.000000',2),(18,'Danza Contemporánea','2026-04-18 20:00:00.000000',3),(19,'Ópera de Cámara','2026-04-22 19:00:00.000000',1),(20,'Gran Concierto Final','2026-04-25 22:00:00.000000',4);
/*!40000 ALTER TABLE `espectaculo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-09  0:19:36
