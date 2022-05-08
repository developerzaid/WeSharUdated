package com.hazyaz.weshare.users.areaincharge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Objects;

public class AIHome extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, innerDB, AiDB;
    FirebaseAuth mAuth;
    ArrayList<ArrayList<String>> donations = new ArrayList<ArrayList<String>>();
    ArrayList<String> DonationPreson = new ArrayList<String>();

    MyListAdapter adapter;
    RecyclerView recyclerView;
    String userKey;
    TextView uName, uPhone, uCity, uEmail, totDonations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_home);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerViewi);

        uName = findViewById(R.id.UNamex);
//        Address is City
        uCity = findViewById(R.id.UCityx);
        uPhone = findViewById(R.id.UPhonex);
        uEmail = findViewById(R.id.UEmailx);


        adapter = new MyListAdapter(donations, "AIHome", getApplicationContext(), DonationPreson);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);


    }


    void getUserInfo() {

        AiDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String uid = mAuth.getCurrentUser().getUid();

                String name = snapshot.child(uid).child("name").getValue().toString();
                String area = "";
                String city = snapshot.child(uid).child("city").getValue().toString();
                String state = snapshot.child(uid).child("state").getValue().toString();
                String phone = snapshot.child(uid).child("phone").getValue().toString();
                String email = snapshot.child(uid).child("email").getValue().toString();

                DonationPreson.add(name);
                DonationPreson.add(area);
                DonationPreson.add(city);
                DonationPreson.add(state);
                DonationPreson.add(phone);
                DonationPreson.add(email);

                Log.d("AIData1", "" + name + email);
                uName.setText(name);
                uEmail.setText(email);
                uPhone.setText(phone);
                uCity.setText(area + ", " + city + ", " + state);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    void getAllDonations() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object ic = snapshot.getValue();


                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String key = dsp.getKey();
                    assert key != null;
                    userKey = key;

                    innerDB.child(key).child("donations").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot isp : snapshot.getChildren()) {
                                ArrayList<String> data = new ArrayList<>();

                                String donationName = snapshot.child(Objects.requireNonNull(isp.getKey())).child("donation_name").getValue().toString();
                                String donationDesc = snapshot.child(Objects.requireNonNull(isp.getKey())).child("donation_desc").getValue().toString();
                                String timestamp = snapshot.child(Objects.requireNonNull(isp.getKey())).child("timestamp").getValue().toString();
                                String ImageXX = snapshot.child(Objects.requireNonNull(isp.getKey())).child("donation_image").getValue().toString();
                                String donationWith = snapshot.child(Objects.requireNonNull(isp.getKey())).child("donation_with").getValue().toString();
                                String CurrentLocation = snapshot.child(Objects.requireNonNull(isp.getKey())).child("current_location").getValue().toString();


                                data.add(donationName);
                                data.add(donationDesc);
                                data.add(timestamp);
                                data.add(ImageXX);
                                data.add(donationWith);
                                data.add(CurrentLocation);


                                data.add(isp.getKey());
                                data.add(dsp.getKey());

                                Log.d("AIData2", "" + isp.getKey() + "   " + dsp.getKey());

                                donations.add(data);

                                adapter.notifyDataSetChanged();

//                                Log.d("asdasdas 2",""+snapshot.child(Objects.requireNonNull(isp.getKey())).child("Donation Name").getValue());

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    Log.d("thisisdoantion", "" + dsp.getKey());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String AILogged = prefs.getString("AreaIncharge", "");

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Log.d("thisiscurrent", "" + currentUser);
        if (currentUser == null) {
            startActivity(new Intent(AIHome.this, AILogin.class));
        } else {

            firebaseDatabase = FirebaseDatabase.getInstance();

            databaseReference = firebaseDatabase.getReference("donater");
            innerDB = firebaseDatabase.getReference("donater");
            AiDB = firebaseDatabase.getReference("area_incharge");

            getAllDonations();
            getUserInfo();
        }
    }


    public void updateDeliveryPerson(String donater, String donation, String delivery) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference deliveryData = firebaseDatabase.getReference("delivery_person");
        deliveryData.child(delivery).child("donations").child(donater).child("donation_object").setValue(donation);


    }


}