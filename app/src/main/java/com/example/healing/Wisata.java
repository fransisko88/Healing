package com.example.healing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Wisata extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigation;

    FirebaseUser user;

    ProgressDialog progressDialog;
    public Intent i;

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28,btn29,btn30,btn31,btn32;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);
        bottomNavigation = findViewById(R.id.bottomnav);

        user = FirebaseAuth.getInstance().getCurrentUser();
        btn1=findViewById(R.id.btn_aceh);
        btn2=findViewById(R.id.btn_bali);
        btn3=findViewById(R.id.btn_bangka);
        btn4=findViewById(R.id.btn_banten);
        btn5=findViewById(R.id.btn_bengkulu);
        btn6=findViewById(R.id.btn_jogja);
        btn7=findViewById(R.id.btn_jakarta);
        btn8=findViewById(R.id.btn_gorontalo);
        btn9=findViewById(R.id.btn_jambi);
        btn10=findViewById(R.id.btn_jabar);
        btn11=findViewById(R.id.btn_jateng);
        btn12=findViewById(R.id.btn_jatim);
        btn13=findViewById(R.id.btn_kalbar);
        btn14=findViewById(R.id.btn_kalsel);
        btn15=findViewById(R.id.btn_kaltim);
        btn16=findViewById(R.id.btn_kaltar);
        btn17=findViewById(R.id.btn_kepri);
        btn18=findViewById(R.id.btn_lampung);
        btn19=findViewById(R.id.btn_maluku);
        btn20=findViewById(R.id.btn_malukuUtara);
        btn21=findViewById(R.id.btn_ntb);
        btn22=findViewById(R.id.btn_ntt);
        btn23=findViewById(R.id.btn_papua);
        btn24=findViewById(R.id.btn_papuabarat);
        btn25=findViewById(R.id.btn_riau);
        btn26=findViewById(R.id.btn_sulbar);
        btn27=findViewById(R.id.btn_sultengah);
        btn28=findViewById(R.id.btn_sulteng);
        btn29=findViewById(R.id.btn_sulut);
        btn30=findViewById(R.id.btn_sumbar);
        btn31=findViewById(R.id.btn_sumsel);
        btn32=findViewById(R.id.btn_sumut);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
        btn18.setOnClickListener(this);
        btn19.setOnClickListener(this);
        btn20.setOnClickListener(this);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn23.setOnClickListener(this);
        btn24.setOnClickListener(this);
        btn25.setOnClickListener(this);
        btn26.setOnClickListener(this);
        btn27.setOnClickListener(this);
        btn28.setOnClickListener(this);
        btn29.setOnClickListener(this);
        btn30.setOnClickListener(this);
        btn31.setOnClickListener(this);
        btn32.setOnClickListener(this);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(Wisata.this,MainActivity.class));
                        finish();
                        break;
                    case R.id.nav_promosi:
                        if (!user.isEmailVerified()) {
                            startActivity(new Intent(Wisata.this,Profile.class));
                            Toast.makeText(Wisata.this, "Silahkan Verifikasi Akun Anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            startActivity(new Intent(Wisata.this,Promosi.class));
                        }
                        break;
                    case R.id.nav_help:
                        startActivity(new Intent(Wisata.this,Help.class));
                        finish();
                        break;
                    case R.id.nav_profile:
                        if (!user.isEmailVerified()) {
                            startActivity(new Intent(Wisata.this,Profile.class));
                            Toast.makeText(Wisata.this, "Silahkan Verifikasi Akun Anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            startActivity(new Intent(Wisata.this,Profile.class));
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_aceh:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Aceh");
                Toast.makeText(Wisata.this, "Membuka wilayah Aceh", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_bali:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Bali");
                Toast.makeText(Wisata.this, "Membuka wilayah Bali", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_bangka:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Bangka");
                Toast.makeText(Wisata.this, "Membuka wilayah Bangka", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_banten:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Banten");
                Toast.makeText(Wisata.this, "Membuka wilayah Banten", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_bengkulu:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Bengkulu");
                Toast.makeText(Wisata.this, "Membuka wilayah Bengkulu", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_jogja:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Yogyakarta");
                Toast.makeText(Wisata.this, "Membuka wilayah Yogyakarta", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_jakarta:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "DKI Jakarta");
                Toast.makeText(Wisata.this, "Membuka wilayah DKI Jakarta", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_gorontalo:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Gorontalo");
                Toast.makeText(Wisata.this, "Membuka wilayah Gorontalo", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_jambi:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Jambi");
                Toast.makeText(Wisata.this, "Membuka wilayah Jambi", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_jabar:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Jawa Barat");
                Toast.makeText(Wisata.this, "Membuka wilayah Jawa Barat", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_jateng:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Jawa Tengah");
                Toast.makeText(Wisata.this, "Membuka wilayah Jawa Tengah", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_jatim:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Jawa Timur");
                Toast.makeText(Wisata.this, "Membuka wilayah Jawa Timur", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_kalbar:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Kalimantan Barat");
                Toast.makeText(Wisata.this, "Membuka wilayah Kalimantan Barat", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_kalsel:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Kalimantan Selatan");
                Toast.makeText(Wisata.this, "Membuka wilayah Kalimantan Selatan", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_kaltim:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Kalimantan Timur");
                Toast.makeText(Wisata.this, "Membuka wilayah Kalimantan Timur", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_kaltar:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Kalimantan Utara");
                Toast.makeText(Wisata.this, "Membuka wilayah Kalimantan Utara", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_kepri:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Kepulauan Riau");
                Toast.makeText(Wisata.this, "Membuka wilayah Kepulauan Riau", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_lampung:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Lampung");
                Toast.makeText(Wisata.this, "Membuka wilayah Lampung", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_maluku:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Maluku");
                Toast.makeText(Wisata.this, "Membuka wilayah Maluku", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_malukuUtara:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Maluku Utara");
                Toast.makeText(Wisata.this, "Membuka wilayah Maluku Utara", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_ntb:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Nusa Tenggara Barat");
                Toast.makeText(Wisata.this, "Membuka wilayah Nusa Tenggara Barat", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_ntt:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Nusa Tenggara Timur");
                Toast.makeText(Wisata.this, "Membuka wilayah Nusa Tenggara Timur", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_papua:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Papua");
                Toast.makeText(Wisata.this, "Membuka wilayah Papua", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_papuabarat:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Papua Barat");
                Toast.makeText(Wisata.this, "Membuka wilayah Papua Barat", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_riau:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Riau");
                Toast.makeText(Wisata.this, "Membuka wilayah Riau", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_sulbar:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Sulawesi Barat");
                Toast.makeText(Wisata.this, "Membuka wilayah Sulawesi Barat", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_sultengah:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Sulawesi Tengah");
                Toast.makeText(Wisata.this, "Membuka wilayah Sulawesi Tengah", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_sulteng:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Sulawesi Tenggara");
                Toast.makeText(Wisata.this, "Membuka wilayah Sulawesi Tenggara", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_sulut:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Sulawesi Utara");
                Toast.makeText(Wisata.this, "Membuka wilayah Sulawesi Utara", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_sumbar:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Sumatera Barat");
                Toast.makeText(Wisata.this, "Membuka wilayah Sumatera Barat", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_sumsel:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Sumatera Selatan");
                Toast.makeText(Wisata.this, "Membuka wilayah Sumatera Selatan", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.btn_sumut:
                i = new Intent(view.getContext(), DetailProvinsi.class);
                i.putExtra("Provinsi", "Sumatera Utara");
                Toast.makeText(Wisata.this, "Membuka wilayah Sumatera Utara", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
        }
    }
}