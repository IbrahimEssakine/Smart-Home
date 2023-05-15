package com.example.smarthome;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Waiting_Room extends AppCompatActivity {
        TextView EmailTxt;
        GoogleSignInOptions gso;
        GoogleSignInClient gsc;
        AppCompatButton buttonSignOut,CreateHouse;
         FirebaseFirestore firestore;
         AppCompatButton back;
        ArrayList<String> data_user_list=new ArrayList<>();
        ArrayList<Boolean> login=new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.no_house_exist_page);
            gso=new GoogleSignInOptions. Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            gsc = GoogleSignIn.getClient(this,gso);
            EmailTxt=findViewById(R.id.EmailTxt);
            buttonSignOut=findViewById(R.id.buttonSignOut);
            CreateHouse=findViewById(R.id.CreateHouse);
            back=findViewById(R.id.buttonback);
            EmailTxt.setText(SignIn());
            data_user_list.add(0,EmailTxt.getText().toString());
            buttonSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gsc.signOut();
                    finish();
                    Intent intent=new Intent(getApplicationContext(), HelloTherePage.class);
                    startActivity (intent);
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmailTxt.setText(SignIn());
                }
            });
            CreateHouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), CreateHousePage.class);
                    intent.putExtra("UserDataListChange",data_user_list);
                    finish();
                    startActivity (intent);
                }
            });

        }
    private String SignIn() {
        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent, 100);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String Mail = account.getEmail();
        return Mail;
            }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode==100) {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult (ApiException.class);
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                if (account!=null) {
                    String Mail = account.getEmail();
                    firestore = FirebaseFirestore.getInstance();
                    firestore.collection("user")
                            .whereEqualTo("Email", Mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot doc : task.getResult()) {
                                        if (doc.contains("Houses")) {
                                            Intent intent = new Intent(getApplicationContext(), MainDashBoard.class);
                                            finish();
                                            startActivityForResult(intent,3);
                                        }
                                    }
                                }
                            });
                }
            } catch (ApiException e) {
                Toast.makeText( this, "Cnx Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
