package com.example.perpus_online;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.BukuAdapterVh> {

    private ArrayList<Buku> bukuModelList;
    private Context context;
    private SelectedBuku selectedBuku;

    public BukuAdapter(ArrayList<Buku> bukuModelList, Context context, SelectedBuku selectedBuku) {
        this.bukuModelList = bukuModelList;
        this.selectedBuku = selectedBuku;
        this.context = context;
    }

    @NonNull
    @Override
    public BukuAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_buku,parent, false);
        return new BukuAdapterVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuAdapterVh holder, int position) {
        Buku bukuModel = bukuModelList.get(position);

        String judul = bukuModel.getJudul();
        holder.tvJudul.setText(judul);
    }

    @Override
    public int getItemCount() {
        return bukuModelList.size();
    }

    public interface SelectedBuku{
        void selectedBuku(Buku bukuModel);
    }

    public class BukuAdapterVh extends RecyclerView.ViewHolder {

        TextView  tvJudul;
        CircleImageView imageBuku;
        public BukuAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.judul_row_buku);
            imageBuku = itemView.findViewById(R.id.prefix);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedBuku.selectedBuku(bukuModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
