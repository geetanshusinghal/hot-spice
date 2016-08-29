CREATE DATABASE  IF NOT EXISTS `hot_spice`
USE `hot_spice`;


DROP TABLE IF EXISTS `categories`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(45) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_active` smallint(6) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_name_UNIQUE` (`category_name`)
);

    
DROP TABLE IF EXISTS `category_attributes`;
CREATE TABLE `category_attributes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL,
  `attribute_name` varchar(45) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_mandatory` smallint(6) NOT NULL DEFAULT '1',
  `data_type` varchar(20) NOT NULL,
  `validation` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_active` smallint(6) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index3` (`category_id`,`attribute_name`),
  KEY `index2` (`category_id`)
);

DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL,
  `dish_name` varchar(45) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `price` double NOT NULL DEFAULT '1',
  `unit` varchar(30) NOT NULL,
  `image` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_active` smallint(6) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dish_name_UNIQUE` (`dish_name`)
);


DROP TABLE IF EXISTS `dish_attributes`;
CREATE TABLE `dish_attributes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dish_id` bigint(20) NOT NULL,
  `attribute_name` varchar(50) NOT NULL,
  `attribute_value` varchar(200) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_active` smallint(6) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`dish_id`,`attribute_name`)
);

DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_mobile` bigint(20) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `order_amount` double NOT NULL,
  `discount_amount` double NOT NULL,
  `payment_amount` double NOT NULL,
  `total_refund_amount` double NOT NULL DEFAULT '0',
  `order_status` varchar(45) NOT NULL,
  `address` varchar(500) NOT NULL,
  `delivered_by` varchar(50) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_active` smallint(6) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `index2` (`user_mobile`),
  KEY `index3` (`user_email`)
);


DROP TABLE IF EXISTS `cart_deail`;
CREATE TABLE `cart_deail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `dish_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL,
  `total_amount` double NOT NULL,
  `refund_amount` double NOT NULL,
  `status` varchar(45) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_active` smallint(6) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `index2` (`order_id`)
);




