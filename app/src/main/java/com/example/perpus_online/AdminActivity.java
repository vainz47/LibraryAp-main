package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {
    CardView data_perpus, pengguna;
    TextView username;
    String _USERNAME;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        reference = FirebaseDatabase.getInstance().getReference("users");

        data_perpus = (CardView) findViewById(R.id.data_perpus);
        pengguna = (CardView) findViewById(R.id.pengguna);
        username = (TextView) findViewById(R.id.username_label);

        data_perpus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DataPerpus.class);
                startActivity(intent);
            }
        });

        pengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DataUsers.class);
                startActivity(intent);
            }
        });

        showData();

    }

    private void showData(){
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        username.setText(_USERNAME);
    }
}