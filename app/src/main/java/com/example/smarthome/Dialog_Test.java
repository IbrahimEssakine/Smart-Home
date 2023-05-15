package com.example.smarthome;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smarthome.data.Devices_Data;
import com.example.smarthome.data.Smart_Devices;

import java.util.List;

public class Dialog_Test extends AppCompatDialogFragment {

    String[] items= {};
    Devices_Data datas;
    AutoCompleteTextView autoCompleteTextView ;
    ImageView icon;
    ArrayAdapter<String> adapterItems;
    int i=0;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<Smart_Devices> devices = datas.getDevicesList();
        /*for (Smart_Devices device:devices) {
            items[i]=device.getName();
            i++;
        }*/
        Toast.makeText(getActivity().getApplicationContext(), datas.getDevicesList().size()+" azeaze : "+items.length, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View  view = inflater.inflate(R.layout.activity_dialog_test,null);
        autoCompleteTextView = view.findViewById(R.id.auto_complet);
        icon = view.findViewById(R.id.ima);

        adapterItems = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.list_items,items);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity().getApplicationContext(), "your Item"+item, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity().getApplicationContext(), "drawable"+R.drawable.baseline_smartphone_24, Toast.LENGTH_SHORT).show();
                String image_id = "device_1"+parent.getItemIdAtPosition(position);
                icon.setImageResource(R.drawable.baseline_smartphone_24);

            }
        });
        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();


    }
}