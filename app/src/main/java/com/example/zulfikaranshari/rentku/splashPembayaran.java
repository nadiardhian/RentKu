package com.example.zulfikaranshari.rentku;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splashPembayaran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_pembayaran);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashPembayaran.this, halamanUtama.class);
                startActivity(i);
                finish();

            }
        },5000);

    }
}
