package com.example.jgarcia.callapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jgarcia.callapp.interfaces.callMethods;

import java.time.Instant;
import java.util.Date;

public abstract class MainActivity extends AppCompatActivity implements callMethods {

    private ImageView imgPersona;
    private ListView lstRegistro;
    private FloatingActionButton fabLlamada;
    private EditText edtNumero;
    private CallReceiver call = new CallReceiver();
    private int number;

    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    public static final int MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS = 2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindUI();

        if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permiso no garantizado, aún así miramos los permisos del usuario
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        } else {
            //No hacer nada
        }


        //Listener del fab que nos lleva a la otra activity donde ya estamos realizando la llamada
        fabLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Se presionó el FAB", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //performDial(edtNumero.getText().toString());
                initiateCall();
            }
        });


/*
        if (getApplicationContext().checkSelfPermission(Manifest.permission.PROCESS_OUTGOING_CALLS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS},
                    MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS);
        }*/
    }

    public void bindUI(){
        fabLlamada = findViewById(R.id.fabLlamada);
        imgPersona = findViewById(R.id.imgPersona);
        edtNumero = findViewById(R.id.edtNumero);
        lstRegistro = findViewById(R.id.lstRegistro);

        fabLlamada.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFabBackground)));
        edtNumero.setHintTextColor(getResources().getColor(R.color.colorFabBackground));
    }

    @Override
    public void initiateCall() {
        if(checkIfAlreadyHaveCallPermission()){
            makeTheCall(edtNumero.getText().toString());
        }
        else{
            requestCallPermission();
        }
    }

    @Override
    public boolean checkIfAlreadyHaveCallPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void requestCallPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            //Habilitamos permisos
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission garanted!", Toast.LENGTH_LONG).show();
                    }
                    makeTheCall(edtNumero.getText().toString());
                } else {
                    Toast.makeText(this, "No permission garanted!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }



    @Override
    public void makeTheCall(String numberString) {
        if (!numberString.equals("")) {
            Uri number = Uri.parse("tel:" + numberString);
            Intent callIntent = new Intent(Intent.ACTION_CALL, number);
            //callIntent.setData(Uri.parse("tel:" + numberString));
            try{
                //AlertDialog

            }catch(android.content.ActivityNotFoundException ex){
                Toast.makeText(getBaseContext(),"Your activity not found", Toast.LENGTH_SHORT).show();

            }
            /*
            Uri number = Uri.parse("tel:" + numberString);
            Intent dial = new Intent(Intent.ACTION_CALL, number);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(dial);
        */
        }
    }
}
