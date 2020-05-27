package com.ApiRest.RestApi.service;

import com.ApiRest.RestApi.entities.MetrobusInfo;
import com.ApiRest.RestApi.repository.MetrobusInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Capa de negocio MetrobusInfo
 * Implementacion de la interfaz :MetrobusInfoServiceInterface
 * @author BrahianVT
 * */

@Service
@Transactional
@Slf4j
public class MetrobusInfoService  implements  MetrobusInfoServiceInterface{
    private final MetrobusInfoRepository metrobusInfoRepository;

    @Autowired
    public MetrobusInfoService(MetrobusInfoRepository metrobusInfoRepository){
        this.metrobusInfoRepository = metrobusInfoRepository;
    }

    @Override
    public List<MetrobusInfo> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<MetrobusInfo> pagedResult = metrobusInfoRepository.findAll(paging);
        return pagedResult.toList();
    }

    @Override
    public List<MetrobusInfo> findByLongitudeAndLatitude(BigDecimal longitude, BigDecimal latitude) {
        List<MetrobusInfo> metrobusInfoOptional = metrobusInfoRepository.findByLongitudeAndLatitude(longitude, latitude);

        return metrobusInfoOptional;
    }

    @Override
    public List<MetrobusInfo> findByCounty(String county) {
        return metrobusInfoRepository.findByCounty(county);
    }

    @Override
    public List<MetrobusInfo> findByIdVehicle(String idVehicle) {
        return metrobusInfoRepository.findByIdVehicle(idVehicle);
    }

    @Override
    public List<String> findAllCounty() {
        return metrobusInfoRepository.findAllCounty();
    }


}
