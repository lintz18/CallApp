package com.example.jgarcia.callapp;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.jgarcia.callapp.interfaces.callMethods;

public abstract class PhonecallReceiver extends BroadcastReceiver implements callMethods{

    //Se ejecutará cuando sea necesario

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;  //because the passed incoming is only valid in ringing

    /*
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Call: onReceive: ", "flag1");

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d("Call: onReceive: ", state);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)
                || state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)
                || state.equals(TelephonyManager.EXTRA_STATE_IDLE)
                || state.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            ) {

            Log.d("Ringing", "Phone is ringing");

            Intent i = new Intent(context, CallActivity.class);
            i.putExtras(intent);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        }
    }
*/


    @Override
    public void onReceive(Context context, Intent intent) {

        try{
            //Recogemos el numero de telf de dos intents
            if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
                savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
            }
            else{
                String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
                String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                int state = 0;
                if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                    state = TelephonyManager.CALL_STATE_IDLE;
                }
                else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                    state = TelephonyManager.CALL_STATE_OFFHOOK;
                }
                else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                    state = TelephonyManager.CALL_STATE_RINGING;
                }

                onCallStateChanged(context, state, number, intent);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    //Metodos de respuesta a la llamada


    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up

    public void onCallStateChanged(Context context, int state, String number, Intent intent) {
        if(lastState == state){
            //Si no hay cambios vuelve
            CallActivity.getInstance().finish();
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallStarted(context, number, callStartTime);
                Intent i = new Intent(context, CallActivity.class);
                i.putExtras(intent);
                i.putExtra("Estado", "1");
                i.putExtra("Num", number);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(i);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transición desde la llamada hasta que se descuelga el telf
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date();
                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                    Intent i2 = new Intent(context, CallActivity.class);
                    i2.putExtras(intent);
                    i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(i2);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Cuando el estado de la llamada es inactiva
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                    //Suena pero no se coge la llamada
                    onMissedCall(context, savedNumber, callStartTime);
                    Intent i3 = new Intent(context, CallActivity.class);
                    i3.putExtras(intent);
                    i3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i3.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(i3);
                }
                else if(isIncoming){
                    onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
                    Intent i4 = new Intent(context, CallActivity.class);
                    i4.putExtras(intent);
                    i4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i4.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(i4);
                }
                else{
                    onOutgoingCallEnded(context, savedNumber, callStartTime, new Date());
                    Intent i5 = new Intent(context, CallActivity.class);
                    i5.putExtras(intent);
                    i5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i5.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(i5);
                }
                break;
        }

        lastState = state;
        CallActivity.getInstance().finish();
    }
}