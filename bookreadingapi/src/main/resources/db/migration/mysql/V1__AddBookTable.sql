CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reader` varchar(64) DEFAULT NULL,
  `isbn` varchar(128) DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;