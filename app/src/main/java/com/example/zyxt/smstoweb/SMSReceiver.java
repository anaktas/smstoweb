package com.example.zyxt.smstoweb;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

@TargetApi(25)
public class SMSReceiver extends BroadcastReceiver {

    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages;
        String strNumber = "";
        String strMessage;
        String[] messageArray;
        String orderType = "";
        String quantity = "";
        String identifier = "";

        try{
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");

                messages = new SmsMessage[pdusObj.length];

                for (int i = 0; i < messages.length; i++) {
                    String format = bundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                    strNumber = messages[i].getOriginatingAddress();
                    strMessage = messages[i].getMessageBody();
                    messageArray = strMessage.split(" ");
                    identifier = messageArray[0];
                    orderType = messageArray[1];
                    quantity = messageArray[2];
                }

                if (identifier.equals("AB")) {
                    Intent intentToResponse = new Intent(context, PostDataService.class);
                    intentToResponse.putExtra(PostDataService.INCOMING_NUMBER, strNumber);
                    intentToResponse.putExtra(PostDataService.ORDER_TYPE, orderType);
                    intentToResponse.putExtra(PostDataService.QUANTITY, quantity);
                    context.startService(intentToResponse);
                    Log.d("DEBUG", "The service was triggered");
                }
            }
        } catch (Exception e) {
            Log.d("DEBUG", e.toString());
            Toast.makeText(context, "Κάτι πήγε στραβά! " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
