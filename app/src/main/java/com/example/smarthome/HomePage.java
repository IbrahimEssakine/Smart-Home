package com.example.smarthome;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
public class HomePage extends AppCompatActivity {
    TextView UsernameTxt, EmailTxt,PhoneNbr;
    ArrayList<String>data_user=new ArrayList<>();
    Button CreateHouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> user_data_list = bundle.getStringArrayList("UserDataList");

        UsernameTxt = findViewById(R.id.UsernameTxt);
        PhoneNbr = findViewById(R.id.PhoneNbr);
        EmailTxt = findViewById(R.id.Password);
        CreateHouse= findViewById(R.id.CreateHouse);

        UsernameTxt.setText(user_data_list.get(0));
        PhoneNbr.setText(user_data_list.get(1));
        EmailTxt.setText(user_data_list.get(2));

        CreateHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_user.add(0,UsernameTxt.getText().toString());
                data_user.add(1,PhoneNbr.getText().toString());
                data_user.add(2,EmailTxt.getText().toString());
                Intent intent=new Intent(getApplicationContext(), CreateHousePage.class);
                intent.putExtra("UserDataListChange",data_user);
                finish();
                startActivity (intent);
            }
        });
    }
}