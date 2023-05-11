    package com.example.smarthome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


    public class SignUpPage extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button returnbackbutton ;

    FirebaseFirestore firestore;
    ArrayList<String> data_user_list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        returnbackbutton = findViewById(R.id.buttonback);
        returnbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button creataccfirebase = findViewById(R.id.buttoncreatfirebaseacc);
        gso=new GoogleSignInOptions. Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);

        creataccfirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpPage.this, "Redirecting You To Firebase", Toast.LENGTH_SHORT).show();
                SignIn();
            }
        });


    }

    private void SignIn() {
        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode==100) {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult (ApiException.class);
//                finish();
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                if (account!=null) {
                    String Name=account.getGivenName();
                    String Family=account.getFamilyName();
                    String Mail=account.getEmail();
                    firestore=FirebaseFirestore.getInstance();


                    AggregateQuery countQuery = firestore.collection ("user").whereEqualTo("Email",Mail).count();
                    countQuery.get(AggregateSource.SERVER).addOnCompleteListener(tasks -> {
                    AggregateQuerySnapshot snapshot = tasks.getResult();

                        if(snapshot.getCount()!=0){
                            Toast.makeText(SignUpPage.this, "this acc exist  "+snapshot.getCount(), Toast.LENGTH_SHORT).show();
                            gsc.signOut();
                            SignIn();
                        }else{
                            data_user_list.add(0,Name);
                            data_user_list.add(1,Family);
                            data_user_list.add(2,Mail);

                            Toast.makeText( this, "Email : "+Mail+" Name : "+Name, Toast.LENGTH_SHORT).show();
                            HomeActivity();
                        }
                    });
                }

            } catch (ApiException e) {
                Toast.makeText( this, "hada errir", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    private void HomeActivity() {
        finish();
        Intent intent=new Intent(getApplicationContext(), HomePage.class);
        intent.putExtra("UserDataList",data_user_list);
        startActivity (intent);
    }
}