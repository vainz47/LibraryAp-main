package com.example.perpus_online;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BukuSkripsiAdapter extends RecyclerView.Adapter<BukuSkripsiAdapter.BukuSkripsiAdapterVh> {

    private ArrayList<BukuSkripsi> skripsiModelList;
    private Context context;
    private SelectedBukuSkripsi selectedBukuSkripsi;

    public BukuSkripsiAdapter(ArrayList<BukuSkripsi> skripsiModelList, Context context, SelectedBukuSkripsi selectedBukuSkripsi) {
        this.skripsiModelList = skripsiModelList;
        this.selectedBukuSkripsi = selectedBukuSkripsi;
        this.context = context;
    }

    @NonNull
    @Override
    public BukuSkripsiAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_buku,parent, false);
        return new BukuSkripsiAdapterVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuSkripsiAdapterVh holder, int position) {
        BukuSkripsi bukuModel = skripsiModelList.get(position);

        String judul = bukuModel.getJudul();
        String keyImage = bukuModel.getImageKey();
        holder.tvJudul.setText(judul);

        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("images/"+keyImage);

        try {
            final File localFile = File.createTempFile(keyImage, "");
            mStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmapImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.imageBuku.setImageBitmap(bitmapImage);
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

    @Override
    public int getItemCount() {
        return skripsiModelList.size();
    }

    public interface SelectedBukuSkripsi{
        void SelectedBukuSkripsi(BukuSkripsi bukuModel);
    }

    public class BukuSkripsiAdapterVh extends RecyclerView.ViewHolder {

        TextView  tvJudul;
        CircleImageView imageBuku;
        public BukuSkripsiAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.judul_row_buku);
            imageBuku = itemView.findViewById(R.id.prefix);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedBukuSkripsi.SelectedBukuSkripsi(skripsiModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
