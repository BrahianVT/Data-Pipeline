package consume.api;

import consume.api.entity.Metrobus;
import consume.api.sqlConn.SqlConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que obtiene los datos relevantes del servicio consumido
 * de pa pagina de datos abiertos de la CDMX
 * @author Brahian VT
 * */
public class ParseApiInformation {

    // Creamos expresiones regulares para buscar estos datos especificos
    Pattern patternRecordId = Pattern.compile("(?<=recordid\": \").*?(?=\")");
    Pattern patternVehicleId = Pattern.compile("(?<=vehicle_id\": \").*?(?=\")");
    Pattern patternDateUpdated = Pattern.compile("(?<=date_updated\": \").*?(?=\")");
    Pattern patternPositionLongitude = Pattern.compile("(?<=position_longitude\": ).*?(?=,)");
    Pattern patternPositionLatitude = Pattern.compile("(?<=position_latitude\": ).*?(?=,)");
    Pattern patternCounty = Pattern.compile("(?<=county\":\").*?(?=\")");



    Matcher matcherRecordId, matcherVehicleId , matcherCounty;
    Matcher matcherDateUpdated, matcherPositionLongitude, matcherPositionLatitude;

    ConnectConsumeApi consumeApiCounty = new ConnectConsumeApi();
    Metrobus metrobus;
    SqlConnection conn = new SqlConnection();

    // obtiene el result y el startIndex para obtener cada registro individual
    // aplicando las expresiones regulares
    public boolean  parseDataApi(String result , int startIndex) throws IOException{

        // con el startIndex comprobamos si el primer registro ya esta en la base de datos
        // si es asi nos salimos de la funcion
        String auxStartIndex = result.substring(110, 111);
        conn.openConnection();
        if(startIndex == 0 && auxStartIndex.equals(""+startIndex))
            if(validateDataSet(result.substring(200, 240))) {  conn.disconnect(); return false; }

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
            metrobus = new Metrobus(recordId, vehicleId, dateUpdated, longitude, latitude, alcaldia);
            conn.insertBusInfo(metrobus);

        }

        conn.disconnect();
        return true;
    }

    // Buscamos la alcaldia en el segundo servicio buscando el campo county
    public String parseCounty(String county){
        matcherCounty = patternCounty.matcher(county);
        if(matcherCounty.find()){
            return matcherCounty.group(0);
        }
        else{
            // si no contiene el campo county. buscamos si contienen alguna delegacion si es asi la retornamos
            String[] alcaldias = { "Gustavo A. Madero","Álvaro Obregón","Tláhuac","Xochimilco","Tlalpan",
                    "La Magdalena Contreras","Venustiano Carranza","Cuauhtémoc","Benito Juárez",
                    "Iztapalapa","Coyoacán","Miguel Hidalgo","Azcapotzalco","Milpa Alta","Iztacalco" };
            for (String alcaldia: alcaldias){
                if(county.contains(alcaldia)) return alcaldia;
            }
        }
            return null;
    }

    // Consume el segundo servicio para encontrar la delegación
    public String consumeAPIGetCounty(BigDecimal longitude, BigDecimal latitude) throws IOException {
        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lon="+longitude+"&lat="+ latitude;
        System.out.println("Consulting Addres with this:" + url);

        BufferedReader br = consumeApiCounty.getBufferedReader(url);
        String result = br.readLine();

        // Esperar un segundo obligatorio para usar el api
    //    try{ Thread.sleep(1000); }  catch (InterruptedException ex){ ex.printStackTrace(); }

        return parseCounty(result);
    }

    // busca si el primer registro ya esta en la base de datos
    public boolean validateDataSet(String record){
        return conn.checkRecordId(record);
    }

}
