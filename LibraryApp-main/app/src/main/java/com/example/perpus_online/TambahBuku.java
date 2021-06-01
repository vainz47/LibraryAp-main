    package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class TambahBuku extends AppCompatActivity {

    TextInputLayout regJudul, regPengarang, regPenerbit, regTahun, regDesc;
    Button tambah_data_btn;

    //Firebase Database
    DatabaseReference bukuDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_buku);

        regJudul = findViewById(R.id.regJudul);
        regPengarang = findViewById(R.id.regPengarang);
        regPenerbit = findViewById(R.id.regPenerbit);
        regTahun = findViewById(R.id.regTahun);
        regDesc = findViewById(R.id.regDesc);
        tambah_data_btn = (Button) findViewById(R.id.tambah_data_btn);

        tambah_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukuDBRef = FirebaseDatabase.getInstance().getReference().child("buku");

                insertDataBuku();

            }
        });

    }

    private void insertDataBuku() {
        // Get Value
        String judul = regJudul.getEditText().getText().toString();
        String pengarang = regPengarang.getEditText().getText().toString();
        String penerbit = regPenerbit.getEditText().getText().toString();
        String tahun = regTahun.getEditText().getText().toString();
        String desc = regDesc.getEditText().getText().toString();

        String peminjam = "none";
        Buku dataBuku = new Buku(judul, pengarang, penerbit, tahun, desc, peminjam);

        bukuDBRef.push().setValue(dataBuku);
        Toast.makeText(TambahBuku.this, "Data Berhasil ditambahkan bun!", Toast.LENGTH_LONG).show();
        finish();
    }

    private Boolean valJudul() {
        String val = regJudul.getEditText().getText().toString();

        if (val.isEmpty()) {
            regJudul.setError("Tolong bun judulnya diisi");
            return false;
        } else {
            regJudul.setError(null);
            regJudul.setErrorEnabled(false);
            return true;
        }
    }

}