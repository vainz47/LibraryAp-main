package com.example.perpus_online;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfile extends Fragment {
    UserHelperClass userProfile;
    Button button, change_image_btn, callLogout;
    TextInputLayout fullName, maill, username, password;
    TextView fullNameLabel, usernameLabel;
    String _NAME, _EMAIL, _USERNAME, _PASSWORD, _KEY;
    private CircleImageView profile_image;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    DatabaseReference reference, logReference;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_user_profile, container, false);

        reference = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        userProfile = new UserHelperClass();


        fullNameLabel = (TextView) root.findViewById(R.id.full_name_label);
        usernameLabel = (TextView) root.findViewById(R.id.username_label);
        maill =  (TextInputLayout) root.findViewById(R.id.mail);
        fullName = (TextInputLayout)root.findViewById(R.id.full_name);
        username = (TextInputLayout)root.findViewById(R.id.username);
        password = (TextInputLayout)root.findViewById(R.id.password);
        button = (Button) root.findViewById(R.id.button);
        profile_image = (CircleImageView) root.findViewById(R.id.profile_image);

        change_image_btn = (Button) root.findViewById(R.id.change_image_btn);
        change_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                imageUri = null;
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        showAllUserData(userProfile);
        callLogout = (Button) root.findViewById(R.id.logout_btn);
        callLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullnamee = fullName.getEditText().getText().toString();
                String emaill = maill.getEditText().getText().toString().trim();
                String passwordd = password.getEditText().getText().toString();


                if(fullnamee.isEmpty() || emaill.isEmpty() || passwordd.isEmpty()){
                    return;
                }
                user.updateEmail(emaill).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        HashMap hashMap= new HashMap();
                        hashMap.put("email",emaill);
                        hashMap.put("name",fullnamee);
                        hashMap.put("password",passwordd);
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap);

                    }
                });

            }
        });
        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == -1 && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profile_image.setImageURI(imageUri);
            uploadPicture();
        }
    }
    private void uploadPicture(){

        Log.d("HASILID", "key : "+ _KEY);
        StorageReference riversRef = storageReference.child("images/"+_KEY);

        riversRef.putFile(imageUri);
    }

    public void showAllUserData(UserHelperClass user){
        Intent intent = getActivity().getIntent();
//        _NAME = user.getName();
//        _USERNAME = user.getUsername();
//        _PASSWORD = user.getPassword();
//        _EMAIL = user.getEmail();
//        _KEY = user.getImageKey();

        _NAME = intent.getStringExtra("name");
        _USERNAME = intent.getStringExtra("username");
        _PASSWORD = intent.getStringExtra("password");
        _EMAIL = intent.getStringExtra("email");
        _KEY = intent.getStringExtra("image");
        fullNameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
        fullName.getEditText().setText(_NAME);
        maill.getEditText().setText(_EMAIL);
        password.getEditText().setText(_PASSWORD);

        System.out.println("NAMA : "+_NAME);

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
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
