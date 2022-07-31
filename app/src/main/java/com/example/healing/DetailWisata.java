package com.example.healing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;

public class DetailWisata extends AppCompatActivity {
    TextView detail_deskripsi,detail_namaWisata,detail_Provinsi,detail_Kabupaten,detail_Kecamatan,detail_Alamat,detail_KTP,detail_Telepon,detail_harga;
    ImageView foto1,foto2,foto3,foto4;
    ImageButton btn_wa,btn_call,btn_back;
    StorageReference storageReference;
    public String Deskripsi,nama_wisata_detailHome,provinsi_detailHome,kabupaten_detailHome,kecamatan_detailHome,alamat_detailHome,ktp_detailHome,telepon_detailHome,harga_detailHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);
        Intent data = getIntent();

        detail_namaWisata=findViewById(R.id.detail_namaWisataHome);
        detail_deskripsi=findViewById(R.id.detail_deskripsi_home);
        detail_Provinsi=findViewById(R.id.detail_provinsiHome);
        detail_Kabupaten=findViewById(R.id.detail_kabuptenHome);
        detail_Kecamatan=findViewById(R.id.detail_kecamatanHome);
        detail_Alamat=findViewById(R.id.detail_alamatHome);
        detail_KTP=findViewById(R.id.detail_ktpHome);
        detail_Telepon=findViewById(R.id.detail_teleponHome);
        detail_harga=findViewById(R.id.detail_hargaHome);
        foto1=findViewById(R.id.detail_foto1Home);
        foto2=findViewById(R.id.detail_foto2Home);
        foto3=findViewById(R.id.detail_foto3Home);
        foto4=findViewById(R.id.detail_foto4Home);
        btn_wa=findViewById(R.id.btn_wa);
        btn_call=findViewById(R.id.btn_call);
        btn_back=findViewById(R.id.btn_back_detail_home);

        nama_wisata_detailHome = data.getStringExtra("namaWisata");
        provinsi_detailHome = data.getStringExtra("Provinsi");
        kabupaten_detailHome = data.getStringExtra("Kabupaten");
        kecamatan_detailHome = data.getStringExtra("Kecamatan");
        alamat_detailHome = data.getStringExtra("Alamat");
        ktp_detailHome = data.getStringExtra("KTP");
        telepon_detailHome = data.getStringExtra("Telepon");
        harga_detailHome = data.getStringExtra("hTiket");
        Deskripsi = data.getStringExtra("Deskripsi");


        detail_namaWisata.setText(nama_wisata_detailHome);
        detail_Provinsi.setText(provinsi_detailHome);
        detail_Kabupaten.setText(kabupaten_detailHome);
        detail_Kecamatan.setText(kecamatan_detailHome);
        detail_Alamat.setText(alamat_detailHome);
        detail_KTP.setText(ktp_detailHome);
        detail_Telepon.setText(telepon_detailHome);
        detail_deskripsi.setText(Deskripsi);
        detail_harga.setText(harga_detailHome);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailWisata.this,MainActivity.class));
                finish();
            }
        });
        btn_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=" + telepon_detailHome + "&text=Halo.."+"\n"+"Saya ingin informasi mengenai wisata " +nama_wisata_detailHome;
                try {
                    PackageManager pm = view.getContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    view.getContext().startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+telepon_detailHome));
                startActivity(intent);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("gambarWisata/foto1/"+nama_wisata_detailHome+".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto1);
//                progressDialog.dismiss();
            }
        });

        StorageReference profileRef1 = storageReference.child("gambarWisata/foto2/"+nama_wisata_detailHome+".jpg");
        profileRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto2);
//                progressDialog.dismiss();
            }
        });

        StorageReference profileRef2 = storageReference.child("gambarWisata/foto3/"+nama_wisata_detailHome+".jpg");
        profileRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto3);
//                progressDialog.dismiss();
            }
        });

        StorageReference profileRef3 = storageReference.child("gambarWisata/foto4/"+nama_wisata_detailHome+".jpg");
        profileRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto4);
//                progressDialog.dismiss();
            }
        });

    }

}