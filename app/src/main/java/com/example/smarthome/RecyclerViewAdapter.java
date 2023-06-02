package com.example.smarthome;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.data.Smart_Devices;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    ArrayList<Smart_Devices> smart_devices;

    public RecyclerViewAdapter(ArrayList<Smart_Devices> smart_devices) {
        this.smart_devices = smart_devices;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        if(smart_devices.get(position).getState()==0){
//            holder.itemView.setBackgroundColor(Color.parseColor("#f15152"));
//        }else{
//            holder.itemView.setBackgroundColor(Color.parseColor("#a4df66"));
//        }

        holder.textView.setText(smart_devices.get(position).getName());
        holder.Descreption.setText(smart_devices.get(position).getDescription());
        if(smart_devices.get(position).getState()!=0){
            holder.icon.setImageResource(smart_devices.get(position).getIconOn());
        }else{
        holder.icon.setImageResource(smart_devices.get(position).getIconOff());}

        if(smart_devices.get(position).getType()==0){
            holder.State.setVisibility(View.INVISIBLE);
        }else{
            holder.State.setVisibility(View.VISIBLE);
            holder.State.setChecked(smart_devices.get(position).getState()==1 ? true : false);
        }
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_in_house_cards);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        if(smart_devices!=null) {
            return smart_devices.size();
        }else{
            return 0;
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Descreption,textView;
        ImageView icon;
        SwitchCompat State;
        AppCompatButton optiondots;

        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    Log.i("Omaar","position : "+pos);

                    Smart_Devices switched = smart_devices.get(pos);
                    if(switched.getType()!=0){
                        switched.setState(switched.getState()==1? 0:1);
                        HomeFragment.addToFirebase(switched);}
                }
            });
            textView=itemView.findViewById(R.id.textView);
            Descreption=itemView.findViewById(R.id.Descreption);
            icon = itemView.findViewById(R.id.imageView);
            State=itemView.findViewById(R.id.State);

            constraintLayout = itemView.findViewById(R.id.devicelayout);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                        view.getBackground().setColorFilter(null);
                    } else {
                        view.setSelected(true);
                        view.getBackground().setColorFilter(Color.parseColor("#70bf73"), PorterDuff.Mode.SRC_ATOP);
                    }
                }
            });


            optiondots = itemView.findViewById(R.id.threedots);
            optiondots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(itemView.getContext(), itemView.findViewById(R.id.threedots), Gravity.END, 0, R.style.PopupMenuStyle);
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
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            HomeFragment.removeFromFirebase(smart_devices.get(getAdapterPosition()));
                            return false;
                        }
                    });
                }
            });

        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }
}
