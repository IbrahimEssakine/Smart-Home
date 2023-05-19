package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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


                            //test connictivity
//                            ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)) ;
//                            NetworkInfo networkInfo =  connectivityManager.getActiveNetworkInfo();
//
//                            while ((networkInfo==null) || !networkInfo.isConnected())
//                            {
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        startActivity( new Intent( MainActivity.this, MainActivity.class));
//                                    }
//                                },5000);
//
//
//                            }



                            startActivity( new Intent( MainActivity.this, MainDashBoard.class));
    //                        finish();

                            // here displays the hellotherepage
//                            Intent intent=new Intent(MainActivity.this,HelloTherePage.class);
//                            // Attach all the elements those you want to animate in design
//                            Pair[]pairs=new Pair[1];
//                            pairs[0]=new Pair<View, String>(gif,"GifImage");
//    //                        pairs[1]=new Pair<View, String>(logo,"logo_text");
//                            //wrap the call in API level 21 or higher
//                            if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP)
//                            {
//                                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
//                                startActivity(intent,options.toBundle());
//                                finish();
//                            }

//

                        }
                    }, 3000);
            }
        }, 1500);

    }
}