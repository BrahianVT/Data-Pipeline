package com.ApiRest.RestApi.repository;


import com.ApiRest.RestApi.entities.MetrobusInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Utilizamos la interfaz de JPA "PagingAndSortingRepository"
 * para la funcionalidad CRUD y paginación
 * @author Brahian VT
 * */
public interface MetrobusInfoRepository extends PagingAndSortingRepository<MetrobusInfo,String> {

    List<MetrobusInfo> findByLongitudeAndLatitude(BigDecimal longitude, BigDecimal latitude );
    List<MetrobusInfo> findByCounty(String county);
    List<MetrobusInfo> findByIdVehicle(String idVehicle);

    @Query(value = "SELECT DISTINCT m.county FROM metrobus_info m where m.county != 'null'", nativeQuery = true)
    List<String> findAllCounty();
}
