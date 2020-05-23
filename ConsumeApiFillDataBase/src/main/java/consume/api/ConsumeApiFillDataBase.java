package consume.api;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConsumeApiFillDataBase {
    private final static String URL_BASE_API  = "https://datos.cdmx.gob.mx/api/records/1.0/search/?dataset=prueba_fetchdata_metrobus&q=";
    private static Integer ROWS = 50;
    private  Integer startIndex = 0;
    private URL url = null;
    private HttpURLConnection conn = null;

    ParseApiInformation parse = new ParseApiInformation();
    public void setupConnection (String urlApi) throws IOException {
        System.out.println(":::::::: Fetching data from : " + urlApi);

        url = new URL(urlApi);
        conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if(conn.getResponseCode() != 200)
            throw new RuntimeException("::::::::Failed: HTTP Error code :" + conn.getResponseCode());

    }

    public void consumeApi() throws IOException {
            while(true) {
                String url = URL_BASE_API +"&rows="+ROWS+"&start="+startIndex;
                InputStreamReader input = null;
                setupConnection(url);
                if (conn != null) input = new InputStreamReader(conn.getInputStream());

                BufferedReader br = new BufferedReader(input);
                String outputApi = br.readLine();

                startIndex += ROWS;
                if(!outputApi.contains("datasetid")) break;
                parse.parseDataApi(outputApi);
            }

    }

    public static void main(String[] args) throws IOException {
        ConsumeApiFillDataBase consumeApiFillDataBase = new ConsumeApiFillDataBase();
        consumeApiFillDataBase.consumeApi();

    }

}
