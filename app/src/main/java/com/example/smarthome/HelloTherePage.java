package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import eightbitlab.com.blurview.BlurView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HelloTherePage extends AppCompatActivity {
    Button loginbutton ;
    Button signupbutton;
    BottomSheetBehavior bottomSheetBehavior;
    BlurView blurview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_there_page);
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
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    Button Continuewithfirebase = findViewById(R.id.buttoncontinue);
                    Continuewithfirebase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(HelloTherePage.this, "Redirecting To Your Firebase Account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}