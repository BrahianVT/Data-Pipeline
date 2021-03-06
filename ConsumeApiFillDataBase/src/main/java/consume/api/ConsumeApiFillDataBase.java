package consume.api;


import consume.api.entity.Metrobus;
import consume.api.sqlConn.SqlConnection;
import consume.api.util.ReadPropertyFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase de entrada desde aqui se comsume el servicio de la pagina https://datos.cdmx.gob.mx
 * y se configura para cada x tiempo consultar de nuevo el servicio
 * @author Brahian VT
 * */

public class ConsumeApiFillDataBase {
    private final static String URL_BASE_API  = "https://datos.cdmx.gob.mx/api/records/1.0/search/?dataset=prueba_fetchdata_metrobus&q=";
    private static Integer ROWS = 50;
    private  Integer startIndex = 0;
    ParseApiInformation parse = new ParseApiInformation();
    ConnectConsumeApi consumeApi = new ConnectConsumeApi();

    public void consumeApi() throws IOException {
            while(true) {
                String url = URL_BASE_API +"&rows="+ROWS+"&start="+startIndex;
                BufferedReader br = consumeApi.getBufferedReader(url);
                String outputApi = br.readLine();
                if(!outputApi.contains("datasetid") || !parse.parseDataApi(outputApi, startIndex)) break;
                startIndex += ROWS;

            }
    }

    // por default la applicacion consultara el servicio cada 3600s osea una hora
    // se puede indicar otro intervalo que puede ser definido como variable de entrada
    public static void main(String[] args) {
        int minuteAtSeconds = 3600;
        if(args.length == 1) minuteAtSeconds = Integer.parseInt(args[0]);

        System.out.println("Every " + minuteAtSeconds + " seconds");
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                ConsumeApiFillDataBase consumeApiFillDataBase = new ConsumeApiFillDataBase();
                try {
                    consumeApiFillDataBase.consumeApi();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1 * minuteAtSeconds * 1000);


    }

}
