package com.example.smarthome;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.app.ActivityCompat;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smarthome.data.Smart_Devices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    ArrayList<String> dataOfuser = new ArrayList<String>();
    ArrayList<String> user_houses = new ArrayList<String>();
    //    Menu menu;
    FirebaseFirestore firestore;
    PopupMenu popup;
    AppCompatImageView userImage;
    static ArrayList<String> rooms = new ArrayList<>();
    static int checkRoom = 0;
    TextView Username;
    String cityName = "Beni Mellal";
    String House = "";
    static RecyclerView DeviceContainer;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    static ArrayList<Smart_Devices> smart_devicesArrayLis = new ArrayList<Smart_Devices>();
    static FirebaseDatabase database;
    AppCompatRadioButton kitchenButton, liviingroomButton, bathroomButton, bedroomButton, checkedButton;
    static RadioGroup radioGroup;
    static AppCompatButton changeshousesbutton;

    ArrayList<String> user_info = new ArrayList<>();
    //Map<String,ArrayList<Smart_Devices>> smart_devicesMap = new HashMap<>();
    Bundle bundle = new Bundle();
    TextView StateWeather, TempDegree, WeatherDescribtion;
    AppCompatImageView imageWeather;
    FusedLocationProviderClient fusedLocationClient;

    double MyLong=6.3758;
    double MyLat= 32.3424;
    public static Map<String, Integer> images = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                MyLat=location.getLatitude();
                                MyLong=location.getLongitude();
                            }
                        }
                    });
        }catch (Exception e){

        }

        Geocoder geocoder = new Geocoder(getContext().getApplicationContext(), Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
            cityName = addresses.get(0).getAddressLine(0).split(",")[0];
            Log.i("abderrazak"," "+cityName);
            weather(cityName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String cityName = addresses.get(0).getAddressLine(0);
        user_info = bundle.getStringArrayList("user_info");
        radioGroup = view.findViewById(R.id.radioRoom);
        rooms.add("LivingRoom");
        rooms.add("Kitchen");
        rooms.add("BedRoom");
        rooms.add("BathRoom");
        images.put("Clear",R.drawable.clear);
        images.put("Clouds",R.drawable.clouds);
        images.put("Rain",R.drawable.rain);
        images.put("thunderstroms",R.drawable.thunderstromspng);
        StateWeather=view.findViewById(R.id.StateWeather);
        TempDegree=view.findViewById(R.id.TempDegree);
        WeatherDescribtion=view.findViewById(R.id.WeatherDescribtion);
        imageWeather=view.findViewById(R.id.imageWeather);
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
        DeviceContainer.setPadding(6,6,6,6);

        float density = getResources().getDisplayMetrics().density;
        int spacingDp = 8; // Desired column margin in dp
        int spacingPx = (int) (spacingDp * density); // Convert dp to pixels
        DeviceContainer.setLayoutManager(layoutManager);

        DeviceContainer.addItemDecoration(new GridSpacingItemDecoration(spacingPx));


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
                                    House = user_houses.get(0);
                                    Refrech(checkRoom);
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



        // ************************ Door Test ***************

        GifImageView Door = view.findViewById(R.id.Door);
        ((GifDrawable)Door.getDrawable()).stop();
        Door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    view.setSelected(false);
                    Door.setBackgroundResource(R.drawable.door_background_closed);
                    ((GifDrawable)Door.getDrawable()).start();
                    Door.setImageResource(R.drawable.close_door_v1);
                } else {
                    view.setSelected(true);
                    Door.setBackgroundResource(R.drawable.door_background_opend);
                    ((GifDrawable)Door.getDrawable()).start();
                    Door.setImageResource(R.drawable.open_door_v1);
                }
            }
        });

        // **************************************************

