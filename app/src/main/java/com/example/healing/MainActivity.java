package com.example.healing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FirebaseAuth fAuth;
    FirebaseUser user;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<ModelFormPromosi> formHomeArraylist;
    AdapterHome myAdapterHome;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    TextView hasil,txthasil,wilayah,txtwilayah;
    TextInputEditText pencarian_nama;

    public String hasil_pencarian,wilayah_pencarian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hasil=findViewById(R.id.result);
        txthasil=findViewById(R.id.txtresult);

        pencarian_nama=findViewById(R.id.txtPencarianNama);
        fAuth = FirebaseAuth.getInstance();



        recyclerView = findViewById(R.id.recycler_view_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //progressDialog.setMessage("Mencari Lokasi...");
        //progressDialog.show();

        db = FirebaseFirestore.getInstance();
        formHomeArraylist = new ArrayList<ModelFormPromosi>();
        myAdapterHome = new AdapterHome(MainActivity.this,formHomeArraylist);
        recyclerView.setAdapter(myAdapterHome);
        EventChangeListener();


        pencarian_nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasil_pencarian = pencarian_nama.getText().toString();
                if(pencarian_nama.getText().toString().isEmpty()){
                    hasil.setVisibility(View.GONE);
                    txthasil.setVisibility(View.GONE);
                    EventChangeListener();


                }else{
                    hasil.setVisibility(View.VISIBLE);
                    txthasil.setVisibility(View.VISIBLE);
                    hasil.setText(hasil_pencarian);
                    EventChangeListenerCek(hasil_pencarian);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Mencari Wisata...");
//        progressDialog.show();

        bottomNavigation = findViewById(R.id.bottomnav);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_wisata:
                        startActivity(new Intent(MainActivity.this,Wisata.class));
                        finish();
                        break;
                    case R.id.nav_promosi:
                        if (!user.isEmailVerified()) {
                            Toast.makeText(MainActivity.this, "Silahkan Verifikasi Akun Anda", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,Profile.class));
                            finish();
                        }else{
                            startActivity(new Intent(MainActivity.this,Promosi.class));
                        }
                        break;
                    case R.id.nav_help:
                        startActivity(new Intent(MainActivity.this,Help.class));
                        finish();
                        break;
                    case R.id.nav_profile:
                        if (!user.isEmailVerified()) {
                            startActivity(new Intent(MainActivity.this,Profile.class));
                            Toast.makeText(MainActivity.this, "Silahkan Verifikasi Akun Anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            startActivity(new Intent(MainActivity.this,Profile.class));
                        }
                        break;
                }
                return true;
            }
        });



    }


    private void EventChangeListener() {
        db.collection("wisata_promosi").orderBy("namaWisata").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    if(progressDialog.isShowing())progressDialog.dismiss();
                    Log.e("Database Error",error.getMessage());
                    return;
                }
                formHomeArraylist.clear();
                for (DocumentChange dc : value.getDocumentChanges()){
                    //formHomeArraylist.clear();
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        formHomeArraylist.add(dc.getDocument().toObject(ModelFormPromosi.class));
                    }
                    myAdapterHome.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });
    }

private void EventChangeListenerCek(String hasil_pencarian) {

    db.collection("wisata_promosi").orderBy("namaWisata").startAt(hasil_pencarian).endAt(hasil_pencarian+"\uf8ff").addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
            if(error != null){
                if(progressDialog.isShowing())progressDialog.dismiss();
                Log.e("Database Error",error.getMessage());
                return;
            }
            formHomeArraylist.clear();
            for (DocumentChange dc : value.getDocumentChanges()){

                if(dc.getType() == DocumentChange.Type.ADDED){
                    formHomeArraylist.add(dc.getDocument().toObject(ModelFormPromosi.class));

                }
                myAdapterHome.notifyDataSetChanged();

                if(progressDialog.isShowing())
                    progressDialog.dismiss();
            }
            if(formHomeArraylist.isEmpty()){
                Toast.makeText(MainActivity.this, "Wisata Tidak Ditemukan !!", Toast.LENGTH_SHORT).show();
            }
        }
    });

}


}