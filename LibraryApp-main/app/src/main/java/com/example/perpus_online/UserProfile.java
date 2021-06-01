package com.example.perpus_online;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    Button button, change_image_btn;
    TextInputLayout fullName, maill, username, password;
    TextView fullNameLabel, usernameLabel;
    String _NAME, _EMAIL, _USERNAME, _PASSWORD, _KEY;
    private CircleImageView profile_image;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    DatabaseReference reference;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseStorage storage;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");
        fAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        fullNameLabel = (TextView) findViewById(R.id.full_name_label);
        usernameLabel = (TextView) findViewById(R.id.username_label);
        maill =  (TextInputLayout) findViewById(R.id.mail);
        fullName = (TextInputLayout)findViewById(R.id.full_name);
        username = (TextInputLayout)findViewById(R.id.username);
        password = (TextInputLayout)findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        change_image_btn = (Button) findViewById(R.id.change_image_btn);

        change_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
                finish();
            }
        });



        showAllUserData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullnamee = fullName.getEditText().getText().toString();
                String emaill = maill.getEditText().getText().toString().trim();
                String passwordd = password.getEditText().getText().toString();


                if(fullnamee.isEmpty() || emaill.isEmpty() || passwordd.isEmpty()){
                    Toast.makeText(UserProfile.this, "Tidak Boleh Kosong Bun", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.updateEmail(emaill).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UserProfile.this, "Berhasil Hore", Toast.LENGTH_SHORT).show();
                        HashMap hashMap= new HashMap();
                        hashMap.put("email",emaill);
                        hashMap.put("name",fullnamee);
                        hashMap.put("password",passwordd);
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap);

                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profile_image.setImageURI(imageUri);
            uploadPicture();
        }

    }

    private void uploadPicture(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        Log.d("HASILID", "key : "+ _KEY);
        StorageReference riversRef = storageReference.child("images/"+_KEY);

        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Upload.", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage: " + (int) progressPercent +"%");
            }
        });
    }

    private void showAllUserData(){
        Intent intent = getIntent();
        _NAME = intent.getStringExtra("name");
        _USERNAME = intent.getStringExtra("username");
        _PASSWORD = intent.getStringExtra("password");
        _EMAIL = intent.getStringExtra("email");
        _KEY = intent.getStringExtra("key");

        fullNameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
        fullName.getEditText().setText(_NAME);
        maill.getEditText().setText(_EMAIL);
        password.getEditText().setText(_PASSWORD);

        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("images/"+_KEY);

        try {
            final File localFile = File.createTempFile(_KEY, "");
            mStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmapImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            profile_image.setImageBitmap(bitmapImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "error : "+e, Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public void update(View view){
//        if (isNameChanged() || isPasswordChanged()){
//            Toast.makeText(this, "Data sudah diubah bun", Toast.LENGTH_LONG).show();
//        }
//        else Toast.makeText(this, "Data masih sama bun", Toast.LENGTH_LONG).show();
//
//    }
//
//    private boolean isNameChanged() {
//        if (!_NAME.equals(fullName.getEditText().getText().toString())){
//            reference.child("name").setValue(fullName.getEditText().getText().toString());
//            _NAME = fullName.getEditText().getText().toString();
//
//            return true;
//        }else {
//            return false;
//        }
//
//    }
//
//    private boolean isPasswordChanged() {
//        if (!_PASSWORD.equals(password.getEditText().getText().toString())){
//            reference.child("password").setValue(password.getEditText().getText().toString());
//            _PASSWORD = password.getEditText().getText().toString();
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    private boolean isEmailChanged(String mail) {
//        if (!_EMAIL.equals(mail)){
//            reference.child(_EMAIL).child("email").setValue(mail);
//            _PASSWORD = password.getEditText().getText().toString();
//            return true;
//        }else {
//            return false;
//        }
//    }

}