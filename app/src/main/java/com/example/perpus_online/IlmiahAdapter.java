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

public class IlmiahAdapter extends RecyclerView.Adapter<IlmiahAdapter.IlmiahAdapterVh> {

    private ArrayList<Ilmiah> ilmiahModelList;
    private Context context;
    private SelectedBukuIlmiah selectedBukuIlmiah;

    public IlmiahAdapter(ArrayList<Ilmiah> ilmiahModelList, Context context, SelectedBukuIlmiah selectedBukuIlmiah) {
        this.ilmiahModelList = ilmiahModelList;
        this.selectedBukuIlmiah = selectedBukuIlmiah;
        this.context = context;
    }

    @NonNull
    @Override
    public IlmiahAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_buku,parent, false);
        return new IlmiahAdapterVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IlmiahAdapterVh holder, int position) {
        Ilmiah bukuModel = ilmiahModelList.get(position);

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
        return ilmiahModelList.size();
    }

    public interface SelectedBukuIlmiah{
        void SelectedBukuIlmiah(Ilmiah bukuModel);
    }

    public class IlmiahAdapterVh extends RecyclerView.ViewHolder {

        TextView  tvJudul;
        CircleImageView imageBuku;
        public IlmiahAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.judul_row_buku);
            imageBuku = itemView.findViewById(R.id.prefix);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedBukuIlmiah.SelectedBukuIlmiah(ilmiahModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
