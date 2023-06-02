package com.example.smarthome;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.data.Devices_Data;
import com.example.smarthome.data.Smart_Devices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LivingRoomFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    GridLayout Devcontrainer;
    AppCompatButton optiondots ;
    TextView textView,Descreption;
    SwitchCompat State;
    ImageView icon;
    ArrayList<Integer> ListOutputs = new ArrayList<>();
    String HouseID="";

    FirebaseDatabase database;
    ArrayList<Smart_Devices> devicesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_living_room, container, false);
        Bundle bundle = this.getArguments();
        //HouseID=bundle.getString("HouseID");
        //HouseID=getArguments().getString("HouseID");
        //Log.i("house_id","House is out : "+HouseID);

        database = FirebaseDatabase.getInstance();

//        Devcontrainer = view.findViewById(R.id.DeviceContainer);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 8;
        layoutParams.topMargin = 8;
        layoutParams.bottomMargin = 8;
        ArrayAdapter<Smart_Devices> myArrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,devicesList);
// Apply the layoutParams to the child view
//        Devcontrainer.setLayoutParams(layoutParams);

        Devcontrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Integer port:ListOutputs) {

                    View ViewOut=v.findViewById(port);
                    ViewOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View vo) {
                            SwitchCompat switchCompat = vo.findViewById(R.id.State);
                            switchCompat.setChecked(!switchCompat.isChecked());
                            Log.i("seraphine"," ID : --"+switchCompat.isChecked());
                            database.getReference("hjhjh").child("LivingRoom").child(String.valueOf(port)).child("state").setValue((switchCompat.isChecked()==true)? 1:0);
                        }
                    });

                }

            }
        });
        Refresh();
        return view;
    }
    public static LivingRoomFragment newInstance() {
        return new LivingRoomFragment();
    }
    public void addToFirebase(Smart_Devices smart_device){
        DatabaseReference myRef = database.getReference("hjhjh");
        myRef.child("LivingRoom").child(String.valueOf(smart_device.getPort())).setValue(smart_device);
        Refresh();
    }
    public void getViewSelected(){
        //TODO
    }
    public void AddDevice(Smart_Devices smart_device) {
        View viewdevice = getLayoutInflater().inflate(R.layout.device,null);
        //Log.i("house_id","House is in : "+HouseID);

        //Log.i("house_id","House is in here  : "+HouseID);
        viewdevice.setId(smart_device.getPort());
        textView=viewdevice.findViewById(R.id.textView);
        Descreption=viewdevice.findViewById(R.id.Descreption);
        textView.setText(smart_device.getName());
        icon = viewdevice.findViewById(R.id.imageView);
        State=viewdevice.findViewById(R.id.State);
        Descreption.setText(smart_device.getDescription());
        if(smart_device.getState()==0){
            viewdevice.setBackgroundColor(Color.parseColor("#a84e32"));
        }else{
            viewdevice.setBackgroundColor(Color.parseColor("#4dab4b"));
        }
        //if(smart_device.getIcon()!=0){
        icon.setImageResource(smart_device.getIconOff());
        if(smart_device.getType()==0){
            State.setVisibility(View.INVISIBLE);
        }

        State=viewdevice.findViewById(R.id.State);
        optiondots = viewdevice.findViewById(R.id.threedots);
        optiondots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(requireContext(), view, Gravity.END, 0, R.style.PopupMenuStyle);
                popup.inflate(R.menu.houses_threedots);

                // Get the MenuItems from the PopupMenu
                Menu menu = popup.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    // Set the desired text color for the MenuItem
                    SpannableString spannableString = new SpannableString(menuItem.getTitle());
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    menuItem.setTitle(spannableString);
                }
                popup.show();
            }
        });

        Devcontrainer.addView(viewdevice);

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return true;
    }
    void Refresh(){
        DatabaseReference myRef = database.getReference("hjhjh").child("LivingRoom");

        // Read from the database
        // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Devcontrainer.removeAllViews();
                int i=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Smart_Devices smart_device = dataSnapshot.getValue(Smart_Devices.class);
                    AddDevice(smart_device);
                    if(smart_device.getType()==1){
                        ListOutputs.add(i,smart_device.getPort());
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}