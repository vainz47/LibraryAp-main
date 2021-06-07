package com.example.perpus_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class SelectedBukuNovelActivity extends AppCompatActivity {
    TextView judul,genre,pengarang,tahunTerbit,deskripsi,penerbit, status, no_hp;
    Button salin_nomor;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_buku_novel_);

        judul = findViewById(R.id.judul_buku);
        pengarang = findViewById(R.id.Pengarang);
        genre = findViewById(R.id.genre);

        tahunTerbit = findViewById(R.id.tahunterbit);
        deskripsi = findViewById(R.id.deskripsi);
        penerbit = findViewById(R.id.penerbit);
        image = findViewById(R.id.ivNovel);
        status = findViewById(R.id.Keterangan);
        no_hp = findViewById(R.id.number_phone);
        salin_nomor = findViewById(R.id.copy_btn);
        salin_nomor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Number", no_hp.getText().toString());
                clipboard.setPrimaryClip(clip);

                clip.getDescription();

                Toast.makeText(SelectedBukuNovelActivity.this, "Nomor HP sudah disalin", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            BukuNovel bukuModel = (BukuNovel) intent.getSerializableExtra("data");
            judul.setText(bukuModel.getJudul())   ;
            pengarang.setText(bukuModel.getPengarang());
            genre.setText(bukuModel.getGenre());
            tahunTerbit.setText(bukuModel.getTahunterbit());
            deskripsi.setText(bukuModel.getDeskripsi());
            penerbit.setText(bukuModel.getPenerbit());
            status.setText(bukuModel.getStatus());
            StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("images/"+bukuModel.getImageKey());

            try {
                final File localFile = File.createTempFile(bukuModel.getImageKey(), "");
//            Toast.makeText(getApplicationContext(), "Image : "+_IMAGE, Toast.LENGTH_LONG).show();
                mStorageReference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmapImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                image.setImageBitmap(bitmapImage);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}