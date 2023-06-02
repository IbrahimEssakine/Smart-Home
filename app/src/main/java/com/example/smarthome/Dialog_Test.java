package com.example.smarthome;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smarthome.data.Devices_Data;
import com.example.smarthome.data.Smart_Devices;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Dialog_Test extends AppCompatDialogFragment {


    AutoCompleteTextView autoCompleteTextView ;
    ImageView icon;
    ArrayAdapter<String> adapterItems;
    List<Smart_Devices> data_devices= new Devices_Data().getDevicesList();
    String[] items = new    String[data_devices.size()];
    TextInputLayout Description;
    TextInputLayout Port;
    int item_id;
    RadioGroup radioGroup ;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton12;
    RadioButton radioButton22;
    RadioGroup radioGroup2;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_test, null);
        radioButton1 = view.findViewById(R.id.radio_button_1);
        radioButton2 = view.findViewById(R.id.radio_button_2);
        radioButton12 = view.findViewById(R.id.radio_button_12);
        radioButton22 = view.findViewById(R.id.radio_button_22);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup2 = view.findViewById(R.id.radioGroup2);
        Description = view.findViewById(R.id.Description);
        Port = view.findViewById(R.id.Port);
        autoCompleteTextView = view.findViewById(R.id.auto_complet);
        icon = view.findViewById(R.id.ima);
        autoCompleteTextView.setDropDownBackgroundDrawable(new ColorDrawable(Color.parseColor("#d8d8d8")));
        // mouad 's part
        int i;
        for (i = 0; i < data_devices.size(); i++) {
            items[i] = data_devices.get(i).getName();
        }
        adapterItems = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_items, items);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              String item = parent.getItemAtPosition(position).toString();
                //radiobutton1.setChecked(true);
//                radioGroup.check(R.id.radio_button_2);
                Log.i("mouad", "checkedbutton :" + radioGroup.getCheckedRadioButtonId());
                //Log.i("SIZEt", "checkedbutton :" + data_devices.get(position).getType());
                if(data_devices.get(position).getType()!=3){
                    radioGroup.check((data_devices.get(position).getType() == 1) ? R.id.radio_button_1 : R.id.radio_button_2);
                    radioGroup2.check((data_devices.get(position).getSignal() == 1) ? R.id.radio_button_12 : R.id.radio_button_22);
                    radioButton1.setEnabled(false);
                    radioButton2.setEnabled(false);
                    radioButton12.setEnabled(false);
                    radioButton22.setEnabled(false);
                    item_id = position;
                }else{
                    autoCompleteTextView.setText("");
                    autoCompleteTextView.setHint("Name Of your Device");
                    radioButton1.setEnabled(true);
                    radioButton2.setEnabled(true);
                    radioButton12.setEnabled(true);
                    radioButton22.setEnabled(true);
                    item_id = 5;
                }
                icon.setImageResource(data_devices.get(position).getIconOff());


            }
        });
        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Smart_Devices smart_device;
                if(item_id!=5) {
                    smart_device = data_devices.get(item_id);
                }else {
                    smart_device = new Smart_Devices(autoCompleteTextView.getText().toString(), item_id, (radioButton12.isChecked() == true) ? 1 : 0, (radioButton1.isChecked() == true) ? 1 : 0);
                }
                smart_device.setPort(Integer.parseInt(Port.getEditText().getText().toString()));
                smart_device.setDescription(Description.getEditText().getText().toString());
                smart_device.setIconOff(data_devices.get(item_id).getIconOff());
                try {
                    smart_device.setIconOn(data_devices.get(item_id).getIconOn());
                }catch (Exception e){}

                HomeFragment.addToFirebase(smart_device);
            }
        });
        return builder.create();

    }
}