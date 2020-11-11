package com.example.zulfikaranshari.rentku;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class FragmentProfilePenyewa extends Fragment {
    private TextView mName, mHP, mAddress, mFacebook, mLine;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseRef;
    List<PenyewaModel> penyewaList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_penyewa, container, false);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("penyewa");

        mName = (TextView) rootView.findViewById(R.id.tv_namaPenyewa);
        mHP = (TextView) rootView.findViewById(R.id.tv_nomorPenyewa);
        mAddress = (TextView) rootView.findViewById(R.id.tv_alamatPenyewa);
        mFacebook = (TextView) rootView.findViewById(R.id.tv_fbPenyewa);
        mLine = (TextView) rootView.findViewById(R.id.tv_linePenyewa);

        mHP.setEnabled(false);
        showData();

        return rootView;
    }

    public void showData() {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                penyewaList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //foreach memanggil data

                    PenyewaModel penyewaInfo = postSnapshot.getValue(PenyewaModel.class); //get data dari firebase
                   // String emailDB = mUser.getEmail().toString();
                    Intent x = getActivity().getIntent();
                    String namaPemilik = x.getStringExtra("namaPemilik");
                    String emailModel = penyewaInfo.getNama();
                    if(namaPemilik.equals(emailModel)){
                        penyewaList.add(penyewaInfo); //add data ke listviews
                        mName.setText(penyewaInfo.getNama());
                        mHP.setText(penyewaInfo.getNomor());
                        mAddress.setText(penyewaInfo.getAlamat());
                        mFacebook.setText(penyewaInfo.getFacebook());
                        mLine.setText(penyewaInfo.getLine());
//						Toast.makeText(getContext(),userInfo.getHp(), Toast.LENGTH_LONG).show();

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_penyewa, container, false);
    }*/
