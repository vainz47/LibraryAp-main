package com.example.perpus_online;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ListBukuSkripsi extends ArrayAdapter {

    private Activity mContext;
    List<BukuSkripsi> bukuList;


    public ListBukuSkripsi(Activity mContext, List<BukuSkripsi> bukunya){
        super(mContext, R.layout.list_buku_skripsi, bukunya);
        this.mContext = mContext;
        this.bukuList = bukunya;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_buku_skripsi, null, true);

        TextView  judul_buku = listItemView.findViewById(R.id.judul_buku);
        TextView  penerbit = listItemView.findViewById(R.id.Pengarang);
        TextView  tahun_buku = listItemView.findViewById(R.id.tahunterbit);

        BukuSkripsi buku = bukuList.get(position);
        penerbit.setText(buku.getPengarang());
        judul_buku.setText(buku.getJudul());
        tahun_buku.setText(buku.getTahunterbit());

        return listItemView;
    }
}
