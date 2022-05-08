package com.hazyaz.weshare.users.donater;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hazyaz.weshare.MainActivity;
import com.hazyaz.weshare.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class DonationForm extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 1;
    EditText donater_name, donater_city, donater_phone, donation_name, donation_desciption;
    Spinner donation_area;
    Button submitDonation, UploadImage;
    ProgressDialog progressDialog;
    Uri downloadUri;
    private StorageReference mStorage;
    private Uri mImageUri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donater_form);


        donation_name = findViewById(R.id.donation_name);
        donation_desciption = findViewById(R.id.donation_description);
        UploadImage = findViewById(R.id.ItemImagexxx);
        submitDonation = findViewById(R.id.submit_donation_form);

        progressDialog = new ProgressDialog(this, 3);

        submitDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Donation Request Sending");
                progressDialog.show();


                String don_name, don_desc;

                don_name = donation_name.getText().toString();
                don_desc = donation_desciption.getText().toString();

                if (don_name.equals("") || don_desc.equals("")) {
                    Toast.makeText(DonationForm.this, "fill all the data ", Toast.LENGTH_SHORT).show();
                }
                filledDonationForm(don_name, don_desc);
            }
        });


        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    Log.d("urdsf", "33333333333333333333");
                }


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("urdsf", "" + data.getData());
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Log.d("urdsf", "" + photo);
            mImageUri = getImageUri(getApplicationContext(), photo);
            Log.d("urdsf", "" + mImageUri);
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    void filledDonationForm(String donation_name, String donation_desc) {

        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data Please Wait...");
        progressDialog.show();


        mStorage = FirebaseStorage.getInstance().getReference();
        StorageReference filepath = mStorage.child("Images").child(mImageUri.getLastPathSegment());
        filepath.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                    putAllContent(donation_name, donation_desc);
                    System.out.println("Upload success: " + downloadUri);
                } else {

                }
            }
        });


    }


    void putAllContent(String donation_name, String donation_desc) {

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("donater").child(uid);
        HashMap<Object, String> hashMap = new HashMap<>();
        String key = reference.push().getKey();


        hashMap.put("donation_name", donation_name);
        hashMap.put("donation_desc", donation_desc);
        hashMap.put("timestamp", "" + System.currentTimeMillis());
        hashMap.put("donation_image", "" + downloadUri);

        hashMap.put("donation_with", "Donater");
        hashMap.put("current_location", MainActivity.lat + "," + MainActivity.lon);


        reference.child("donations").child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();

                    Toast.makeText(DonationForm.this, "Data Sent Succeddult ", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(DonationForm.this, "Cannot send data to server ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}







