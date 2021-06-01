package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main_Page extends AppCompatActivity {
<<<<<<< Updated upstream
    TextView username, password;
=======
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
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
<<<<<<< Updated upstream
=======
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,f).commit();
    }
>>>>>>> Stashed changes

        username = findViewById(R.id.getUsername);
        password = findViewById(R.id.getPassword);

        Intent intent = getIntent();
        String usernameS = intent.getStringExtra("username");
        String passwordS = intent.getStringExtra("password");

        username.setText(usernameS);
        password.setText(passwordS);


    }
}