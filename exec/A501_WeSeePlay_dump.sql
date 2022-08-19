-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: i7a501.p.ssafy.io    Database: ssafy_web_db
-- ------------------------------------------------------
-- Server version	5.7.38

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
-- Table structure for table `callmyname_word`
--

DROP TABLE IF EXISTS `callmyname_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `callmyname_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `callmyname_word`
--

LOCK TABLES `callmyname_word` WRITE;
/*!40000 ALTER TABLE `callmyname_word` DISABLE KEYS */;
INSERT INTO `callmyname_word` VALUES (1,'이순신'),(2,'세종대왕'),(3,'주몽'),(4,'원효대사'),(5,'나폴레옹'),(6,'링컨'),(7,'간디'),(8,'아인슈타인'),(9,'베토벤'),(10,'간디'),(11,'파브르'),(12,'퇴계이황'),(13,'유관순'),(14,'원숭이'),(15,'고양이'),(16,'강아지'),(17,'코끼리'),(18,'사자'),(19,'돼지'),(20,'소'),(21,'기린'),(22,'박쥐'),(23,'호랑이'),(24,'토끼'),(25,'거북이'),(26,'개구리'),(27,'빈지노'),(28,'임영웅'),(29,'싸이'),(30,'비비'),(31,'선미'),(32,'태연'),(33,'박재범'),(34,'아이유'),(35,'헤이즈'),(36,'제시'),(37,'청하'),(38,'윤하'),(39,'현아'),(40,'성시경'),(41,'김종국'),(42,'유재석'),(43,'강호동'),(44,'양세찬'),(45,'송지효'),(46,'하하'),(47,'정형돈'),(48,'박명수'),(49,'정준하'),(50,'광희'),(51,'임창정'),(52,'보아'),(53,'창모'),(54,'손흥민'),(55,'백종원'),(56,'원빈'),(57,'수지'),(58,'이효리'),(59,'비'),(60,'신동엽');
/*!40000 ALTER TABLE `callmyname_word` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certification`
--

DROP TABLE IF EXISTS `certification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(30) DEFAULT NULL,
  `certification_check` tinyint(1) DEFAULT '0',
  `register_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certification`
--

LOCK TABLES `certification` WRITE;
/*!40000 ALTER TABLE `certification` DISABLE KEYS */;
/*!40000 ALTER TABLE `certification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certification_pw`
--

DROP TABLE IF EXISTS `certification_pw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certification_pw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(30) DEFAULT NULL,
  `certification_check` tinyint(1) DEFAULT '0',
  `register_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certification_pw`
--

LOCK TABLES `certification_pw` WRITE;
/*!40000 ALTER TABLE `certification_pw` DISABLE KEYS */;
/*!40000 ALTER TABLE `certification_pw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liar_genre`
--

DROP TABLE IF EXISTS `liar_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `liar_genre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `genre` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liar_genre`
--

LOCK TABLES `liar_genre` WRITE;
/*!40000 ALTER TABLE `liar_genre` DISABLE KEYS */;
INSERT INTO `liar_genre` VALUES (1,'음식'),(2,'위인'),(3,'동물'),(4,'직업'),(5,'영화'),(6,'스포츠');
/*!40000 ALTER TABLE `liar_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liar_word`
--

DROP TABLE IF EXISTS `liar_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `liar_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `genre_id` int(11) DEFAULT NULL,
  `word` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `genre_id` (`genre_id`),
  CONSTRAINT `liar_word_ibfk_1` FOREIGN KEY (`genre_id`) REFERENCES `liar_genre` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liar_word`
--

LOCK TABLES `liar_word` WRITE;
/*!40000 ALTER TABLE `liar_word` DISABLE KEYS */;
INSERT INTO `liar_word` VALUES (1,1,'아이스크림'),(2,1,'햄버거'),(3,2,'이순신'),(4,2,'세종대왕'),(5,2,'장영실'),(6,3,'원숭이'),(7,3,'고양이'),(8,3,'강아지'),(9,3,'코끼리'),(10,3,'사자'),(11,1,'커피'),(12,2,'유관순'),(13,2,'안중근'),(14,2,'퇴계이황'),(15,2,'문익점'),(16,2,'의자왕'),(17,2,'광개토대왕'),(18,2,'원효대사'),(19,2,'링컨'),(20,2,'나폴레옹'),(21,2,'간디'),(22,2,'뉴턴'),(23,2,'노벨'),(24,2,'아인슈타인'),(25,2,'베토벤'),(26,2,'셰익스피어'),(27,2,'공자'),(28,2,'파브르'),(29,2,'마더 테레사'),(30,1,'김치'),(31,1,'냉면'),(32,1,'짜장면'),(33,1,'짬뽕'),(34,1,'도토리묵'),(35,1,'치킨'),(36,1,'피자'),(37,1,'회'),(38,1,'만두'),(39,1,'김밥'),(40,1,'떡볶이'),(41,1,'라면'),(42,1,'붕어빵'),(43,1,'떡'),(44,1,'곱창'),(45,1,'우동'),(46,3,'돼지'),(47,3,'소'),(48,3,'기린'),(49,3,'얼룩말'),(50,3,'박쥐'),(51,3,'하마'),(52,3,'호랑이'),(53,3,'앵무새'),(54,3,'토끼'),(55,3,'거북이'),(56,3,'뱀'),(57,3,'잠자리'),(58,3,'개구리'),(59,3,'물고기'),(60,3,'비둘기'),(61,4,'공무원'),(62,4,'감독'),(63,4,'농부'),(64,4,'디자이너'),(65,4,'마술사'),(66,4,'배우'),(67,4,'가수'),(68,4,'바리스타'),(69,4,'개그맨'),(70,4,'성우'),(71,4,'요리사'),(72,4,'의사'),(73,4,'변호사'),(74,4,'기자'),(75,4,'작가'),(76,4,'통역사'),(77,4,'개발자'),(78,4,'프로게이머'),(79,4,'망나니'),(80,4,'머슴'),(81,4,'깡패'),(82,4,'경호원'),(83,4,'장의사'),(84,4,'교수'),(85,4,'광부'),(86,4,'정치인'),(87,4,'왕'),(88,5,'타짜'),(89,5,'어벤져스'),(90,5,'스파이더맨'),(91,5,'범죄도시'),(92,5,'은밀하게 위대하게'),(93,5,'도둑들'),(94,5,'해운대'),(95,5,'여고괴담'),(96,5,'악마를 보았다'),(97,5,'괴물'),(98,5,'실미도'),(99,5,'아바타'),(100,5,'태극기 휘날리며'),(101,5,'신과 함께'),(102,5,'인터스텔라'),(103,5,'왕의 남자'),(104,5,'겨울왕국'),(105,5,'명량'),(106,5,'기생충'),(107,5,'극한직업'),(108,5,'7번방의 선물'),(109,5,'부산행'),(110,5,'변호인'),(111,5,'택시 운전사'),(112,5,'아이언맨'),(113,5,'설국열차'),(114,5,'엑시트'),(115,5,'분신사바'),(116,6,'마라톤'),(117,6,'축구'),(118,6,'농구'),(119,6,'배구'),(120,6,'야구'),(121,6,'당구'),(122,6,'족구'),(123,6,'탁구'),(124,6,'수영'),(125,6,'태권도'),(126,6,'씨름'),(127,6,'복싱'),(128,6,'양궁'),(129,6,'쇼트트랙'),(130,6,'아이스하키'),(131,6,'서핑'),(132,6,'테니스'),(133,6,'배드민턴'),(134,6,'골프'),(135,6,'볼링'),(136,6,'멀리뛰기'),(137,6,'다이빙');
/*!40000 ALTER TABLE `liar_word` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) DEFAULT NULL,
  `descript` varchar(300) DEFAULT NULL,
  `room_password` varchar(100) DEFAULT NULL,
  `game` int(11) DEFAULT NULL,
  `call_start_time` datetime DEFAULT NULL,
  `is_private` tinyint(1) DEFAULT '0',
  `join_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=627 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (500,'1','어서와요',NULL,1,'2022-08-17 22:14:26',0,2),(506,'adad','어서와요',NULL,1,'2022-08-17 23:16:43',0,2),(507,'외 않도ㅔ','어서와요',NULL,1,'2022-08-17 23:28:12',0,1),(508,'엥','어서와요',NULL,1,'2022-08-17 23:43:51',0,2),(509,'12시 19분이네','어서와요',NULL,1,'2022-08-18 00:19:34',0,1),(510,'d','어서와요',NULL,1,'2022-08-18 00:25:18',0,1),(515,'asdfasdf','어서와요',NULL,1,'2022-08-18 00:52:58',0,1),(523,'채팅 테스트요','어서와요',NULL,2,'2022-08-18 02:00:47',0,3),(527,'아하','어서와요',NULL,1,'2022-08-18 08:23:50',0,3),(531,'13241234','어서와요',NULL,2,'2022-08-18 09:00:53',0,2),(532,'ㅁㅇㅁㅇ','어서와요',NULL,1,'2022-08-18 09:23:50',0,2),(533,'ㅂㄷㅂㄷ','어서와요',NULL,1,'2022-08-18 09:32:52',0,3),(536,'ㅂㅈㄷ','어서와요',NULL,1,'2022-08-18 10:07:39',0,2),(550,'34534534','어서와요',NULL,1,'2022-08-18 11:10:38',0,2),(553,'ㅁㅇㅁㅇ','어서와요',NULL,1,'2022-08-18 11:31:27',0,3),(556,'ㅁㄴㅇㅁㄴㅇ','어서와요',NULL,1,'2022-08-18 11:55:33',0,1),(558,'안녕','어서와요',NULL,1,'2022-08-18 12:24:02',0,1),(560,'컴','어서와요',NULL,1,'2022-08-18 13:02:32',0,1),(561,'홍제민','어서와요',NULL,1,'2022-08-18 13:10:24',0,2),(562,'흠냐링','어서와요',NULL,1,'2022-08-18 13:12:13',0,1),(563,'dfgjhdfgrsegrjudfrse','어서와요','$2a$10$rRuYGvnudIgd1cBzGDmgy.GDygzVAwklyGDunK..cjkP.lEDUeqWy',1,'2022-08-18 13:19:54',1,1),(565,'ㄴㅁㅇㄹㄴㄻ','어서와요',NULL,1,'2022-08-18 14:21:23',0,1),(588,'투표창 테스트방','어서와요','$2a$10$X.XABgq1r027qYINLVrir.mtHSD4zHL4sFWKwLKPT1yrbiDJetRoe',1,'2022-08-18 17:21:11',1,1),(590,'드가자~','어서와요',NULL,1,'2022-08-18 17:22:28',0,1),(593,'asdasd','어서와요',NULL,1,'2022-08-18 17:38:26',0,1),(594,'asd','어서와요',NULL,1,'2022-08-18 17:38:52',0,1),(597,'ㅁㅇㅁㅇ','어서와요',NULL,3,'2022-08-18 18:05:09',0,2),(598,'adad','어서와요',NULL,1,'2022-08-18 19:55:00',0,5),(599,'스크린샷','어서와요',NULL,1,'2022-08-18 20:56:59',0,1),(601,'초보만','어서와요',NULL,1,'2022-08-18 21:04:29',0,2),(602,'라이어 게임 할 사람! ㅇㅁㅇ',NULL,NULL,1,'2022-08-18 21:05:59',0,2),(603,'넌 못 지나간다!','어서와요',NULL,1,'2022-08-18 21:06:37',0,3),(604,'심심해요','같이놀사람~',NULL,1,'2022-08-18 21:07:12',0,1),(605,'빨리 빨리 들어오세요','얼른 들어와서 스겜 ㄱ ㄱ ㄱ ',NULL,1,'2022-08-18 21:12:01',0,1),(606,'초보도 상관 ㄴㄴ','편하게 들어오세요',NULL,1,'2022-08-18 21:12:56',0,1),(607,'5반 1팀 팀원들 들어오라구!','우리 팀원들 얼른 들어와라 쒸익','$2a$10$uWIrJAIPO2bY6/KD6XwQqeheP7F7sCsXViZOFOxUmJQQu1EHJkmBC',1,'2022-08-18 21:13:58',1,1),(623,'다 들어와!','어서와요',NULL,1,'2022-08-19 01:05:42',0,3),(624,'게임 고고','',NULL,1,'2022-08-19 01:45:11',0,2),(626,'어서들어와요','어서와요',NULL,1,'2022-08-19 02:28:18',0,3);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(30) DEFAULT NULL,
  `user_password` varchar(100) DEFAULT NULL,
  `user_nickname` varchar(20) DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'dheldh20@naver.com','$2a$10$VmDTxjTwt3Zo11VTixjytu0ZRP0R8WUQvvLIQK3Bzzvz7cx43nHCG','안녕로봇','2022-08-04 16:41:49','2022-08-18 21:15:36'),(2,'bizyoung93@gmail.com','$2a$10$a5AnoBLpj8DE91OCBkQqke/XKByPlpNfQbtQLynJnXiNhkeHfuP8a','아부다비','2022-08-04 17:13:35','2022-08-18 11:55:55'),(6,'chwon03@naver.com','$2a$10$irj6MeEQw1SaJ46rn/PXFuiMawaFwTafRirC7ug4HKBcZIu70yraq','aaaaaa','2022-08-08 10:42:52','2022-08-18 18:06:02'),(8,'zz132456zz@kakao.com','$2a$10$pTVP7kjR8/Ut2FR47Xxm2.PbsFJcSFOYaF0QfLBBagf/quIrf.CRK','나라이어아닌데','2022-08-08 11:33:07','2022-08-19 01:01:05'),(9,'weseeplay@ssafy.com','$2a$10$fZArj9oQpeJajs81AZKIT.d/zumYb/oxGa3WiPFC/K2CjboE9uOrG','WeSeePlay','2022-08-08 13:51:43','2022-08-19 02:29:01'),(10,'2366561667','$2a$10$G9KcuVVQAoJ8cWxeSKZyKegwQCAPRv/bJy3lFIglRZ27bvuEE1ijW','오동통턱살','2022-08-08 15:23:32','2022-08-19 00:51:20'),(11,'2380190861','$2a$10$zuC.3z2lWLbDS3nVFNOcjeHe2HbD8ls20DNPr0lJZMB9DpQZICUU.','이도경78495','2022-08-08 17:29:40','2022-08-17 09:46:55'),(12,'ygpark96@naver.com','$2a$10$xkXxkp5ZSBsrDxvpAWb5pOfThkVPsSz4zpj0ultYc534zs9Ww6gWu','여빈이','2022-08-08 17:34:38','2022-08-08 17:34:50'),(14,'wonch30@gmail.com','$2a$10$PKoUWz3X5IekBEzkhBg3kO96jtelnDeZMXvbn10H9L43u/3Fv6D12','ChanChan','2022-08-08 19:06:48','2022-08-18 21:46:43'),(15,'leesh0768@gmail.com','$2a$10$Z252xKQAcAD9OT8ydORAle0.ia7Ny55LNhjP1IAJ1VuWey0S2/DGq','testtesttest','2022-08-09 00:45:45',NULL),(18,'2382843725','$2a$10$ioYPdUuFVEBXNXdBE86bB..91GCJxBSiB/jG0/KMlbxm1avv.nnc.','천민우86164','2022-08-10 14:34:42','2022-08-10 14:34:42'),(19,'bluefri0329@gmail.com','$2a$10$yXSLPjfrSmAfeCI6dF0sq.pkEIlw1.H.hi36XUpX7Yd1NFGvJ4fxa','김현영투','2022-08-10 16:38:13','2022-08-14 00:41:36'),(20,'bljh1008@naver.com','$2a$10$2dDeVhqfPAFHXXf871iABebmW1vrYOUsjux/UCLAUxSnqU.vgUS7O','일조','2022-08-10 17:56:06','2022-08-19 00:16:55'),(21,'tjdytpq0310@gmail.com','$2a$10$52Bk4gkb0uFx9ge5yJ.oFOBFNwpTvQ.eXtUVuKdD1AS8YVASgrn.u','호에엥','2022-08-11 14:22:50','2022-08-18 10:54:05'),(22,'2379997759','$2a$10$SAR82QU6RetSpvjs/MXICuRPdr5YeD7QOJ0WKSVl6vdAM2JqlhmGS','Chan48989','2022-08-11 17:41:07','2022-08-11 17:41:07'),(23,'2384956504','$2a$10$jefhWh8Jtg6UadBWDU.NFOHc0a276s5AwigWD5hcViCG6b2I.7iNy','리밍밍구','2022-08-12 00:59:55','2022-08-18 21:13:13'),(24,'2384957974','$2a$10$uBHtt6wysinvKHzpIGPNhe4xH2VTy879KS52ov8t30.WAp/FeU80i','하이하이','2022-08-12 01:02:03','2022-08-17 01:17:43'),(25,'2385007549','$2a$10$jWoHBqBAV5bbyiGjux8jMOFmFSIeUnITgbEyXdwcxbuv4eCx8WwIy','마유선34751','2022-08-12 02:46:58','2022-08-12 02:46:58'),(26,'2385008020','$2a$10$DXqVJ45e6ECywnAOxSPW3uOTB2hboagXVOVvyfytBqTTS/ZtXwaLO','유재열08872','2022-08-12 02:48:22','2022-08-12 02:48:22'),(29,'chwon03@hanmail.net','$2a$10$FVRhYb8ffgfDm/mmyE4Jau5u1.aDCu8sb5YRai2N.08Xz7p7DhHDm','asdasd','2022-08-12 11:25:49','2022-08-12 11:26:01'),(30,'dheldh20@gmail.com','$2a$10$2wTUk.jb38wUTviBA1xpQupvYB7MW/9bEwI.lHjIQKD0cx6JUVzuW','흙흙흙','2022-08-12 15:42:26','2022-08-19 02:28:41'),(31,'kimkc3117@gmail.com','$2a$10$Hoyrf.TV5uwiNyxlkjpS7OhYcafdTQ7oaOho7UJ6qUt91kj51eKLq','KSADJS','2022-08-14 01:03:18','2022-08-14 01:03:24'),(32,'dannba1@naver.com','$2a$10$/hWy9Ej.KmY/Tvn.91nvq.DnHKX.A6LNgA74.guFoyblw1SH7NJZK','퍼가요','2022-08-15 23:54:44','2022-08-15 23:54:48'),(33,'2390150933','$2a$10$wDgo9ykQPhxdACuF9eXFGeDw5ek/WYtwfYHGFKthbYTMBihC/DHxW','박지원08609','2022-08-16 02:01:12','2022-08-16 02:01:26'),(34,'snowbr92@gmail.com','$2a$10$AcmjiuowxIEHvI499Dh13uMhmj2oqwf.PvW0da5Vs1PKDaJRwbZg.','테스트','2022-08-16 07:15:24','2022-08-16 07:15:34'),(35,'zzangsin2002@hanmail.net','$2a$10$ox1SS8rYthha6YgfpLCiH./xXrsGqsc7NKm4NXMe1lPnn7I7hsw.G','adfdsfasd','2022-08-16 10:27:16','2022-08-16 10:27:27'),(37,'2391463316','$2a$10$XjiGdS.xsJ/88JbGgpsLUOoVKtd9fEayzGATX11mwwhga7zD1PTQ2','조윤재58555','2022-08-17 00:30:13','2022-08-17 00:30:13'),(38,'khsung0@nate.com','$2a$10$rXcroGIXITeF30Nj5UbYDukbPIxHtfta37YDyHptrO/LgDNuhEDuy','변경 후 nickName','2022-08-17 01:14:17','2022-08-18 16:36:43'),(40,'rrimm012@gmail.com','$2a$10$.SW2CRQecbgKV9RWNpGdRuT3LOFP9L2ApjChcuO9fjc3BbZCRyEN.','혜리무','2022-08-17 13:05:06','2022-08-18 17:27:30'),(41,'ypd02363@naver.com','$2a$10$5pMpjInidNz3SCjwJ5fbVe2nDq0Ay1PX1NUNRCtUHA.GfZhfC4MSu','GuestGuest','2022-08-17 20:23:07','2022-08-17 20:23:14'),(42,'zz132456zz@naver.com','$2a$10$Ec7wiHrZ/O4b2vwCtkrfJug6UI/DrxngRaKHSRtHHnTunDeRVHwSm','라이어아닙니다','2022-08-17 22:18:43','2022-08-18 14:31:47'),(44,'2381901564','$2a$10$RA8yzS5eVUwmrxTijQx93.rfXXwZj8KYD8HzgxXfcnwHt4Ldv1zsC','hs12345','2022-08-18 10:41:14','2022-08-18 10:41:14'),(45,'kang070415@naver.com','$2a$10$NSru/ytqxMzAdS5OflYC4eamEOjT8z69R2PZRacZdeKRv0ioyjnUW','다으니','2022-08-18 12:23:50','2022-08-18 12:23:54'),(47,'2393594656','$2a$10$t/eIjnV4kOwxPBLl90Q4VOM6gTY6b.89uD1oWAmYSQj6FROqcS0YS','하다윤26887','2022-08-18 15:51:29','2022-08-18 15:51:29'),(48,'fkeldh2089@gmail.com','$2a$10$QAYWbPMdxaVfDYiRnq4houOjl4wkfKtFNKsRJj8acOAopQqpKeDuS','쏘가리','2022-08-18 19:57:48','2022-08-18 23:39:00'),(49,'minah741@naver.com','$2a$10$dasBrFqH5Py/zQf3paW8C.fs6tX66MsbQlbUFMPqYH9JcdtLQRR5K','지나갑니당','2022-08-19 00:16:02','2022-08-19 00:16:12'),(50,'xowase9625@yasiok.com','$2a$10$aJEpR4C3afwdHbqiXdNvKe1KIXsUZfNzoTAetcVvBlNXAFEXrykRW','김싸피','2022-08-19 01:44:12','2022-08-19 01:44:29'),(51,'nohod39265@ukgent.com','$2a$10$xj6jOs4he4GT2ljMI6J3tulkpMH.SS7mbnalqWwZt8QDpG1M4K1Da','노마드','2022-08-19 01:46:45','2022-08-19 01:46:48'),(52,'321zmzm@naver.com','$2a$10$bkwwv327GfglMhqJwxP.gued3pbWdqKre8soj7wqBMpxGYgfpuenq','콩순이','2022-08-19 01:48:57','2022-08-19 01:49:07'),(53,'zkdlajt96@naver.com','$2a$10$vW/crR0DYGjBr7LRXn1VReqexsX4P0SokJ.27e103iHUUkCw9Th2e','아아아아','2022-08-19 01:58:47','2022-08-19 01:58:56'),(54,'2394251993','$2a$10$kJIIU1rDlaqEzI0PUSRoJerCoHGqiGuu4Y.6xLuysK7cFDh3AT7PC','김신철78876','2022-08-19 02:03:28','2022-08-19 02:03:28'),(55,'2394252007','$2a$10$0K8/9WmRYi7oMmR8hGly2.zJjHCbNzqUFtoQL7mS0pW9xYvfLjotu','신창호05170','2022-08-19 02:03:30','2022-08-19 02:03:30');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_room`
--

DROP TABLE IF EXISTS `user_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_player` tinyint(1) DEFAULT '0',
  `is_host` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1335 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_room`
--

LOCK TABLES `user_room` WRITE;
/*!40000 ALTER TABLE `user_room` DISABLE KEYS */;
INSERT INTO `user_room` VALUES (973,500,8,0,1),(977,500,10,0,0),(995,506,1,0,1),(997,507,1,0,1),(999,506,14,0,0),(1000,508,1,0,1),(1001,508,30,0,0),(1002,509,42,0,1),(1003,510,42,0,1),(1008,515,14,0,1),(1019,523,14,0,1),(1021,523,6,0,0),(1023,523,9,0,0),(1028,527,1,0,1),(1029,527,30,0,0),(1037,531,14,0,1),(1038,531,6,0,0),(1039,532,1,0,1),(1040,532,30,0,0),(1042,533,1,0,1),(1043,533,9,0,0),(1044,533,30,0,0),(1049,536,1,0,1),(1051,536,30,0,0),(1076,527,34,0,0),(1095,550,14,0,1),(1096,550,6,0,0),(1099,553,1,0,1),(1100,553,30,0,0),(1101,553,9,0,0),(1115,556,10,0,1),(1120,558,45,0,1),(1130,560,40,0,1),(1132,561,1,0,1),(1133,561,30,0,0),(1135,562,40,0,1),(1136,563,9,0,1),(1139,565,2,0,1),(1206,588,40,0,1),(1208,590,14,0,1),(1215,593,23,0,1),(1216,594,9,0,1),(1223,597,1,0,1),(1227,598,1,0,1),(1228,598,30,0,0),(1229,598,9,0,0),(1230,598,48,0,0),(1231,599,10,0,1),(1233,598,8,0,0),(1234,601,1,0,1),(1235,601,8,0,0),(1236,602,14,0,1),(1237,602,8,0,0),(1238,603,30,0,1),(1239,603,14,0,0),(1240,604,8,0,1),(1241,603,8,0,0),(1242,605,40,0,1),(1243,606,9,0,1),(1244,607,23,0,1),(1315,623,1,0,1),(1316,623,30,0,0),(1317,623,9,0,0),(1320,624,50,0,1),(1322,624,51,0,0),(1332,626,1,0,1),(1333,626,30,0,0),(1334,626,9,0,0);
/*!40000 ALTER TABLE `user_room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-19  2:39:53
