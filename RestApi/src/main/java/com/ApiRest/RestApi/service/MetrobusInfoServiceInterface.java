package com.ApiRest.RestApi.service;

import com.ApiRest.RestApi.entities.MetrobusInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Capa de negocio MetrobusInfo
 * @author BrahianVT
 * */

public interface  MetrobusInfoServiceInterface {
    List<MetrobusInfo> findAll(int pageNo, int pageSize);
    List<MetrobusInfo> findByLongitudeAndLatitude(BigDecimal longitude, BigDecimal latitude );
    List<MetrobusInfo> findByCounty(String county);
    List<MetrobusInfo> findByIdVehicle(String idVehicle);
    List<String> findAllCounty();
}
