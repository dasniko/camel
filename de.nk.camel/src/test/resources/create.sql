/* usage at command line: mysql -u root < create.sql */

CREATE DATABASE `camel` /*!40100 CHARACTER SET utf8 COLLATE 'utf8_general_ci' */;

USE `camel`;

CREATE TABLE `camel_demo` (
	`id` BIGINT(18) NULL DEFAULT NULL,
	`message` VARCHAR(255) NULL DEFAULT NULL,
	`datetime` DATETIME NULL DEFAULT NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
