package com.example.perpus_online;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {

    //Button
    protected Button callSignUp, callLogin;
    TextInputLayout username, password;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp = (Button) findViewById(R.id.signup_btn);
        callLogin = (Button) findViewById(R.id.login_btn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Regis.class);
                startActivity(intent);
            }
        });
        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private Boolean valUsername() {
        String val = username.getEditText().getText().toString();

        if (val.isEmpty()) {
            username.setError("Tolong bun usernamenya diisi");
            return false;
        }
        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valPassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Tolong bun passwordnya diisi");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(){
        if (!valUsername() || !valPassword()){
            return;
        }else isUser();
    }

    private void isUser(){
        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);

                    UserHelperClass data1 = new UserHelperClass();
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        data1 = data.getValue(UserHelperClass.class);
                        data1.setKey(data.getKey());
                    }

//                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    String passwordFromDB = data1.getPassword();
                    Log.d("HASILDB", "UID : " + data1.getKey());

                    if (passwordFromDB != null && passwordFromDB.equals(userEnteredPassword)){
                        username.setError(null);
                        username.setErrorEnabled(false);
                        String status = data1.getAs();
                        String nameFromDB = data1.getName();
                        String emailFromDB = data1.getEmail();
                        String usernameFromDB = data1.getUsername();
                        String keyFromDB = data1.getKey();

                        if(status.equals("admin")){
                            Intent intent = new Intent(Login.this, AdminActivity.class);
                            intent.putExtra("name",nameFromDB);
                            intent.putExtra("email",emailFromDB);
                            intent.putExtra("username",usernameFromDB);
                            intent.putExtra("password",passwordFromDB);
                            intent.putExtra("key", keyFromDB);
                            startActivity(intent);
                        }
                        if(status.equals("user")){
                            Intent intent = new Intent(Login.this, UserProfile.class);
                            intent.putExtra("name",nameFromDB);
                            intent.putExtra("email",emailFromDB);
                            intent.putExtra("username",usernameFromDB);
                            intent.putExtra("password",passwordFromDB);
                            intent.putExtra("key", keyFromDB);
                            startActivity(intent);
                        }

                    }
                    else {
                        password.setError("Passwordnya salah bun");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("Usernamenya belum ada bun");
                    username.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}

