package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

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

import org.checkerframework.framework.qual.FromByteCode;

import java.lang.reflect.Array;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseFirestore firestore;
    Pair[]pairs=new Pair[1];
    Intent intent;

    GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ArrayList<String> user_info =new ArrayList<>();
        super.onCreate(savedInstanceState); 
        gso=new GoogleSignInOptions. Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        GifImageView gif = findViewById(R.id.LogoGif);
        ((GifDrawable)gif.getDrawable()).stop();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run(){
                ((GifDrawable)gif.getDrawable()).start();
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run(){
//                        startActivity( new Intent( MainActivity.this, MainDashBoard.class));
//                        finish();
                        pairs[0]=new Pair<View, String>(gif,"GifImage");
                        // Attach all the elements those you want to animate in design

//                        pairs[1]=new Pair<View, String>(logo,"logo_text");
                        //wrap the call in API level 21 or higher
                        account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

                        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP & account!=null)
                        {
                            Log.i("ireliawashere"," acc exist");
                            Log.i("ireliawashere",account.getDisplayName());
                            if (account!=null) {
                                String Mail = account.getEmail();
                                firestore = FirebaseFirestore.getInstance();
                                firestore.collection("user")
                                        .whereEqualTo("Email", Mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                Log.i("ireliawashere","inside complete");
                                                    if (!task.getResult().getDocuments().get(0).contains("Houses")) {
                                                        Log.i("ireliawashere","WaitingRoom");
                                                        finish();
                                                        Intent intent = new Intent(MainActivity.this, Waiting_Room.class);
                                                        startActivity(intent);
                                                    }else if(task.getResult().getDocuments().get(0).contains("Houses")) {
                                                        Log.i("ireliawashere", "MainDash");
                                                        finish();
                                                        Intent intent = new Intent(MainActivity.this, MainDashBoard.class);
                                                        user_info.add(0,account.getEmail());
                                                        user_info.add(1,task.getResult().getDocuments().get(0).get("Username").toString());
                                                        if(account.getPhotoUrl()!=null){
                                                            user_info.add(2,account.getPhotoUrl().toString());
                                                        }
                                                        intent.putExtra("FromMain",user_info);
                                                        startActivity(intent);
                                                    }
                                            }
                                        });
                            }
                        }else{
                            Log.i("MOKAI","HELLO THERE");
                            Intent intent = new Intent(MainActivity.this, HelloTherePage.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                }, 500);
            }
        }, 2500);

    }
}