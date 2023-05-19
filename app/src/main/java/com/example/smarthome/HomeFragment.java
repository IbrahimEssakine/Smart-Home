package com.example.smarthome;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import  androidx.fragment.app.FragmentActivity;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    AppCompatButton liviingroomButton;
    AppCompatButton kitchenButton;
    AppCompatButton bedroomButton;
    AppCompatButton bathroomButton;
    AppCompatButton changeshousesbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        replaceFragment(new LivingRoomFragment());

        liviingroomButton = view.findViewById(R.id.button_1);
        kitchenButton = view.findViewById(R.id.button_2);
        bedroomButton    = view.findViewById(R.id.button_3);
        bathroomButton = view.findViewById(R.id.button_4);
        changeshousesbutton = view.findViewById(R.id.houses_button_menu);
        changeshousesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(requireContext(), view, Gravity.END, 0, R.style.PopupMenuStyle);
                popup.inflate(R.menu.houses_popup_menu);

                // Get the MenuItems from the PopupMenu
                Menu menu = popup.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);

                    // Set the desired text color for the MenuItem
                    SpannableString spannableString = new SpannableString(menuItem.getTitle());
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.blue_main)), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    menuItem.setTitle(spannableString);
                }

                popup.show();
            }
        });


        liviingroomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new LivingRoomFragment());
            }
        });
        kitchenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new KitchenFragment());
            }
        });


        return view;
    }
    private void replaceFragment(Fragment fragment ) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.DeviceFrame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
//        Log.i("menutitle","menuTitle"+menuItem.getTitle());
//        changeshousesbutton.setText(menuItem.getTitle());


        return true;
    }

}