package com.example.perpus_online;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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


public class FragmentHome extends Fragment implements BukuAdapter.SelectedBuku {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView, recyclerView2 , recyclerView3;
    Toolbar toolbar;
    ArrayList<Buku> bukuArrayList, bukuArrayList2, bukuArrayList3;
    BukuAdapter bukuAdapter, bukuSMPAdapter, bukuSMAAdapter;

    FirebaseDatabase mFirebaseInstance;
    DatabaseReference DBReference, DBReference2, DBReference3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.layout_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerviewBuku);
        FirebaseApp.initializeApp(root.getContext());
        mFirebaseInstance = FirebaseDatabase.getInstance();
        DBReference = mFirebaseInstance.getReference("buku");
        DBReference2 = mFirebaseInstance.getReference("bukuPelajaranSMP");
        DBReference3 = mFirebaseInstance.getReference("bukuPelajaranSMA");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        toolbar = root.findViewById(R.id.toolbar);
        recyclerView = root.findViewById(R.id.recyclerviewBuku);
        recyclerView2 = root.findViewById(R.id.recyclerviewBuku2);
        recyclerView3 = root.findViewById(R.id.recyclerviewBuku3);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager2 =new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager3 =new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView3.setLayoutManager(linearLayoutManager3);

        bukuArrayList = new ArrayList<>();
        bukuArrayList2 = new ArrayList<>();
        bukuArrayList3 = new ArrayList<>();
        bukuAdapter = new BukuAdapter(bukuArrayList, root.getContext(), this);
        bukuSMPAdapter = new BukuAdapter(bukuArrayList2, root.getContext(), this);
        bukuSMAAdapter = new BukuAdapter(bukuArrayList3, root.getContext(), this);

        recyclerView.setAdapter(bukuAdapter);
        recyclerView2.setAdapter(bukuSMPAdapter);
        recyclerView3.setAdapter(bukuSMAAdapter);
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


        return root;
    }

    @Override
    public void selectedBuku(Buku bukuModel) {
        startActivity(new Intent(getActivity(), SelectedBukuActivity.class).putExtra("data", bukuModel));
    }

}
