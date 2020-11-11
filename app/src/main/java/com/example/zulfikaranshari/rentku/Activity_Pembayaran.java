package com.example.zulfikaranshari.rentku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.Inet4Address;

public class Activity_Pembayaran extends AppCompatActivity {
String merk,noPolisi,harga,namaPemilik,db,tanggalPinjam, tanggalKembali;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__pembayaran);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        //set title
        setTitle("Pembayaran");

        Intent i = getIntent();
        this.merk = i.getStringExtra("merk");
        this.noPolisi = i.getStringExtra("noPolisi");
        this.harga = i.getStringExtra("harga");
        this.namaPemilik = i.getStringExtra("namaPemilik");
        this.db = i.getStringExtra("db");
        tanggalPinjam = i.getStringExtra("tanggalPinjam");
        tanggalKembali = i.getStringExtra("tanggalKembali");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.home) {
//
//            return true;
//        }else if (id == R.id.profile){
//            startActivity(new Intent(this, MainActivity_Profile.class ));
//            return true;
//        }else if(id ==R.id.pembayaran){
//            startActivity(new Intent(this, Activity_Pembayaran.class));
//            return true;
//        }else if(id ==R.id.signout){
//            mAuth.signOut();
//            finish();
//
//            // Redirect to Login Activity after click on logout button.
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            return true;
//        }else{
//            startActivity(new Intent(this, riwayatHome.class));
//
//        }

        return super.onOptionsItemSelected(item);
    }

    public void alfa(View view) {
        Intent intent = new Intent(this, pembayaran.class);
        intent.putExtra("merk",this.merk);
        intent.putExtra("noPolisi",this.noPolisi);
        intent.putExtra("harga",this.harga);
        intent.putExtra("namaPemilik",this.namaPemilik);
        intent.putExtra("metode","Alfamart");
        intent.putExtra("db",db);
        intent.putExtra("tanggalPinjam",tanggalPinjam);
        intent.putExtra("tanggalKembali",tanggalKembali);

        startActivity(intent);
    }

    public void indomaret(View view) {
        Intent intent = new Intent(this, pembayaran.class);
        intent.putExtra("merk",this.merk);
        intent.putExtra("noPolisi",this.noPolisi);
        intent.putExtra("harga",this.harga);
        intent.putExtra("namaPemilik",this.namaPemilik);
        intent.putExtra("metode","Indomaret");
        intent.putExtra("db",db);
        intent.putExtra("tanggalPinjam",tanggalPinjam);
        intent.putExtra("tanggalKembali",tanggalKembali);

        startActivity(intent);
    }

    public void bca(View view) {
        Intent intent = new Intent(this, pembayaran.class);
        intent.putExtra("merk",this.merk);
        intent.putExtra("noPolisi",this.noPolisi);
        intent.putExtra("harga",this.harga);
        intent.putExtra("namaPemilik",this.namaPemilik);
        intent.putExtra("metode","BCA");
        intent.putExtra("db",db);
        intent.putExtra("tanggalPinjam",tanggalPinjam);
        intent.putExtra("tanggalKembali",tanggalKembali);

        startActivity(intent);
    }

    public void bni(View view) {
        Intent intent = new Intent(this, pembayaran.class);
        intent.putExtra("merk",this.merk);
        intent.putExtra("noPolisi",this.noPolisi);
        intent.putExtra("harga",this.harga);
        intent.putExtra("namaPemilik",this.namaPemilik);
        intent.putExtra("metode","BNI");
        intent.putExtra("db",db);
        intent.putExtra("tanggalPinjam",tanggalPinjam);
        intent.putExtra("tanggalKembali",tanggalKembali);

        startActivity(intent);
    }

    public void mandiri(View view) {
        Intent intent = new Intent(this, pembayaran.class);
        intent.putExtra("merk",this.merk);
        intent.putExtra("noPolisi",this.noPolisi);
        intent.putExtra("harga",this.harga);
        intent.putExtra("namaPemilik",this.namaPemilik);
        intent.putExtra("metode","Mandiri");
        intent.putExtra("db",db);
        intent.putExtra("tanggalPinjam",tanggalPinjam);
        intent.putExtra("tanggalKembali",tanggalKembali);

        startActivity(intent);
    }

    public void btnpesan(View view) {


    }
}
