package com.example.healing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Promosi extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    BottomNavigationView bottomNavigation;
    TextInputEditText editDeskripsi,editNamaWisata,editProvinsi,editKabupaten,editKecamatan,editAlamat,editTiket,editTelepon,editKTP;
    TextView nama_wisata,promosikan;
    ImageView foto1,foto2,foto3,foto4;
    Button btn_tampilFoto,btn_promosi,btn_coba,btn_gantiFoto3;
    LinearLayout form_btn,form_foto1,form_foto2;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    FirebaseUser user,userEmail;
    StorageReference storageReference,fileRef1,fileRef2;
    StorageReference profileRef1,profileRef2;
    FirebaseAuth fAuth;
    UploadTask uploadTask;
    private Uri filePath1,filePath2;
    private final int PICK_IMAGE_REQUEST = 71;

    private static final int GALLERY_REQUEST = 1;
    private static final int GALLERY_REQUEST2 = 2;
    private static final int GALLERY_REQUEST3 = 3;
    private static final int GALLERY_REQUEST4 = 4;

    private Uri mImageUri = null;
    private Uri mImageUri1 = null;
    private Uri mImageUri2 = null;
    private Uri mImageUri3 = null;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;




    public String nm_wisata,spinner_provinsi;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promosi);
        bottomNavigation = findViewById(R.id.bottomnav);
        editNamaWisata=findViewById(R.id.editNamaWisataP);
        form_btn=findViewById(R.id.form_btn);
        foto1=findViewById(R.id.foto1);
        foto2=findViewById(R.id.foto2);
        foto3=findViewById(R.id.foto3);
        foto4=findViewById(R.id.foto4);
        editProvinsi=findViewById(R.id.editProvinsi);
        editKabupaten=findViewById(R.id.editKabupaten);
        editKecamatan=findViewById(R.id.editKecamatan);
        editAlamat=findViewById(R.id.editAlamat);
        editTiket=findViewById(R.id.editTiket);
        editTelepon=findViewById(R.id.editTelepon);
        editKTP=findViewById(R.id.editKTP);
        editDeskripsi=findViewById(R.id.editDeskripsi);
        btn_promosi=findViewById(R.id.btn_promosi);
        form_foto1=findViewById(R.id.formfoto1);
        form_foto2=findViewById(R.id.formfoto2);
        btn_tampilFoto=findViewById(R.id.btn_tampilFotoWisata);
        promosikan=findViewById(R.id.promosikan);

        nama_wisata=findViewById(R.id.txtNamaWisata);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userEmail = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        //promosikan.setText(user.getEmail().toString());

        Spinner spinner=findViewById(R.id.spinner_kota);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.wilayah, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_provinsi = adapterView.getItemAtPosition(i).toString();
                editProvinsi.setEnabled(false);
                editProvinsi.setText(spinner_provinsi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        editNamaWisata.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nama_wisata.setText(editNamaWisata.getText().toString());
                nm_wisata = nama_wisata.getText().toString();
                if(editNamaWisata.getText().toString().isEmpty()){
                    form_btn.setVisibility(View.GONE);
                    nama_wisata.setVisibility(View.GONE);
                }else{
                    form_btn.setVisibility(View.VISIBLE);
                    nama_wisata.setVisibility(View.VISIBLE);
                    btn_tampilFoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            form_foto1.setVisibility(View.VISIBLE);
                            form_foto2.setVisibility(View.VISIBLE);

                            foto1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                    galleryIntent.setType("image/*");
                                    galleryIntent.putExtra(Intent.ACTION_PICK, true);
                                    startActivityForResult(galleryIntent, GALLERY_REQUEST);
                                }
                            });

                            foto2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                    galleryIntent.setType("image/*");
                                    galleryIntent.putExtra(Intent.ACTION_PICK, true);
                                    startActivityForResult(galleryIntent, GALLERY_REQUEST2);
                                }
                            });

                            foto3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                    galleryIntent.setType("image/*");
                                    galleryIntent.putExtra(Intent.ACTION_PICK, true);
                                    startActivityForResult(galleryIntent, GALLERY_REQUEST3);
                                }
                            });


                            foto4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                    galleryIntent.setType("image/*");
                                    galleryIntent.putExtra(Intent.ACTION_PICK, true);
                                    startActivityForResult(galleryIntent, GALLERY_REQUEST4);
                                }
                            });





                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_promosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editNamaWisata.getText().toString().isEmpty() || editProvinsi.getText().toString().isEmpty() || editKabupaten.getText().toString().isEmpty() || editKecamatan.getText().toString().isEmpty() || editAlamat.getText().toString().isEmpty() || editTiket.getText().toString().isEmpty() || editTelepon.getText().toString().isEmpty() || editKTP.getText().toString().isEmpty()){
                    Toast.makeText(Promosi.this, "Periksa Data ! Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                    HashMap<String,Object> user=new HashMap<>();
                    String documentID = firestore.collection("wisata").document().getId();
                    user.put("namaWisata",editNamaWisata.getText().toString());
                    user.put("Provinsi",spinner_provinsi);
                    user.put("Kabupaten",editKabupaten.getText().toString());
                    user.put("Kecamatan",editKecamatan.getText().toString());
                    user.put("Alamat",editAlamat.getText().toString());
                    user.put("hTiket",editTiket.getText().toString());
                    user.put("Telepon","+62"+editTelepon.getText().toString());
                    user.put("KTP",editKTP.getText().toString());
                    user.put("Deskripsi",editDeskripsi.getText().toString());
                    user.put("id",documentID);
                    user.put("EmailP",userEmail.getEmail().toString());


                    firestore.collection("wisata").document(documentID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Promosi.this,"Silahkan Tunggu Verifikasi Admin !", Toast.LENGTH_SHORT).show();
                            StorageReference filepath = storageReference.child("gambarWisata/foto1/"+nm_wisata+".jpg");
                            StorageReference filepath1 = storageReference.child("gambarWisata/foto2/"+nm_wisata+".jpg");
                            StorageReference filepath2 = storageReference.child("gambarWisata/foto3/"+nm_wisata+".jpg");
                            StorageReference filepath3 = storageReference.child("gambarWisata/foto4/"+nm_wisata+".jpg");


                            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            filepath1.putFile(mImageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filepath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            filepath2.putFile(mImageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filepath2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            filepath3.putFile(mImageUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filepath3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            Toast.makeText(Promosi.this,"Silahkan Tunggu Verifikasi Admin !", Toast.LENGTH_SHORT).show();
                            editNamaWisata.setText("");
                            editProvinsi.setText("");
                            editKabupaten.setText("");
                            editKecamatan.setText("");
                            editAlamat.setText("");
                            editKTP.setText("");
                            editTiket.setText("");
                            editTelepon.setText("");
                            editDeskripsi.setText("");
                            startActivity(new Intent(Promosi.this,MainActivity.class));
                            finish();
                        }
                    }) .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Task","Error",e.getCause());
                        }
                    });
