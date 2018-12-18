package com.example.jgarcia.callapp.interfaces;

import android.content.Context;

import java.util.Date;

public interface callMethods {

    void onIncomingCallStarted(Context ctx, String number, Date start);
    void onOutgoingCallStarted(Context ctx, String number, Date start);
    void onIncomingCallEnded(Context ctx, String number, Date start, Date end);
    void onOutgoingCallEnded(Context ctx, String number, Date start, Date end);
    void onMissedCall(Context ctx, String number, Date start);

}
