-- phpMyAdmin SQL Dump
-- version 4.3.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 20, 2017 at 05:16 PM
-- Server version: 5.6.32-78.1-log
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `magpiehu_cmsdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `AppUserData`
--

CREATE TABLE IF NOT EXISTS `AppUserData` (
  `UserID` int(11) NOT NULL,
  `UserName` varchar(50) DEFAULT '{ EMPTY }',
  `UserEmail` varchar(100) DEFAULT '{ EMPTY }'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `CollectionImages`
--

CREATE TABLE IF NOT EXISTS `CollectionImages` (
  `PicID` int(11) NOT NULL,
  `CID` int(11) DEFAULT '0',
  `FileLocation` varchar(200) DEFAULT '{ EMPTY }',
  `ImageType` varchar(50) DEFAULT '{ EMPTY }',
  `IsCopyright` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CollectionImages`
--

INSERT INTO `CollectionImages` (`PicID`, `CID`, `FileLocation`, `ImageType`, `IsCopyright`) VALUES
(2, 1, 'TEST://CASE', '{ EMPTY }', 0),
(3, 2, 'TEST://CASE', '{ EMPTY }', 0),
(4, 8, 'ewulogo.png', 'png', 1);

-- --------------------------------------------------------

--
-- Table structure for table `CollectionLandmarks`
--

