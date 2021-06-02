package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DataPerpus extends AppCompatActivity {

    CardView dataBuku, dataNovel, dataIlmiah, dataSkripsi, dataSMA, dataSMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_perpus);

        dataBuku = (CardView) findViewById(R.id.book);
        dataNovel = (CardView) findViewById(R.id.novel);
        dataSkripsi = (CardView) findViewById(R.id.skripsi);
        dataIlmiah = (CardView) findViewById(R.id.ilmiah);
        dataSMA =  (CardView) findViewById(R.id.sma);
        dataSMP = (CardView) findViewById(R.id.smp);

        dataBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataPerpus.this, MenuBuku.class);
                startActivity(intent);
            }
        });

        dataSkripsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataPerpus.this, MenuBukuSkripsi.class);
                startActivity(intent);
            }
        });

        dataIlmiah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataPerpus.this, MenuBukuKaryaIlmiah.class);
                startActivity(intent);
            }
        });

        dataSMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataPerpus.this, MenuBukuPelajaranSMP.class);
                startActivity(intent);
            }
        });
        dataSMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataPerpus.this, MenuBukuPelajaranSMA.class);
                startActivity(intent);
            }
        });

        dataNovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataPerpus.this, MenuBukuNovel.class);
                startActivity(intent);
            }
        });
    }
}