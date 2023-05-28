package com.example.smarthome;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Waiting_Room extends AppCompatActivity {
        TextView EmailTxt;
        GoogleSignInAccount account;
        GoogleSignInOptions gso;
        GoogleSignInClient gsc;
        AppCompatButton buttonSignOut,CreateHouse;
         FirebaseFirestore firestore;
         AppCompatButton back;
        ImageView userImage;
        ArrayList<String> data_user_list=new ArrayList<>();
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
            userImage=findViewById(R.id.ImageAcc);
            EmailTxt.setText(SignIn());

            buttonSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gsc.signOut();
                    finish();
                    Log.i("MO3MO3AZEAZE","cant be clicked");
                    Intent intent=new Intent(getApplicationContext(), HelloTherePage.class);
                    startActivity (intent);
                }
            });

            CreateHouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(getApplicationContext(), CreateHousePage.class);
                    Log.i("GGEZ"," : "+data_user_list.get(1));
                    intent.putExtra("UserDataListChange",data_user_list);
                    finish();
                    startActivity (intent);
                }
            });

        }
    private String SignIn() {
        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent, 100);
        account = GoogleSignIn.getLastSignedInAccount(this);
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
                account = GoogleSignIn.getLastSignedInAccount(this);
                if (account!=null) {
                    String Mail = account.getEmail();
                    if(account.getPhotoUrl()!=null){
                        Picasso.get().load(account.getPhotoUrl().toString()).into(userImage);
                        Log.i("Lux","AZEAZEAZEAZE");
                    }else{
                        userImage.setImageResource(R.drawable.user_image);
                    }
                    firestore = FirebaseFirestore.getInstance();
                    firestore.collection("user")
                            .whereEqualTo("Email", Mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                 @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    for (DocumentSnapshot doc : task.getResult()) {
                                        Log.i("MO3MO3AZEAZE","inside complete");
                                        data_user_list.add(0,account.getEmail());
                                        data_user_list.add(1,doc.get("Username").toString());
                                        if(account.getPhotoUrl()!=null){
                                            data_user_list.add(2,account.getPhotoUrl().toString());
                                        }
                                        if (doc.contains("Houses")) {
                                            Log.i("MO3MO3AZEAZE","inside no house");
                                            Intent intent = new Intent(getApplicationContext(), MainDashBoard.class);
                                            intent.putExtra("FromMain",data_user_list);
                                            finish();
                                            startActivity(intent);
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
