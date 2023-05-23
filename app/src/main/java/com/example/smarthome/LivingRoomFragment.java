package com.example.smarthome;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

public class LivingRoomFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    GridLayout Devcontrainer;
    AppCompatButton optiondots ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_living_room, container, false);

        Devcontrainer = view.findViewById(R.id.DeviceContainer);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 8;
        layoutParams.topMargin = 8;
        layoutParams.bottomMargin = 8;

// Apply the layoutParams to the child view
        Devcontrainer.setLayoutParams(layoutParams);

        // Inflate the layout for this fragment
        return view;
    }
    public static LivingRoomFragment newInstance() {
        return new LivingRoomFragment();
    }

    public void AddDevice() {
        View viewdevice = getLayoutInflater().inflate(R.layout.device,null);
        optiondots = viewdevice.findViewById(R.id.threedots);
        optiondots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(requireContext(), view, Gravity.END, 0, R.style.PopupMenuStyle);
                popup.inflate(R.menu.houses_threedots);

                // Get the MenuItems from the PopupMenu
                Menu menu = popup.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);

                    // Set the desired text color for the MenuItem
                    SpannableString spannableString = new SpannableString(menuItem.getTitle());
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    menuItem.setTitle(spannableString);
                }

                popup.show();
            }
        });

        Devcontrainer.addView(viewdevice);

    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return true;
    }
}