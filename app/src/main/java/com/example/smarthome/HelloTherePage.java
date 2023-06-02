package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.accounts.Account;
import android.accounts.AccountManager;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HelloTherePage extends AppCompatActivity {
    Button loginbutton ;
    Button signupbutton, Continuewithfirebase;
    BottomSheetBehavior bottomSheetBehavior;
    BlurView blurview ;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseFirestore firestore;
    Boolean loginx=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_there_page);

        gso=new GoogleSignInOptions. Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        //SignIn();
        ConstraintLayout bottomSheetLayout = findViewById(R.id.login_buttomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        blurview = findViewById(R.id.blurbg);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float radius = (float) (2*slideOffset);
                Log.d("mo3mo3slidoffsetradius", String.valueOf(radius));
                if (slideOffset>0)
                {
                    blurview.setupWith(getWindow().getDecorView().findViewById(android.R.id.content)).setBlurEnabled(true);
                    blurview.setupWith(getWindow().getDecorView().findViewById(android.R.id.content)).setBlurRadius(radius);
                }else {
                    blurview.setupWith(getWindow().getDecorView().findViewById(android.R.id.content)).setBlurEnabled(false);
                }
            }


        });



        GifImageView gif = (GifImageView)findViewById(R.id.LogoGif);
        ((GifDrawable)gif.getDrawable()).stop();
        signupbutton = findViewById(R.id.button_signup);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                blurview.setBlurRadius(25f);
                Intent intent=new Intent(HelloTherePage.this,SignUpPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.silde_in_right,R.anim.slide_out_left);
            }
        });
        // buttom sheet section
        loginbutton = findViewById(R.id.button_login);
        Continuewithfirebase = findViewById(R.id.buttoncontinue);

        loginbutton.setClickable(true);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    Continuewithfirebase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(HelloTherePage.this, "Redirecting To Your Firebase Account", Toast.LENGTH_SHORT).show();
                            SignIn();}
                    });
                }
            }
        });

    }
    private void SignIn() {
        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent, 100);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        ArrayList<String> user_info =new ArrayList<>();
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
                                    if (task.getResult().isEmpty()){
                                        Log.i("ireliawashere","Hello-therePage");
                                        Intent intent = new Intent(HelloTherePage.this, SignUpPage.class);
                                        startActivity(intent);
                                    }else{
                                        if (!task.getResult().getDocuments().get(0).contains("Houses")) {
                                            Log.i("ireliawashere","WaitingRoom");
                                            Intent intent = new Intent(HelloTherePage.this, Waiting_Room.class);
                                            finish();
                                            startActivity(intent);
                                        }else if(task.getResult().getDocuments().get(0).contains("Houses")) {
                                            Log.i("ireliawashere", "MainDash");
                                            Intent intent = new Intent(HelloTherePage.this, MainDashBoard.class);
                                            user_info.add(0,account.getEmail());
                                            user_info.add(1,task.getResult().getDocuments().get(0).get("Username").toString());
                                            user_info.add(2,account.getPhotoUrl().toString());
                                            intent.putExtra("FromMain",user_info);

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