package com.example.jgarcia.callapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;


public class CallActivity extends Activity {

    static CallActivity callActivity;
    private TextView txtTime;
    private int iClicks;

    private Button btnEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity);
        callActivity= this;

        bindUI();

        btnEnd = findViewById(R.id.btnEnd);

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cerrar", Toast.LENGTH_SHORT).show();
                disconnectCall();
                //android.os.Process.killProcess(android.os.Process.myPid());
            }
        });


        try {
            Log.d("Call: onCreate: ", "flag2");


            // TODO Auto-generated method stub
            //super.onCreate(savedInstanceState);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

            Log.d("Call: onCreate: ", "flagy");

            //setContentView(R.layout.call_activity);


            TextView text = findViewById(R.id.txtNum);
            Log.d("Call: onCreate: ", "flagz");
            String state = getIntent().getStringExtra("Estado");
            //int num = getIntent().getIntExtra("Num", 0);
            Log.d("Call: onReceive: ", "1");
            if(state != null){
                if (state.equals("1")) {
                    String number = getIntent().getStringExtra(
                            TelephonyManager.EXTRA_INCOMING_NUMBER);
                    text.setText("Número entrante:  " + number);
                }
            }
            else{
                String num = getIntent().getStringExtra("Telf");
                text.setText("Número saliente: " + num);
            }

        }
        catch (Exception e) {
            Log.d("Exception", e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            //TextView txtTime = (TextView) findViewById(R.id.txtTime);
                            // task to be done every 1000 milliseconds
                            iClicks = iClicks + 1;
                            TextView txtTime = findViewById(R.id.txtTime);
                            txtTime.setText(String.valueOf(iClicks));
                        }
                    });

                }
            }, 0, 1000);



/*
            String number = getIntent().getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER);
            text.setText("Número entrante" + number);

            */
/*
            String num = getIntent().getStringExtra("tel:");
            text.setText("Número saliente" + num);
*/

    }

    public void bindUI(){
        //TextView text = findViewById(R.id.txtNum);
        //txtTime = findViewById(R.id.txtTime);
        //btnEnd = findViewById(R.id.btnEnd);
    }

    public static CallActivity getInstance(){
        return   callActivity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Acepta la ejecución de este programa en modo prueba ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                disconnectCall();
                android.os.Process.killProcess(android.os.Process.myPid());
                //aceptar();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();
    }

    public void cancelar() {
        finish();
    }

    public void disconnectCall(){
        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Call",
                    "FATAL ERROR: could not connect to telephony subsystem");
            Log.e("Call","Exception object: " + e);
        }
    }
}
