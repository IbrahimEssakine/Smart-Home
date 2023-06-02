package com.example.smarthome;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ManageFragment extends Fragment {

    AppCompatButton HousePopupList;

    private List<String> dataList; // List to hold the data for GridView
    private CustomAdapter_user adapter_user; // Adapter for GridView
    private CustomAdapter_hex adapter_hex; // Adapter for GridView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage, container, false);
        HousePopupList = view.findViewById(R.id.ShowHouses);
        HousePopupList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        RecyclerView recyclerView_hex = view.findViewById(R.id.recyclerView_hex);

// Initialize dataList with initial data from the database
        dataList = fetchDataFromDatabase();

// Set the layout manager
        GridLayoutManager layoutManager_hex = new GridLayoutManager(getActivity(), 1);
        recyclerView_hex.setLayoutManager(layoutManager_hex);

// Create the adapter with the dataList
        adapter_hex = new CustomAdapter_hex(getActivity(), R.layout.grid_item_layout_hex, dataList);
        recyclerView_hex.setAdapter(adapter_hex);

//********************************************************************

        RecyclerView recyclerView_user = view.findViewById(R.id.recyclerView_user);

//        // Initialize dataList with initial data from the database
        dataList = fetchDataFromDatabase();

// Set the layout manager
        GridLayoutManager layoutManager_user = new GridLayoutManager(getActivity(), 1);
        recyclerView_user.setLayoutManager(layoutManager_user);

// Create the adapter with the dataList
        adapter_user = new CustomAdapter_user(getActivity(), R.layout.grid_item_layout_user, dataList);
        recyclerView_user.setAdapter(adapter_user);


        return view;
    }
    public void openDialog(){
        Houses_Popup_Dialog houses_popup_dialog = new Houses_Popup_Dialog();
        houses_popup_dialog.show(getFragmentManager(),"Example dialog");
    }
    private List<String> fetchDataFromDatabase() {
        // Retrieve data from the database and return as a List<String>
        // Example:
        List<String> data = new ArrayList<>();
        data.add("Mouad");
        data.add("Ibrahim");
        data.add("Omar");
        data.add("Ikram");
        data.add("Souhaib");
        data.add("Anouar");
        data.add("Ouanan");
        return data;
    }


private class CustomAdapter_hex extends RecyclerView.Adapter<CustomAdapter_hex.ViewHolder> {
        // Your existing code...

        private int resourceId;
        private int selectedItem = -1;

        public CustomAdapter_hex(Context context, int resourceId, List<String> dataList) {
            this.resourceId = resourceId;
        }

        public void setSelectedItem(int position) {
            selectedItem = position;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String dataItem = dataList.get(position);
            holder.itemTextView.setText(dataItem);

            if (position == selectedItem) {
                holder.itemLayout.setBackgroundColor(Color.parseColor("#a4b6ce"));
            } else {
                holder.itemLayout.setBackgroundColor(Color.WHITE);
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            LinearLayout itemLayout;
            TextView itemTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemLayout = itemView.findViewById(R.id.itemLayout);
                itemTextView = itemView.findViewById(R.id.itemTextView);

                itemLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                setSelectedItem(getAdapterPosition());
            }
        }
    }


private class CustomAdapter_user extends RecyclerView.Adapter<CustomAdapter_user.ViewHolder> {
    // Your existing code...

    private int resourceId;
    private int selectedItem = -1;

    public CustomAdapter_user(Context context, int resourceId, List<String> dataList) {
        this.resourceId = resourceId;
    }

    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String dataItem = dataList.get(position);
        holder.itemTextView.setText(dataItem);

        if (position == selectedItem) {
            holder.itemLayout.setBackgroundColor(Color.parseColor("#a4b6ce"));
        } else {
            holder.itemLayout.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout itemLayout;
        TextView itemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            itemTextView = itemView.findViewById(R.id.itemTextView);

            itemLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            setSelectedItem(getAdapterPosition());
        }
    }
}








}