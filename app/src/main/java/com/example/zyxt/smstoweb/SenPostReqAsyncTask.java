package com.example.zyxt.smstoweb;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by zyxt on 30/9/2016.
 */

public class SenPostReqAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String number = params[0];
        String orderType = params[1];
        String quantity = params[2];
        Log.d("DEBUG", "BG Number: " + number);
        Log.d("DEBUG", "BG OrderType: " + orderType);
        Log.d("DEBUG", "BG Quantity: " + quantity);
        try {
            Log.d("DEBUG", "Inside backgroundWorker...");
            URL url = new URL("http://services1.galaelass.gr/ordering/ordering.php");
            Log.d("DEBUG", url.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.connect();

            Map<String, String> values = new HashMap<String, String>();
            values.put("number", number);
            values.put("orderType", orderType);
            values.put("quantity", quantity);
            values.put("source", "0");

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(getQuery(values));
            writer.flush();
            writer.close();
            Log.d("DEBUG", "We send the data.");
            readInputStreamToString(conn);
            conn.disconnect();
        } catch(IOException e) {
            Log.d("DEBUG", e.toString());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    private String getQuery(Map<String, String> values) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(String myKey : values.keySet()) {
            String myVal = values.get(myKey);

            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(myKey, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(myVal, "UTF-8"));
        }
        Log.d("DEBUG", "queryString: " + result.toString());
        return result.toString();
    }

    private void readInputStreamToString(HttpURLConnection connection) {
        String result = null;
        StringBuilder sb = new StringBuilder();
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        } catch (IOException e) {
            Log.d("DEBUG", e.toString());
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.d("DEBUG", e.toString());
                    e.printStackTrace();
                }
            }
        }

        Log.d("DEBUG", "Response: " + result);
    }
}

