package com.example.zulfikaranshari.rentku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private FirebaseAuth mAuth;

    private DatabaseReference mDatabaseRef;
    private String TAG = "com.example.zulfikaranshari.rentku";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user");
        mAuth = FirebaseAuth.getInstance();
        mEmail = (EditText)findViewById(R.id.email);
        mPassword = (EditText)findViewById(R.id.password);
        progressDialog =  new ProgressDialog(MainActivity.this);
        if(mAuth.getCurrentUser() != null){

            // Finishing current Login Activity.
            finish();

            // Opening UserProfileActivity .
            Intent intent = new Intent(this, splashAwal.class);
            startActivity(intent);
        }

    }

    public void login(View view) {
        final String lEmail = mEmail.getText().toString();
        final String lPass = mPassword.getText().toString();
        progressDialog.setMessage("Please Wait");

        // Showing progressDialog.
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(lEmail, lPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent x = new Intent(MainActivity.this, halamanUtama.class);
                            startActivity(x);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    public void register(View view) {
        Intent x  = new Intent(MainActivity.this, Daftar.class);
        startActivity(x);
    }
}
