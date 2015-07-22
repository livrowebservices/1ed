-- MySQL dump 10.13  Distrib 5.6.25, for Win64 (x86_64)
--
-- Host: localhost    Database: livro
-- ------------------------------------------------------
-- Server version	5.6.25-log

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
-- Table structure for table `carro`
--

DROP TABLE IF EXISTS `carro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `url_foto` varchar(255) DEFAULT NULL,
  `url_video` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carro`
--

LOCK TABLES `carro` WRITE;
/*!40000 ALTER TABLE `carro` DISABLE KEYS */;
INSERT INTO `carro` VALUES (1,'Tucker 1948','DescriþÒo Tucker 1948','http://www.livroandroid.com.br/livro/carros/classicos/Tucker.png','http://www.livroiphone.com.br/carros/classicos/tucker.mp4','-23.564224','-46.653156','classicos'),(2,'Chevrolet Corvette','DescriþÒo Chevrolet Corvette','http://www.livroandroid.com.br/livro/carros/classicos/Chevrolet_Corvette.png','http://www.livroiphone.com.br/carros/classicos/corvette.mp4','-23.564224','-46.653156','classicos'),(3,'Chevrolet Impala Coupe','DescriþÒo Chevrolet Impala Coupe','http://www.livroandroid.com.br/livro/carros/classicos/Chevrolet_Impala_Coupe.png','http://www.livroiphone.com.br/carros/classicos/chevrolet_impala.mp4','-23.564224','-46.653156','classicos'),(4,'Cadillac Deville Convertible','DescriþÒo Cadillac Deville Convertible','http://www.livroandroid.com.br/livro/carros/classicos/Cadillac_Deville_Convertible.png','http://www.livroiphone.com.br/carros/classicos/cadillac_deville.mp4','-23.564224','-46.653156','classicos'),(5,'Chevrolet Bel-Air','DescriþÒo Chevrolet Bel-Air','http://www.livroandroid.com.br/livro/carros/classicos/Chevrolet_BelAir.png','http://www.livroiphone.com.br/carros/classicos/chevrolet_bel_air.mp4','-23.564224','-46.653156','classicos'),(6,'Cadillac Eldorado','DescriþÒo Cadillac Eldorado','http://www.livroandroid.com.br/livro/carros/classicos/Cadillac_Eldorado.png','http://www.livroiphone.com.br/carros/classicos/cadillac_eldorado.mp4','-23.564224','-46.653156','classicos'),(7,'Ferrari 250 GTO','DescriþÒo Ferrari 250 GTO','http://www.livroandroid.com.br/livro/carros/classicos/Ferrari_250_GTO.png','http://www.livroiphone.com.br/carros/classicos/ferrari_gto.mp4','-23.564224','-46.653156','classicos'),(8,'Dodge Challenger','DescriþÒo Dodge Challenger','http://www.livroandroid.com.br/livro/carros/classicos/Dodge_Challenger.png','http://www.livroiphone.com.br/carros/classicos/dodge_challenger.mp4','','','classicos'),(9,'Camaro SS 1969','DescriþÒo Camaro SS 1969','http://www.livroandroid.com.br/livro/carros/classicos/Camaro_SS.png','http://www.livroiphone.com.br/carros/classicos/camaro_ss.mp4','-23.564224','-46.653156','classicos'),(10,'Ford Mustang 1976','DescriþÒo Ford Mustang 1976','http://www.livroandroid.com.br/livro/carros/classicos/Ford_Mustang.png','http://www.livroiphone.com.br/carros/classicos/ford_mustang.mp4','-23.564224','-46.653156','classicos'),(11,'Ferrari FF','DescriþÒo Ferrari FF','http://www.livroandroid.com.br/livro/carros/esportivos/Ferrari_FF.png','http://www.livroiphone.com.br/carros/esportivos/ferrari_ff.mp4','44.532218','10.864019','esportivos'),(12,'AUDI GT Spyder','DescriþÒo AUDI GT Spyder','http://www.livroandroid.com.br/livro/carros/esportivos/Audi_Spyder.png','http://www.livroiphone.com.br/carros/esportivos/audi_gt.mp4','-23.564224','-46.653156','esportivos'),(13,'Porsche Panamera','DescriþÒo Porsche Panamera','http://www.livroandroid.com.br/livro/carros/esportivos/Porsche_Panamera.png','http://www.livroiphone.com.br/carros/esportivos/porsche_panamera.mp4','-23.564224','-46.653156','esportivos'),(14,'Lamborghini Aventador','DescriþÒo Lamborghini Aventador','http://www.livroandroid.com.br/livro/carros/esportivos/Lamborghini_Aventador.png','http://www.livroiphone.com.br/carros/esportivos/lamborghini_aventador.mp4','-23.564224','-46.653156','esportivos'),(15,'Chevrolet Corvette Z06','DescriþÒo Chevrolet Corvette Z06','http://www.livroandroid.com.br/livro/carros/esportivos/Chevrolet_Corvette_Z06.png','http://www.livroiphone.com.br/carros/esportivos/chevrolet_corvette.mp4','-23.564224','-46.653156','esportivos'),(16,'BMW M5','DescriþÒo BMW M5','http://www.livroandroid.com.br/livro/carros/esportivos/BMW.png','http://www.livroiphone.com.br/carros/esportivos/bmw-m5.mp4','-23.564224','-46.653156','esportivos'),(17,'Renault Megane RS Trophy','DescriþÒo Renault Megane RS Trophy','http://www.livroandroid.com.br/livro/carros/esportivos/Renault_Megane_Trophy.png','http://www.livroiphone.com.br/carros/esportivos/renault_megane.mp4','-23.564224','-46.653156','esportivos'),(18,'Maserati Grancabrio Sport','DescriþÒo Maserati Grancabrio Sport','http://www.livroandroid.com.br/livro/carros/esportivos/Maserati_Grancabrio_Sport.png','http://www.livroiphone.com.br/carros/esportivos/renault_megane.mp4','-23.564224','-46.653156','esportivos'),(19,'McLAREN MP4-12C','DescriþÒo McLAREN MP4-12C','http://www.livroandroid.com.br/livro/carros/esportivos/McLAREN.png','http://www.livroiphone.com.br/carros/esportivos/mcLaren.mp4','-23.564224','-46.653156','esportivos'),(20,'MERCEDES-BENZ C63 AMG','DescriþÒo MERCEDES-BENZ C63 AMG','http://www.livroandroid.com.br/livro/carros/esportivos/MERCEDES_BENZ_AMG.png','http://www.livroiphone.com.br/carros/esportivos/mercedes.mp4','-23.564224','-46.653156','esportivos'),(21,'Bugatti Veyron','DescriþÒo Bugatti Veyron','http://www.livroandroid.com.br/livro/carros/luxo/Bugatti_Veyron.png','http://www.livroiphone.com.br/carros/luxo/bugatti_veyron.mp4','-23.564224','-46.653156','luxo'),(22,'Ferrari Enzo','DescriþÒo Ferrari Enzo','http://www.livroandroid.com.br/livro/carros/luxo/Ferrari_Enzo.png','http://www.livroiphone.com.br/carros/luxo/ferrari_enzo.mp4','-23.564224','-46.653156','luxo'),(23,'Lamborghini Reventon','DescriþÒo Lamborghini Reventon','http://www.livroandroid.com.br/livro/carros/luxo/Lamborghini_Reventon.png','http://www.livroiphone.com.br/carros/luxo/lamborghini _reventon.mp4','-23.564224','-46.653156','luxo'),(24,'Leblanc Mirabeau','DescriþÒo Leblanc Mirabeau','http://www.livroandroid.com.br/livro/carros/luxo/Leblanc_Mirabeau.png','http://www.livroiphone.com.br/carros/luxo/leblanc_mirabeau.mp4','-23.564224','-46.653156','luxo'),(25,'Shelby Supercars Ultimate','DescriþÒo Shelby Supercars Ultimate','http://www.livroandroid.com.br/livro/carros/luxo/Shelby_Supercars_Ultimate.png','http://www.livroiphone.com.br/carros/luxo/shelby.mp4','-23.564224','-46.653156','luxo'),(26,'Pagani Zonda','DescriþÒo Pagani Zonda','http://www.livroandroid.com.br/livro/carros/luxo/Pagani_Zonda.png','http://www.livroiphone.com.br/carros/luxo/pagani_zonda.mp4','-23.564224','-46.653156','luxo'),(27,'Koenigsegg CCX','DescriþÒo Koenigsegg CCX','http://www.livroandroid.com.br/livro/carros/luxo/Koenigsegg_CCX.png','http://www.livroiphone.com.br/carros/luxo/koenigsegg.mp4','-23.564224','-46.653156','luxo'),(28,'Mercedes SLR McLaren','DescriþÒo Mercedes SLR McLaren','http://www.livroandroid.com.br/livro/carros/luxo/Mercedes_McLaren.png','http://www.livroiphone.com.br/carros/luxo/mclaren_slr.mp4','-23.564224','-46.653156','luxo'),(29,'Rolls Royce Phantom','DescriþÒo Rolls Royce Phantom','http://www.livroandroid.com.br/livro/carros/luxo/Rolls_Royce_Phantom.png','http://www.livroiphone.com.br/carros/luxo/rolls_royce.mp4','-23.564224','-46.653156','luxo'),(30,'Lexus LFA','DescriþÒo Lexus LFA','http://www.livroandroid.com.br/livro/carros/luxo/Lexus_LFA.png','http://www.livroiphone.com.br/carros/luxo/lexus.mp4','-23.564224','-46.653156','luxo');
/*!40000 ALTER TABLE `carro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-13 19:51:49