//                    firestore.collection("wisata")
//                            .add(user)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    Toast.makeText(Promosi.this,"Upload Gambar....", Toast.LENGTH_SHORT).show();
//                                    StorageReference filepath = storageReference.child("gambarWisata/foto1/"+nm_wisata+".jpg");
//                                    StorageReference filepath1 = storageReference.child("gambarWisata/foto2/"+nm_wisata+".jpg");
//                                    StorageReference filepath2 = storageReference.child("gambarWisata/foto3/"+nm_wisata+".jpg");
//                                    StorageReference filepath3 = storageReference.child("gambarWisata/foto4/"+nm_wisata+".jpg");
//
//
//                                    filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                @Override
//                                                public void onSuccess(Uri uri) {
//
//                                                }
//                                            });
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                    filepath1.putFile(mImageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                            filepath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                @Override
//                                                public void onSuccess(Uri uri) {
//
//                                                }
//                                            });
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                    filepath2.putFile(mImageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                            filepath2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                @Override
//                                                public void onSuccess(Uri uri) {
//
//                                                }
//                                            });
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                    filepath3.putFile(mImageUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                            filepath3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                @Override
//                                                public void onSuccess(Uri uri) {
//                                                }
//                                            });
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                    Toast.makeText(Promosi.this,"Silahkan Tunggu Verifikasi Admin !", Toast.LENGTH_SHORT).show();
//                                    editNamaWisata.setText("");
//                                    editProvinsi.setText("");
//                                    editKabupaten.setText("");
//                                    editKecamatan.setText("");
//                                    editAlamat.setText("");
//                                    editKTP.setText("");
//                                    editTiket.setText("");
//                                    editTelepon.setText("");
//                                    editDeskripsi.setText("");
//                                    startActivity(new Intent(Promosi.this,MainActivity.class));
//                                    finish();
//
//
//
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.e("Task","Error",e.getCause());
//                                }
//                            });


                }
            }

        });


        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_wisata:
                        startActivity(new Intent(Promosi.this,Wisata.class));
                        finish();
                        break;
                    case R.id.nav_home:
                        startActivity(new Intent(Promosi.this,MainActivity.class));
                        finish();
                        break;
                    case R.id.nav_help:
                        startActivity(new Intent(Promosi.this,Help.class));
                        finish();
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(Promosi.this,Profile.class));
                        finish();
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            mImageUri = data.getData();
            foto1.setImageURI(mImageUri);

        }

        if(requestCode == GALLERY_REQUEST2 && resultCode == RESULT_OK){
            mImageUri1 = data.getData();
            foto2.setImageURI(mImageUri1);
        }

        if(requestCode == GALLERY_REQUEST3 && resultCode == RESULT_OK){
            mImageUri2 = data.getData();
            foto3.setImageURI(mImageUri2);
        }

        if(requestCode == GALLERY_REQUEST4 && resultCode == RESULT_OK){
            mImageUri3 = data.getData();
            foto4.setImageURI(mImageUri3);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}