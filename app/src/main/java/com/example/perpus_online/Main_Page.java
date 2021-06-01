package com.example.perpus_online;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,f).commit();
    }

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