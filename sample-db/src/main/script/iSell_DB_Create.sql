DROP TABLE IF EXISTS property_details;
CREATE TABLE `property_details` (
  `id` varchar(10) NOT NULL,
  `name` varchar(250) NOT NULL,
  `PropertyUId` varchar(250) DEFAULT NULL,
  `Address1` varchar(250) DEFAULT NULL,
  `Address2` varchar(250) DEFAULT NULL,
  `Address3` varchar(250) DEFAULT NULL,
  `City` varchar(250) DEFAULT NULL,
  `State` varchar(250) DEFAULT NULL,
  `Country` varchar(250) DEFAULT NULL,
  `PinCode` int(11) NOT NULL,
  `Capacity` int(11) NOT NULL,
  PRIMARY KEY (`HotelID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*DO we need to consider that there will be 3 different address fields? Or It can be a plain text field. Whole address togeather
 * City and State are Important but I think we should also have Country as we are targeting clients outside India.
 * We should define a screen to add property details. Do we also need to store information about contanct person to talk to. This will 
 * help our team to start discussions in case of any doubts or clarifications required.
 * At present we can add data to this table using scripts but I think for future we should give access to hotel administrator or revnomix user
 * to update the information. Hotel Capacity may change.
 * We should also have a flag to indicate property status is live / prelive / discontinued.
 * We should also have modificationDate to understand when information was modified.
 * Currency field needs to be added for property
 * 
 * Add geo cordinated to show on the map
*/
DROP TABLE IF EXISTS hotelchannel; /*OTAs*/
CREATE TABLE `hotelchannel` (
  `ChannelID` varchar(10) NOT NULL,
  `ChannelName` varchar(255) NOT NULL,
  PRIMARY KEY (`ChannelID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/* API  URL
 * User name and password for OTA should be stored. 
 * */

/*
 * 14 Sep 2016: Can One OTA have multiple URLs based on the region
 * Like for Australia say for tripadvisor tripadvisor.au
 * For India tripadvisor.in In such cases just name can not be unique key
 * We should have url + name as unique key
 * 
 * Hotel and OTA mapping is there in the transcational data but that mapping is missing as mappings table
 * would there be any validtions required for handling such scenarios?
 * */
DROP TABLE IF EXISTS channelproduction; /*transactional data*/
CREATE TABLE `channelproduction` (
  `HotelID` varchar(10) NOT NULL,
  `RefID` varchar(25) NOT NULL,
  `ChannelID` varchar(10) NOT NULL,
  `CaptureDate` date NOT NULL,
  `ModifiedDate` date NOT NULL,
  `ArrivalDate` date NOT NULL,
  `DepartureDate` date NOT NULL,
  `LOS` int(11) NOT NULL DEFAULT '0',
  `Status` varchar(50) NOT NULL,
  `RoomSold` int(11) NOT NULL DEFAULT '0',
  `RoomRevenue` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`RefID`,`HotelID`,`ChannelID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/* 
 * Transaction Revenue table needs to be added 
 * which will store all the details for money like commission, extra charges, tax etc and net amount 
 * and currency
 * 
 * RefId is to be read from
 * ChannelRefID: Staah_data
 * Channel Ref Id:Staah_data_max
 * Booking No: Maximojo

	CapturedDate
	Staah: CreateDate
	Staah_max: Booking Date
	Maximojo: Arrival Date

	LOS: departure - arrival
	
	RoomRevenue:
	Staah: AmountPaid
	Staah_max: Total Amount: (All Inclusive)(Commission as well)
	Maximojo: Total Amount

	For Status we should have a table this staus will be used by Revnomix Different channel managers will have different statuses
	So those can be stored in channel manager status and then we can map the revnomix status to channel manager status.
	
	JSON object to store the amount breakup 

	Number of guests should be added database
	Add Json field for additional field.
	
	Tractional and room type mapping needs to be stored.
	
	Country of arrival for Guest needs to be stored
 */

/*
DROP TABLE IF EXISTS compset;
CREATE TABLE `compset` (
  `CompetitorID` varchar(10) NOT NULL,
  `CompetitorName` varchar(255) NOT NULL,
  PRIMARY KEY (`CompetitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

*/

CREATE TABLE `rateshoppingdata` (
  `HotelID` varchar(10) NOT NULL,
  `ExtractionDate` Date NOT NULL,
  `OccupanyDate` Date NOT NULL,
  `ChannelID` Date NOT NULL,
  `Rate` float  default,
  'Status' varchar(10) NULL
  PRIMARY KEY (`CompetitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*
 * Status will indicate where the hotel is sold or not if the hotel is sold we 
 * have to fetch the last received rate to show in the rates
 * 
 * */

CREATE TABLE `raterecommendationdata` (
  `HotelID` varchar(10) NOT NULL,
  `RecommendationDate` Date NOT NULL,
  `OccupanyDate` Date NOT NULL,
  `MAR` float  NOT NULL,
  `BAR` float  NOT NULL,
  `OVR` float  NULL,
  `RecommendedRate` float  NOT NULL,
  `Comments` varchar(10) NULL
  PRIMARY KEY (`CompetitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* MAR Market appropriate rate
 * BAR Best Available Rate derived from remaining capacity
 * OVR Over ridden rate
 * Comments are mandatory and upto 20 characters if rates areover  ridden
 * By default MAR should be selected as Radio option but if user wants to select BAR then BAR value
 * should go to the recommended rate. Not BAR Value
 * */

CREATE TABLE `baralgodata` (
  `HotelID` varchar(10) NOT NULL,
  `SeasonID` varchar(10) NOT NULL,
  `DOW` varchar(10) NOT NULL,
  `slope` float  NOT NULL,
  `intercept` float  NOT NULL
  PRIMARY KEY (`CompetitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS hotelcompset; /*Competitor mapping*/
CREATE TABLE `hotelcompset` (
  `HotelID` varchar(10) NOT NULL,
  `CompetitorID` varchar(10) NOT NULL,
  `ClientHotel` tinyint(1) DEFAULT '1',
  `MarketPosition` int(11) NOT NULL,
  `
` tinyint(1) DEFAULT '1',
  `UseMktPos` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`HotelID`,`CompetitorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS marketpricing;
CREATE TABLE `marketpricing` (
  `CompetitorID` varchar(10) NOT NULL,
  `CaptureDate` date NOT NULL,
  `OccupancyDate` date NOT NULL,
  `CompRate` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CompetitorID`,`CaptureDate`,`OccupancyDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*This data we get from hotelier we do not get from the excel*/
DROP TABLE IF EXISTS isell_hotel;
CREATE TABLE `isell_hotel` (
  `HotelID` varchar(10) NOT NULL,
  `CaptureDate` date NOT NULL,
  `OccupancyDate` date NOT NULL,
  `Capacity` int(11) NOT NULL,
  `OOO` int(11) NOT NULL DEFAULT '0',
  `TotalSold` int(11) NOT NULL DEFAULT '0',
  `TotalRevenue` double NOT NULL DEFAULT '0',
  `TransSold` int(11) NOT NULL DEFAULT '0',
  `TransRevenue` double NOT NULL DEFAULT '0',
  `GroupBlock` int(11) NOT NULL DEFAULT '0',
  `GroupPickup` int(11) NOT NULL DEFAULT '0',
  `GroupRevenue` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`HotelID`,`CaptureDate`,`OccupancyDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS isell_segment;
CREATE TABLE `isell_segment` (
  `HotelID` varchar(10) NOT NULL,
  `CaptureDate` date NOT NULL,
  `OccupancyDate` date NOT NULL,
  `SegmentID` int(11) NOT NULL,
  `RoomSold` int(11) NOT NULL DEFAULT '0',
  `RoomRevenue` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`HotelID`,`CaptureDate`,`OccupancyDate`,`SegmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS market_price_sensetivity;
CREATE TABLE `market_price_sensetivity` (
  `HotelID` varchar(10) NOT NULL,
  `MONTH` int(11) NOT NULL COMMENT 'Analytical MONTH - there will be 13 months AND EACH MONTH will have 4 weeks',
  `DOWID` int(11) NOT NULL, /*Check in postgres how we can define the default day of week*/
  `PriceMU` double NOT NULL DEFAULT '0',
  `PriceSigma` double NOT NULL DEFAULT '0' 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS segments;
CREATE TABLE `segments` (
  `HotelID` varchar(10) NOT NULL,
  `SegmentID` int(11) NOT NULL,
  `SegmentName` varchar(250) NOT NULL,
  `SegmentType` varchar(250) NOT NULL COMMENT 'GROUP,Qualified,Unqualified',
  `ForPricing` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
