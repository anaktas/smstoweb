package com.example.zyxt.smstoweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
            //enableIncomingCallReceiver();
        } else {
            //disableIncomingCallReceiver();
        }
    }
}
