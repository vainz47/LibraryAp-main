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


public class ListBukuNovel extends ArrayAdapter {

    private Activity mContext;
    List<BukuNovel> bukuList;


    public ListBukuNovel(Activity mContext, List<BukuNovel> bukunya){
        super(mContext, R.layout.list_buku, bukunya);
        this.mContext = mContext;
        this.bukuList = bukunya;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_buku_novel, null, true);

        TextView  judul_buku = listItemView.findViewById(R.id.judul_buku);
        TextView  penerbit = listItemView.findViewById(R.id.Pengarang);
        TextView  tahun_buku = listItemView.findViewById(R.id.tahunterbit);

        BukuNovel buku = bukuList.get(position);

        penerbit.setText(buku.getPengarang());
        judul_buku.setText(buku.getJudul());
        tahun_buku.setText(buku.getTahunterbit());

        return listItemView;
    }
}
