package com.example.jgarcia.callapp.adapters;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jgarcia.callapp.R;
import com.example.jgarcia.callapp.model.CallObject;

import java.util.List;

public class LogAdapter extends ArrayAdapter<CallObject> {

    private List<CallObject> logList;
    private Context mContext;

    public LogAdapter(Context context, List<CallObject> log){
        super(context,0, log);
        this.mContext = context;
        this.logList = log;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.call_log_adapter,parent,false);

        CallObject logO = logList.get(position);

        TextView txtId = listItem.findViewById(R.id.txtId);
        String txt = Integer.toString(position);
        txtId.setText(txt);

        TextView txtNumero = listItem.findViewById(R.id.txtNumero);
        txtNumero.setText(logO.getNumero());

        TextView txtNombre = listItem.findViewById(R.id.txtNombre);
        txtNombre.setText(logO.getNombre());


        return listItem;
    }
}
