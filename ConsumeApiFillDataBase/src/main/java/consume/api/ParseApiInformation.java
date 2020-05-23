package consume.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseApiInformation {
    Pattern patternRecordId = Pattern.compile("(?<=recordid\": \").*?(?=\")");
    Pattern patternVehicleId = Pattern.compile("(?<=vehicle_id\": \").*?(?=\")");
    Pattern patternDateUpdated = Pattern.compile("(?<=date_updated\": \").*?(?=\")");
    Pattern patternPositionLongitude = Pattern.compile("(?<=position_longitude\": ).*?(?=,)");
    Pattern patternPositionLatitude = Pattern.compile("(?<=position_latitude\": ).*?(?=,)");

    Matcher matcherRecordId, matcherVehicleId;
    Matcher matcherDateUpdated, matcherPositionLongitude, matcherPositionLatitude;

    public void parseDataApi(String result){
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
            System.out.println("recordId: "+ recordId+" vehicleId: "+vehicleId
                    +"dateUpdate: "+ dateUpdated+" positionLatitude: "+ positionLatitude+ "positionLongitud: "+ positionLongitude);

        }

    }
}
