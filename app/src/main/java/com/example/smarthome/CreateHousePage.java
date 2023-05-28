package com.example.smarthome;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateHousePage extends AppCompatActivity {
    TextInputLayout HName,Password,HexCode;
    FirebaseFirestore firestore;
    DatabaseReference database;
    AppCompatButton CancelCreateHouse,HouseCreation;
    String User_ID;

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
        CancelCreateHouse= findViewById(R.id.CancelCreateHouse);

        HouseCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore=FirebaseFirestore.getInstance();
                firestore.collection("user").whereEqualTo("Email", user_data_list.get(0)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            User_ID=doc.getString("ID");
                        }
                    }
                });


                firestore=FirebaseFirestore.getInstance();

                Map<String, Object> house_data = new HashMap<>();
                house_data.put("Name", HName.getEditText().getText().toString());
                house_data.put("Password", Password.getEditText().getText().toString());
                house_data.put("HexCode", Arrays.asList(HexCode.getEditText().getText().toString()));

                firestore.collection("House")
                .add(house_data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference HouseID) {
                        firestore.collection("user").document(User_ID).update("Houses", Arrays.asList(HouseID.getId()));

                        database = FirebaseDatabase.getInstance().getReference (HouseID.getId());
                        //DatabaseReference usersRef = database.child("Sensor");
                        //Map<String,Object> Sensor = new HashMap<>();
                        //Sensor.put("LED", Arrays.asList(1,2,3));
                        //usersRef.setValue(Sensor);
                        DatabaseReference usersRefPass = database.child("Password");
                        usersRefPass.setValue(Password.getEditText().getText().toString());

                        HouseCreation.setBackgroundColor(Color.GRAY);
                        HouseCreation.setClickable(false);
                        Intent intent = new Intent(getApplicationContext(), MainDashBoard.class);
                        intent.putExtra("FromMain",user_data_list);
                        finish();
                        startActivity(intent);
                    }
                });
            }
        });
        CancelCreateHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Waiting_Room.class);
                intent.putExtra("UserDataListChange",user_data_list);
                finish();
                startActivity (intent);
            }
        });
    }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}