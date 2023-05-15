package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

public class MainDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main_dash_board_active);
        replaceFragment(new HomeFragment(),true);
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigationView);
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
                            replaceFragment(new HomeFragment(),false);

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

}