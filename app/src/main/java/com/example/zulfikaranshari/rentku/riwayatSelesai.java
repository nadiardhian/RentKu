package com.example.zulfikaranshari.rentku;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class riwayatSelesai extends android.support.v4.app.Fragment {
    TextView status;
    DatabaseReference databaseReference; //inisiasi variable
    RecyclerView.Adapter adapter; //inisiasi variable
    RecyclerView recyclerView; //inisiasi variable
    List<pembayaranModel> komenList = new ArrayList<>();
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth ; //inisiasi variable
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_riwayat_selesai, container, false);
        //super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rchomeuser); //referensi variable
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true); //referensi variable
        firebaseAuth = FirebaseAuth.getInstance(); //referensi variable
        firebaseUser = firebaseAuth.getCurrentUser();
        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference("pemesanan");
        showData();
        return rootView;
    }

    public void showData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                komenList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //foreach memanggil data

                    pembayaranModel imageUploadInfo = postSnapshot.getValue(pembayaranModel.class); //get data dari firebase
                    //Toast.makeText(DetailGambar.this, imageUploadInfo.getUserKomen(), Toast.LENGTH_LONG).show();
                    if(imageUploadInfo.getUser().equals(firebaseUser.getEmail().toString()) && imageUploadInfo.getStatus().equals("Selesai")){
                        komenList.add(imageUploadInfo); //add data ke listviews

                    }

                }
                adapter = new BelumLunasAdapter(getContext(), komenList);//set adapter

                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
}
