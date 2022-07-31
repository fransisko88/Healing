package com.example.healing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DetailPromosi extends AppCompatActivity {
    public  String email_detail,idW,Deskripsi,emailUser,id_user,nama_wisata_detail,provinsi_detail,kabupaten_detail,kecamatan_detail,alamat_detail,ktp_detail,telepon_detail,harga_detail;
    TextView detail_email,detail_deskripsi,detail_namaWisata,detail_Provinsi,detail_Kabupaten,detail_Kecamatan,detail_Alamat,detail_KTP,detail_Telepon,detail_harga;
    ImageView foto1,foto2,foto3,foto4;
    ImageButton btn_back_detail;
    FirebaseFirestore fStore;
    FirebaseUser user;
    Button btn_setuju,btn_cancel,btn_hapus;

    StorageReference storageReference;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promosi);
        detail_namaWisata=findViewById(R.id.detail_namaWisata);
        detail_Provinsi=findViewById(R.id.detail_provinsi);
        detail_Kabupaten=findViewById(R.id.detail_kabupten);
        detail_Kecamatan=findViewById(R.id.detail_kecamatan);
        detail_Alamat=findViewById(R.id.detail_alamat);
        detail_KTP=findViewById(R.id.detail_ktp);
        detail_Telepon=findViewById(R.id.detail_telepon);
        detail_deskripsi=findViewById(R.id.detail_deskripsi);
        detail_harga=findViewById(R.id.detail_harga);
        detail_email=findViewById(R.id.detail_EmailP);

        foto1=findViewById(R.id.detail_foto1);
        foto2=findViewById(R.id.detail_foto2);
        foto3=findViewById(R.id.detail_foto3);
        foto4=findViewById(R.id.detail_foto4);
        btn_back_detail=findViewById(R.id.btn_back_detail);
        btn_setuju=findViewById(R.id.btn_setuju);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_hapus=findViewById(R.id.btn_hapus);

        Intent data = getIntent();
        nama_wisata_detail = data.getStringExtra("namaWisata");
        provinsi_detail = data.getStringExtra("Provinsi");
        kabupaten_detail = data.getStringExtra("Kabupaten");
        kecamatan_detail = data.getStringExtra("Kecamatan");
        alamat_detail = data.getStringExtra("Alamat");
        ktp_detail = data.getStringExtra("KTP");
        telepon_detail = data.getStringExtra("Telepon");
        harga_detail = data.getStringExtra("hTiket");
        Deskripsi = data.getStringExtra("Deskripsi");
        idW = data.getStringExtra("id");
        email_detail = data.getStringExtra("EmailP");


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();

        reference = firestore.collection("users").document(currentid);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){
                    emailUser = task.getResult().getString("email");
                }else{
                    Toast.makeText(DetailPromosi.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
            }
        });

        StorageReference profileRef = storageReference.child("gambarWisata/foto1/"+nama_wisata_detail+".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto1);
//                progressDialog.dismiss();
            }
        });

        StorageReference profileRef1 = storageReference.child("gambarWisata/foto2/"+nama_wisata_detail+".jpg");
        profileRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto2);
//                progressDialog.dismiss();
            }
        });

        StorageReference profileRef2 = storageReference.child("gambarWisata/foto3/"+nama_wisata_detail+".jpg");
        profileRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto3);
//                progressDialog.dismiss();
            }
        });

        StorageReference profileRef3 = storageReference.child("gambarWisata/foto4/"+nama_wisata_detail+".jpg");
        profileRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto4);
//                progressDialog.dismiss();
            }
        });

        btn_back_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailPromosi.this,FormAdmin.class));
                finish();
            }
        });




        detail_namaWisata.setText(nama_wisata_detail);
        detail_Provinsi.setText(provinsi_detail);
        detail_Kabupaten.setText(kabupaten_detail);
        detail_Kecamatan.setText(kecamatan_detail);
        detail_Alamat.setText(alamat_detail);
        detail_KTP.setText(ktp_detail);
        detail_Telepon.setText(telepon_detail);
        detail_deskripsi.setText(Deskripsi);
        detail_harga.setText("Rp. "+harga_detail);
        detail_email.setText(email_detail);


        btn_setuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                HashMap<String,Object> user=new HashMap<>();

                user.put("namaWisata",detail_namaWisata.getText().toString());
                user.put("Provinsi",detail_Provinsi.getText().toString());
                user.put("Kabupaten",detail_Kabupaten.getText().toString());
                user.put("Kecamatan",detail_Kecamatan.getText().toString());
                user.put("Alamat",detail_Alamat.getText().toString());
                user.put("hTiket",detail_harga.getText().toString());
                user.put("Telepon",detail_Telepon.getText().toString());
                user.put("Deskripsi",detail_deskripsi.getText().toString());
                user.put("KTP",detail_KTP.getText().toString());
                firestore.collection("wisata_promosi")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Toast.makeText(DetailPromosi.this,"Wisata Berhasil Di Promosikan", Toast.LENGTH_SHORT).show();
                                detail_namaWisata.setText("");
                                detail_Provinsi.setText("");
                                detail_Kabupaten.setText("");
                                detail_Kecamatan.setText("");
                                detail_Alamat.setText("");
                                detail_KTP.setText("");
                                detail_harga.setText("");
                                detail_Telepon.setText("");
                                startActivity(new Intent(DetailPromosi.this,MainActivity.class));
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Task","Error",e.getCause());
                            }
                        });

                firestore.collection("wisata").document(idW).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjek = "Konfirmasi Promosi Wisata";
                String msg = "Mohon maaf, data promosi wisata ("+ detail_namaWisata.getText().toString() +") yang anda upload kurang lengkap";
                String[] CC = {detail_email.getText().toString()};
                String[] BCC = {detail_email.getText().toString()};
                Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
                sendEmail.setData(Uri.parse("mailto:"));
                sendEmail.putExtra(Intent.EXTRA_CC,CC);
                sendEmail.putExtra(Intent.EXTRA_BCC,BCC);
                sendEmail.putExtra(Intent.EXTRA_SUBJECT,subjek);
                sendEmail.putExtra(Intent.EXTRA_TEXT,msg);
                Toast.makeText(DetailPromosi.this,"Membuka Gmail", Toast.LENGTH_SHORT).show();
                startActivity(Intent.createChooser(sendEmail,"Pilih Mengirim Email"));
                btn_hapus.setVisibility(View.VISIBLE);
            }
        });

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("wisata").document(idW).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DetailPromosi.this,"Promosi Wisata Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DetailPromosi.this,MainActivity.class));
                        finish();
                    }
                });
            }
        });

    }
}