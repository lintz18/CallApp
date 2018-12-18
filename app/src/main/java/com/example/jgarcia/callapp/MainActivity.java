package com.example.jgarcia.callapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jgarcia.callapp.adapters.LogAdapter;
import com.example.jgarcia.callapp.model.CallObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    private ImageView imgPersona;
    private ListView lstRegistro;
    private FloatingActionButton fabLlamada;
    private EditText edtNumero;
    private CallReceiver call = new CallReceiver();
    private int number;
    private ArrayList<CallObject> logObjects = new ArrayList<>();

    private LogAdapter mLogAdapter;

    public static Intent callIntent;

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

        //recibirDatos(getBaseContext(),null, null);

        ArrayList<CallObject> logObjects = new ArrayList<>();

        logObjects.add(new CallObject(null, "650649878", "Jose"));
        logObjects.add(new CallObject(null, "635678878", "Anto"));


        mLogAdapter = new LogAdapter(getBaseContext(), logObjects);
        lstRegistro.setAdapter(mLogAdapter);


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

    }

    public void bindUI() {
        fabLlamada = findViewById(R.id.fabLlamada);
        imgPersona = findViewById(R.id.imgPersona);
        edtNumero = findViewById(R.id.edtNumero);
        lstRegistro = findViewById(R.id.lstRegistro);

        fabLlamada.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFabBackground)));
        edtNumero.setHintTextColor(getResources().getColor(R.color.colorFabBackground));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }


    public void initiateCall() {
        if(checkIfAlreadyHaveCallPermission()){
            makeTheCall(edtNumero.getText().toString());
        }
        else{
            requestCallPermission();
        }
    }

    public boolean checkIfAlreadyHaveCallPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

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
                    initiateCall();
                    //makeTheCall(edtNumero.getText().toString());
                } else {
                    Toast.makeText(this, "No permission garanted!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    public void makeTheCall(String numberString) {
        if (!numberString.equals("")) {
            callIntent = new Intent(this, CallActivity.class);
            callIntent.putExtra("Telf", edtNumero.getText().toString());
            Log.i("NUMERO: ", edtNumero.getText().toString());
            callIntent.setData(Uri.parse("tel:" + numberString));
            /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            else{
                startActivity(callIntent);
            }*/

            try{
                AlertDialog.Builder builderCall = new AlertDialog.Builder(this);
                builderCall.setMessage("Quieres realizar la llamada?");
                builderCall.setCancelable(true);

                builderCall.setPositiveButton("No", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });

                builderCall.setNegativeButton("Si", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        startActivity(callIntent);
                    }
                });

                AlertDialog alertCall = builderCall.create();
                alertCall.show();


            }catch(android.content.ActivityNotFoundException ex){
                Toast.makeText(getBaseContext(),"Your activity not found", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
