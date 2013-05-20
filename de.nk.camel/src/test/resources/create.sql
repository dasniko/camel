CREATE DATABASE `camel` /*!40100 CHARACTER SET utf8 COLLATE 'utf8_general_ci' */;

USE `camel`;

CREATE TABLE `camel_demo` (
	`id` INT(18) NULL,
	`message` VARCHAR(255) NULL,
	`datetime` DATETIME NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
