CREATE SCHEMA `demo` ;

CREATE  TABLE `demo`.`Vote` (
  `Nom` VARCHAR(45) NULL ,
  `Num` INT NULL ,
  `Pourc` INT NULL );


CREATE  TABLE `demo`.`Votant` (
  `Nom` VARCHAR(45) NULL ,
  `Vote` INT NULL ,
  `Age` INT NULL );

SET @@global.time_zone = '+00:00';
SET @@session.time_zone = '+00:00';
