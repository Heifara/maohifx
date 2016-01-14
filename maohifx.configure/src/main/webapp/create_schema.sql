-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema maohifx
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema maohifx
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `maohifx` DEFAULT CHARACTER SET utf8 ;
USE `maohifx` ;

-- -----------------------------------------------------
-- Table `maohifx`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`contact` (
  `uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `lastname` VARCHAR(255) NULL,
  `middlename` VARCHAR(255) NULL,
  `firstname` VARCHAR(255) NULL,
  PRIMARY KEY (`uuid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`customer` (
  `uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `code` VARCHAR(45) NULL,
  INDEX `fk_supplier_contact1_idx` (`uuid` ASC),
  PRIMARY KEY (`uuid`),
  CONSTRAINT `fk_customer_contact`
    FOREIGN KEY (`uuid`)
    REFERENCES `maohifx`.`contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`salesman`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`salesman` (
  `uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `code` VARCHAR(45) NULL,
  `sales_commission` DOUBLE NULL,
  INDEX `fk_salesman_contact1_idx` (`uuid` ASC),
  PRIMARY KEY (`uuid`),
  CONSTRAINT `fk_salesman_contact1`
    FOREIGN KEY (`uuid`)
    REFERENCES `maohifx`.`contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`invoice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`invoice` (
  `uuid` VARCHAR(255) NOT NULL,
  `customer_uuid` VARCHAR(255) NOT NULL,
  `salesman_uuid` VARCHAR(255) NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `number` INT NULL,
  `date` DATETIME NULL,
  `customer_name` VARCHAR(255) NULL,
  `valid_date` DATETIME NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_invoice_customer1_idx` (`customer_uuid` ASC),
  INDEX `fk_invoice_salesman1_idx` (`salesman_uuid` ASC),
  CONSTRAINT `fk_invoice_customer1`
    FOREIGN KEY (`customer_uuid`)
    REFERENCES `maohifx`.`customer` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_salesman1`
    FOREIGN KEY (`salesman_uuid`)
    REFERENCES `maohifx`.`salesman` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`tva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`tva` (
  `type` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `label` VARCHAR(255) NULL,
  `rate` DOUBLE NULL,
  PRIMARY KEY (`type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`product` (
  `uuid` VARCHAR(255) NOT NULL,
  `tva_type` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `designation` VARCHAR(255) NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_product_tva1_idx` (`tva_type` ASC),
  CONSTRAINT `fk_product_tva1`
    FOREIGN KEY (`tva_type`)
    REFERENCES `maohifx`.`tva` (`type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`packaging`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`packaging` (
  `code` VARCHAR(45) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`product_packaging`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`product_packaging` (
  `product_uuid` VARCHAR(255) NOT NULL,
  `packaging_code` VARCHAR(45) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `main` TINYINT(1) NULL,
  `selling_price` DOUBLE NULL,
  INDEX `fk_product_packaging_product1_idx` (`product_uuid` ASC),
  INDEX `fk_product_packaging_packaging1_idx` (`packaging_code` ASC),
  PRIMARY KEY (`product_uuid`, `packaging_code`),
  CONSTRAINT `fk_product_packaging_product1`
    FOREIGN KEY (`product_uuid`)
    REFERENCES `maohifx`.`product` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_packaging_packaging1`
    FOREIGN KEY (`packaging_code`)
    REFERENCES `maohifx`.`packaging` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`invoice_line`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`invoice_line` (
  `uuid` VARCHAR(255) NOT NULL,
  `invoice_uuid` VARCHAR(255) NOT NULL,
  `product_packaging_product_uuid` VARCHAR(255) NULL,
  `product_packaging_packaging_code` VARCHAR(45) NULL,
  `tva_type` INT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `position` INT NULL,
  `bar_code` VARCHAR(255) NULL,
  `label` VARCHAR(255) NULL,
  `quantity` DOUBLE NULL,
  `selling_price` DOUBLE NULL,
  `discount_rate` DOUBLE NULL,
  `tva_rate` DOUBLE NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_invoice_line_invoice_idx` (`invoice_uuid` ASC),
  INDEX `fk_invoice_line_tva1_idx` (`tva_type` ASC),
  INDEX `fk_invoice_line_product_packaging1_idx` (`product_packaging_product_uuid` ASC, `product_packaging_packaging_code` ASC),
  CONSTRAINT `fk_invoice_line_invoice`
    FOREIGN KEY (`invoice_uuid`)
    REFERENCES `maohifx`.`invoice` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_line_tva1`
    FOREIGN KEY (`tva_type`)
    REFERENCES `maohifx`.`tva` (`type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_line_product_packaging1`
    FOREIGN KEY (`product_packaging_product_uuid` , `product_packaging_packaging_code`)
    REFERENCES `maohifx`.`product_packaging` (`product_uuid` , `packaging_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`payment_mode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`payment_mode` (
  `id` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `label` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`invoice_payment_line`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`invoice_payment_line` (
  `uuid` VARCHAR(255) NOT NULL,
  `invoice_uuid` VARCHAR(255) NOT NULL,
  `payment_mode` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `position` INT NULL,
  `amount` DOUBLE NULL,
  `comment` VARCHAR(255) NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_payment_line_invoice1_idx` (`invoice_uuid` ASC),
  INDEX `fk_payment_mode_line_payment_mode1_idx` (`payment_mode` ASC),
  CONSTRAINT `fk_payment_line_invoice1`
    FOREIGN KEY (`invoice_uuid`)
    REFERENCES `maohifx`.`invoice` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_mode_line_payment_mode1`
    FOREIGN KEY (`payment_mode`)
    REFERENCES `maohifx`.`payment_mode` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`phone` (
  `uuid` VARCHAR(255) NOT NULL,
  `contact_uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `label` VARCHAR(255) NULL,
  `number` VARCHAR(45) NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_phone_contact1_idx` (`contact_uuid` ASC),
  CONSTRAINT `fk_phone_contact1`
    FOREIGN KEY (`contact_uuid`)
    REFERENCES `maohifx`.`contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`email` (
  `uuid` VARCHAR(255) NOT NULL,
  `contact_uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `label` VARCHAR(255) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_email_contact1_idx` (`contact_uuid` ASC),
  CONSTRAINT `fk_email_contact1`
    FOREIGN KEY (`contact_uuid`)
    REFERENCES `maohifx`.`contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`supplier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`supplier` (
  `uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `code` VARCHAR(45) NULL,
  INDEX `fk_supplier_contact1_idx` (`uuid` ASC),
  PRIMARY KEY (`uuid`),
  CONSTRAINT `fk_supplier_contact1`
    FOREIGN KEY (`uuid`)
    REFERENCES `maohifx`.`contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`product_packaging_lot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`product_packaging_lot` (
  `product_packaging_product_uuid` VARCHAR(255) NOT NULL,
  `product_packaging_packaging_code` VARCHAR(45) NOT NULL,
  `lot` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `cost_price` DOUBLE NULL,
  `weighted_average_cost_price` DOUBLE NULL,
  `best_before` DATETIME NULL,
  PRIMARY KEY (`lot`, `product_packaging_packaging_code`, `product_packaging_product_uuid`),
  INDEX `fk_product_lot_product_packaging1_idx` (`product_packaging_product_uuid` ASC, `product_packaging_packaging_code` ASC),
  CONSTRAINT `fk_product_lot_product_packaging1`
    FOREIGN KEY (`product_packaging_product_uuid` , `product_packaging_packaging_code`)
    REFERENCES `maohifx`.`product_packaging` (`product_uuid` , `packaging_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`product_movement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`product_movement` (
  `id` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `quantity` DOUBLE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`barcode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`barcode` (
  `code` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maohifx`.`product_packaging_barcode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maohifx`.`product_packaging_barcode` (
  `product_packaging_product_uuid` VARCHAR(255) NOT NULL,
  `product_packaging_packaging_code` VARCHAR(45) NOT NULL,
  `barcode_code` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  INDEX `fk_product_packaging_barcode_product_packaging1_idx` (`product_packaging_product_uuid` ASC, `product_packaging_packaging_code` ASC),
  INDEX `fk_product_packaging_barcode_barcode1_idx` (`barcode_code` ASC),
  PRIMARY KEY (`product_packaging_product_uuid`, `product_packaging_packaging_code`, `barcode_code`),
  CONSTRAINT `fk_product_packaging_barcode_product_packaging1`
    FOREIGN KEY (`product_packaging_product_uuid` , `product_packaging_packaging_code`)
    REFERENCES `maohifx`.`product_packaging` (`product_uuid` , `packaging_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_packaging_barcode_barcode1`
    FOREIGN KEY (`barcode_code`)
    REFERENCES `maohifx`.`barcode` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
