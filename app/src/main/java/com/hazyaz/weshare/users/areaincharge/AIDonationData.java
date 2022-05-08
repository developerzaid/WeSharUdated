package com.hazyaz.weshare.users.areaincharge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.maps;
import com.squareup.picasso.Picasso;

public class AIDonationData extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String key, userkey;
    TextView name, city, phone, itemname, itemdesc, areax, currentlocationx, xTrackDonation;
    ImageView imageViewd;
    String donation_uid, person_uid;
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

        donation_uid = intent.getStringExtra("donation_key");
        person_uid = intent.getStringExtra("person_key");

        Log.d("as23as AIDOnationData", "" + donation_uid + person_uid);


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

        if (acti.equals("DonaterHome")) {
            btn.setText("Track My Donation");
        } else if (acti.equals("DPHOME")) {
            btn.setText("Accept Delivery");
        } else {
            btn.setText("Accept Donation");
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (acti.equals("DonaterHome")) {
                    Intent i = new Intent(AIDonationData.this, maps.class);
                    i.putExtra("location", current_location);
                    startActivity(i);
                } else if (acti.equals("AIHome")) {
                    Log.d("insidebtton", "inside button");

                    Intent i = new Intent(AIDonationData.this, AssignDP.class);
                    i.putExtra("uid_person", person_uid);
                    i.putExtra("uid_donation", donation_uid);
                    Log.d("as23as AIDOnationinbside", "" + donation_uid + person_uid);

                    startActivity(i);
                } else if (acti.equals("DPHOME")) {
                    Toast.makeText(getApplicationContext(), "sdfsdf", Toast.LENGTH_LONG).show();
                }

            }
        });

        Picasso.get().load(Image).into(imageViewd);

        name.setText("User Name : " + uName);
        city.setText("Donation Location : " + area + ", " + uCity + ", " + state);
        phone.setText("User Phone : " + uPhone);

        itemname.setText("Donation Name : " + ItemName);
        itemdesc.setText("Donation Description : " + ItemDesc);
        currentlocationx.setText("Donation With : " + donation_with);
        xTrackDonation.setText("Current Donation Coordinates: " + current_location);


    }


    void updateDatabase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Donater").child(userkey).child("donations").child(key).child("Current Location").setValue("Area Incharge");


    }


}