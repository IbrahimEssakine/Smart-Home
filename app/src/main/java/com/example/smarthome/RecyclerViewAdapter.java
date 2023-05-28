package com.example.smarthome;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
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
        holder.textView.setText(smart_devices.get(position).getName());
        holder.Descreption.setText(smart_devices.get(position).getDescription());
        holder.icon.setImageResource(smart_devices.get(position).getIcon());
        if(smart_devices.get(position).getType()==0){
            holder.State.setVisibility(View.INVISIBLE);
        }else{
            holder.State.setVisibility(View.VISIBLE);
            holder.State.setChecked(smart_devices.get(position).getState()==1 ? true : false);
        }
    }

    @Override
    public int getItemCount() {
        return smart_devices.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Descreption,textView;
        ImageView icon;
        SwitchCompat State;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            Descreption=itemView.findViewById(R.id.Descreption);
            icon = itemView.findViewById(R.id.imageView);
            State=itemView.findViewById(R.id.State);
        }
    }
}
