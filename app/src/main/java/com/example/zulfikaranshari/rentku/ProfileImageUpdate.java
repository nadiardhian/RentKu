package com.example.zulfikaranshari.rentku;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ProfileImageUpdate extends AppCompatActivity {

    ImageView SelectImage;
    Button ChooseButton, UploadButton;
    Uri FilePathUri;
    int Image_Request_Code = 7;
    String Storage_Path = "profile/";
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth ; //inisiasi variable
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_update);
        ChooseButton = (Button)findViewById(R.id.ButtonChooseImage);
        UploadButton = (Button)findViewById(R.id.ButtonUploadImage);


        // Assign ID'S to image view.
        SelectImage = (ImageView)findViewById(R.id.ShowImageView);
        firebaseAuth = FirebaseAuth.getInstance(); //referensi variable
        firebaseUser = firebaseAuth.getCurrentUser(); //referensi variable
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        ChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to upload selected image on Firebase storage.
                UploadImageFileToFirebaseStorage();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                ChooseButton.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {



            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.

                            Query applesQuery = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail().toString());
                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                        // messageModel imageUploadInfo = appleSnapshot.getValue(messageModel.class);
                                        appleSnapshot.getRef().child("url").setValue(taskSnapshot.getDownloadUrl().toString());
                                        //key = appleSnapshot.getRef().getKey();

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Log.e(TAG, "onCancelled", databaseError.toException());
                                }
                            });


                            // Hiding the progressDialog after done uploading.

                            // Showing toast message after done uploading.

                            Intent i = new Intent(ProfileImageUpdate.this,MainActivity_Profile.class);
                            startActivity(i);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            //progressDialog.dismiss();

                            // Showing exception erro message.
                            //Toast.makeText(TambahGambar.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            //progressDialog.setTitle("Image is Uploading...");

                        }
                    });

        }
        else {

            Toast.makeText(ProfileImageUpdate.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }

}





