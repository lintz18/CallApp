package com.example.jgarcia.callapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jgarcia.callapp.adapters.LogAdapter;
import com.example.jgarcia.callapp.model.CallObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CallReceiver extends PhonecallReceiver {


    public static String numero = "";
    public static String fecha = "";

    //private LogAdapter mLogAdapter;

    ArrayList<CallObject> logObjects = new ArrayList<>();


    //Llamada entrante
    @Override
    public void onIncomingCallStarted(Context ctx, String number, Date start) {
        Toast.makeText(ctx,"Phone Is Ringing" + ":  " + number, Toast.LENGTH_LONG).show();
    }

    //Llamada saliente
    @Override
    public void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Toast.makeText(ctx,"Started", Toast.LENGTH_LONG).show();
    }

    //Llamada cogida finalizada
    @Override
    public void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx,"Call is finished", Toast.LENGTH_LONG).show();
    }

    //Llamada saliente colgada
    @Override
    public void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx,"Outgoing", Toast.LENGTH_LONG).show();
    }

    //Llamada perdida*
    @Override
    public void onMissedCall(Context ctx, String number, Date start) {
        //ADD TO GUIDE, MISSED CALL
        Toast.makeText(ctx,"MISSED CALL:  " + number, Toast.LENGTH_SHORT).show();

    }

}