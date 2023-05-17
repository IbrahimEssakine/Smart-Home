package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class MainDashBoard extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseFirestore firestore;
    TextView Username;
    ImageView appCompatImageView;
    ArrayList<String> user_data= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gso=new GoogleSignInOptions. Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        setContentView(R.layout.activity_main_dash_board_active);
        Bundle data=new Bundle();

        //Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home);


        //appCompatImageView = fragment.getView().findViewById(R.id.appCompatImageView);
        //Username.setText(SignIn());

        replaceFragment(new HomeFragment(),true);
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigationView);
        FloatingActionButton bottomAppBar = findViewById(R.id.floatingactionbutton);
        bottomAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String iteq = item.getTitle().toString();
                item.setChecked(true);
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                Fragment ManFrag = getSupportFragmentManager().findFragmentById(R.id.manage);
                switch (item.getItemId()) {
                    case R.id.home:
                        if (!(currentFragment instanceof HomeFragment))
                        {

                            HomeFragment myHomeFragment = new HomeFragment();
                            data.putStringArrayList("info",user_data);
                            replaceFragment(new HomeFragment(),false);
//                            SignIn();
//                            openDialog();

                        }
                        break;
                    case R.id.statistics:
                        if ( (currentFragment instanceof ManageFragment) || (currentFragment instanceof SettingsFragment))
                        {

                            replaceFragment(new StatisticsFragment(),false);

                        }else if (!(currentFragment instanceof StatisticsFragment)) {
                            replaceFragment(new StatisticsFragment(),true);
                        }
                        break;
                    case R.id.manage:
                        if ((currentFragment instanceof SettingsFragment))
                        {
                            replaceFragment(new ManageFragment(),false);

                        } else if (!(currentFragment instanceof ManageFragment)) {
                            replaceFragment(new ManageFragment(),true);
                        }
                        break;
                    case R.id.settings:
                        if (!(currentFragment instanceof SettingsFragment))
                        {
                            replaceFragment(new SettingsFragment(),true);

                        }
                        break;
                }
                return true;
            }
        });
    }
    public void openDialog(){
        Dialog_Test dialog_test = new Dialog_Test();
        dialog_test.show(getSupportFragmentManager(),"Example dialog");
    }
    private void replaceFragment(Fragment fragment , boolean trans ) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(trans)
        {
            fragmentTransaction.setCustomAnimations(R.anim.silde_in_right,R.anim.slide_out_left);
        }else {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
        }
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
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
                                        if (!doc.contains("Houses")) {
                                            Intent intent = new Intent(getApplicationContext(), Waiting_Room.class);
                                            finish();
                                            startActivity(intent);
                                        }else{
//                                            for (ArrayList house : doc.get("Houses").) {
//
//                                            }
//                                            Log.i("MO3MO3AZEAZE",doc.get("Houses").toString());
                                            user_data.add(0,doc.get("Email").toString());                                            user_data.add(0,doc.get("Email").toString());
                                            user_data.add(1,doc.get("ID").toString());
                                            user_data.add(2,doc.get("Password").toString());
                                            user_data.add(3,doc.get("Phone").toString());
                                            user_data.add(4,doc.get("Username").toString());
                                            user_data.add(5,doc.get("Houses").toString());

                                            //user_data.add(1,doc.getData().toString());
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

}