package com.example.jgarcia.callapp;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

public class CallReceiver extends PhonecallReceiver {

    //Llamada entrante
    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        Toast.makeText(ctx,"Phone Is Ringing" + ":  " + number, Toast.LENGTH_LONG).show();
    }

    //Llamada saliente
    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Toast.makeText(ctx,"Started", Toast.LENGTH_LONG).show();
    }

    //Llamada cogida finalizada
    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx,"Call is finished", Toast.LENGTH_LONG).show();
    }

    //Llamada saliente colgada
    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx,"Outgoing", Toast.LENGTH_LONG).show();
    }

    //Llamada perdida*
    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        //ADD TO GUIDE, MISSED CALL
        Toast.makeText(ctx,"MISSED CALL", Toast.LENGTH_SHORT).show();
    }

}