//        database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Mx2Knn4Kb9lpnCTvErBV");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                smart_devicesArrayLis=new ArrayList<Smart_Devices>();
//                if (snapshot.child(rooms.get(checkRoom)).exists()) {
//                    Log.i("omar", rooms.get(checkRoom));
//                    int i = 0;
//                    for (DataSnapshot dataSnapshot : snapshot.child(rooms.get(checkRoom)).getChildren()) {
//
//                        Smart_Devices smart_device = dataSnapshot.getValue(Smart_Devices.class);
//                        smart_devicesArrayLis.add(i, smart_device);
//                        i++;
//
//                    }
//                }
//                recyclerViewAdapter = new RecyclerViewAdapter(smart_devicesArrayLis);
//                DeviceContainer.setAdapter(recyclerViewAdapter);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

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

                Menu menu = popup.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    SpannableString spannableString = new SpannableString(menuItem.getTitle());
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.blue_main)), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    menuItem.setTitle(spannableString);
                }
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        changeshousesbutton.setText(item.getTitle());
                        Refrech(checkRoom);
                        return false;
                    }
                });
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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getContext(), "you pressed on : "+checkedId , Toast.LENGTH_SHORT).show();
                checkedButton =  view.findViewById(checkedId);
                switch (checkedId){
                    case R.id.button_1:
                        checkRoom = 0;
                        Refrech(0);
                        break;
                    case R.id.button_2:
                        checkRoom = 1;
                        Refrech(1);
                        break;
                    case R.id.button_3:
                        checkRoom = 2;
                        Refrech(2);
                        break;
                    case R.id.button_4:
                        checkRoom = 3;
                        Refrech(3);
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        changeshousesbutton.setText(menuItem.getTitle());
        Log.i("ionia", "in clicked");
        return true;
    }

    public static void addToFirebase(Smart_Devices smart_device){
        database=FirebaseDatabase.getInstance();
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.button_1:
                try {
                    database.getReference(changeshousesbutton.getText().toString()).child("LivingRoom").child(String.valueOf(smart_device.getPort())).setValue(smart_device);
                }catch (Exception e){
                    break;
                }
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
    }
    public static void removeFromFirebase(Smart_Devices smart_device) {
        database=FirebaseDatabase.getInstance();
        database.getReference(changeshousesbutton.getText().toString()).child(rooms.get(checkRoom)).child(String.valueOf(smart_device.getPort())).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });

        }
    void Refrech(int checkRoom){
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(changeshousesbutton.getText().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    smart_devicesArrayLis=new ArrayList<Smart_Devices>();
                    if (snapshot.child(rooms.get(checkRoom)).exists()) {
                        int i = 0;
                        for (DataSnapshot dataSnapshot : snapshot.child(rooms.get(checkRoom)).getChildren()) {
                            Smart_Devices smart_device = dataSnapshot.getValue(Smart_Devices.class);
                            smart_devicesArrayLis.add(i, smart_device);
                            i++;
                        }
                    }
                recyclerViewAdapter = new RecyclerViewAdapter(smart_devicesArrayLis);
                DeviceContainer.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void weather(String CityName){
        Log.i("abderrazak2",CityName);
        RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
        String url="https://api.openweathermap.org/data/2.5/weather?q="+"Beni Mellal"+"&appid=31d49ebece13e9a409b91f382a1edaca";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("MyLog", "---------");
                    Log.i("MyLog", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject main = jsonObject.getJSONObject("main");
                    JSONArray weatherArray = jsonObject.getJSONArray("weather");
                    JSONObject weather = weatherArray.getJSONObject(0);
                    String tempMin = String.valueOf((int) (main.getDouble("temp_min") - 273.15));
                    String tempMax = String.valueOf((int) (main.getDouble("temp_max") - 273.15));
                    String feels_like =String.valueOf((int) (main.getDouble("feels_like") - 273.15));
                    String stateWeather= (String) (weather.getString("main"));
                    String weatherDesc= (String) (weather.getString("description"));
                    int pression = main.getInt("pressure");
                    int humidity = main.getInt("humidity");
                    StateWeather.setText(stateWeather);
                    TempDegree.setText(feels_like+"°C");
                    WeatherDescribtion.setText("The temperature , below "+tempMin+"°C out cost by "+tempMax+"°C, Humidity "+humidity+", Pressure "+pression);
                    imageWeather.setImageResource(images.get(stateWeather));
                    String img = (weather.getString("main"));

//                    meteoItem.image = img;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MyLog","Connection Probelm !");
            }
        });
        queue.add(stringRequest);
    }
}