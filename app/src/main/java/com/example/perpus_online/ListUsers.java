package com.example.perpus_online;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ListUsers extends ArrayAdapter {

    private Activity mContext;
    List<UserHelperClass> listUsers;


    public ListUsers(Activity mContext, List<UserHelperClass> users){
        super(mContext, R.layout.list_users, users);
        this.mContext = mContext;
        this.listUsers = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_users, null, true);

        TextView  namaUser = listItemView.findViewById(R.id.namaUser);
        TextView  emailUser = listItemView.findViewById(R.id.emailUser);
        TextView  genderUser = listItemView.findViewById(R.id.genderUser);
        CircleImageView imageV = listItemView.findViewById(R.id.profile_image);
        UserHelperClass user = listUsers.get(position);

        namaUser.setText(user.getName());
        emailUser.setText(user.getEmail());
        genderUser.setText(user.getGender());


        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("images/"+user.getImageKey());

        try {
            final File localFile = File.createTempFile(user.getImageKey(), "");
//            Toast.makeText(getApplicationContext(), "Image : "+_IMAGE, Toast.LENGTH_LONG).show();
            mStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmapImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageV.setImageBitmap(bitmapImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listItemView;
    }

}
