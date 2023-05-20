package com.example.smarthome;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smarthome.data.Devices_Data;
import com.example.smarthome.data.Smart_Devices;

import java.util.ArrayList;
import java.util.List;

public class Dialog_Test extends AppCompatDialogFragment {

    String[] items = new    String[getDevicesList().size()];
    Devices_Data datas;
    AutoCompleteTextView autoCompleteTextView ;
    ImageView icon;
    ArrayAdapter<String> adapterItems;
    int i=0;
    RadioButton radiobutton1;
    RadioButton radiobutton2;

    RadioGroup radioGroup ;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity() , R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View  view = inflater.inflate(R.layout.activity_dialog_test,null);


        autoCompleteTextView = view.findViewById(R.id.auto_complet);
        icon = view.findViewById(R.id.ima);


        autoCompleteTextView.setDropDownBackgroundDrawable( new ColorDrawable(Color.parseColor("#d8d8d8")));


        // mouad 's part
        while (i<getDevicesList().size())
        {
            items[i] = getDevicesList().get(i).getName();
            Log.i("mouad" ,""+ items[i] );
            i++;
        }
        i=0;

        adapterItems = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.list_items,items);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();


                icon.setImageResource(R.drawable.baseline_smartphone_24);
                while ((i<getDevicesList().size()) && (getDevicesList().get(i).getID() != id+1))
                {
                    i++;
                }
                Log.i("mouad" ,""+ getDevicesList().get(i).getID() );
//
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View  viewroot = inflater.inflate(R.layout.activity_dialog_test,null);
                radiobutton1 = viewroot.findViewById(R.id.radio_button_1);
                radiobutton2 = viewroot.findViewById(R.id.radio_button_2);
                radioGroup = viewroot.findViewById(R.id.radioGroup);

//                radioGroup.check(R.id.radio_button_2);
                Log.i("mouad","checkedbutton :" + radioGroup.getCheckedRadioButtonId());
                radioGroup.check((getDevicesList().get(i).getType() == 1) ? R.id.radio_button_1 : R.id.radio_button_2);
//                Log.i("mouad","beforradio 1 :" + radiobutton1.isChecked());
//                radiobutton1.setChecked((getDevicesList().get(i).getType() == 1) ? true : false);
//                Log.i("mouad","afterradio 1 :" + radiobutton1.isChecked());
//                radiobutton2.setChecked((getDevicesList().get(i).getType()==1)? false : true);
                i=0;

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

    public static List<Smart_Devices> getDevicesList() {
        List<Smart_Devices> devicesList = new ArrayList<>();

        Smart_Devices Led = new Smart_Devices();
        Led.setID(1);
        Led.setName("Led");
        Led.setType(1);
        devicesList.add(Led);

        Smart_Devices GazSensor = new Smart_Devices();
        GazSensor.setID(2);
        GazSensor.setName("GazSensor");
        GazSensor.setType(0);
        devicesList.add(GazSensor);

        Smart_Devices SmokeSensor = new Smart_Devices();
        SmokeSensor.setID(3);
        SmokeSensor.setName("SmokeSensor");
        SmokeSensor.setType(0);
        devicesList.add(SmokeSensor);

        Smart_Devices PresenceSensor = new Smart_Devices();
        PresenceSensor.setName("PresenceSensor");
        PresenceSensor.setID(4);
        PresenceSensor.setType(0);
        devicesList.add(PresenceSensor);

        Smart_Devices ServoMotor = new Smart_Devices();
        ServoMotor.setID(5);
        ServoMotor.setName("ServoMotor");
        ServoMotor.setType(1);
        devicesList.add(ServoMotor);

        return devicesList;
    }



}