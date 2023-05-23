package com.example.smarthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    ArrayList<String> dataOfuser = new ArrayList<String>();
    ArrayList<String> user_houses= new ArrayList<String>();
    Menu menu ;
    FirebaseFirestore firestore;
    PopupMenu popup ;
    //ArrayAdapter<String> adapterItems;
    //AutoCompleteTextView autoCompleteTextView;
    ImageView userImage;
    TextView Username,test;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
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
        gso=new GoogleSignInOptions. Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(getContext().getApplicationContext(),gso);
        replaceFragment(new LivingRoomFragment());
        liviingroomButton = view.findViewById(R.id.button_1);
        kitchenButton = view.findViewById(R.id.button_2);
        bedroomButton    = view.findViewById(R.id.button_3);
        bathroomButton = view.findViewById(R.id.button_4);
        changeshousesbutton = view.findViewById(R.id.houses_button_menu);
        Username=view.findViewById(R.id.UsernameTxt);
        userImage=view.findViewById(R.id.appCompatImageView);
        SignIn();

        popup = new PopupMenu(requireContext(), view, Gravity.END, 0, R.style.PopupMenuStyle);
        popup.inflate(R.menu.houses_popup_menu);
        changeshousesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup = new PopupMenu(requireContext(), view, Gravity.END, 0, R.style.PopupMenuStyle);
                popup.inflate(R.menu.houses_popup_menu);
                    for (int i = 0; i < user_houses.size(); i++) {
                        popup.getMenu().add(1, 1, 1, user_houses.get(i).toString());
                    }

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

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    changeshousesbutton.setText(item.getTitle());
                    Log.i("ionia","in clicked");
                    return true;
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
        changeshousesbutton.setText(menuItem.getTitle());
//        Log.i("menutitle","menuTitle"+menuItem.getTitle());
//        changeshousesbutton.setText(menuItem.getTitle());


        return true;
    }
    private String SignIn() {
        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent, 100);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext().getApplicationContext());
        String Mail = account.getEmail();
        return Mail;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode==100) {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult (ApiException.class);
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext().getApplicationContext());
                if (account!=null) {
                    String Mail = account.getEmail();
                    firestore = FirebaseFirestore.getInstance();
                    firestore.collection("user")
                            .whereEqualTo("Email", Mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot doc : task.getResult()) {
                                        if(doc.exists()){
                                            Log.i("Singed","in doc");
                                            user_houses= (ArrayList<String>) doc.get("Houses");
                                            if(user_houses!=null){
                                                changeshousesbutton.setText(user_houses.get(0).toString());
                                            }
                                            dataOfuser.add(0,doc.get("Email").toString());
                                            dataOfuser.add(1,doc.get("ID").toString());
                                            dataOfuser.add(2,doc.get("Password").toString());
                                            dataOfuser.add(3,doc.get("Phone").toString());
                                            dataOfuser.add(4,doc.get("Username").toString());
                                            Log.i("garen","in activity");
                                            try {
                                                dataOfuser.add(5,account.getPhotoUrl().toString());
                                            }catch (Exception e){
                                                dataOfuser.add(5,"No");
                                            }
                                        }
                                        Log.i("KHALIDHH","Doc Email : "+doc.get("Email").toString());
                                        Username.setText(dataOfuser.get(4).toString());
                                        if (dataOfuser.get(5) != "No") {
                                            Picasso.get().load(dataOfuser.get(5)).into(userImage);
                                        } else {
                                            userImage.setImageResource(R.drawable.user_image);}
                                        //autoCompleteTextView.setText(user_houses.get(0).toString());
                                    }
                                }
                            });
                }
            } catch (ApiException e) {
                Toast.makeText( getContext().getApplicationContext(), "Cnx Error", Toast.LENGTH_SHORT).show();
            }
            //adapterItems = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_items, user_houses);
            //autoCompleteTextView.setAdapter(adapterItems);
            Toast.makeText(getContext().getApplicationContext(), "after adding data", Toast.LENGTH_SHORT).show();
        }
    }
}