package com.example.healing;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.MyViewHolder> {
    Context context;
    ArrayList<ModelFormPromosi> formHomeArraylist;

    public AdapterHome(Context context, ArrayList<ModelFormPromosi> formHomeArraylist) {
        this.context = context;
        this.formHomeArraylist = formHomeArraylist;
    }

    @NonNull
    @Override
    public AdapterHome.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_home,parent,false);
        return new AdapterHome.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHome.MyViewHolder holder, int position) {
        ModelFormPromosi dataHome = formHomeArraylist.get(position);
        holder.nama_wisataItem_home.setText(dataHome.namaWisata);

        FirebaseFirestore fStore;
        FirebaseUser user;
        Button btn_setuju;
        LocationManager locationManager;
        StorageReference storageReference;
        FirebaseAuth fAuth;

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();



        StorageReference profileRef = storageReference.child("gambarWisata/foto1/"+dataHome.namaWisata+".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.foto);
//                progressDialog.dismiss();
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DetailWisata.class);
                i.putExtra("namaWisata", dataHome.namaWisata.toString());
                i.putExtra("Provinsi", dataHome.Provinsi.toString());
                i.putExtra("Kabupaten", dataHome.Kabupaten.toString());
                i.putExtra("Kecamatan", dataHome.Kecamatan.toString());
                i.putExtra("Alamat", dataHome.Alamat.toString());
                i.putExtra("KTP", dataHome.KTP.toString());
                i.putExtra("Telepon", dataHome.Telepon.toString());
                i.putExtra("hTiket", dataHome.hTiket.toString());
                i.putExtra("Deskripsi", dataHome.Deskripsi.toString());
                context.startActivity(i);
            }
        });




    }

    @Override
    public int getItemCount() {
        return formHomeArraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama_wisataItem_home;
        ImageView foto;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_wisataItem_home = itemView.findViewById(R.id.nama_wisata_item_home);
            foto = itemView.findViewById(R.id.foto_item_home);
            btn = itemView.findViewById(R.id.btn_detail_home);
        }
    }
}
