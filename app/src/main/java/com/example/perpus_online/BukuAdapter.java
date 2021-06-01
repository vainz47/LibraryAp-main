package com.example.perpus_online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.BukuAdapterVh> {

    private ArrayList<Buku> bukuModelList;
    private Context context;
    private SelectedBuku selectedBuku;

    public BukuAdapter(ArrayList<Buku> bukuModelList, SelectedBuku selectedBuku) {
        this.bukuModelList = bukuModelList;
        this.selectedBuku = selectedBuku;
    }

    @NonNull
    @Override
    public BukuAdapter.BukuAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new BukuAdapterVh(LayoutInflater.from(context).inflate(R.layout.row_buku, null));
    }

    @Override
    public void onBindViewHolder(@NonNull BukuAdapter.BukuAdapterVh holder, int position) {
        Buku bukuModel = bukuModelList.get(position);

        String judul = bukuModel.getJudul();
        String prefix = bukuModel.getJudul().substring(0,1);

        holder.tvJudul.setText(judul);
        holder.tvPrefix.setText(prefix);
    }

    @Override
    public int getItemCount() {
        return bukuModelList.size();
    }

    public interface SelectedBuku{
        void selectedBuku(Buku bukuModel);
    }

    public class BukuAdapterVh extends RecyclerView.ViewHolder {

        TextView tvPrefix, tvJudul;
        ImageView imIcon;
        public BukuAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvPrefix = itemView.findViewById(R.id.prefix);
            tvJudul = itemView.findViewById(R.id.judul_row_buku);
            imIcon = itemView.findViewById(R.id.image_row_buku);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedBuku.selectedBuku(bukuModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
