CREATE DATABASE  IF NOT EXISTS `tateti` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tateti`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: tateti
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `idioma`
--

DROP TABLE IF EXISTS `idioma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `idioma` (
  `idIdioma` int NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idIdioma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Idiomas del tateti';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `idioma`
--

LOCK TABLES `idioma` WRITE;
/*!40000 ALTER TABLE `idioma` DISABLE KEYS */;
INSERT INTO `idioma` VALUES (1,'Ingles'),(2,'Español'),(3,'Portugués');
/*!40000 ALTER TABLE `idioma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugador`
--

DROP TABLE IF EXISTS `jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jugador` (
  `idjugador` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idjugador`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador`
--

LOCK TABLES `jugador` WRITE;
/*!40000 ALTER TABLE `jugador` DISABLE KEYS */;
INSERT INTO `jugador` VALUES (1,'uwu'),(2,'w'),(3,'1'),(4,'jaz'),(5,'jazminn'),(6,'jaz'),(7,'jenry'),(8,'lula'),(9,'lula'),(10,'uwu'),(11,'joel'),(12,'magi'),(13,'magi'),(14,'magi'),(15,'magi'),(16,'jaz');
/*!40000 ALTER TABLE `jugador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensajexidioma`
--

DROP TABLE IF EXISTS `mensajexidioma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensajexidioma` (
  `id_Mensaje` int NOT NULL,
  `id_idioma` int NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`id_Mensaje`,`id_idioma`),
  KEY `id_mensajes_idx` (`id_idioma`),
  CONSTRAINT `id_mensajes` FOREIGN KEY (`id_idioma`) REFERENCES `idioma` (`idIdioma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensajexidioma`
--

LOCK TABLES `mensajexidioma` WRITE;
/*!40000 ALTER TABLE `mensajexidioma` DISABLE KEYS */;
INSERT INTO `mensajexidioma` VALUES (1,1,'Welcome to the game TATETI'),(1,2,'Bienvenido al juego TATETI'),(1,3,'Bem vindo ao jogo TATETI'),(2,1,'Enter player name'),(2,2,'Ingrese el nombre del jugador'),(2,3,'Digite o nome do jogador'),(3,1,'it\'s the computer\'s turn'),(3,2,'es el turno de la computadora'),(3,3,'é a vez do computador'),(4,1,'it\'s your turn'),(4,2,'es tu turno'),(4,3,'É a sua vez'),(5,1,'the computer won'),(5,2,'la computadora ganó'),(5,3,'o computador ganhou'),(6,1,'the player won'),(6,2,'ganó el jugador'),(6,3,'o jogador ganhou'),(7,1,'Thanks for playing !'),(7,2,'Gracias por jugar !'),(7,3,'Obrigado por jogar !'),(8,1,'Play'),(8,2,'Jugar'),(8,3,'Jogar'),(9,1,'See stats'),(9,2,'Ver estadísticas'),(9,3,'Ver estatísticas'),(10,1,'See statistics by player'),(10,2,'Ver estadisticas por jugador'),(10,3,'Veja as estatísticas por jogador'),(11,1,'Tie'),(11,2,'Empate'),(11,3,'Empate'),(12,1,'Player piece'),(12,2,'Pieza del jugador'),(12,3,'Peça do jogador'),(13,1,'Computer piece'),(13,2,'Pieza de la computadora'),(13,3,'Peça do computador'),(14,1,'You can only enter numbers from 1 to 3'),(14,2,'Solo puede ingresar numeros del 1 al 3'),(14,3,'Você só pode inserir números de 1 a 3'),(15,1,'Enter column (numbers from 1 to 3)'),(15,2,'Ingrese la columna (números del 1 al 3)'),(15,3,'Digite a coluna (números de 1 a 3)'),(16,1,'Enter row (numbers from 1 to 3)'),(16,2,'Ingrese la fila  (números del 1 al 3)'),(16,3,'Digite linha (números de 1 a 3)'),(17,1,'the space is already occupied !'),(17,2,'El espacio  ya esta ocupado !'),(17,3,'o espaço já está ocupado !'),(18,1,'These are the statistics of'),(18,2,'Estas son las estadisticas de'),(18,3,'Estas são as estatísticas de'),(19,1,'The game started'),(19,2,'La partida inicio'),(19,3,'O jogo começou'),(20,1,'and ended the'),(20,2,'y finalizo el'),(20,3,'e terminou o'),(21,1,'against the machine'),(21,2,'contra la maquina'),(21,3,'contra a máquina'),(22,1,'You are saving your game..'),(22,2,'Estas guardando tu partida..'),(22,3,'Você está salvando seu jogo..'),(23,1,'\r\nEnter your name below'),(23,2,'A continuación ingresa tu nombre'),(23,3,'Digite seu nome abaixo'),(24,1,'Registered successfully'),(24,2,'Se registró correctamente'),(24,3,'Registrado com sucesso'),(25,1,'won'),(25,2,'ganó'),(25,3,'Ganhou'),(26,1,'lost'),(26,2,'perdió'),(26,3,'perdido'),(27,1,'tied'),(27,2,'empató'),(27,3,'amarrado'),(28,1,'Leave'),(28,2,'Salir'),(28,3,'Sair'),(29,1,'\r\nYou have no recorded statistics'),(29,2,'No tienes estadísticas registradas'),(29,3,'Você não tem estatísticas registradas'),(30,1,'General statistics..'),(30,2,'Estadísticas generales..'),(30,3,'Estatísticas gerais..'),(31,1,'You can only enter numbers from 1 to 5.'),(31,2,'solo puede ingresar numeros del 1 al 5'),(31,3,'Você só pode inserir números de 1 a 5.'),(32,1,'Change language'),(32,2,'Cambiar idioma'),(32,3,'Mudar idioma'),(33,1,'Select your language'),(33,2,'Seleccione su idioma'),(33,3,'selecione sua lingua'),(34,1,'The player does not exist'),(34,2,'No existe el jugador'),(34,3,'O jogador não existe');
/*!40000 ALTER TABLE `mensajexidioma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrodepartida`
--

DROP TABLE IF EXISTS `registrodepartida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registrodepartida` (
  `idregistrodepartida` int NOT NULL AUTO_INCREMENT,
  `InicioDePartida` datetime NOT NULL,
  `FinDePartida` datetime NOT NULL,
  `Gano` tinyint(1) NOT NULL,
  `idjugador` int NOT NULL,
  PRIMARY KEY (`idregistrodepartida`),
  KEY `registrodepartida_idjugador_jugador` (`idjugador`),
  CONSTRAINT `registrodepartida_idjugador_jugador` FOREIGN KEY (`idjugador`) REFERENCES `jugador` (`idjugador`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrodepartida`
--

LOCK TABLES `registrodepartida` WRITE;
/*!40000 ALTER TABLE `registrodepartida` DISABLE KEYS */;
INSERT INTO `registrodepartida` VALUES (15,'2022-11-14 18:06:18','2022-11-14 18:06:40',1,1),(16,'2022-11-14 18:07:41','2022-11-14 18:07:50',0,2),(18,'2022-11-14 18:25:30','2022-11-14 18:25:39',0,4),(19,'2022-11-14 20:58:37','2022-11-14 20:58:42',1,5),(20,'2022-11-14 21:18:47','2022-11-14 21:18:57',1,6),(21,'2022-11-14 21:20:31','2022-11-14 21:20:37',1,7),(22,'2022-11-14 21:28:33','2022-11-14 21:28:40',1,8),(23,'2022-11-14 21:29:22','2022-11-14 21:29:51',1,9),(24,'2022-11-15 16:21:51','2022-11-15 16:21:56',1,10),(25,'2022-11-15 16:23:31','2022-11-15 16:23:47',0,11),(26,'2022-11-15 16:24:18','2022-11-15 16:24:36',1,12),(27,'2022-11-15 16:24:38','2022-11-15 16:24:55',1,13),(28,'2022-11-15 16:25:00','2022-11-15 16:25:26',1,14),(29,'2022-11-15 16:25:28','2022-11-15 16:25:49',2,15),(30,'2022-11-15 23:48:18','2022-11-15 23:48:40',1,16);
/*!40000 ALTER TABLE `registrodepartida` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-27 14:04:11
