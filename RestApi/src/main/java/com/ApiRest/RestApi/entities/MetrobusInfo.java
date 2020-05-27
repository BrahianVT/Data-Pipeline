package com.ApiRest.RestApi.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad para la tabla en base de datos
 * "metrobus_info"
 * Aqui usamos JPA y  Lombok que implementara getter/setter
 * @author BrahianVT
 * */

@Data
@Entity
@Table(name = "metrobus_info")
public class MetrobusInfo {

    @Id
    @Column(name = "id_record")
    private String IdRecord;

    @Column(name = "id_vehicle")
    private String idVehicle;

    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "county")
    private String county;
}
