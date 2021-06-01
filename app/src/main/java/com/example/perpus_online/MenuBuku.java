package com.example.perpus_online;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class MenuBuku extends AppCompatActivity {

    ExtendedFloatingActionButton tambah_buku;
    EditText edit_judul, edit_pengarang, edit_penerbit, edit_tahun_terbit, edit_isbn, edit_desc, edit_ISBN;
    FirebaseDatabase mFirebaseInstance;
    DatabaseReference mDatabaseReference;
    ArrayList<Buku> listBuku;
    ListAdapter mAdapter;
    ListView mListView;
    SearchView mSearch;
    String imageID;
    Uri imageUri;
    ImageView mImageBuku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_buku);

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

        tambah_buku = (ExtendedFloatingActionButton) findViewById(R.id.tambah_buku);
        mListView = findViewById(R.id.list_buku);

        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("buku");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBuku = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                    Buku mBuku = mDataSnapshot.getValue(Buku.class);
                    mBuku.setKode(mDataSnapshot.getKey());
                    listBuku.add(mBuku);
                }

                mAdapter = new ListAdapter(MenuBuku.this, listBuku);
                mListView.setAdapter(mAdapter);
                System.out.println("DATAAAA : " + listBuku.size());

                mSearch = findViewById(R.id.search);
                mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if (!s.isEmpty()) {
                            System.out.println("MASUK GA");

                            ArrayList<Buku> tempBuku = new ArrayList<>();
                            System.out.println(listBuku.size());
                            for (int i = 0; i < listBuku.size(); i++) {
                                if (listBuku.get(i).getJudul().toLowerCase().contains(s.toLowerCase()) || listBuku.get(i).getPenerbit().toLowerCase().contains(s.toLowerCase()) || listBuku.get(i).getTahunterbit().toLowerCase().contains(s.toLowerCase())) {
                                    tempBuku.add(listBuku.get(i));
                                    System.out.println("MASUK GA");
                                }
                            }
                            if (!(tempBuku.size() == 0)) {
                                ListAdapter newAdapter = new ListAdapter(MenuBuku.this, tempBuku);
                                mListView.setAdapter(newAdapter);
                            } else {
                                System.out.println("MASUK GA");
                            }
                        } else {
                            ListAdapter newAdapter = new ListAdapter(MenuBuku.this, listBuku);
                            mListView.setAdapter(newAdapter);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        if (!s.isEmpty()) {
                            System.out.println("MASUK GA");

                            ArrayList<Buku> tempBuku = new ArrayList<>();
                            System.out.println(listBuku.size());
                            for (int i = 0; i < listBuku.size(); i++) {
                                if (listBuku.get(i).getJudul().toLowerCase().contains(s.toLowerCase()) || listBuku.get(i).getPenerbit().toLowerCase().contains(s.toLowerCase()) || listBuku.get(i).getTahunterbit().toLowerCase().contains(s.toLowerCase())) {
                                    tempBuku.add(listBuku.get(i));
                                    System.out.println("MASUK GA");
                                }
                            }
                            if (!(tempBuku.size() == 0)) {
                                ListAdapter newAdapter = new ListAdapter(MenuBuku.this, tempBuku);
                                mListView.setAdapter(newAdapter);
                            } else {
                                // action jika hasil kosong
                            }
                        } else {
                            ListAdapter newAdapter = new ListAdapter(MenuBuku.this, listBuku);
                            mListView.setAdapter(newAdapter);
                        }
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuBuku.this,
                        error.getDetails() + " " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        tambah_buku = findViewById(R.id.tambah_buku);

        tambah_buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageID = UUID.randomUUID().toString();
                dialogTambahBuku();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Buku newBuku = new Buku();
                newBuku = listBuku.get(position);
                listBuku.get(position).setImageKey(newBuku.getImageKey());
                System.out.println("IMAGE KEYNYA DAPAT : "+newBuku.getImageKey());
                onDataClick(listBuku.get(position), position);
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

    public void onDataClick(final Buku buku, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String theJudul     = "Judul\t\t\t\t\t\t: "+buku.getJudul();
        String thePengarang = "Pengarang\t\t\t: "+buku.getPengarang();
        String thePenerbit  = "Penerbit\t\t\t\t\t: "+buku.getPenerbit();
        String theTahun     = "Tahun Terbit\t\t: "+buku.getTahunterbit();
        String thePeminjam  = "Status\t\t\t\t: "+buku.getStatus();
        String theDeskripsi = "Deskripsi\t\t\t\t: "+buku.getDeskripsi();

        builder.setTitle("Pilih Aksi");
        builder.setMessage(theJudul + "\n" + thePengarang + "\n" + thePenerbit +
                "\n" + theTahun + "\n" + thePeminjam + "\n" + theDeskripsi + "\n");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogUpdateBuku(buku);
            }
        });

        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                hapusDataBuku(buku);
            }
        });

        builder.setNeutralButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dialogTambahBuku() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Buku");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_buku, null);

        edit_judul = (EditText) view.findViewById(R.id.edit_judul_buku);
        edit_pengarang = (EditText) view.findViewById(R.id.edit_pengarang);
        edit_penerbit = (EditText) view.findViewById(R.id.edit_penerbit);
        edit_tahun_terbit = (EditText) view.findViewById(R.id.edit_tahun);
        edit_isbn = (EditText) view.findViewById(R.id.edit_isbn);
        edit_desc = (EditText) view.findViewById(R.id.edit_desc);
        mImageBuku = (ImageView)view.findViewById(R.id.image_buku);

        builder.setView(view);


        builder.setNeutralButton("GAMBAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                imageUri = null;
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);
            }
        });
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regJudul = edit_judul.getText().toString();
                String regPengarang = edit_pengarang.getText().toString();
                String regPenerbit = edit_penerbit.getText().toString();
                String regTahun = edit_tahun_terbit.getText().toString();
                String regDesc = edit_desc.getText().toString();
                String regISBN = edit_isbn.getText().toString();
                String peminjam = "Buku Tersedia";

                if (!regJudul.isEmpty() && !regPengarang.isEmpty() && !regPenerbit.isEmpty() && !regTahun.isEmpty()
                        && !regDesc.isEmpty()) {
                    String kode = UUID.randomUUID().toString();
                    submitDataBuku(new Buku(kode, regJudul, regPengarang, regPenerbit, regTahun, regDesc, peminjam, regISBN, imageID));
                    dialog.dismiss();
                } else {
                    Toast.makeText(MenuBuku.this, "Data harus di isi bun!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void dialogUpdateBuku(final Buku buku) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Buku");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_buku, null);

        String dataJudul = buku.getJudul();
        String dataPengarang = buku.getPengarang();
        String dataPenerbit = buku.getPenerbit();
        String dataTahun = buku.getTahunterbit();
        String dataDesc = buku.getDeskripsi();
        String dataISBN = buku.getISBN();
        String dataImage = buku.getImageKey();

        edit_judul = (EditText) view.findViewById(R.id.edit_judul_buku);
        edit_pengarang = (EditText) view.findViewById(R.id.edit_pengarang);
        edit_penerbit = (EditText) view.findViewById(R.id.edit_penerbit);
        edit_tahun_terbit = (EditText) view.findViewById(R.id.edit_tahun);
        edit_desc = (EditText) view.findViewById(R.id.edit_desc);
        edit_ISBN = (EditText) view.findViewById(R.id.edit_isbn);
        mImageBuku = (ImageView) view.findViewById(R.id.image_buku);

        edit_judul.setText(dataJudul);
        edit_pengarang.setText(dataPengarang);
        edit_penerbit.setText(dataPenerbit);
        edit_tahun_terbit.setText(dataTahun);
        edit_desc.setText(dataDesc);
        edit_ISBN.setText(dataISBN);


        builder.setView(view);

        if (buku != null) {
            builder.setNeutralButton("GAMBAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setNegativeButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            });
        }

        builder.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                imageUri = null;
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);
            }
        });
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    buku.setJudul(edit_judul.getText().toString());
                    buku.setPengarang(edit_pengarang.getText().toString());
                    buku.setPenerbit(edit_penerbit.getText().toString());
                    buku.setTahunterbit(edit_tahun_terbit.getText().toString());
                    buku.setDeskripsi(edit_desc.getText().toString());
                    buku.setISBN(edit_ISBN.getText().toString());
                    buku.setImageKey(dataImage);
                    updateDataBuku(buku);
                    uploadPicture(dataImage);
                    dialog.dismiss();
                }catch (Exception e){
                    Toast.makeText(MenuBuku.this, "Data harus di isi bun!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            System.out.println(data.getData().toString());
            imageUri = data.getData();
            mImageBuku.setImageURI(imageUri);
        }
    }

    private void submitDataBuku(Buku buku) {
        mDatabaseReference.push()
                .setValue(buku).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                uploadPicture(imageID);
                Toast.makeText(MenuBuku.this, "Data buku berhasil di simpan bun!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateDataBuku(Buku buku) {
        mDatabaseReference.child(buku.getKode())
                .setValue(buku).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                uploadPicture(buku.getImageKey());
                Toast.makeText(MenuBuku.this, "Data berhasil di update bun!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hapusDataBuku(Buku buku) {
        if (mDatabaseReference != null) {
            mDatabaseReference.child(buku.getKode())
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(MenuBuku.this, "Data berhasil di hapus bun!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void uploadPicture(String key){

        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("images/"+key);

        if(imageUri == null){
            imageUri = Uri.parse("android.resource://"+ R.class.getPackage().getName()+"/"+R.drawable.book);
        }
        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Failed to Upload.", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
            }
        });
    }
}