package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main_Page extends AppCompatActivity {
    TextView username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        username = findViewById(R.id.getUsername);
        password = findViewById(R.id.getPassword);

        Intent intent = getIntent();
        String usernameS = intent.getStringExtra("username");
        String passwordS = intent.getStringExtra("password");

        username.setText(usernameS);
        password.setText(passwordS);


    }
}