package com.example.healing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healing.ModelFormPromosi;
import com.example.healing.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterFormPromosi extends RecyclerView.Adapter<AdapterFormPromosi.MyViewHolder>{
    Context context;
    ArrayList<ModelFormPromosi> formPromosiArraylist;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public AdapterFormPromosi(Context context, ArrayList<ModelFormPromosi> formPromosiArraylist) {
        this.context = context;
        this.formPromosiArraylist = formPromosiArraylist;
        firestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_form_admin,parent,false);
        return new AdapterFormPromosi.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelFormPromosi dataPromosi = formPromosiArraylist.get(position);
        holder.nama_wisataItem.setText(dataPromosi.namaWisata);
        holder.provinsiItem.setText(dataPromosi.Provinsi);
        holder.kabupatenItem.setText(dataPromosi.Kabupaten);
        holder.kecamatanItem.setText(dataPromosi.Kecamatan);

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DetailPromosi.class);
                i.putExtra("namaWisata", dataPromosi.namaWisata.toString());
                i.putExtra("Provinsi", dataPromosi.Provinsi.toString());
                i.putExtra("Kabupaten", dataPromosi.Kabupaten.toString());
                i.putExtra("Kecamatan", dataPromosi.Kecamatan.toString());
                i.putExtra("Alamat", dataPromosi.Alamat.toString());
                i.putExtra("KTP", dataPromosi.KTP.toString());
                i.putExtra("Telepon", dataPromosi.Telepon.toString());
                i.putExtra("hTiket", dataPromosi.hTiket.toString());
                i.putExtra("Deskripsi", dataPromosi.Deskripsi.toString());
                i.putExtra("id", dataPromosi.id.toString());
                i.putExtra("EmailP", dataPromosi.EmailP.toString());

                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return formPromosiArraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama_wisataItem,provinsiItem,kabupatenItem,kecamatanItem,btn_detail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_wisataItem = itemView.findViewById(R.id.nama_wisata_item);
            btn_detail = itemView.findViewById(R.id.btn_detail);
            provinsiItem = itemView.findViewById(R.id.provinsi_item);
            kabupatenItem = itemView.findViewById(R.id.kabupaten_item);
            kecamatanItem = itemView.findViewById(R.id.kecamatan_item);

        }
    }
}

