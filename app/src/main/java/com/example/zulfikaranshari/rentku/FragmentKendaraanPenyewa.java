package com.example.zulfikaranshari.rentku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentKendaraanPenyewa extends android.support.v4.app.Fragment {
    DatabaseReference databaseReference; //inisiasi variable
    RecyclerView.Adapter adapter; //inisiasi variable
    RecyclerView recyclerView; //inisiasi variable
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth ; //inisiasi variable
    FirebaseUser firebaseUser;
    RecyclerView rv;

    List<pencarianModel> pemilikList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_kendaraan_penyewa, container, false);

        progressDialog = new ProgressDialog(getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvkendaraan); //referensi variable
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true); //referensi variable
        firebaseAuth = FirebaseAuth.getInstance(); //referensi variable
        firebaseUser = firebaseAuth.getCurrentUser();
        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference("pemilik");
        showData();
        return rootView;
    }
    public void showData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { //foreach memanggil data
                    Intent x = getActivity().getIntent();
                    String namaPemilik = x.getStringExtra("namaPemilik");
                    pencarianModel pemilikInfo = postSnapshot.getValue(pencarianModel.class); //get data dari firebase
                    String pemilik = String.valueOf(pemilikInfo.getNama_pemilik());
                   // Toast.makeText(getContext(), namaPemilik, Toast.LENGTH_LONG).show();
                    if(namaPemilik.equals(pemilik)){
                        pemilikList.add(pemilikInfo); //add data ke listviews

                    }
                    adapter = new pencarianAdapter(getContext(), pemilikList);//set adapter

                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}