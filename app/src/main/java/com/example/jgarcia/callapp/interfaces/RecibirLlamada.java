package com.example.jgarcia.callapp.interfaces;

import android.content.Context;

import com.example.jgarcia.callapp.model.CallObject;

import java.util.ArrayList;
import java.util.Date;

public interface RecibirLlamada {

    void recibirDatos(Context ctx, String number, Date start);

}
