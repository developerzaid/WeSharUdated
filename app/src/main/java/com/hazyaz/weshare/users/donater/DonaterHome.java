package com.hazyaz.weshare.users.donater;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hazyaz.weshare.R;

import java.util.ArrayList;
import java.util.Objects;


public class DonaterHome extends AppCompatActivity {
    ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseRef;
    FirebaseAuth mAuth;
    ArrayList<ArrayList<String>> donations = new ArrayList<ArrayList<String>>();
    ArrayList<String> personData = new ArrayList<>();
    MyListAdapter adapter;
    RecyclerView recyclerView;
    TextView uName, uPhone, uCity, uEmail, totDonations;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donater_home);
        mAuth = FirebaseAuth.getInstance();


        uName = findViewById(R.id.UName);
//        Address is City
        uCity = findViewById(R.id.UCity);

        uPhone = findViewById(R.id.UPhone);
        uEmail = findViewById(R.id.UEmail);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        adapter = new MyListAdapter(donations, "DonaterHome", getApplicationContext(), personData);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
//

        progressDialog = new ProgressDialog(this, 4);


        Button donate = findViewById(R.id.donate_button);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DonaterHome.this, DonationForm.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {

            startActivity(new Intent(DonaterHome.this, DonaterLogin.class));

        } else {
            String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("donater").child(uid).child("donations");
            databaseRef = firebaseDatabase.getReference("donater").child(uid);
            getUserInfo();
            getDonationInfo();

        }


    }

    void getUserInfo() {

        databaseRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("name").getValue().toString();
                String area = snapshot.child("area").getValue().toString();
                String city = snapshot.child("city").getValue().toString();
                String state = snapshot.child("state").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();
                String email = snapshot.child("email").getValue().toString();

                personData.add(name);
                personData.add(area);
                personData.add(city);
                personData.add(state);
                personData.add(phone);
                personData.add(email);

                uName.setText("Name: " + name);
                uCity.setText("Address: " + area + ", " + city + ", " + state);
                uPhone.setText("Phone no: " + phone);
                uEmail.setText("Email: " + email);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    void getDonationInfo() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dsp : snapshot.getChildren()) {

                    ArrayList<String> data = new ArrayList<>();


                    String item_name = dsp.child("donation_name").getValue().toString();
                    String item_desc = dsp.child("donation_desc").getValue().toString();
                    String donatedOn = dsp.child("timestamp").getValue().toString();
                    String Imagecc = dsp.child("donation_image").getValue().toString();
                    String donationWith = dsp.child("donation_with").getValue().toString();
                    String currentLocation = dsp.child("current_location").getValue().toString();


                    data.add(item_name);
                    data.add(item_desc);
                    data.add(donatedOn);
                    data.add(Imagecc);

                    data.add(donationWith);
                    data.add(currentLocation);

                    donations.add(data);
                    adapter.notifyDataSetChanged();


//                    Toast.makeText(DonaterHome.this,""+data,Toast.LENGTH_LONG).show();

                }

                String key = snapshot.getKey();

                // String name = snapshot.child(key).child("Donater Name").getValue().toString();
                // after getting the value we are setting
                // our value to our text view in below line.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(DonaterHome.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }


}