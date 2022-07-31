package com.example.healing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.healing.AdapterFormPromosi;
import com.example.healing.ModelFormPromosi;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FormAdmin extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ModelFormPromosi> formPromosiArraylist;
    AdapterFormPromosi myAdapterPromosi;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_admin);
        recyclerView = findViewById(R.id.recycler_view_promosi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mencari Lokasi...");
        progressDialog.show();

        db = FirebaseFirestore.getInstance();
        formPromosiArraylist = new ArrayList<ModelFormPromosi>();
        myAdapterPromosi = new AdapterFormPromosi(FormAdmin.this,formPromosiArraylist);
        recyclerView.setAdapter(myAdapterPromosi);
        EventChangeListener();

    }
    private void EventChangeListener() {

        db.collection("wisata").orderBy("id").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    if(progressDialog.isShowing())progressDialog.dismiss();
                    Log.e("Database Error",error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){

                    if(dc.getType() == DocumentChange.Type.ADDED){
                        formPromosiArraylist.add(dc.getDocument().toObject(ModelFormPromosi.class));
                    }
                    myAdapterPromosi.notifyDataSetChanged();

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
                if(formPromosiArraylist.isEmpty()){
                    Toast.makeText(FormAdmin.this, "Promosi Wisata Kosong !!", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            }
        });
    }
}