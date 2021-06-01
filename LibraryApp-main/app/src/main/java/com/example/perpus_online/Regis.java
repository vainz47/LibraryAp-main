package com.example.perpus_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;

public class Regis extends AppCompatActivity {

    TextInputLayout regName, regUsername, regPassword, regEmail;
    protected Button regBtn, regToLoginBtn;
    RadioButton laki, perempuan;
    String gender = "";s
    String as = "user";
    //Firebase Database
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        regToLoginBtn = (Button) findViewById(R.id.reg_login_btn);
        regBtn = (Button) findViewById(R.id.reg_btn);
        regName = (TextInputLayout) findViewById(R.id.regName);
        regUsername = (TextInputLayout) findViewById(R.id.regUsername);
        regPassword = (TextInputLayout) findViewById(R.id.regPassword);
        regEmail = (TextInputLayout) findViewById(R.id.regEmail);
        laki = (RadioButton) findViewById(R.id.laki);
        perempuan = (RadioButton) findViewById(R.id.perempuan);


        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Regis.this, Login.class);
                startActivity(intent);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                firebaseAuth = FirebaseAuth.getInstance();
                // Get Value
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();

                if(!valName() || !valEmail() || !valUsername() || !valPassword() || !valGender()){
                    return;
                }
                
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Regis.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference key = reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                UserHelperClass helperClass = new UserHelperClass(key.toString(), name, email, username, password, gender, as);
                                key.setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Regis.this, "SELESEEEEE", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Regis.this, Login.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {

                            }
                        }
                    });

            }
        });

    }

    private Boolean valUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            regUsername.setError("Tolong bun usernamenya diisi");
            return false;
        } else if (val.length() >= 15){
            regUsername.setError("Usernamenya maks 15 karakter bun");
            return false;
        }else if (!val.matches(noWhiteSpace)){
            regUsername.setError("Username gabisa ada spasi bun");
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Tolong bun usernamenya diisi");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Tolong bun emailnya diisi");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Emailnya gak valid bun");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valPassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 6 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Tolong bun passwordnya diisi");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password harus 1 angka besar, minimal 6 karakter, dan no spasi");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valGender() {
        if (laki.isChecked()) {
            gender = "Laki-Laki";
        } else if (perempuan.isChecked()) {
            gender = "Female";
        }
        if (gender.isEmpty()) {
            laki.setError("Tolong bun gendernya dipilih");
            perempuan.setError("Tolong bun gendernya dipilih");
            return false;
        } else {
            laki.setError(null);
            return true;
        }
    }

}

