package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManajemenNovel extends AppCompatActivity {

    Button tambah_novel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajemen_novel);

        tambah_novel_btn = findViewById(R.id.tambah_novel_btn);

        tambah_novel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManajemenNovel.this, MenuNovel.class);
                startActivity(intent);
            }
        });
    }
}