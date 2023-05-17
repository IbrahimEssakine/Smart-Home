package com.example.smarthome;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.ListPopupWindowCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<String> user_data;
    private ArrayList<String> user_houses;
    TextView Username,test;
    int i=0;
    ImageView userImage;
    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoCompleteTextView;
    TextInputLayout button_menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        Username=view.findViewById(R.id.UsernameTxt);
        userImage=view.findViewById(R.id.appCompatImageView);
        button_menu=view.findViewById(R.id.button_menu);
        test=view.findViewById(R.id.DevicesTxt);


        autoCompleteTextView = view.findViewById(R.id.items);

        //menu1.inflate(R.menu.homes,);
        Toast.makeText(getActivity().getApplicationContext(), "test", Toast.LENGTH_SHORT).show();

        Bundle data = getArguments();
        if (data!=null){
            user_data=data.getStringArrayList("user_data");
            user_houses=data.getStringArrayList("user_houses");

            adapterItems = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.list_items,user_houses);
            autoCompleteTextView.setAdapter(adapterItems);

            Username.setText(user_data.get(4).toString());
            if(user_data.get(5)!="No"){
                Log.i("Lux","AZEAZEAZEAZE");
                Picasso.get().load(user_data.get(5)).into(userImage);
            }else{
                userImage.setImageResource(R.drawable.user_image);
            }

//            test.setText(user_houses.get(1).toString());
            //adapterItems = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.list_items,user_houses);
            //list.setAdapter(adapterItems);
            button_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
        return view;
    }
}