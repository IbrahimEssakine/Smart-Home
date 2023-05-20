package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.ListPopupWindowCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<String> dataOfuser = new ArrayList<String>();
    ArrayList<String> user_houses= new ArrayList<String>();
    TextView Username,test;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    FirebaseFirestore firestore;
    ImageView userImage;
    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoCompleteTextView;
    TextInputLayout button_menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        gso=new GoogleSignInOptions. Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(getContext().getApplicationContext(),gso);
        Username=view.findViewById(R.id.UsernameTxt);
        userImage=view.findViewById(R.id.appCompatImageView);
        button_menu=view.findViewById(R.id.button_menu);
        test=view.findViewById(R.id.DevicesTxt);
        autoCompleteTextView = view.findViewById(R.id.items);

        Toast.makeText(getActivity().getApplicationContext(), "in frag if", Toast.LENGTH_SHORT).show();


        SignIn();
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
            autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    firestore.collection("House").document(user_houses.get(0).toString())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    Toast.makeText(getContext().getApplicationContext(), "here your house name : " + document.get("Name"), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

        return view;
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
                                        Log.i("KHALID","Doc Email : "+doc.get("Email").toString());
                                        Username.setText(dataOfuser.get(4).toString());
                                                if (dataOfuser.get(5) != "No") {
                                                    Picasso.get().load(dataOfuser.get(5)).into(userImage);
                                                } else {
                                                    userImage.setImageResource(R.drawable.user_image);}
                                    autoCompleteTextView.setText(user_houses.get(0).toString());
                                    }
                                }
                            });
                }
            } catch (ApiException e) {
                Toast.makeText( getContext().getApplicationContext(), "Cnx Error", Toast.LENGTH_SHORT).show();
            }
            adapterItems = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_items, user_houses);
            autoCompleteTextView.setAdapter(adapterItems);
            Toast.makeText(getContext().getApplicationContext(), "after adding data", Toast.LENGTH_SHORT).show();
        }
    }
}