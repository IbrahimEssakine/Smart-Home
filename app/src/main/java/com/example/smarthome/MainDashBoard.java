package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.ArrayList;


public class MainDashBoard extends AppCompatActivity {
    ArrayList<String> user_info = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board_active);
        user_info= getIntent().getStringArrayListExtra("FromMain");
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigationView);
        FloatingActionButton floatingActionBar = findViewById(R.id.floatingactionbutton);
        floatingActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home);
        replaceFragment(new HomeFragment(),true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int iteq = item.getItemId();
                Log.i("Syndra","item is : "+iteq);
                item.setChecked(true);
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                switch (item.getItemId()) {
                    case R.id.home:
                        floatingActionBar.setVisibility(View.VISIBLE);
                        if (!(currentFragment instanceof HomeFragment))
                        {
                            HomeFragment myHomeFragment = new HomeFragment();
                            replaceFragment(new HomeFragment(),false);

                        }
                        break;
                    case R.id.statistics:
                        StatisticsFragment statisticsFragment = new StatisticsFragment();
                        floatingActionBar.setVisibility(View.INVISIBLE);
                        if ( (currentFragment instanceof ManageFragment) || (currentFragment instanceof SettingsFragment))
                        {
                            replaceFragment(new StatisticsFragment(),false);
                        }else if (!(currentFragment instanceof StatisticsFragment)) {
                            replaceFragment(new StatisticsFragment(),true);
                        }
                        break;
                    case R.id.manage:
                        ManageFragment manageFragment = new ManageFragment();
                        floatingActionBar.setVisibility(View.INVISIBLE);
                        if ((currentFragment instanceof SettingsFragment))
                        {
                            replaceFragment(new ManageFragment(),false);

                        } else if (!(currentFragment instanceof ManageFragment)) {
                            replaceFragment(new ManageFragment(),true);
                        }
                        break;
                    case R.id.settings:

                        SettingsFragment settingsFragment = new SettingsFragment();
                        floatingActionBar.setVisibility(View.INVISIBLE);

                        if (!(currentFragment instanceof SettingsFragment))
                        {
                            replaceFragment(new SettingsFragment(),true);

                        }
                        break;
                    default:
                        HomeFragment myHomeFragment = new HomeFragment();
                        floatingActionBar.setVisibility(View.VISIBLE);

                        replaceFragment(new HomeFragment(),false);
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
        Bundle bundle = new Bundle();

        Log.i("heroMe","youuuuu");
        Log.i("heroMe","  you "+ user_info.get(1));
        bundle.putStringArrayList("user_info",user_info);
        fragment.setArguments(bundle);
        if(trans)
        {
            fragmentTransaction.setCustomAnimations(R.anim.silde_in_right,R.anim.slide_out_left);
        }else {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
        }
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}