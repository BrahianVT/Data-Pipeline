package consume.api;


import consume.api.entity.Metrobus;
import consume.api.sqlConn.SqlConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author Brahian VT
 * */

public class ConsumeApiFillDataBase {
    private final static String URL_BASE_API  = "https://datos.cdmx.gob.mx/api/records/1.0/search/?dataset=prueba_fetchdata_metrobus&q=";
    private static Integer ROWS = 50;
    private  Integer startIndex = 0;

    ParseApiInformation parse = new ParseApiInformation();
    ConnectConsumeApi consumeApi = new ConnectConsumeApi();
    SqlConnection sqlConn = new SqlConnection();
    public void consumeApi() throws IOException {
            while(true) {
                String url = URL_BASE_API +"&rows="+ROWS+"&start="+startIndex;
                BufferedReader br = consumeApi.getBufferedReader(url);
                String outputApi = br.readLine();
                if(!outputApi.contains("datasetid") || !parse.parseDataApi(outputApi, startIndex)) break;
                startIndex += ROWS;

            }
    }


    public static void main(String[] args) throws IOException {
        ConsumeApiFillDataBase consumeApiFillDataBase = new ConsumeApiFillDataBase();
        consumeApiFillDataBase.consumeApi();

    }

}
