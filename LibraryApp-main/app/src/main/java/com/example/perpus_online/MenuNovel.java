package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuNovel extends AppCompatActivity {

    Button tambah_novel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_novel);

        tambah_novel = (Button) findViewById(R.id.tambah_novel_btn);

        tambah_novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuNovel.this, TambahNovel.class);
                startActivity(intent);
            }
        });
    }
}