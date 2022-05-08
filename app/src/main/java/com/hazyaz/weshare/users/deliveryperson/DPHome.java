package com.hazyaz.weshare.users.deliveryperson;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.donater.MyListAdapter;

import java.util.ArrayList;


public class DPHome extends AppCompatActivity {
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    MyListAdapter adapter;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference, innderDB, userDatabase, UserDonations;
    String xdonater, xdonation;
    ArrayList<String> donationPerson = new ArrayList<>();
    ArrayList<ArrayList<String>> donationsArray = new ArrayList<ArrayList<String>>();
    private Button mLocationReq;
    TextView uName, uPhone, uCity, uEmail, totDonations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dp_home);

        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("delivery_person");
        innderDB = mFirebaseDatabase.getReference("delivery_person");
        userDatabase = mFirebaseDatabase.getReference("donater");
        UserDonations = mFirebaseDatabase.getReference("donater");


        Toolbar toolbar = findViewById(R.id.toolbar54);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DELIVERY HOME");

        getAllDeliveryDonations();
        getEachDonations();

        uName = findViewById(R.id.UNamew);
//        Address is City
        uCity = findViewById(R.id.UCityw);
        uPhone = findViewById(R.id.UPhonew);
        uEmail = findViewById(R.id.UEmailw);

         getUserInfo();

        recyclerView = findViewById(R.id.delivery_recyclerviewDelivery);
        adapter = new MyListAdapter(donationsArray, "DPHOME", getApplicationContext(), donationPerson);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(DPHome.this, DPLogin.class));
        }
    }
    void getUserInfo() {


                uName.setText("Rahul Verma");
                uEmail.setText("rahul912@gmail.com");
                uPhone.setText("+91 798 548 6649");
                uCity.setText("Yashwant Nagar, Nalasopara West, Phalgar, Maharashtra 401203");


    }







    void getAllDeliveryDonations() {

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot isp : snapshot.getChildren()) {

                    mDatabaseReference.child(isp.getKey()).child("donations").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot lsp : snapshot.getChildren()) {
                                xdonater = lsp.getKey();
                                xdonation = lsp.child("donation_object").getValue().toString();

                                Log.d("getalldeiver", " " + xdonation + xdonater);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    void getEachDonations() {
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dsp : snapshot.getChildren()) {

                    String name = dsp.child("name").getValue().toString();
                    String area = "";
                    String city = dsp.child("city").getValue().toString();
                    String state = dsp.child("state").getValue().toString();
                    String phone = dsp.child("phone").getValue().toString();
                    String email = dsp.child("email").getValue().toString();

                    donationPerson.add(name);
                    donationPerson.add(area);
                    donationPerson.add(city);
                    donationPerson.add(state);
                    donationPerson.add(phone);
                    donationPerson.add(email);
                    donationPerson.add(mAuth.getUid());

                    Log.d("donation_person",name+state+"   "+mAuth.getUid());

                    userDatabase.child(dsp.getKey()).child("donations").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot isp : snapshot.getChildren()) {

                                        ArrayList<String> data = new ArrayList<>();

                                        String donationName = isp.child("donation_name").getValue().toString();
                                        String donationDesc = isp.child("donation_desc").getValue().toString();
                                        String timestamp = isp.child("timestamp").getValue().toString();
                                        String ImageXX = isp.child("donation_image").getValue().toString();
                                        String donationWith = isp.child("donation_with").getValue().toString();
                                        String CurrentLocation = isp.child("current_location").getValue().toString();

                                        data.add(donationName);
                                        data.add(donationDesc);
                                        data.add(timestamp);
                                        data.add(ImageXX);
                                        data.add(donationWith);
                                        data.add(CurrentLocation);

                                        data.add(xdonater);
                                        data.add(xdonation );


                                        donationsArray.add(data);
                                        adapter.notifyDataSetChanged();


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}