package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class HomePage extends AppCompatActivity {

    TextView NameTxt, EmailTxt,LastNameTxt;
    ArrayList<String>data_user=new ArrayList<>();
    Button CreateHouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> user_data_list = bundle.getStringArrayList("UserDataList");

        NameTxt = findViewById(R.id.NameTxt);
        LastNameTxt = findViewById(R.id.LNameTxt);
        EmailTxt = findViewById(R.id.EmailTxt);
        CreateHouse= findViewById(R.id.CreateHouse);

        NameTxt.setText(user_data_list.get(0));
        LastNameTxt.setText(user_data_list.get(1));
        EmailTxt.setText(user_data_list.get(2));


        //Toast.makeText( this, "Email : "+NameTxt.getText().toString()+" Name : "+LastNameTxt.getText().toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText( this, "Email : "+user_data_list.get(2)+" Name : "+user_data_list.get(1), Toast.LENGTH_SHORT).show();
        CreateHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_user.add(0,NameTxt.getText().toString());
                data_user.add(1,LastNameTxt.getText().toString());
                data_user.add(2,EmailTxt.getText().toString());
                Intent intent=new Intent(getApplicationContext(), CreateHousePage.class);
                intent.putExtra("UserDataListChange",data_user);
                finish();
                startActivity (intent);
            }
        });
    }
}