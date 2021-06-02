package com.example.perpus_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DataUsers extends AppCompatActivity {

    FirebaseDatabase mFirebaseInstance;
    DatabaseReference mDatabaseReference;
    ListView lvUsers;
    ArrayList<UserHelperClass> listUsers;
    ListUsers mAdapter;
    SearchView mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_users);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        lvUsers = findViewById(R.id.list_users);
        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("users");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listUsers = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                    UserHelperClass user = mDataSnapshot.getValue(UserHelperClass.class);
                    listUsers.add(user);
                }

                mAdapter = new ListUsers(DataUsers.this, listUsers);
                lvUsers.setAdapter(mAdapter);
                System.out.println("DATAAAA : " + listUsers.size());

                mSearch = findViewById(R.id.search);
                mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if (!s.isEmpty()) {
                            System.out.println("MASUK GA");

                            ArrayList<UserHelperClass> tempUsers = new ArrayList<>();
                            System.out.println(listUsers.size());
                            for (int i = 0; i < listUsers.size(); i++) {
                                if (listUsers.get(i).getName().contains(s.toLowerCase()) || listUsers.get(i).getEmail().contains(s.toLowerCase()) || listUsers.get(i).getGender().contains(s.toLowerCase())) {
                                    tempUsers.add(listUsers.get(i));
                                    System.out.println("MASUK GA");
                                }
                            }
                            if (!(tempUsers.size() == 0)) {
                                ListUsers newAdapter = new ListUsers(DataUsers.this, tempUsers);
                                lvUsers.setAdapter(newAdapter);
                            } else {
                                System.out.println("MASUK GA");
                            }
                        } else {
                            ListUsers newAdapter = new ListUsers(DataUsers.this, listUsers);
                            lvUsers.setAdapter(newAdapter);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        if (!s.isEmpty()) {
                            System.out.println("MASUK GA");

                            ArrayList<UserHelperClass> tempUsers = new ArrayList<>();
                            System.out.println(listUsers.size());
                            for (int i = 0; i < listUsers.size(); i++) {
                                if (listUsers.get(i).getName().contains(s.toLowerCase()) || listUsers.get(i).getEmail().contains(s.toLowerCase()) || listUsers.get(i).getGender().contains(s.toLowerCase())) {
                                    tempUsers.add(listUsers.get(i));
                                    System.out.println("MASUK GA");
                                }
                            }
                            if (!(tempUsers.size() == 0)) {
                                ListUsers newAdapter = new ListUsers(DataUsers.this, tempUsers);
                                lvUsers.setAdapter(newAdapter);
                            } else {
                                System.out.println("MASUK GA");
                            }
                        } else {
                            ListUsers newAdapter = new ListUsers(DataUsers.this, listUsers);
                            lvUsers.setAdapter(newAdapter);
                        }
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DataUsers.this,
                        error.getDetails() + " " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}