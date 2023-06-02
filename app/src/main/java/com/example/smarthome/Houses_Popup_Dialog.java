package com.example.smarthome;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Houses_Popup_Dialog extends AppCompatDialogFragment {

    private List<String> dataList; // List to hold the data for GridView
    private CustomAdapter adapter; // Adapter for GridView

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_houses_popup_dialog, null);

        GridView gridView = view.findViewById(R.id.gridView1);

        // Initialize dataList with initial data from the database
        dataList = fetchDataFromDatabase();

        // Create the adapter with the dataList
        adapter = new CustomAdapter(getActivity(), android.R.layout.simple_list_item_1, dataList);

        // Set the adapter to the GridView
        gridView.setAdapter(adapter);




        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the selection action
                    }
                });

        return builder.create();
    }

    private List<String> fetchDataFromDatabase() {
        // Retrieve data from the database and return as a List<String>
        // Example:
        List<String> data = new ArrayList<>();
        data.add("House 1");
        data.add("House 2");
        data.add("House 3");
        data.add("House 4");
        data.add("House 5");
        data.add("House 6");
        data.add("House 7");
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_layout, parent, false);
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



}
