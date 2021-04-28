/*
Project work
SleepTracker
Joona Saloniemi
*/


package com.example.harkka_ver_1;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Sunrise {

    /*
    This method gets coordinates string from MainActivity, startButton and send it to getJSON.
    getJSON returns JSON object and readJSON pick sunrise time from object string and return it to MainActivity.
    */
    public String readJSON(String coordinates) {
        String json = getJSON(coordinates);

        String sunrise = null;
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject resultsObject = jsonObject.getJSONObject("results");
                sunrise = resultsObject.getString("sunrise");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sunrise;
    }

    // This method gets coordinates string from readJSON and use it in URL. Use GET method to get object string and return it to readJSON.
    public String getJSON(String coordinates) {
        String response = null;
        try {
            URL url = new URL("https://api.sunrise-sunset.org/json?"+ coordinates +"&&date=today");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("/n");
            }
            // List method to String:
            response = sb.toString();
            in.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
