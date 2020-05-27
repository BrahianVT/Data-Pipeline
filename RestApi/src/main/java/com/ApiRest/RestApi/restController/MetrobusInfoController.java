package com.ApiRest.RestApi.restController;

import com.ApiRest.RestApi.entities.MetrobusInfo;
import com.ApiRest.RestApi.service.MetrobusInfoServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Rest API para la entidad metroBusInfo
 * @author BrahianVT
 * */

@RestController
@RequestMapping("api/metrobusInfo")
@Slf4j
public class MetrobusInfoController {
    private final MetrobusInfoServiceInterface metrobusService;

    @Autowired
    public MetrobusInfoController(MetrobusInfoServiceInterface metrobusService){
        this.metrobusService = metrobusService;
    }
    @GetMapping("findAllMetroBus/{pageNo}/{pageSize}")
    public ResponseEntity<List<MetrobusInfo>> allMetrobus(@PathVariable int pageNo, @PathVariable int pageSize){
        log.info("Finding information for all metrobus info");
        List<MetrobusInfo> findMetrobus = metrobusService.findAll(pageNo, pageSize);
        if(findMetrobus== null && findMetrobus.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<List<MetrobusInfo>>(findMetrobus, HttpStatus.OK);
    }

    @GetMapping("findByLonAndLat/{longitude}/{latitude}")
    public ResponseEntity<List<MetrobusInfo>> findByLongitudeAndLatitude(@PathVariable  BigDecimal longitude, @PathVariable BigDecimal latitude) {
        log.info("Finding  for coordinates  Lon: " + longitude + " Lat: " + latitude);

        List<MetrobusInfo> listByCoordinates = metrobusService.findByLongitudeAndLatitude(longitude, latitude);
        if(listByCoordinates == null && listByCoordinates.size() == 0)
            return new ResponseEntity<List<MetrobusInfo>>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<List<MetrobusInfo>>(listByCoordinates,HttpStatus.OK);
    }

    @GetMapping("findByCounty/{county}")
    public ResponseEntity<List<MetrobusInfo>> findByCounty(@PathVariable  String county) {
        log.info("Finding  information for county  " + county );

        List<MetrobusInfo> findMetrobusByCounty = metrobusService.findByCounty(county);
        if(findMetrobusByCounty == null && findMetrobusByCounty.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<List<MetrobusInfo>>(findMetrobusByCounty, HttpStatus.OK);
    }

    @GetMapping("findByIdVehicle/{idVehicle}")
    public ResponseEntity<List<MetrobusInfo>> findByIdVehicle(@PathVariable String idVehicle){
        log.info("Find information about the IdVehicle: " + idVehicle);

        List<MetrobusInfo> findMetrobusById = metrobusService.findByIdVehicle(idVehicle);

        if(findMetrobusById == null && findMetrobusById.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<List<MetrobusInfo>>(findMetrobusById, HttpStatus.OK);
    }

    @GetMapping("findAllCounty")
    public ResponseEntity<List<String>> findAllCounty(){
        log.info("Find all the counties" );

        List<String> listCounty = metrobusService.findAllCounty();
        if(listCounty == null && listCounty.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<List<String>>(listCounty, HttpStatus.OK);
    }

}
