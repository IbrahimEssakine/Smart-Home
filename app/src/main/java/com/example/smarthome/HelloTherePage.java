package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HelloTherePage extends AppCompatActivity {


    Button loginbutton ;
    Button signupbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(500);
        getWindow().setSharedElementEnterTransition(bounds);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_there_page);

        GifImageView gif = (GifImageView)findViewById(R.id.LogoGif);
        ((GifDrawable)gif.getDrawable()).stop();

        // SignUp translate page section

        signupbutton = findViewById(R.id.button_signup);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                showDialog();
            }

            private void showDialog() {
                final Dialog dialog = new Dialog(HelloTherePage.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.login_buttomsheet);

                Button Continuewithfirebase = dialog.findViewById(R.id.buttoncontinue);
                Continuewithfirebase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(HelloTherePage.this, "Redirecting Your To Firebase Account", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);

            }
        });





    }
}