package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SelectedBukuActivity extends AppCompatActivity {
    TextView tvJudul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_buku);

        tvJudul = findViewById(R.id.selected_buku);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            Buku bukuModel = (Buku) intent.getSerializableExtra("data");
            tvJudul.setText(bukuModel.getJudul())   ;
        }

    }
}