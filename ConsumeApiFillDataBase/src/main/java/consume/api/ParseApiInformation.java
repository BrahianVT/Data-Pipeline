package consume.api;

import consume.api.entity.Metrobus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brahian VT
 * */
public class ParseApiInformation {
    Pattern patternRecordId = Pattern.compile("(?<=recordid\": \").*?(?=\")");
    Pattern patternVehicleId = Pattern.compile("(?<=vehicle_id\": \").*?(?=\")");
    Pattern patternDateUpdated = Pattern.compile("(?<=date_updated\": \").*?(?=\")");
    Pattern patternPositionLongitude = Pattern.compile("(?<=position_longitude\": ).*?(?=,)");
    Pattern patternPositionLatitude = Pattern.compile("(?<=position_latitude\": ).*?(?=,)");
    Pattern patternCounty = Pattern.compile("(?<=county\":\").*?(?=\")");


    Matcher matcherRecordId, matcherVehicleId , matcherCounty;
    Matcher matcherDateUpdated, matcherPositionLongitude, matcherPositionLatitude;

    ConnectConsumeApi consumeApiCounty = new ConnectConsumeApi();

    public Metrobus parseDataApi(String result) throws IOException{
        matcherRecordId = patternRecordId.matcher(result);
        matcherVehicleId = patternVehicleId.matcher(result);
        matcherDateUpdated = patternDateUpdated.matcher(result);
        matcherPositionLatitude = patternPositionLatitude.matcher(result);
        matcherPositionLongitude = patternPositionLongitude.matcher(result);
        String recordId ="", vehicleId ="", dateUpdated ="", positionLatitude ="", positionLongitude = "";

        while(matcherRecordId.find() ){
            matcherPositionLongitude.find(); matcherVehicleId.find();
            matcherPositionLatitude.find(); matcherDateUpdated.find();
            recordId = matcherRecordId.group(0); vehicleId = matcherVehicleId.group(0);
            dateUpdated = matcherDateUpdated.group(0); positionLatitude = matcherPositionLatitude.group(0);
            positionLongitude = matcherPositionLongitude.group(0);
            System.out.println("recordId: "+ recordId+" vehicleId: "+vehicleId+" dateUpdate: "
                    + dateUpdated+" positionLatitude: "+ positionLatitude+ " positionLongitud: "+ positionLongitude);

            BigDecimal longitude = new BigDecimal(positionLongitude);
            BigDecimal latitude = new BigDecimal(positionLatitude);
            String alcaldia = consumeAPIGetCounty(longitude, latitude);
            Metrobus metrobus = new Metrobus(recordId, vehicleId, dateUpdated, longitude, latitude, alcaldia);
            return metrobus;

        }
        return null;
    }

    public String parseCounty(String county){
        matcherCounty = patternCounty.matcher(county);
        if(matcherCounty.find())
            return matcherCounty.group(0);
        else
            return null;
    }

    public String consumeAPIGetCounty(BigDecimal longitude, BigDecimal latitude) throws IOException {
        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lon="+longitude+"&lat="+ latitude;
        System.out.println("Consulting Addres with this:" + url);

        BufferedReader br = consumeApiCounty.getBufferedReader(url);
        String result = br.readLine();

        // Esperar un segundo obligatorio para usar el api
        try{ Thread.sleep(1000); }  catch (InterruptedException ex){ ex.printStackTrace(); }

        return parseCounty(result);
    }

}
