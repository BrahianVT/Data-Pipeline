package consume.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Brahian VT
 * */
public class ConnectConsumeApi {
     URL url = null;
     HttpURLConnection conn = null;

    public HttpURLConnection setupConnection (String urlApi) throws IOException {
        System.out.println(":::::::: Fetching data from : " + urlApi);
        url = new URL(urlApi);
        conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if(conn.getResponseCode() != 200)
            throw new RuntimeException("::::::::Failed: HTTP Error code :" + conn.getResponseCode());

        return conn;
    }

    public BufferedReader getBufferedReader(String urlApi) throws IOException {
        InputStreamReader input = null;
        setupConnection(urlApi);
        if (conn != null) input = new InputStreamReader(conn.getInputStream(), "UTF8");
        BufferedReader br = new BufferedReader(input);
        return br;
    }
}
