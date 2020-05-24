-- Script Inicial al crear servicio de Mysql para Docker
CREATE DATABASE IF NOT EXISTS metrobus CHARACTER SET utf8 COLLATE utf8_general_ci;
USE metrobus;
CREATE TABLE metrobus_info(
	id_record varchar(40) NOT NULL,
	id_vehicle varchar(4) NOT NULL,
	date_updated datetime,
	longitude decimal(15,3),
	latitude decimal(15,3),
	county varchar(30),
	PRIMARY KEY(id_record)
);