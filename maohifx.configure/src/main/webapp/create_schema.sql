-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema maohifx
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `maohifx` ;

-- -----------------------------------------------------
-- Schema maohifx
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `maohifx` DEFAULT CHARACTER SET utf8 ;
USE `maohifx` ;

-- -----------------------------------------------------
-- Table `contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contact` (
  `uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `lastname` VARCHAR(255) NULL,
  `middlename` VARCHAR(255) NULL,
  `firstname` VARCHAR(255) NULL,
  PRIMARY KEY (`uuid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `customer` (
  `uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `code` VARCHAR(45) NULL,
  INDEX `fk_supplier_contact1_idx` (`uuid` ASC),
  PRIMARY KEY (`uuid`),
  CONSTRAINT `fk_customer_contact`
    FOREIGN KEY (`uuid`)
    REFERENCES `contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `salesman`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `salesman` (
  `uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `code` VARCHAR(45) NULL,
  `sales_commission` DOUBLE NULL,
  INDEX `fk_salesman_contact1_idx` (`uuid` ASC),
  PRIMARY KEY (`uuid`),
  CONSTRAINT `fk_salesman_contact1`
    FOREIGN KEY (`uuid`)
    REFERENCES `contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `invoice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invoice` (
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
    REFERENCES `customer` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_salesman1`
    FOREIGN KEY (`salesman_uuid`)
    REFERENCES `salesman` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tva` (
  `type` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `label` VARCHAR(255) NULL,
  `rate` DOUBLE NULL,
  PRIMARY KEY (`type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product` (
  `uuid` VARCHAR(255) NOT NULL,
  `tva_type` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `designation` VARCHAR(255) NULL,
  PRIMARY KEY (`uuid`),
  INDEX `fk_product_tva1_idx` (`tva_type` ASC),
  CONSTRAINT `fk_product_tva1`
    FOREIGN KEY (`tva_type`)
    REFERENCES `tva` (`type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `packaging`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `packaging` (
  `code` VARCHAR(45) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_packaging`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_packaging` (
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
    REFERENCES `product` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_packaging_packaging1`
    FOREIGN KEY (`packaging_code`)
    REFERENCES `packaging` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `invoice_line`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invoice_line` (
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
    REFERENCES `invoice` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_line_tva1`
    FOREIGN KEY (`tva_type`)
    REFERENCES `tva` (`type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_line_product_packaging1`
    FOREIGN KEY (`product_packaging_product_uuid` , `product_packaging_packaging_code`)
    REFERENCES `product_packaging` (`product_uuid` , `packaging_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `payment_mode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_mode` (
  `id` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `label` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `invoice_payment_line`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invoice_payment_line` (
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
    REFERENCES `invoice` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_mode_line_payment_mode1`
    FOREIGN KEY (`payment_mode`)
    REFERENCES `payment_mode` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `phone` (
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
    REFERENCES `contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `email` (
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
    REFERENCES `contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supplier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supplier` (
  `uuid` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `code` VARCHAR(45) NULL,
  INDEX `fk_supplier_contact1_idx` (`uuid` ASC),
  PRIMARY KEY (`uuid`),
  CONSTRAINT `fk_supplier_contact1`
    FOREIGN KEY (`uuid`)
    REFERENCES `contact` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_packaging_lot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_packaging_lot` (
  `product_packaging_product_uuid` VARCHAR(255) NOT NULL,
  `product_packaging_packaging_code` VARCHAR(45) NOT NULL,
  `lot` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `cost_price` DOUBLE NULL,
  `weighted_average_cost_price` DOUBLE NULL,
  `best_before` DATETIME NULL,
  PRIMARY KEY (`product_packaging_product_uuid`, `product_packaging_packaging_code`, `lot`),
  INDEX `fk_product_lot_product_packaging1_idx` (`product_packaging_product_uuid` ASC, `product_packaging_packaging_code` ASC),
  CONSTRAINT `fk_product_lot_product_packaging1`
    FOREIGN KEY (`product_packaging_product_uuid` , `product_packaging_packaging_code`)
    REFERENCES `product_packaging` (`product_uuid` , `packaging_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_packaging_movement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_packaging_movement` (
  `product_uuid` VARCHAR(255) NOT NULL,
  `packaging_code` VARCHAR(45) NOT NULL,
  `lot` INT NOT NULL,
  `id` INT NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  `quantity` DOUBLE NULL,
  PRIMARY KEY (`product_uuid`, `packaging_code`, `lot`, `id`),
  INDEX `fk_product_packaging_movement_product_packaging_lot1_idx` (`product_uuid` ASC, `packaging_code` ASC, `lot` ASC),
  CONSTRAINT `fk_product_packaging_movement_product_packaging_lot1`
    FOREIGN KEY (`product_uuid` , `packaging_code` , `lot`)
    REFERENCES `product_packaging_lot` (`product_packaging_product_uuid` , `product_packaging_packaging_code` , `lot`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `barcode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `barcode` (
  `code` VARCHAR(255) NOT NULL,
  `creation_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_packaging_barcode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_packaging_barcode` (
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
    REFERENCES `product_packaging` (`product_uuid` , `packaging_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_packaging_barcode_barcode1`
    FOREIGN KEY (`barcode_code`)
    REFERENCES `barcode` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
