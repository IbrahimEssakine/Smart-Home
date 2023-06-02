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
    AppCompatButton HousePopupList_user;

    private List<String> dataList; // List to hold the data for GridView
    private CustomAdapter adapter; // Adapter for GridView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage, container, false);
        HousePopupList = view.findViewById(R.id.ShowHouses);
        HousePopupList_user = view.findViewById(R.id.ShowHouses_user);
        HousePopupList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        HousePopupList_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


        GridView gridView = view.findViewById(R.id.gridView_user);

        // Initialize dataList with initial data from the database
        dataList = fetchDataFromDatabase();

        // Create the adapter with the dataList
        adapter = new CustomAdapter(getActivity(), android.R.layout.simple_list_item_1, dataList);

        // Set the adapter to the GridView
        gridView.setAdapter(adapter);


//
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_user);
//
//// Set the layout manager
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
//        recyclerView.setLayoutManager(layoutManager);
//
//// Create the adapter with the dataList
//        adapter = new CustomAdapter(getActivity(), R.layout.grid_item_layout_user, dataList);
//        recyclerView.setAdapter(adapter);


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




    private class CustomAdapter extends ArrayAdapter<String> {
        private int resourceId;
        private int selectedItem = -1;

        public CustomAdapter(Context context, int resourceId, List<String> dataList) {
            super(context, resourceId, dataList);
            this.resourceId = resourceId;
        }

        public void setSelectedItem(int position) {
            selectedItem = position;
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_layout_user, parent, false);
            }

            LinearLayout itemLayout = convertView.findViewById(R.id.itemLayout);
            TextView itemTextView = convertView.findViewById(R.id.itemTextView);
            ImageView imageView = convertView.findViewById(R.id.textdivider);

            String dataItem = getItem(position);
            itemTextView.setText(dataItem);
            itemLayout.setClickable(true);

            if (position == selectedItem) {
                itemLayout.setBackgroundColor(Color.parseColor("#a4b6ce"));
            } else {
                itemLayout.setBackgroundColor(Color.WHITE);
            }

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectedItem(position);
                }
            });

            return convertView;
        }
        @Override
        public boolean isEnabled(int position) {
            // Enable selection on grid items
            return true;
        }

    }

//
//private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
//    // Your existing code...
//
//    private int resourceId;
//        private int selectedItem = -1;
//
//        public CustomAdapter(Context context, int resourceId, List<String> dataList) {
//            super(context, resourceId, dataList);
//            this.resourceId = resourceId;
//        }
//
//        public void setSelectedItem(int position) {
//            selectedItem = position;
//            notifyDataSetChanged();
//        }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        String dataItem = getItem(position);
//        holder.itemTextView.setText(dataItem);
//
//        if (position == selectedItem) {
//            holder.itemLayout.setBackgroundColor(Color.parseColor("#a4b6ce"));
//        } else {
//            holder.itemLayout.setBackgroundColor(Color.WHITE);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        LinearLayout itemLayout;
//        TextView itemTextView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemLayout = itemView.findViewById(R.id.itemLayout);
//            itemTextView = itemView.findViewById(R.id.itemTextView);
//
//            itemLayout.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            setSelectedItem(getAdapterPosition());
//        }
//    }
//}








}