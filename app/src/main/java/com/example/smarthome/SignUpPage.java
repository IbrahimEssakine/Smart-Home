package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

        //*******************************************************

        // Create the container layout styled as a button
        ConstraintLayout containerLayout = new ConstraintLayout(this);
        containerLayout.setId(View.generateViewId());
        containerLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_white_bg));
        containerLayout.setClickable(true);


        // Create the elements
        ImageView imageView = new ImageView(this);
        imageView.setId(View.generateViewId());
        imageView.setLayoutParams(new ConstraintLayout.LayoutParams(58, 58));
        imageView.setImageResource(R.drawable.icon);
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.gray_null));

        TextView textView = new TextView(this);
        textView.setId(View.generateViewId());
        textView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.outfit_semibold));
        textView.setText("SmartLamp");
        textView.setTextColor(ContextCompat.getColor(this, R.color.gray_null));
        textView.setTextSize(28);

        TextView descriptionTextView = new TextView(this);
        descriptionTextView.setId(View.generateViewId());
        descriptionTextView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        descriptionTextView.setTextColor(Color.parseColor("#c1c1c1"));
        descriptionTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_circle_24, 0, 0, 0);
        descriptionTextView.setCompoundDrawablePadding(6);
        descriptionTextView.setTypeface(ResourcesCompat.getFont(this, R.font.outfit_medium));
        descriptionTextView.setText("Esp32 colorful lamp");
        descriptionTextView.setTextSize(12);
        descriptionTextView.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#c1c1c1")));

        SwitchCompat switchCompat = new SwitchCompat(this);
        switchCompat.setId(View.generateViewId());
        switchCompat.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        switchCompat.setTypeface(ResourcesCompat.getFont(this, R.font.outfit_medium));
        switchCompat.setText("Turned Off  ");
        switchCompat.setTextColor(ContextCompat.getColor(this, R.color.gray_null));

        // Add the elements to the container layout
        containerLayout.addView(imageView);
        containerLayout.addView(textView);
        containerLayout.addView(descriptionTextView);
        containerLayout.addView(switchCompat);

        // Create the parent container layout
        ConstraintLayout parentContainer = findViewById(R.id.parent_devicecard);

        // Add the container layout to the parent container
        parentContainer.addView(containerLayout);

        // Set constraints between the elements
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(containerLayout);

        constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16);

        constraintSet.connect(textView.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, 16);
        constraintSet.connect(textView.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.TOP);

        constraintSet.connect(descriptionTextView.getId(), ConstraintSet.START, textView.getId(), ConstraintSet.START);
        constraintSet.connect(descriptionTextView.getId(), ConstraintSet.TOP, textView.getId(), ConstraintSet.BOTTOM, 8);

        constraintSet.connect(switchCompat.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
        constraintSet.connect(switchCompat.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.BOTTOM, 16);

        constraintSet.applyTo(containerLayout);





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
                    String Username=account.getDisplayName();
                    String Mail=account.getEmail();
                    firestore=FirebaseFirestore.getInstance();

                    AggregateQuery countQuery = firestore.collection ("user").whereEqualTo("Email",Mail).count();
                    countQuery.get(AggregateSource.SERVER).addOnCompleteListener(tasks -> {
                    AggregateQuerySnapshot snapshot = tasks.getResult();

                        if(snapshot.getCount()!=0){
                            Toast.makeText(SignUpPage.this, "this acc exist  "+snapshot.getCount(), Toast.LENGTH_SHORT).show();
                                firestore = FirebaseFirestore.getInstance();
                                firestore.collection("user")
                                        .whereEqualTo("Email", Mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (DocumentSnapshot doc : task.getResult()) {
                                                    if (!doc.contains("Houses")) {
                                                        Intent intent = new Intent(getApplicationContext(), Waiting_Room.class);
                                                        finish();
                                                        startActivity(intent);
                                                    }else{
                                                        Intent intent = new Intent(getApplicationContext(), MainDashBoard.class);
                                                        finish();
                                                        startActivity(intent);
                                                    }
                                                }
                                            }
                                        });
                        }else{
                            data_user_list.add(0,Username);
                            data_user_list.add(1,Mail);
                            Toast.makeText( this, "Email : "+Mail+" Username : "+Username, Toast.LENGTH_SHORT).show();
                            HomeActivity();
                        }
                    });
                }
            } catch (ApiException e) {
                Toast.makeText( this, "Cnx Error", Toast.LENGTH_SHORT).show();
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