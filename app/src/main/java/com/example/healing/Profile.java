package com.example.healing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ImageButton btn_keluar,btn_backProfil;
    TextView pilih_foto,verifikasi;
    Button btn_ubah,btn_admin;
    public String namaUser,emailUser,passwordUser,teleponUser,alamatUser;
    TextInputEditText editnama,edittelepon,editalamat,editemail;
    FirebaseFirestore fStore;
    FirebaseUser user;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    CircleImageView profil_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn_keluar=findViewById(R.id.btn_keluar);
        bottomNavigation = findViewById(R.id.bottomnav);
        editnama=findViewById(R.id.txtUsernameProfil);
        editemail=findViewById(R.id.txtEmailProfil);
        edittelepon=findViewById(R.id.txtTeleponProfil);
        btn_ubah=findViewById(R.id.btn_ubah);
        pilih_foto=findViewById(R.id.pilih_foto);
        profil_image=findViewById(R.id.edit_foto_profil);
        btn_backProfil=findViewById(R.id.btn_backProfil);
        btn_admin=findViewById(R.id.btn_admin);
        editalamat=findViewById(R.id.txtAlamatProfil);
        verifikasi=findViewById(R.id.verifikasi);


        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        if (!user.isEmailVerified()) {
            verifikasi.setVisibility(View.VISIBLE);
            verifikasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(view.getContext(), "Verifikasi Email Telah Terkirim ! Cek Gmail Anda", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "Error!!" + e.getMessage());
                        }
                    });
                }
            });
            profil_image.setEnabled(false);
            pilih_foto.setEnabled(false);
            editnama.setEnabled(false);
            edittelepon.setEnabled(false);
            editalamat.setEnabled(false);
            btn_ubah.setEnabled(false);

        } else{
            profil_image.setEnabled(true);
            pilih_foto.setEnabled(true);
            editnama.setEnabled(true);
            edittelepon.setEnabled(true);
            editalamat.setEnabled(true);
            btn_ubah.setEnabled(true);
        }

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profil_image);
//                progressDialog.dismiss();
            }
        });

        reference = firestore.collection("users").document(currentid);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){

                    namaUser = task.getResult().getString("fName");
                    emailUser = task.getResult().getString("email");
                    passwordUser = task.getResult().getString("password");
                    teleponUser = task.getResult().getString("telepon");
                    alamatUser = task.getResult().getString("alamat");

                    if(emailUser.equals("fransiskosihombing@gmail.com")) {
                        btn_admin.setVisibility(View.VISIBLE);

                    }else {
                        btn_admin.setVisibility(View.GONE);
                    }

                    editnama.setText(namaUser);
                    editemail.setText(emailUser);
                    edittelepon.setText(teleponUser);
                    editalamat.setText(alamatUser);
                }else{
                    Toast.makeText(Profile.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
            }
        });

        btn_backProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,MainActivity.class));
                finish();
            }
        });

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,FormAdmin.class));

            }
        });

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editnama.getText().toString().isEmpty() || edittelepon.getText().toString().isEmpty() || editalamat.getText().toString().isEmpty() ){
                    Toast.makeText(Profile.this, "Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email = emailUser;

                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("fName",editnama.getText().toString());
                        edited.put("telepon",edittelepon.getText().toString());
                        edited.put("alamat",editalamat.getText().toString());


                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Profile.this, "Profil Berhasil Diubah", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(view.getContext(), Profile.class);
//                                i.putExtra("email",hemail);
//                                i.putExtra("fName",editnama.getText().toString());
//                                i.putExtra("telepon",edithp.getText().toString());
                                startActivity(i);
                                finish();
                            }
                        });
//                        Toast.makeText(EdituserActivity.this, "Email is changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this,   e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        pilih_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        profil_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });


        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_wisata:
                        startActivity(new Intent(Profile.this,Wisata.class));
                        finish();
                        break;
                    case R.id.nav_promosi:
                        if (!user.isEmailVerified()) {
                            Toast.makeText(Profile.this, "Silahkan Verifikasi Akun Anda", Toast.LENGTH_SHORT).show();

                        }else{
                            startActivity(new Intent(Profile.this,Promosi.class));
                        }
                        break;
                    case R.id.nav_help:
                        startActivity(new Intent(Profile.this,Help.class));
                        finish();
                        break;
                    case R.id.nav_home:
                        startActivity(new Intent(Profile.this,MainActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });

        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();//logout
                Toast.makeText(Profile.this, "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        }

    }

    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profil_image);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}