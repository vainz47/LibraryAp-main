
package com.example.perpus_online;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main_Page extends AppCompatActivity {
    Fragment f = new FragmentHome();
    BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.books: //id apa iniiiiiiiiiiiiiiiiiiiiii
                    f = new FragmentHome();
                    break;
                case R.id.assignment:
                    f = new FragmentAssignment();
                    break;
                case R.id.profil:
                    f = new FragmentProfile();
                    break;
                default:
                    f = new FragmentHome();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,f).commit();
            return true;
        }
    };

//    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,f).commit();
//        recyclerView = findViewById(R.id.recyclerviewBuku);
//        FirebaseApp.initializeApp(this);
//        mFirebaseInstance = FirebaseDatabase.getInstance();
//        DBReference = mFirebaseInstance.getReference("buku");
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);
//
//        toolbar = findViewById(R.id.toolbar);
//        recyclerView = findViewById(R.id.recyclerviewBuku);
//        this.setSupportActionBar(toolbar);
//        this.getSupportActionBar().setTitle("");
//
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        bukuArrayList = new ArrayList<>();
//        bukuAdapter = new BukuAdapter(bukuArrayList,this,this);
//        recyclerView.setAdapter(bukuAdapter);
//        DBReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot snap: snapshot.getChildren()){
//                    Buku bukuDB = snap.getValue(Buku.class);
//                    bukuDB.setKode(snap.getKey());
//                    bukuArrayList.add(bukuDB);
//                    System.out.println("BUKU : "+bukuDB.getJudul());
//                }
//                bukuAdapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
//
//
    }
//
//    @Override
//    public void selectedBuku(Buku bukuModel) {
//        startActivity(new Intent(Main_Page.this, SelectedBukuActivity.class).putExtra("data", bukuModel));
//    }

    //    TextView username, password;
//
//        username = findViewById(R.id.getUsername);
//        password = findViewById(R.id.getPassword);

    //@Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_page);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        Intent intent = getIntent();
//        String usernameS = intent.getStringExtra("username");
//        String passwordS = intent.getStringExtra("password");
//
//        username.setText(usernameS);
//        password.setText(passwordS);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
////        return super.onCreateOptionsMenu(menu);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if(id == R.id.share){
//            Toast.makeText(getApplicationContext(),  resdl text:"You Click Me",
//                Toast.LENGTH_SHORT).show();
//            }
// //        return super.onOptionsItemSelected(item);
//    }
}
