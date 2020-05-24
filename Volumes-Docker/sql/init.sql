-- Script Inicial al crear servicio de Mysql para Docker
CREATE DATABASE IF NOT EXISTS metrobus CHARACTER SET utf8 COLLATE utf8_general_ci;
USE metrobus;
CREATE TABLE metrobus_info(
	id_record varchar(40) NOT NULL,
	id_vehicle varchar(6) NOT NULL,
	date_updated datetime,
	longitude decimal(17,14),
	latitude decimal(16,14),
	county varchar(30) CHARSET utf8mb4,
	PRIMARY KEY(id_record)
) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;