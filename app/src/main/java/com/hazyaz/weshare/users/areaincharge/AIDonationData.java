package com.hazyaz.weshare.users.areaincharge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.maps;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class AIDonationData extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    String key,userkey;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    TextView name,city,phone,itemname,itemdesc, areax,currentlocationx,xTrackDonation;
    ImageView imageViewd;
    Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_donation_data);

        Intent intent = getIntent();
        String uName = intent.getStringExtra("name");
        String uCity = intent.getStringExtra("city");
        String area = intent.getStringExtra("area");
        String state = intent.getStringExtra("state");
        String uPhone = intent.getStringExtra("phone");
        String uEmail = intent.getStringExtra("email");


        String ItemName = intent.getStringExtra("itemname");
        String ItemDesc = intent.getStringExtra("itemdesc");
        String time = intent.getStringExtra("time");
        String Image = intent.getStringExtra("Image");
        String donation_with = intent.getStringExtra("donation_with");
        String current_location = intent.getStringExtra("current_location");

        String acti = intent.getStringExtra("activity");


        name = findViewById(R.id.xUserName);
        phone = findViewById(R.id.xUserPhone);
        itemname = findViewById(R.id.xItemName);
        itemdesc = findViewById(R.id.xItemDesc);
        city = findViewById(R.id.xUserCity);

        currentlocationx = findViewById(R.id.xCurrentLocation);
        imageViewd = findViewById(R.id.singleDonationImg);
        xTrackDonation = findViewById(R.id.xTrackDonation);

        Button btn = findViewById(R.id.xacceptdonation);

        if(acti.equals("DonaterHome")){
            btn.setText("Track My Donation");
        }
        else{
            btn.setText("Accept Donation");
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(acti.equals("DonaterHome")){
                    Intent i = new Intent(AIDonationData.this, maps.class);
                    i.putExtra("location",current_location);
                    startActivity(i);
                }
                else if(acti.equals("AIHome")){

                }

            }
        });

        Picasso.get().load(Image).into(imageViewd);

        name.setText("User Name : "+uName);
        city.setText("Donation Location : "+area+", "+uCity+", "+state);
        phone.setText("User Phone : "+uPhone);

        itemname.setText("Donation Name : "+ItemName);
        itemdesc.setText("Donation Description : "+ItemDesc);
        currentlocationx.setText("Donation With : "+donation_with);
        xTrackDonation.setText("Current Donation Coordinates: " + current_location);





    }




    void updateDatabase(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Donater").child(userkey).child("donations").child(key).child("Current Location").setValue("Area Incharge");


    }



}