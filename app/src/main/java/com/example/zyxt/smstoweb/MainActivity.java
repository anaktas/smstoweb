package com.example.zyxt.smstoweb;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toggleChecked(View view) {
        //final ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        boolean isOn = ((ToggleButton) view).isChecked();

        if(isOn) {
            enableIncomingSMSReceiver();
        } else {
            disableIncomingSMSReceiver();
        }
    }

    public void enableIncomingSMSReceiver() {
        ComponentName receiver = new ComponentName(this, SMSReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Η εφαρμογή ενεργοποιήθηκε.", Toast.LENGTH_LONG).show();
    }

    public void disableIncomingSMSReceiver() {
        ComponentName receiver = new ComponentName(this, SMSReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Η εφαρμογή απενεργοποιήθηκε.", Toast.LENGTH_LONG).show();
    }
}
