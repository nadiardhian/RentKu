package com.example.zulfikaranshari.rentku;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PencarianAwal extends AppCompatActivity {
    TextView date, returnDate,tKota, t_lokasi;
    static String kendaraan;
    RecyclerView.Adapter adapter; //inisiasi variable
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    List<pencarianModel> kendaraanList = new ArrayList<>();
    static String tanggalPinjam, tanggalKembali;
    static int  iHarga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian_awal);
        //set title
        setTitle("Hasil Pencarian");
        progressDialog = new ProgressDialog(this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        tKota = (TextView) findViewById(R.id.tKota);
        tKota.setText(halamanUtama.kota);
        date = (TextView) findViewById(R.id.tanggalPinjam);
        returnDate = (TextView) findViewById(R.id.tanggalKembali);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView); //referensi variable
        t_lokasi = (TextView)findViewById(R.id.t_lokasi);
        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true); //referensi variable

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(PencarianAwal.this));
        Intent i = getIntent();
        tanggalKembali = i.getStringExtra("date_return");
        tanggalPinjam = i.getStringExtra("date");
        kendaraan = i.getStringExtra("kendaraan");
        iHarga = Integer.valueOf(i.getStringExtra("harga"));
        date.setText(tanggalPinjam);
        returnDate.setText(tanggalKembali);
        t_lokasi.setText(i.getStringExtra("metodeAntar"));
        databaseReference = FirebaseDatabase.getInstance().getReference(kendaraan);
        showData();

    }

    public void showData(){ //Method untuk menampilkan data

        databaseReference.addValueEventListener(new ValueEventListener() {//eksekusi data dari firebase
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                kendaraanList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //foreach memanggil data

                    pencarianModel imageUploadInfo = postSnapshot.getValue(pencarianModel.class); //get data dari firebase
                    // Toast.makeText(PencarianAwal.this, imageUploadInfo.getHarga(), Toast.LENGTH_LONG).show();
                    String data = String.valueOf(imageUploadInfo.getStatus());
                    String kota = String.valueOf(imageUploadInfo.getKota());
                    if (data.toLowerCase().equals("Tersedia".toLowerCase())&& kota.equals(halamanUtama.kota)) {
                        kendaraanList.add(imageUploadInfo); //add data ke listviews
                    }
                }
                adapter = new pencarianAdapter(PencarianAwal.this, kendaraanList);//set adapter

                recyclerView.setAdapter(adapter); //set adapter

                if(kendaraanList.isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PencarianAwal.this);
                    alertDialogBuilder.setMessage("Pencarian yang anda maksud tidak tersedia, silahkan kembali ke Halaman Utama");         //menggunakan pemanis untuk pesanan berhasil
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), halamanUtama.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                   // Toast.makeText(PencarianAwal.this, "wkwkwk", Toast.LENGTH_LONG).show();

                }

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.


            }
        });
    }
}
