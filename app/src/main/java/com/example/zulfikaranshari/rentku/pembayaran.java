package com.example.zulfikaranshari.rentku;

import com.example.zulfikaranshari.rentku.model.*;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.zulfikaranshari.rentku.model.ActionSMS;
import com.example.zulfikaranshari.rentku.network.DataProvider;
import com.example.zulfikaranshari.rentku.network.DataService;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pembayaran extends AppCompatActivity {
    public static DataService nService;
    TextView tMerk, total, tNoPolisi, tMetode, tNamaPemilik, tTanggalPinjam, tTanggalKembali;
    public String merk, noPolisi, harga, namaPemilik, db,metode, tanggalPinjam, tanggalKembali, pesan, pesanEmail, key;
    static String nohp;
    DatabaseReference databaseReference;
    List<messageModel> keyList = new ArrayList<>();
    Switch sw;
    Button btnPesan;
    FirebaseAuth firebaseAuth ; //inisiasi variable
    FirebaseUser firebaseUser;
    DatabaseReference databaseReferences;
    DatabaseReference databaseReferencesUser;
    ProgressDialog pdialog = null;
   messageModel keyModel = new messageModel();
    Session session = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        firebaseAuth = FirebaseAuth.getInstance(); //referensi variable
        firebaseUser = firebaseAuth.getCurrentUser(); //referensi variable
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
        Intent i = getIntent();
        merk = i.getStringExtra("merk");
        noPolisi = i.getStringExtra("noPolisi");
        harga = i.getStringExtra("harga");
        namaPemilik = i.getStringExtra("namaPemilik");
        db = i.getStringExtra("db");
        metode =  i.getStringExtra("metode");
        tanggalPinjam = i.getStringExtra("tanggalPinjam");
        tanggalKembali = i.getStringExtra("tanggalKembali");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rentalkuapps@gmail.com", "tubes12345");
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference(db);
        databaseReferences = FirebaseDatabase.getInstance().getReference("pemesanan");
        databaseReferencesUser = FirebaseDatabase.getInstance().getReference("user");

        tMerk = (TextView) findViewById(R.id.tMerk);
        total = (TextView) findViewById(R.id.total);
        tNoPolisi = (TextView) findViewById(R.id.tNoPolisi);
        tMetode = (TextView) findViewById(R.id.tMetode);
        tNamaPemilik = (TextView) findViewById(R.id.tNamaPemilik);
        tTanggalKembali = (TextView) findViewById(R.id.tTanggalKembali);
        tTanggalPinjam = (TextView) findViewById(R.id.tTanggalPinjam);
        sw = (Switch)findViewById(R.id.switch1);
        btnPesan = (Button) findViewById(R.id.btnPesan);
        total.setText("Rp."+harga+",-");
        tNoPolisi.setText(noPolisi);
        tNamaPemilik.setText(namaPemilik);
        tMetode.setText(metode);
        tMerk.setText(merk);
        btnPesan.setEnabled(false);
        tTanggalKembali.setText(tanggalKembali);
        tTanggalPinjam.setText(tanggalPinjam);

       this.pesan = "Selamat pemesanan anda berhasil \n"
                +"Merk: "+merk+" ("+noPolisi+")"
        +"\n Total Harga: Rp."+harga+"\n Transfer Melalui: "+metode+"\nHarap segera melakukan pembayaran\n";


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    btnPesan.setEnabled(true);
                } else {
                    btnPesan.setEnabled(false);

                }
            }
        });

    }

public void generateKey(){
        this.key = generateRandomChars(
                "1234567890", 9);

}

    public static String generateRandomChars(String candidateChars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }
    public void btnpesan(View view) {
    generateKey();
        pembayaranModel pembayaranModel = new pembayaranModel(firebaseUser.getEmail().toString(),harga,merk,noPolisi,
                "Belum Lunas",namaPemilik,tanggalKembali,tanggalPinjam,key);
        String id = databaseReferences.push().getKey(); //refrensikan variable ke id
        databaseReferences.child(id).setValue(pembayaranModel); //set child firebase


        Query applesQuery = databaseReference.orderByChild("no_polisi").equalTo(noPolisi);
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                   // messageModel imageUploadInfo = appleSnapshot.getValue(messageModel.class);
                    appleSnapshot.getRef().child("status").setValue("Tidak Tersedia");
                    //key = appleSnapshot.getRef().getKey();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
  // key = keyList.get(0).toString();

        pesanEmail = "<strong>Selamat pemesanan anda berhasil</strong> <br>"
                +"<strong>Merk:</strong> "+merk+" ("+noPolisi+")"
                +"<br><strong>Total Harga:</strong> Rp."+harga+"<br><strong>Transfer Melalui:</strong> "
                +metode+"<br>"+"<strong>Kode Pemesanan: </strong>"+key+"<br>Harap segera melakukan pembayaran<br>";
       // coba();
        if(!this.isFinishing()) {
            pdialog = ProgressDialog.show(this, "", "Sedang memproses...", true);
        }
        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
       // Intent i = new Intent(this,splashPembayaran.class);
        //startActivity(i);
    }

    public void coba() {

        Query userQuery = databaseReferencesUser.orderByChild("email").equalTo(firebaseUser.getEmail().toString());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                   pembayaran.nohp = appleSnapshot.child("hp").getValue().toString();

                    Call<List<ActionSMS>> call = nService.SendAction("17cerp", "wkwkwk",
                            pembayaran.nohp ,pembayaran.this.pesan);
                    call.enqueue(new Callback<List<ActionSMS>>() {
                        @Override
                        public void onResponse(Call<List<ActionSMS>> call, Response<List<ActionSMS>> response) {
                        }

                        @Override
                        public void onFailure(Call<List<ActionSMS>> call, Throwable t) {
                        }
                    } );

                  // Toast.makeText(pembayaran.this,  pembayaran.nohp, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });



    }
    protected void onDestroy(){
        super.onDestroy();
        if(pdialog != null && pdialog.isShowing())
            pdialog.cancel();
    }
    public void cabs(View view) {
    generateKey();
        Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();

    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("rentalkuapps@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(firebaseUser.getEmail().toString()));
                message.setSubject("Pemberitahuan Pembayaran");
                message.setContent(pesanEmail, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent i = new Intent(getApplicationContext(),splashPembayaran.class);
           // pdialog.dismiss();
            startActivity(i);
            finish();
           // Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }

}
