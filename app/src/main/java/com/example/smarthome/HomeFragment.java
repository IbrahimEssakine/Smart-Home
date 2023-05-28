package com.example.smarthome;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.data.Smart_Devices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    ArrayList<String> dataOfuser = new ArrayList<String>();
    ArrayList<String> user_houses = new ArrayList<String>();
//    Menu menu;
    FirebaseFirestore firestore;
    PopupMenu popup;
    AppCompatImageView userImage;
    ArrayList<String> rooms = new ArrayList<>();

    TextView Username;
    static RecyclerView DeviceContainer;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    static ArrayList<Smart_Devices> smart_devicesArrayLis = new ArrayList<Smart_Devices>();
    static FirebaseDatabase database;
    AppCompatRadioButton kitchenButton,liviingroomButton,bathroomButton,bedroomButton,checkedButton;
    static RadioGroup radioGroup ;
    static AppCompatButton changeshousesbutton;
    ArrayList<String> user_info = new ArrayList<>();
    Map<String,ArrayList<Smart_Devices>> smart_devicesMap = new HashMap<>();
    Bundle bundle=new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        user_info = bundle.getStringArrayList("user_info");
        radioGroup = view.findViewById(R.id.radioRoom);
        rooms.add("LivingRoom");
        rooms.add("Kitchen");
        rooms.add("BedRoom");
        rooms.add("BathRoom");

        liviingroomButton = view.findViewById(R.id.button_1);
        kitchenButton = view.findViewById(R.id.button_2);
        bedroomButton = view.findViewById(R.id.button_3);
        bathroomButton = view.findViewById(R.id.button_4);
        changeshousesbutton = view.findViewById(R.id.houses_button_menu);
        Username = view.findViewById(R.id.UsernameTxt);
        Username.setText(user_info.get(1));
        userImage = view.findViewById(R.id.appCompatImageView);
        DeviceContainer  = view.findViewById(R.id.DeviceContainer);
        layoutManager = new GridLayoutManager(getContext().getApplicationContext(),2);
        DeviceContainer.setLayoutManager(layoutManager);
        DeviceContainer.setHasFixedSize(true);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hjhjh");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                smart_devicesMap.clear();
                for (String room:rooms) {
                    smart_devicesArrayLis=new ArrayList<Smart_Devices>();

                    if (snapshot.child(room).exists()) {
                        Log.i("omar", room);
                        int i = 0;
                        for (DataSnapshot dataSnapshot : snapshot.child(room).getChildren()) {

                            Smart_Devices smart_device = dataSnapshot.getValue(Smart_Devices.class);
                            Log.i("omar"," smart : "+ smart_device.getName());
                            smart_devicesArrayLis.add(i, smart_device);
                            i++;
                            Log.i("omaro"," array : "+smart_devicesArrayLis.get(0).getName());

                        }
                    }
                    smart_devicesMap.put(room, smart_devicesArrayLis);
                }
                DeviceContainer.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("user")
                .whereEqualTo("Email", user_info.get(0)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            if(doc.exists()){
                                user_houses= (ArrayList<String>) doc.get("Houses");
                                if(user_houses!=null){
                                    changeshousesbutton.setText(user_houses.get(0).toString());
//                                        replaceFragment(new LivingRoomFragment());
                                }
                                dataOfuser.add(0,doc.get("Email").toString());
                                dataOfuser.add(1,doc.get("ID").toString());
                                dataOfuser.add(2,doc.get("Password").toString());
                                dataOfuser.add(3,doc.get("Phone").toString());
                                dataOfuser.add(4,doc.get("Username").toString());
                            }
                            Username.setText(dataOfuser.get(4).toString());
                            if (user_info.size()==3) {
                                Picasso.get().load(user_info.get(2)).into(userImage);
                            } else {
                                userImage.setImageResource(R.drawable.user_image);}
                        }
                    }
                });



        popup = new PopupMenu(requireContext(), view, Gravity.END, 0, R.style.PopupMenuStyle);
        popup.inflate(R.menu.houses_popup_menu);
        changeshousesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup = new PopupMenu(requireContext(), view, Gravity.END, 0, R.style.PopupMenuStyle);
                popup.inflate(R.menu.houses_popup_menu);
                for (int i = 0; i < user_houses.size(); i++) {
                    popup.getMenu().add(1, 1, 1, user_houses.get(i).toString());
                }

                // Get the MenuItems from the PopupMenu
                Menu menu = popup.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    // Set the desired text color for the MenuItem
                    SpannableString spannableString = new SpannableString(menuItem.getTitle());
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.blue_main)), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    menuItem.setTitle(spannableString);
                }
                popup.show();
            }
        });

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                changeshousesbutton.setText(item.getTitle());
                Log.i("ionia", "in clicked");
                return true;
            }
        });


        liviingroomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                replaceFragment(new LivingRoomFragment());
            }
        });
        kitchenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                replaceFragment(new KitchenFragment());
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getContext(), "you pressed on : "+checkedId , Toast.LENGTH_SHORT).show();
                checkedButton =  view.findViewById(checkedId);
                checkedButton.setTextColor(Color.parseColor("#FFFFFFFF"));
                switch (checkedId){
                    case R.id.button_1:
                        try{
                            recyclerViewAdapter = new RecyclerViewAdapter(smart_devicesMap.get("LivingRoom"));
                            DeviceContainer.setAdapter(recyclerViewAdapter);
                            Toast.makeText(getContext(), "No LivingRoom "+smart_devicesMap.get("LivingRoom").size(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getContext(), "LivingRoom", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.button_2:
                        try{
                            Log.i("omarr","here a");
                            recyclerViewAdapter = new RecyclerViewAdapter(smart_devicesMap.get("Kitchen"));
                            DeviceContainer.setAdapter(recyclerViewAdapter);


                        }catch (Exception e){
                            Toast.makeText(getContext(), "Kitchen", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.button_3:
                        try{
                            recyclerViewAdapter = new RecyclerViewAdapter(smart_devicesMap.get("BedRoom"));
                            DeviceContainer.setAdapter(recyclerViewAdapter);

                        }catch (Exception e){
                            Toast.makeText(getContext(), "BedRoom", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.button_4:
                        try{
                            recyclerViewAdapter = new RecyclerViewAdapter(smart_devicesMap.get("BathRoom"));
                            DeviceContainer.setAdapter(recyclerViewAdapter);

                        }catch (Exception e){
                            Toast.makeText(getContext(), "BathRoom", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
        if(smart_devicesMap.get("LivingRoom")!=null){
        recyclerViewAdapter = new RecyclerViewAdapter(smart_devicesMap.get("LivingRoom"));}

//        DeviceContainer
        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        changeshousesbutton.setText(menuItem.getTitle());

        return true;
    }
//    static void Refresh(){
//
//    }

    public static void addToFirebase(Smart_Devices smart_device){
        database=FirebaseDatabase.getInstance();
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.button_1:
                database.getReference(changeshousesbutton.getText().toString()).child("LivingRoom").child(String.valueOf(smart_device.getPort())).setValue(smart_device);
                break;
            case R.id.button_2:
                database.getReference(changeshousesbutton.getText().toString()).child("Kitchen").child(String.valueOf(smart_device.getPort())).setValue(smart_device);
                break;
            case R.id.button_3:
                database.getReference(changeshousesbutton.getText().toString()).child("BedRoom").child(String.valueOf(smart_device.getPort())).setValue(smart_device);
                break;
            case R.id.button_4:
                database.getReference(changeshousesbutton.getText().toString()).child("BathRoom").child(String.valueOf(smart_device.getPort())).setValue(smart_device);
                break;
        }

//        DatabaseReference myRef = database.getReference(changeshousesbutton.getText().toString());
//        myRef.child("LivingRoom").child(String.valueOf(smart_device.getPort())).setValue(smart_device);
//        Refresh();
    }

}