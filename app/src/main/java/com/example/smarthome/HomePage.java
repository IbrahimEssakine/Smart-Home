package com.example.smarthome;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity {
    TextInputLayout UsernameTxt, PasswordTxt,PhoneNbr;
    TextView EmailTxt;
    ArrayList<String>data_user=new ArrayList<>();
    Button CreateHouse;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseFirestore firestore;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        gso=new GoogleSignInOptions. Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> user_data_list = bundle.getStringArrayList("UserDataList");

        UsernameTxt = findViewById(R.id.UsernameTxt);
        PhoneNbr = findViewById(R.id.PhoneNbr);
        PasswordTxt = findViewById(R.id.Password);
        EmailTxt = findViewById(R.id.EmailTxt);
        CreateHouse= findViewById(R.id.CreateHouse);

        UsernameTxt.getEditText().setText(user_data_list.get(0));
        EmailTxt.setText(user_data_list.get(1));

        CreateHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_user.add(0,UsernameTxt.getEditText().getText().toString());
                data_user.add(1,PhoneNbr.getEditText().getText().toString());
                data_user.add(2,EmailTxt.getText().toString());
                data_user.add(3,PasswordTxt.getEditText().getText().toString());
                firestore=FirebaseFirestore.getInstance();
                Map<String, Object> user_data = new HashMap<>();
                user_data.put("Username",UsernameTxt.getEditText().getText().toString() );
                user_data.put("Phone",PhoneNbr.getEditText().getText().toString());
                user_data.put("Email", EmailTxt.getText().toString());
                user_data.put("Password", PasswordTxt.getEditText().getText().toString());
                user_data.put("ID", null);
                firestore.collection("user")
                    .add(user_data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                      @Override
                      public void onSuccess(DocumentReference UserID) {
                          UserID.update("ID", UserID.getId());
                          data_user.add(4,UserID.getId());
                          //user_data.put("ID", UserID.getId());
                      }
                      });
                //firestore.collection("user").document(data_user.get(4)).set(user_data);
                Intent intent=new Intent(getApplicationContext(), Waiting_Room.class);
                intent.putExtra("UserDataListChange",data_user);
                finish();
                startActivity (intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        gsc.signOut();
    }
}