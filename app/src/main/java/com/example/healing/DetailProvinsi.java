package com.example.healing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DetailProvinsi extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseUser user;
    RecyclerView recyclerView;
    ImageButton btn_back;
    ArrayList<ModelFormPromosi> formHomeArraylist;
    AdapterHome myAdapterHome;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    public String hProvinsi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_provinsi);
        btn_back=findViewById(R.id.btn_backDetailProvinsi);
        Intent data = getIntent();
        hProvinsi = data.getStringExtra("Provinsi");

        recyclerView = findViewById(R.id.recycler_view_provinsi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //progressDialog.setMessage("Mencari Lokasi...");
        //progressDialog.show();

        db = FirebaseFirestore.getInstance();
        formHomeArraylist = new ArrayList<ModelFormPromosi>();
        myAdapterHome = new AdapterHome(DetailProvinsi.this,formHomeArraylist);
        recyclerView.setAdapter(myAdapterHome);
        EventChangeListener();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailProvinsi.this,Wisata.class));
                finish();
            }
        });



    }

    private void EventChangeListener() {

        db.collection("wisata_promosi").whereEqualTo("Provinsi",hProvinsi).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                if(formHomeArraylist.isEmpty()){
                    Toast.makeText(DetailProvinsi.this, "Wilayah wisata belum tersedia !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}