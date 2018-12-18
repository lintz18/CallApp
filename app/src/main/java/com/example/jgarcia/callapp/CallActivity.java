package com.example.jgarcia.callapp;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

public class CallActivity extends Activity {
    static CallActivity callActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        callActivity= this;

        try {
            Log.d("Call: onCreate: ", "flag2");


            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

            Log.d("Call: onCreate: ", "flagy");

            setContentView(R.layout.call_activity);

            Log.d("Call: onCreate: ", "flagz");

            String number = getIntent().getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER);
            TextView text = (TextView) findViewById(R.id.txtNum);
            text.setText("Incoming call from " + number);
        }
        catch (Exception e) {
            Log.d("Exception", e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static CallActivity getInstance(){
        return   callActivity;
    }
}
