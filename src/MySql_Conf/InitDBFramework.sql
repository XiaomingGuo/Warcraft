-- Create DB
create database earthquake;

-- Create tables
CREATE TABLE IF NOT EXISTS `earthquake`.`user_info` (`id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,`name` VARCHAR(50) NOT NULL,`password` VARCHAR(50) NOT NULL,`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,`department` VARCHAR(20) NOT NULL,`permission` int(10) NOT NULL DEFAULT 0,`picture` BLOB NULL,UNIQUE INDEX `id_UNIQUE` (`id` ASC),
UNIQUE INDEX `name_UNIQUE` (`name` ASC));

CREATE TABLE IF NOT EXISTS `earthquake`.`product_info` (
`id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(200) NOT NULL,`product_type` VARCHAR(100) NOT NULL,
`IN_QTY` INT(50) NOT NULL DEFAULT 0,

`OUT_QTY` INT(50) NOT NULL DEFAULT 0,

`description` VARCHAR(200) NULL,
`picture` BLOB NULL,UNIQUE INDEX `id_UNIQUE` (`id` ASC),
UNIQUE INDEX `name_UNIQUE` (`name` ASC));

CREATE TABLE IF NOT EXISTS `earthquake`.`material_record` (
`id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
`proposer` VARCHAR(50) NOT NULL,
`material_name` VARCHAR(50) NOT NULL,
`QTY` INT(11) NOT NULL,
`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
`isApprove` INT(11) NOT NULL DEFAULT '0',UNIQUE INDEX `id_UNIQUE` (`id` ASC))
;

CREATE TABLE IF NOT EXISTS `earthquake`.`product_type` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `id_UNIQUE` (`id` ASC),
UNIQUE INDEX `name_UNIQUE` (`name` ASC));

CREATE TABLE IF NOT EXISTS `earthquake`.`material_storage` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`Bar_Code` VARCHAR(8) NOT NULL,`Batch_Lot` VARCHAR(11) NOT NULL,`Amount` INT(10) NOT NULL,`Price_Per_Unit` FLOAT NOT NULL,`Total_Price` DOUBLE NOT NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `id_UNIQUE` (`id` ASC));