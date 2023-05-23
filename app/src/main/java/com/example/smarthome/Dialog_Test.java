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

import java.util.ArrayList;
import java.util.List;

public class Dialog_Test extends AppCompatDialogFragment {


    AutoCompleteTextView autoCompleteTextView ;
    ImageView icon;
    ArrayAdapter<String> adapterItems;
    List<Smart_Devices> data_devices= new Devices_Data().getDevicesList();
    String[] items = new    String[data_devices.size()+1];
    TextInputLayout Description;
    RadioButton radiobutton1;
    RadioButton radiobutton2;
    RadioGroup radioGroup ;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_test, null);
        radiobutton1 = view.findViewById(R.id.radio_button_1);
        radiobutton2 = view.findViewById(R.id.radio_button_2);
        radioGroup = view.findViewById(R.id.radioGroup);
        Description = view.findViewById(R.id.Description);
        autoCompleteTextView = view.findViewById(R.id.auto_complet);
        icon = view.findViewById(R.id.ima);
        Log.i("SIZEr", "hahah" + data_devices.size());
        Log.i("SIZEr", "hahah" + data_devices.get(4).getName());
        autoCompleteTextView.setDropDownBackgroundDrawable(new ColorDrawable(Color.parseColor("#d8d8d8")));
        // mouad 's part
        int i;
        for (i = 0; i < data_devices.size(); i++) {
            items[i] = data_devices.get(i).getName();
            Log.i("mouad", "" + items[i]);
        }
        items[i] = "Other";
        adapterItems = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_items, items);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              String item = parent.getItemAtPosition(position).toString();

                icon.setImageResource(R.drawable.baseline_smartphone_24);

                Log.i("mouad", "" + data_devices.get(position).getID());
//

                Log.i("SIZEr"," radio "+radiobutton1.isChecked());
                //radiobutton1.setChecked(true);

//                radioGroup.check(R.id.radio_button_2);
                Log.i("mouad", "checkedbutton :" + radioGroup.getCheckedRadioButtonId());
                radioGroup.check((data_devices.get(position).getType() == 1) ? R.id.radio_button_1 : R.id.radio_button_2);
//                Log.i("mouad","beforradio 1 :" + radiobutton1.isChecked());
//                radiobutton1.setChecked((getDevicesList().get(i).getType() == 1) ? true : false);
//                Log.i("mouad","afterradio 1 :" + radiobutton1.isChecked());
//                radiobutton2.setChecked((getDevicesList().get(i).getType()==1)? false : true);

            }
        });

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LivingRoomFragment livingRoomFragment = LivingRoomFragment.newInstance();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_house_cards, R.anim.slide_in_house_cards);
                transaction.add(R.id.DeviceContainer, livingRoomFragment);
                transaction.commitNow();  // Use commitNow() for immediate fragment attachment

// Call the AddDevice() method
                Smart_Devices smart_devices = new Smart_Devices();
                livingRoomFragment.AddDevice();
            }
        });
        return builder.create();

    }
}