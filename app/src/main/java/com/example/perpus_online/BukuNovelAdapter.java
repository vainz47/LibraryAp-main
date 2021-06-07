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

public class BukuNovelAdapter extends RecyclerView.Adapter<BukuNovelAdapter.BukuNovelAdapterVh> {

    private ArrayList<BukuNovel> novelModelList;
    private Context context;
    private SelectedBukuNovel selectedBukuNovel;

    public BukuNovelAdapter(ArrayList<BukuNovel> novelModelList, Context context, SelectedBukuNovel selectedBukuNovel) {
        this.novelModelList = novelModelList;
        this.selectedBukuNovel = selectedBukuNovel;
        this.context = context;
    }

    @NonNull
    @Override
    public BukuNovelAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_buku,parent, false);
        return new BukuNovelAdapterVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuNovelAdapterVh holder, int position) {
        BukuNovel bukuModel = novelModelList.get(position);

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
        return novelModelList.size();
    }

    public interface SelectedBukuNovel{
        void SelectedBukuNovel(BukuNovel bukuModel);
    }

    public class BukuNovelAdapterVh extends RecyclerView.ViewHolder {

        TextView  tvJudul;
        CircleImageView imageBuku;
        public BukuNovelAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.judul_row_buku);
            imageBuku = itemView.findViewById(R.id.prefix);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedBukuNovel.SelectedBukuNovel(novelModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
