package com.example.zyxt.smstoweb;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PostDataService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.zyxt.smstoweb.action.FOO";
    private static final String ACTION_BAZ = "com.example.zyxt.smstoweb.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.zyxt.smstoweb.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.zyxt.smstoweb.extra.PARAM2";

    public static final String INCOMING_NUMBER = "";
    public static final String ORDER_TYPE = "";
    public static final String QUANTITY = "";

    public PostDataService() {
        super("PostDataService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PostDataService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PostDataService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }

            final String number = intent.getStringExtra(INCOMING_NUMBER);
            final String orderType = intent.getStringExtra(ORDER_TYPE);
            final String quantity = intent.getStringExtra(QUANTITY);

            Log.d("DEBUG", "Handling the intent from the service");

            postData(number, orderType, quantity);
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void postData(final String number, final String orderType, final String quantity) {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://galaelass.gr/ordering/ordering.php");

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

                    conn.disconnect();
                } catch(IOException e) {
                    Log.d("DEBUG", e.toString());
                    e.printStackTrace();
                }
            }
        }).start();*/

        SenPostReqAsyncTask senPostReqAsyncTask = new SenPostReqAsyncTask();
        senPostReqAsyncTask.execute(number, orderType, quantity);
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