CREATE TABLE IF NOT EXISTS `CollectionLandmarks` (
  `CollectionID` int(11) NOT NULL,
  `LandmarkID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CollectionLandmarks`
--

INSERT INTO `CollectionLandmarks` (`CollectionID`, `LandmarkID`) VALUES
(1, 1),
(3, 1),
(10, 1),
(1, 2),
(2, 2),
(10, 2),
(2, 3),
(10, 3),
(3, 4),
(10, 4),
(1, 5),
(10, 5),
(10, 6),
(8, 7),
(10, 7),
(8, 8),
(10, 8),
(8, 9),
(10, 9),
(8, 10),
(10, 10),
(8, 11),
(8, 12),
(8, 13),
(8, 14),
(9, 15),
(9, 16),
(9, 17),
(9, 18),
(9, 19),
(7, 20),
(7, 21),
(7, 22),
(7, 23),
(7, 24),
(7, 25),
(7, 26),
(7, 27),
(7, 28),
(7, 29),
(7, 30),
(7, 31),
(7, 32),
(7, 33),
(7, 34),
(7, 35),
(7, 36),
(7, 37),
(7, 38),
(7, 39),
(7, 40);

-- --------------------------------------------------------

--
-- Table structure for table `Collections`
--

CREATE TABLE IF NOT EXISTS `Collections` (
  `CID` int(11) NOT NULL,
  `Status` tinyint(4) unsigned NOT NULL DEFAULT '1',
  `Name` varchar(100) NOT NULL,
  `City` varchar(100) NOT NULL DEFAULT 'Spokane',
  `State` varchar(100) NOT NULL DEFAULT 'Washington',
  `Rating` varchar(50) NOT NULL DEFAULT 'E',
  `Description` varchar(1000) NOT NULL,
  `NumberOfLandmarks` int(11) DEFAULT '0',
  `CollectionLength` double DEFAULT '0',
  `IsOrder` tinyint(1) DEFAULT '0',
  `PicID` int(11) DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Collections`
--

INSERT INTO `Collections` (`CID`, `Status`, `Name`, `City`, `State`, `Rating`, `Description`, `NumberOfLandmarks`, `CollectionLength`, `IsOrder`, `PicID`) VALUES
(1, 0, '1st', 'Spokane', 'Washington', 'E', 'Test case 1', 3, 5, 0, 0),
(2, 0, '2nd', 'Spokane', 'Washington', 'E', 'Test case 2', 2, 0, 0, 0),
(3, 0, '3rd', 'Spokane', 'Washington', 'E', 'Test case 3', 2, 0, 0, 0),
(7, 0, 'Art Walk', 'Spokane', 'Washington', 'E', 'This is a art walk at Riverfront park, Prototype (Missing Landmarks)', 21, 0, 0, 4),
(8, 1, 'EWU Walk', 'Cheney', 'Washington', 'E', 'This is a test walk for APP testing', 8, 0, 0, 4),
(9, 1, 'ProtoWalk', 'Anytown', 'Somestate', 'A', 'Test walk for APP testing', 5, 0, 0, 4),
(10, 255, 'test #10', 'thisDB', 'Magpie.com', 'T', 'Testing SQL statements', 10, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `LandmarkDescription`
--

CREATE TABLE IF NOT EXISTS `LandmarkDescription` (
  `DesID` int(11) NOT NULL,
  `LID` int(11) DEFAULT '0',
  `CID` int(11) NOT NULL,
  `Description` varchar(1000) DEFAULT '{ Empty }'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `LandmarkDescription`
--

INSERT INTO `LandmarkDescription` (`DesID`, `LID`, `CID`, `Description`) VALUES
(0, 1, 1, '{ EMPTY }'),
(0, 2, 1, '{ EMPTY }'),
(0, 3, 1, '{ EMPTY }'),
(0, 4, 1, '{ EMPTY }'),
(0, 5, 1, '{ EMPTY }'),
(1, 7, 8, 'This is JFK library'),
(2, 8, 8, 'Rec Center'),
(3, 9, 8, 'CEB, Its were its at'),
(4, 10, 8, 'Showalter Hall, is a building at EWU'),
(5, 11, 8, 'Roos Field, its RED'),
(6, 12, 8, 'Isle Hall, another building'),
(7, 13, 8, 'Campus Mall, its open?'),
(8, 14, 8, 'Music Departmaent, its where Music happens'),
(9, 15, 9, 'Is a door'),
(10, 16, 9, 'Is a chair'),
(11, 17, 9, 'Is a table'),
(12, 18, 9, 'More bull than you can handle'),
(13, 19, 9, 'and Wall'),
(14, 20, 7, 'Fabricated from three stainless steel panels, the sculpture has irregular edges on the tops and sides. Hodges calls the work, a paradox of solid steel and open windows; a fusion of manmade modernity and natural environment.'),
(15, 21, 7, 'This work of outdoor furniture works both as a seating area and a beautiful aesthetic object. Commissioned by the Washington State Arts Commission, Light Reading is located at the southeast corner of the WSU Academic Building'),
(16, 22, 7, 'A series of sculptures with granite boulders and basalt columns are located on the Washington State University downtown campus near the University''s Health Science and Interdisciplinary Design Institue'),
(17, 23, 7, 'In this piece, Zentz aggregated symbols of the specific environment. Features of the piece include the topography of Mount Spokane, the Spokane River and the elements of climate. It encourages viewers to look at the intricacies of the landscape, the incidence of time and the apparent chaos of the atmosphere.'),
(18, 24, 7, 'This hammered copper sculpture was a gift to the people of Spokane from our former Sister City, Makhachkala, Russia. Depicting a folk hero and general, the sculpture stands for the preference of peace over war, and the importance of freedom as a basis for peace.'),
(19, 25, 7, 'This environmental artwork creates a viewing area for the Spokane River. The arbor join both East and West with a symbolic threshing floor. The arbor is also surrounded by Gift Gardens planted to represent Spokane''s Sister Cities.'),
(20, 26, 7, 'This is a Larger-than-life bronze sculpture which commemorates the Centennial (1895-1995) of the Mining Association. It depicts a hard-rock miner checking the quality of the ore he is extraction.'),
(21, 27, 7, 'Michael P. Anderson gave his life exploring Space. This Spokane resident perished along with six other heroes in the Columbia Space Shuttle Tragedy in 2003.'),
(22, 28, 7, 'This sculpture was commissioned in recognition of the centennial of Sacred Heart Medical Center.'),
(23, 29, 7, 'A concrete sculpture in the form of a large scale Japanese Lantern.'),
(24, 30, 7, 'An abstract bronze sculpture of a moon crater with wood textures.'),
(25, 31, 7, 'A tall aluminum fountain in an abstract style located on the south side of the INB Performing Arts Center. In his lifetime, Tsutakawa created more than 80 fountains in the U.S. and in Japan.'),
(26, 32, 7, 'An abstract aluminum sculpture which floats in the Spokane River.'),
(27, 33, 7, 'Spokane''s Red Wagon sculpture was created in honor of Washington States 1989 Centennial. It was a gift from the Junior League of Spokane, many local businesses and the Spokane Arts Commission to the children of Spokane.'),
(28, 34, 7, 'This corten steel sculpture of a goat will eat small pieces of trash with the aid of its vacuum digestive system.'),
(29, 35, 7, 'The names of all the Vietnam veterans from the Spokane area are engraved in the sculpture''s pedestal.'),
(30, 36, 7, 'This life-size sundial was created for Expo ''74 and contains many symbolic Australian animals.'),
(31, 37, 7, 'This corten steel sculpture depicts a mountain sheep climbing the rocks.'),
(32, 38, 7, 'This interactive, monumental abstract structure is both a big sprinkler for child play and visual play. It was commissined by Rotary Club 21 with support from donations from the community.'),
(33, 39, 7, 'The corten steel sculpture depicts runners of all kinds. It celebrates the Spokane tradition of Bloomsday, the largest timed road running race in the world.'),
(34, 40, 7, 'This piece of artwork is a poem engraved in graite and formed into a spiral on the ground. The poem is about the Spokane Falls, which can be seen from where the poem is written. It is also representative of the Spokane heritage.');

-- --------------------------------------------------------

--
-- Table structure for table `LandmarkImages`
--

CREATE TABLE IF NOT EXISTS `LandmarkImages` (
  `PicID` int(11) NOT NULL,
  `LID` int(11) DEFAULT '0',
  `FileLocation` varchar(200) DEFAULT '{ Empty }',
  `ImageType` varchar(50) DEFAULT '{ EMPTY }',
  `IsCopyright` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `LandmarkImages`
--

INSERT INTO `LandmarkImages` (`PicID`, `LID`, `FileLocation`, `ImageType`, `IsCopyright`) VALUES
(1, 1, '{ EMPTY }', '{ NILL }', 0),
(2, 2, '{ EMPTY }', '{ NILL }', 0),
(3, 3, '{ EMPTY }', '{ NILL }', 0),
(4, 4, '{ EMPTY }', '{ NILL }', 0),
(5, 5, '{ EMPTY }', '{ NILL }', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Landmarks`
--

CREATE TABLE IF NOT EXISTS `Landmarks` (
  `LID` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Longitude` double DEFAULT '0',
  `Latitude` double DEFAULT '0',
  `NumberOfCollections` int(11) DEFAULT '0',
  `DescID` int(11) DEFAULT '0',
  `QRCode` varchar(625) DEFAULT '{ Empty }',
  `PicID` int(11) DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Landmarks`
--

INSERT INTO `Landmarks` (`LID`, `Name`, `Longitude`, `Latitude`, `NumberOfCollections`, `DescID`, `QRCode`, `PicID`) VALUES
(1, 'Test 1', 50.5, 45.55, 3, 1, '{ Empty }', 0),
(2, 'Test 2', 50.5, 45.55, 3, 2, '{ Empty }', 0),
(3, 'Test 3', 50.5, 45.55, 2, 3, '{ Empty }', 0),
(4, 'Test 4', 50.5, 45.55, 2, 4, '{ Empty }', 0),
(5, 'Test 5', 50.5, 45.55, 2, 5, '{ Empty }', 0),
(6, 'Goat (Test)', -117.5, 46.8, 1, 0, '{ TEST }', 255),
(7, 'JFK Library', -117.5837, 47.490508, 2, 1, '{ Empty }', 0),
(8, 'URC', -117.584188, 47.493304, 2, 2, '{ Empty }', 0),
(9, 'CEB', -117.585206, 47.490227, 2, 3, '{ Empty }', 0),
(10, 'Showalter Hall', -117.579736, 47.490165, 2, 4, '{ Empty }', 0),
(11, 'Roos Field', -117.587844, 47.492818, 1, 5, '{ Empty }', 0),
(12, 'Isle Hall', -117.58109, 47.492232, 1, 6, '{ Empty }', 0),
(13, 'Campus Mall', -117.582143, 47.499803, 1, 7, '{ Empty }', 0),
(14, 'Music Departmaent', -117.58458, 47.497814, 1, 8, ' ~(._.~)', 0),
(15, 'Door', -117.55, 47.5, 1, 9, '{ Empty }', 0),
(16, 'Chair', -117.558, 47.5, 1, 10, '{ Empty }', 0),
(17, 'Table', -117.5585, 47.5, 1, 11, '{ Empty }', 0),
(18, 'MiniBull', -117.5586, 47.5, 1, 12, '{ Empty }', 0),
(19, 'Wall', -117.5587, 47.5, 1, 13, '{ Empty }', 0),
(20, 'Alive, Lively, Living', -117.407082, 47.660706, 1, 14, '{ Empty }', 0),
(21, 'Light Reading', -117.405243, 47.661442, 1, 15, '{ Empty }', 0),
(22, 'Cooperation', -117.405281, 47.660969, 1, 16, '{ Empty }', 0),
(23, 'Riverpoint Observatory', -117.40509, 47.661224, 1, 17, '{ Empty }', 0),
(24, 'Shamil', -117.411743, 47.662018, 1, 18, '{ Empty }', 0),
(25, 'East-West Arbor', -117.413483, 47.661896, 1, 19, '{ Empty }', 0),
(26, 'From this Earth', -117.414757, 47.661869, 1, 20, '{ Empty }', 0),
(27, 'Michael P. Anderson', -117.416954, 47.660988, 1, 21, '{ Empty }', 0),
(28, 'The Call and the Challenge', -117.417328, 47.66098, 1, 22, '{ Empty }', 0),
(29, 'Lantern', -117.417671, 47.660999, 1, 23, '{ Empty }', 0),
(30, 'Moon Crater', -117.417824, 47.660854, 1, 24, '{ Empty }', 0),
(31, 'Aluminum Fountain', -117.417892, 47.660248, 1, 25, '{ Empty }', 0),
(32, 'Centennial Sculpture', -117.418709, 47.660793, 1, 26, '{ Empty }', 0),
(33, 'The Childhood Express', -117.419044, 47.660416, 1, 27, '{ Empty }', 0),
(34, 'Goat', -117.426048, 47.658779, 1, 28, '{ Empty }', 0),
(35, 'Vietnam Veteran''s Memorial', -117.417976, 47.662025, 1, 29, '{ Empty }', 0),
(36, 'Australian Sundial', -117.415955, 47.662895, 1, 30, '{ Empty }', 0),
(37, 'Mountain Sheep', -117.421204, 47.662594, 1, 31, '{ Empty }', 0),
(38, 'Rotary Riverfront Fountain', -117.421104, 47.660576, 1, 32, '{ Empty }', 0),
(39, 'The Joy of Running Together', -117.423424, 47.660225, 1, 33, '{ Empty }', 0),
(40, 'The Place Where Chosts of Salmon Jump', -117.426254, 47.659447, 1, 34, '{ Empty }', 0);

-- --------------------------------------------------------

--
-- Table structure for table `UserCollectionCompleted`
--

CREATE TABLE IF NOT EXISTS `UserCollectionCompleted` (
  `UserID` int(11) NOT NULL,
  `CollectionID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `UserCollectionDownloaded`
--

CREATE TABLE IF NOT EXISTS `UserCollectionDownloaded` (
  `UserID` int(11) NOT NULL,
  `CollectionID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `UserCollectionInprogress`
--

CREATE TABLE IF NOT EXISTS `UserCollectionInprogress` (
  `UserID` int(11) NOT NULL,
  `CollectionID` int(11) NOT NULL,
  `NumberOfLandmarksSeen` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `UserMadeCollectionList`
--

CREATE TABLE IF NOT EXISTS `UserMadeCollectionList` (
  `UserID` int(11) NOT NULL,
  `CollectionID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `WebUserData`
--

CREATE TABLE IF NOT EXISTS `WebUserData` (
  `UserID` int(11) NOT NULL,
  `UserName` varchar(50) DEFAULT '{ EMPTY }',
  `UserEmail` varchar(100) DEFAULT '{ EMPTY }'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `AppUserData`
--
ALTER TABLE `AppUserData`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `CollectionImages`
--
ALTER TABLE `CollectionImages`
  ADD PRIMARY KEY (`PicID`), ADD KEY `LinkKeyCtoCI` (`CID`);

--
-- Indexes for table `CollectionLandmarks`
--
ALTER TABLE `CollectionLandmarks`
  ADD PRIMARY KEY (`CollectionID`,`LandmarkID`), ADD KEY `LinkKeyLMtoCLM` (`LandmarkID`);

--
-- Indexes for table `Collections`
--
ALTER TABLE `Collections`
  ADD PRIMARY KEY (`CID`);

--
-- Indexes for table `LandmarkDescription`
--
ALTER TABLE `LandmarkDescription`
  ADD KEY `LinkKeyLMtoLMD` (`LID`), ADD KEY `LinkKeyCtoLMD` (`CID`);

--
-- Indexes for table `LandmarkImages`
--
ALTER TABLE `LandmarkImages`
  ADD PRIMARY KEY (`PicID`), ADD KEY `LinkKeyLMtoLMI` (`LID`);

--
-- Indexes for table `Landmarks`
--
ALTER TABLE `Landmarks`
  ADD PRIMARY KEY (`LID`);

--
-- Indexes for table `UserCollectionCompleted`
--
ALTER TABLE `UserCollectionCompleted`
  ADD PRIMARY KEY (`UserID`,`CollectionID`), ADD KEY `linkUCCtoC` (`CollectionID`);

--
-- Indexes for table `UserCollectionDownloaded`
--
ALTER TABLE `UserCollectionDownloaded`
  ADD PRIMARY KEY (`UserID`,`CollectionID`), ADD KEY `linkUCDtoC` (`CollectionID`);

--
-- Indexes for table `UserCollectionInprogress`
--
ALTER TABLE `UserCollectionInprogress`
  ADD PRIMARY KEY (`UserID`,`CollectionID`), ADD KEY `linkUCItoC` (`CollectionID`);

--
-- Indexes for table `UserMadeCollectionList`
--
ALTER TABLE `UserMadeCollectionList`
  ADD PRIMARY KEY (`UserID`,`CollectionID`), ADD KEY `linkUMCLtoC` (`CollectionID`);

--
-- Indexes for table `WebUserData`
--
ALTER TABLE `WebUserData`
  ADD PRIMARY KEY (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `AppUserData`
--
ALTER TABLE `AppUserData`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `CollectionImages`
--
ALTER TABLE `CollectionImages`
  MODIFY `PicID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `Collections`
--
ALTER TABLE `Collections`
  MODIFY `CID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `LandmarkImages`
--
ALTER TABLE `LandmarkImages`
  MODIFY `PicID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `Landmarks`
--
ALTER TABLE `Landmarks`
  MODIFY `LID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT for table `WebUserData`
--
ALTER TABLE `WebUserData`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `CollectionImages`
--
ALTER TABLE `CollectionImages`
ADD CONSTRAINT `LinkKeyCtoCI` FOREIGN KEY (`CID`) REFERENCES `Collections` (`CID`);

--
-- Constraints for table `CollectionLandmarks`
--
ALTER TABLE `CollectionLandmarks`
ADD CONSTRAINT `LinkKeyCtoCLM` FOREIGN KEY (`CollectionID`) REFERENCES `Collections` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `LinkKeyLMtoCLM` FOREIGN KEY (`LandmarkID`) REFERENCES `Landmarks` (`LID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `LandmarkDescription`
--
ALTER TABLE `LandmarkDescription`
ADD CONSTRAINT `LinkKeyCtoLMD` FOREIGN KEY (`CID`) REFERENCES `Collections` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `LinkKeyLMtoLMD` FOREIGN KEY (`LID`) REFERENCES `Landmarks` (`LID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `LandmarkImages`
--
ALTER TABLE `LandmarkImages`
ADD CONSTRAINT `LinkKeyLMtoLMI` FOREIGN KEY (`LID`) REFERENCES `Landmarks` (`LID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `UserCollectionCompleted`
--
ALTER TABLE `UserCollectionCompleted`
ADD CONSTRAINT `linkUCCtoAUD` FOREIGN KEY (`UserID`) REFERENCES `AppUserData` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `linkUCCtoC` FOREIGN KEY (`CollectionID`) REFERENCES `Collections` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `UserCollectionDownloaded`
--
ALTER TABLE `UserCollectionDownloaded`
ADD CONSTRAINT `linkUCDtoAUD` FOREIGN KEY (`UserID`) REFERENCES `AppUserData` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `linkUCDtoC` FOREIGN KEY (`CollectionID`) REFERENCES `Collections` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `UserCollectionInprogress`
--
ALTER TABLE `UserCollectionInprogress`
ADD CONSTRAINT `linkUCItoAUD` FOREIGN KEY (`UserID`) REFERENCES `AppUserData` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `linkUCItoC` FOREIGN KEY (`CollectionID`) REFERENCES `Collections` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `UserMadeCollectionList`
--
ALTER TABLE `UserMadeCollectionList`
ADD CONSTRAINT `linkUMCLtoC` FOREIGN KEY (`CollectionID`) REFERENCES `Collections` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `linkUMCLtoWUD` FOREIGN KEY (`UserID`) REFERENCES `WebUserData` (`UserID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
