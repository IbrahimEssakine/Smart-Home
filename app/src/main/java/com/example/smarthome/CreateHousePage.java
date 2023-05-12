package com.example.smarthome;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateHousePage extends AppCompatActivity {
    TextView HName,Password,HexCode;
    Button HouseCreation;
    FirebaseFirestore firestore;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_house_page);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> user_data_list = bundle.getStringArrayList("UserDataListChange");
        HName = findViewById(R.id.HouseTxt);
        Password = findViewById(R.id.KeypadPassword);
        HexCode = findViewById(R.id.CardHexNbr);
        HouseCreation= findViewById(R.id.HouseCreation);
        HouseCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                firestore=FirebaseFirestore.getInstance();

                Map<String, Object> house_data = new HashMap<>();
                house_data.put("Name", HName.getText().toString());
                house_data.put("Password", Password.getText().toString());
                house_data.put("HexCode", Arrays.asList(HexCode.getText().toString()));

                firestore.collection("House")
                .add(house_data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference HouseID) {
                        //Toast.makeText(CreateHousePage.this,"DocumentSnapshot written with ID: " + documentReference.getId(),Toast.LENGTH_SHORT).show();
                        Map<String, Object> user_data = new HashMap<>();
                        user_data.put("Username", user_data_list.get(0));
                        user_data.put("Phone",user_data_list.get(1));
                        user_data.put("Email", user_data_list.get(2));
                        user_data.put("Password", user_data_list.get(3));
                        user_data.put("Houses", Arrays.asList(HouseID.getId()));
                        firestore.collection("user").add(user_data);

                        database = FirebaseDatabase.getInstance().getReference (HouseID.getId());
                        //DatabaseReference usersRef = database.child("Sensor");
                        //Map<String,Object> Sensor = new HashMap<>();
                        //Sensor.put("LED", Arrays.asList(1,2,3));
                        //usersRef.setValue(Sensor);
                        DatabaseReference usersRefPass = database.child("Password");
                        usersRefPass.setValue(Password.getText().toString());

                        HouseCreation.setBackgroundColor(Color.GRAY);
                        HouseCreation.setClickable(false);
                    }
                });
            }
        });
    }
}