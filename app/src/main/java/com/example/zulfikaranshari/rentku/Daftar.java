package com.example.zulfikaranshari.rentku;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Daftar extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private EditText mRePassword;
    private EditText mEmail;
    private EditText mHp;
    private EditText mName;
    private Button mButtonImage;
    private String uid;
    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    ProgressDialog progressDialog;
    private Uri mImageUri;
    public static Uri imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user");
        mStorageRef = FirebaseStorage.getInstance().getReference("user");

        mUsername = (EditText) findViewById(R.id.username_field);
        mPassword = (EditText) findViewById(R.id.password_field);
        mRePassword = (EditText) findViewById(R.id.repassword_field);
        mEmail = (EditText) findViewById(R.id.email_field);
        mHp = (EditText) findViewById(R.id.hp_field);
        mName = (EditText) findViewById(R.id.name_field);





    }

    public void register(View view) {
       String email = mEmail.getText().toString();
       String password = mPassword.getText().toString();
       String repassword = mRePassword.getText().toString();


       if (password.equals(repassword)){
           mAuth.createUserWithEmailAndPassword(email, password)
                   .addOnCompleteListener(Daftar.this, new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String uid = task.getResult().getUser().getUid();
                        String email = mEmail.getText().toString();
                        String username = mUsername.getText().toString();
                        String hp = mHp.getText().toString();
                        String name = mName.getText().toString();
                        String url = "https://firebasestorage.googleapis.com/v0/b/rentku-d1b61.appspot.com/o/profile.png?alt=media&token=7bd6c34f-ca02-44bb-883b-02334c2361a3";
                        String address = "-";
//                        Uri imageURL;

//                            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
//                                    + "." + getFileExtension(mImageUri));
//                            mUploadTask = fileReference.putFile(mImageUri)
//                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                            //upload data to DB
//                                            Daftar.imageURL = taskSnapshot.getDownloadUrl();
//
//                                        }
//                                    });

                        UserModel user = new UserModel(uid, username, name, email, hp, url, address) ;

                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(user);

                        Toast.makeText(Daftar.this, "Register success", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Daftar.this, halamanUtama.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(Daftar.this, "Register failed", Toast.LENGTH_LONG).show();
                    }

               }
           });
       }else{
           Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show();
       }


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

//            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    public void ProfileUpload(){

    }


}
