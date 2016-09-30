package com.example.zyxt.smstoweb;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyxt on 30/9/2016.
 */

public class SenPostReqAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        final String number = params[0];
        final String orderType = params[1];
        final String quantity = params[2];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("DEBUG", "Inside backgroundWorker...");
                    URL url = new URL("http://services1.galaelass.gr/ordering/ordering.php");

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
                    conn.disconnect();
                } catch(IOException e) {
                    Log.d("DEBUG", e.toString());
                    e.printStackTrace();
                }
            }
        }).start();
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

        return result.toString();
    }
}

