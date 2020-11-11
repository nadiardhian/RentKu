package com.example.zulfikaranshari.rentku;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

public class halamanUtama extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private EditText date, returnDate;
    private RadioGroup radioGroup;
    String lanjut = "pemilik";
    static String kota, metode;
    String tglMulai="0", tglSelesai="0", sTotalHarga="0";
    int iTotalHarga ;
    List<UserModel> userList = new ArrayList<>();
    private static final String TAG = "halamanUtama";
    String profilePic;
    String name;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new DrawerBuilder().withActivity(this).build();
        //set title
        setTitle("Pemesanan");

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        date = (EditText) findViewById(R.id.rentDate);
        returnDate = (EditText) findViewById(R.id.returnDate);
        Intent i = getIntent();
        // Toast.makeText(getApplicationContext() , i.getStringExtra("toast"), Toast.LENGTH_SHORT).show();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });
        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogReturn(view);
            }
        });
        spinner();
        spinnerMetode();
        if (isServicesOK()) {
            init();
        }
        showData();
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(name).withEmail(mAuth.getCurrentUser().getEmail()).withIcon(getResources().getDrawable(R.drawable.profileheader))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Profile");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Riwayat");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Tentang Kami");
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("FAQ");


//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5

                )
                .withSelectedItem(1)
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position){
                            case 1:
                                startActivity(new Intent(halamanUtama.this, halamanUtama.class));
//                                Toast.makeText(halamanUtama.this, "Home", Toast.LENGTH_SHORT).show();
                                break;

                            case 2:
                                startActivity(new Intent(halamanUtama.this, MainActivity_Profile.class));
//                                Toast.makeText(halamanUtama.this, "Profile", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                startActivity(new Intent(halamanUtama.this, riwayatHome.class));
//                                Toast.makeText(halamanUtama.this, "Riwayat", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                startActivity(new Intent(halamanUtama.this, about.class));
//                                Toast.makeText(halamanUtama.this, "Tentang Kami", Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                                startActivity(new Intent(halamanUtama.this, faq.class));
//                                Toast.makeText(halamanUtama.this, "FAQ", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                })
                .build();

    }


    public void harga() {
        tglSelesai = returnDate.getText().toString();
        String[] selesai = tglSelesai.split("/");
        tglSelesai = selesai[0];
        tglMulai = date.getText().toString();
        String[] mulai = tglMulai.split("/");
        tglMulai = mulai[0];
        if(tglSelesai!=""||tglMulai!="") {
            this.iTotalHarga = Integer.valueOf(tglSelesai) - Integer.valueOf(tglMulai);
            this.sTotalHarga = String.valueOf(this.iTotalHarga);
        }
    }

    public void Pesan(View view) {
        Intent intent;
        int selectedjenis = radioGroup.getCheckedRadioButtonId();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DateTimePickers();
        newFragment.show(getSupportFragmentManager(), "date Picker");
    }

    public void showDatePickerDialogReturn(View v) {
        DialogFragment newFragment = new DateReturn();
        newFragment.show(getSupportFragmentManager(), "date Picker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        // Assign the concatenated strings to dateMessage.
        String dateMessage = (day_string + "/" +
                month_string + "/" + year_string);
        date.setText(dateMessage);
       // this.tglMulai = day_string;
        // returnDate.setText(dateMessage);
    }

    public void processDatePickerResultReturn(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        //this.tglSelesai = day_string;
        // Assign the concatenated strings to dateMessage.
        String dateMessage = (day_string + "/" +
                month_string + "/" + year_string);
        // date.setText(dateMessage);
        returnDate.setText(dateMessage);
    }

    public void spinner() {
        Spinner spinner = (Spinner) findViewById(R.id.optionspinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(halamanUtama.this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }


    public void spinnerMetode() {
        Spinner spinner = (Spinner) findViewById(R.id.optionmetodespinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(halamanUtama.this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.metode, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        spinner.setOnItemSelectedListener(new metodeSpinner());

    }

    class metodeSpinner implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            metode = parent.getItemAtPosition(position).toString();
            //Toast.makeText(v.getContext(), "Your choose :" + metode,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void riwayat(View view) {
        Intent intent = new Intent(this, riwayatHome.class);
        startActivity(intent);
    }

    public void cariRental(View view) {

        harga();
        if (date.getText().toString().equals("") || returnDate.getText().toString().equals("") || kota.equals("Pilih Kota")
                || tglMulai.equals(tglSelesai) || tglMulai.equals("0") || tglSelesai.equals("0")) {
            Snackbar snackbar = Snackbar
                    .make(view, "Whoops, isi semua form dengan benar yaa..", Snackbar.LENGTH_LONG);

            snackbar.show();
        } else {

            if (metode.equals("Ambil di Pool RentalKu")) {
                Intent intent = new Intent(this, PencarianAwal.class);
                intent.putExtra("kendaraan", lanjut);
                intent.putExtra("date", date.getText().toString());
                intent.putExtra("date_return", returnDate.getText().toString());
                intent.putExtra("harga", this.sTotalHarga);
                intent.putExtra("metodeAntar", "Pool RentKu");
                startActivity(intent);
            } else if (metode.equals("Antar ke Lokasi Saya")) {
                if (isServicesOK()) {
                    Intent intent = new Intent(this, MapActivity.class);
                    intent.putExtra("kendaraan", lanjut);
                    intent.putExtra("date", date.getText().toString());
                    intent.putExtra("date_return", returnDate.getText().toString());
                    intent.putExtra("harga", this.sTotalHarga);
                    startActivity(intent);
                }
            }
        }
    }

    public void mobil(View view) {
        this.lanjut = "pemilik";
    }

    public void motor(View view) {
        this.lanjut = "pemilik_motor";
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

        if (id == R.id.signout) {
            mAuth.signOut();
            finish();

            // Redirect to Login Activity after click on logout button.
            Intent intent = new Intent(halamanUtama.this, MainActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.kota = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void init() {
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(halamanUtama.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void showData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //foreach memanggil data

                    UserModel userInfo = postSnapshot.getValue(UserModel.class); //get data dari firebase
                    String emailDB = firebaseUser.getEmail().toString();
                    String emailModel = userInfo.getEmail().toString();
                    if(emailDB.equals(emailModel)){
                        userList.add(userInfo); //add data ke listviews
                        profilePic = userInfo.getUrl().toString();
                        name = userInfo.getName().toString();
                        //Toast.makeText(halamanUtama.this, name, Toast.LENGTH_SHORT).show();


                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
