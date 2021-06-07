package com.example.perpus_online;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragmentHome extends Fragment implements BukuAdapter.SelectedBuku, BukuSkripsiAdapter.SelectedBukuSkripsi, BukuNovelAdapter.SelectedBukuNovel, IlmiahAdapter.SelectedBukuIlmiah {

    RecyclerView recyclerView, recyclerView2 , recyclerView3, recyclerView4, recyclerView5, recyclerView6;
    ArrayList<Buku> bukuArrayList, bukuArrayList2, bukuArrayList3;
    ArrayList<BukuSkripsi> bukuSkripsiArrayList;
    ArrayList<BukuNovel> bukuNovelArrayList;
    ArrayList<Ilmiah> ilmiahArrayList;
    BukuAdapter bukuAdapter, bukuSMPAdapter, bukuSMAAdapter;
    BukuSkripsiAdapter bukuSkripsiAdapter;
    BukuNovelAdapter bukuNovelAdapter;
    IlmiahAdapter ilmiahAdapter;

    FirebaseDatabase mFirebaseInstance;
    DatabaseReference DBReference, DBReference2, DBReference3, DBReference4, DBReference5, DBReference6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.layout_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerviewBuku);
        recyclerView2 = root.findViewById(R.id.recyclerviewBuku2);
        recyclerView3 = root.findViewById(R.id.recyclerviewBuku3);
        recyclerView4 = root.findViewById(R.id.recyclerviewBuku4);
        recyclerView5 = root.findViewById(R.id.recyclerviewBuku5);
        recyclerView6 = root.findViewById(R.id.recyclerviewBuku6);

        FirebaseApp.initializeApp(root.getContext());
        mFirebaseInstance = FirebaseDatabase.getInstance();
        DBReference = mFirebaseInstance.getReference("buku");
        DBReference2 = mFirebaseInstance.getReference("bukuPelajaranSMP");
        DBReference3 = mFirebaseInstance.getReference("bukuPelajaranSMA");
        DBReference4 = mFirebaseInstance.getReference("karyaBukuSkripsi");
        DBReference5 = mFirebaseInstance.getReference("bukuNovel");
        DBReference6 = mFirebaseInstance.getReference("karyaIlmiah");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView5.setHasFixedSize(true);
        recyclerView5.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView6.setHasFixedSize(true);
        recyclerView6.setLayoutManager(new LinearLayoutManager(root.getContext()));

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager2 =new LinearLayoutManager(root.getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager3 =new LinearLayoutManager(root.getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager4 =new LinearLayoutManager(root.getContext());
        linearLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager5 =new LinearLayoutManager(root.getContext());
        linearLayoutManager5.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager6 =new LinearLayoutManager(root.getContext());
        linearLayoutManager6.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView3.setLayoutManager(linearLayoutManager3);
        recyclerView4.setLayoutManager(linearLayoutManager4);
        recyclerView5.setLayoutManager(linearLayoutManager5);
        recyclerView6.setLayoutManager(linearLayoutManager6);

        bukuArrayList = new ArrayList<>();
        bukuArrayList2 = new ArrayList<>();
        bukuArrayList3 = new ArrayList<>();
        bukuSkripsiArrayList = new ArrayList<>();
        bukuNovelArrayList = new ArrayList<>();
        ilmiahArrayList = new ArrayList<>();

        bukuAdapter = new BukuAdapter(bukuArrayList, root.getContext(), this);
        bukuSMPAdapter = new BukuAdapter(bukuArrayList2, root.getContext(), this);
        bukuSMAAdapter = new BukuAdapter(bukuArrayList3, root.getContext(), this);
        bukuSkripsiAdapter = new BukuSkripsiAdapter(bukuSkripsiArrayList, root.getContext(), this);
        bukuNovelAdapter = new BukuNovelAdapter(bukuNovelArrayList, root.getContext(), this);
        ilmiahAdapter = new IlmiahAdapter(ilmiahArrayList, root.getContext(), this);

        recyclerView.setAdapter(bukuAdapter);
        recyclerView2.setAdapter(bukuSMPAdapter);
        recyclerView3.setAdapter(bukuSMAAdapter);
        recyclerView4.setAdapter(bukuNovelAdapter);
        recyclerView5.setAdapter(bukuSkripsiAdapter);
        recyclerView6.setAdapter(ilmiahAdapter);

        //buku get database
        DBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Buku bukuDB = snap.getValue(Buku.class);
                    bukuDB.setKode(snap.getKey());
                    bukuArrayList.add(bukuDB);
                    System.out.println("BUKU : " + bukuDB.getJudul());
                }
                bukuAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        //buku smp get database
        DBReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Buku bukuDB = snap.getValue(Buku.class);
                    bukuDB.setKode(snap.getKey());
                    bukuArrayList2.add(bukuDB);
                    System.out.println("BUKU : " + bukuDB.getJudul());
                }
                bukuSMPAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        //buku sma get database
        DBReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Buku bukuDB = snap.getValue(Buku.class);
                    bukuDB.setKode(snap.getKey());
                    bukuArrayList3.add(bukuDB);
                    System.out.println("BUKU SMA: " + bukuDB.getJudul());
                }
                bukuSMAAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        //skripsi get database
        DBReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    BukuSkripsi bukuDB = snap.getValue(BukuSkripsi.class);
                    bukuDB.setKode(snap.getKey());
                    bukuSkripsiArrayList.add(bukuDB);
                    System.out.println("Skripsi: " + bukuDB.getJudul());
                }
                bukuSkripsiAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        //Novel get database
        DBReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    BukuNovel bukuDB = snap.getValue(BukuNovel.class);
                    bukuDB.setKode(snap.getKey());
                    bukuNovelArrayList.add(bukuDB);
                    System.out.println("Skripsi: " + bukuDB.getJudul());
                }
                bukuNovelAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        //ilmiah get database
        DBReference6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Ilmiah bukuDB = snap.getValue(Ilmiah.class);
                    bukuDB.setKode(snap.getKey());
                    ilmiahArrayList.add(bukuDB);
                    System.out.println("Skripsi: " + bukuDB.getJudul());
                }
                ilmiahAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        return root;
    }

    @Override
    public void selectedBuku(Buku bukuModel) {
        startActivity(new Intent(getActivity(), SelectedBukuActivity.class).putExtra("data", bukuModel));
    }
    @Override
    public void SelectedBukuSkripsi(BukuSkripsi bukuModel) {
        startActivity(new Intent(getActivity(), SelectedBukuSkripsiActivity.class).putExtra("data", bukuModel));
    }
    @Override
    public void SelectedBukuNovel(BukuNovel bukuModel) {
        startActivity(new Intent(getActivity(), SelectedBukuNovelActivity.class).putExtra("data", bukuModel));
    }
    @Override
    public void SelectedBukuIlmiah(Ilmiah bukuModel) {
        startActivity(new Intent(getActivity(), SelectedBukuIlmiahActivity.class).putExtra("data", bukuModel));
    }
}
