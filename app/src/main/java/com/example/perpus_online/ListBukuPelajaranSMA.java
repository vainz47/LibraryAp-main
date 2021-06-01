package com.example.perpus_online;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ListBukuPelajaranSMA extends ArrayAdapter {

    private Activity mContext;
    List<BukuPelajaranSMA> bukuList;


    public ListBukuPelajaranSMA(Activity mContext, List<BukuPelajaranSMA> bukunya){
        super(mContext, R.layout.list_buku_pelajaran_sma, bukunya);
        this.mContext = mContext;
        this.bukuList = bukunya;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_buku_pelajaran_sma, null, true);

        TextView  judul_buku = listItemView.findViewById(R.id.judul_buku);
        TextView  penerbit = listItemView.findViewById(R.id.Pengarang);
        TextView  tahun_buku = listItemView.findViewById(R.id.tahunterbit);
        CircleImageView imageV = listItemView.findViewById(R.id.profile_image);
        BukuPelajaranSMA buku = bukuList.get(position);

        penerbit.setText(buku.getPengarang());
        judul_buku.setText(buku.getJudul());
        tahun_buku.setText(buku.getTahunterbit());


        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("images/"+buku.getImageKey());

        try {
            final File localFile = File.createTempFile(buku.getImageKey(), "");
//            Toast.makeText(getApplicationContext(), "Image : "+_IMAGE, Toast.LENGTH_LONG).show();
            mStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmapImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageV.setImageBitmap(bitmapImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listItemView;
    }

}
