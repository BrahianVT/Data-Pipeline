package com.ApiRest.RestApi;

import com.ApiRest.RestApi.entities.MetrobusInfo;
import com.ApiRest.RestApi.restController.MetrobusInfoController;
import com.ApiRest.RestApi.service.MetrobusInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.doReturn;

import static org.hamcrest.Matchers.hasSize;

/**
 * Prueba Unitarias  para el Rest API
 *
 * @Brahian VT
 * */

@RunWith(SpringRunner.class)
@WebMvcTest(MetrobusInfoController.class)
public class MetrobusInfoControllerTest {

    // Creamos un mock para la capa de controlador
    @Autowired
    private MockMvc mockMvc;

    //  Creamos un mock del servicio metrobusInfoService
    @MockBean
    private MetrobusInfoService metrobusService;

    // para mapear de Json
    @Autowired
    ObjectMapper objectMapper;

    List<MetrobusInfo>  listMetrobusInfo;

    @Before
    public void setup(){
        listMetrobusInfo = new ArrayList<>();
        MetrobusInfo metrobus = new MetrobusInfo();
        metrobus.setIdRecord("qwe123");
        metrobus.setIdVehicle("2");
        metrobus.setDateUpdated(LocalDateTime.now());
        metrobus.setLongitude(new BigDecimal("-90.33535"));
        metrobus.setLatitude(new BigDecimal("-16.246"));
        metrobus.setCounty("Coyoacán");
        listMetrobusInfo.add(metrobus);

        metrobus = new MetrobusInfo();
        metrobus.setIdRecord("sfer234");
        metrobus.setIdVehicle("5");
        metrobus.setDateUpdated(LocalDateTime.now());
        metrobus.setLongitude(new BigDecimal("-93.3466"));
        metrobus.setLatitude(new BigDecimal("-12.573"));
        metrobus.setCounty("Iztapalapa");
        listMetrobusInfo.add(metrobus);

        metrobus = new MetrobusInfo();
        metrobus.setIdRecord("ryr66t");
        metrobus.setIdVehicle("56");
        metrobus.setDateUpdated(LocalDateTime.now());
        metrobus.setLongitude(new BigDecimal("-92.19495"));
        metrobus.setLatitude(new BigDecimal("-20.603"));
        metrobus.setCounty("Cuauhtémoc");
        listMetrobusInfo.add(metrobus);
    }

    @Test
    public void testAllMetrobus() throws  Exception {
        doReturn(listMetrobusInfo).when(metrobusService).findAll(1,3);
        mockMvc.perform(
                get("/api/metrobusInfo/findAllMetroBus/1/3").contentType(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$", hasSize(3)))
                  .andExpect(jsonPath("$[0].idRecord", is("qwe123")))
                  .andExpect(jsonPath("$[1].idVehicle", is("5")))
                  .andExpect(jsonPath("$[2].county", is("Cuauhtémoc")));
    }

    @Test
    public void testFindByLongitudeAndLatitude() throws Exception {
        List<MetrobusInfo> testLonLat = Arrays.asList(listMetrobusInfo.get(0));

        doReturn(testLonLat).when(metrobusService).findByLongitudeAndLatitude(any(BigDecimal.class), any(BigDecimal.class));


        mockMvc.perform(
                get("/api/metrobusInfo/findByLonAndLat/-90.33535/-16.246").contentType(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$", hasSize(1)))
                  .andExpect(jsonPath("$[0].idRecord", is("qwe123")))
                  .andExpect(jsonPath("$[0].longitude",is(-90.33535)))
                  .andExpect(jsonPath("$[0].latitude",is(-16.246)));
    }

    @Test
    public void testFindByCounty() throws Exception {
        List<MetrobusInfo> listByCounty = new ArrayList<>();
        listByCounty.add(listMetrobusInfo.get(1));
        MetrobusInfo m = new MetrobusInfo();
        m.setIdRecord("1q2w3e");
        m.setIdVehicle("68");
        m.setDateUpdated(LocalDateTime.now());
        m.setLongitude(new BigDecimal("-90.457"));
        m.setLatitude(new BigDecimal("-14.395"));
        m.setCounty("Iztapalapa");
        listByCounty.add(m);

        doReturn(listByCounty).when(metrobusService).findByCounty(anyString());

        mockMvc.perform(
                get("/api/metrobusInfo/findByCounty/Iztapalapa").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].county", is("Iztapalapa")))
                .andExpect(jsonPath("$[1].county", is("Iztapalapa")));
    }

    @Test
    public void testFindByIdVehicle() throws  Exception {
        List<MetrobusInfo> listByIDVehicle = new ArrayList<>();
        listByIDVehicle.add(listMetrobusInfo.get(2));

        doReturn(listByIDVehicle).when(metrobusService).findByIdVehicle(anyString());

        mockMvc.perform(
                get("/api/metrobusInfo/findByIdVehicle/ryr66t").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idRecord", is("ryr66t")))
                .andExpect(jsonPath("$[0].longitude",is(-92.19495)))
                .andExpect(jsonPath("$[0].latitude",is(-20.603)));
    }

    @Test
    public void testFindAllCounty() throws Exception {
        List<String> listAllCounty = Arrays.asList("Iztapalapa", "Miguel Hidalgo", "Venustiano Carranza", "Gustavo A. Madero", "Cuauhtémoc");

        doReturn(listAllCounty).when(metrobusService).findAllCounty();

        mockMvc.perform(
                get("/api/metrobusInfo/findAllCounty").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0]", is("Iztapalapa")))
                .andExpect(jsonPath("$[1]",is("Miguel Hidalgo")))
                .andExpect(jsonPath("$[4]",is("Cuauhtémoc")));

    }
}
