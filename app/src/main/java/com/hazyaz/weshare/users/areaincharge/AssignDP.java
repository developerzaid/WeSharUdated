package com.hazyaz.weshare.users.areaincharge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

public class AssignDP extends AppCompatActivity {

    UpdateDP adapter;
    RecyclerView recyclerView;
    ArrayList<String> deliveryPerson = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, updatedp;
    FirebaseAuth mAuth;
    String donation_key, person_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_assign_dp);


        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("delivery_person");

        updatedp = firebaseDatabase.getReference("donater");
        getallDeliveryPerson();

        Intent intent = getIntent();
        donation_key = intent.getStringExtra("uid_donation");
        person_key = intent.getStringExtra("uid_person");

        Log.d("as23as", "" + person_key + donation_key);


        recyclerView = findViewById(R.id.recyclerViewdeliveryperson);
        adapter = new UpdateDP(deliveryPerson, getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);


    }

    void getallDeliveryPerson() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("sdfsdf34234", "" + snapshot.getValue());
                for (DataSnapshot dsp : snapshot.getChildren()) {

                    String name, city, area, email, phone, state, Image, key_donation;
                    name = dsp.child("name").getValue().toString();
                    email = dsp.child("email").getValue().toString();
                    phone = dsp.child("phone").getValue().toString();
                    city = dsp.child("city").getValue().toString();
                    state = dsp.child("state").getValue().toString();

                    deliveryPerson.add(name);
                    deliveryPerson.add(email);
                    deliveryPerson.add(phone);
                    deliveryPerson.add(city);
                    deliveryPerson.add(state);

                    Log.d("as23as", "" + person_key + donation_key);
                    deliveryPerson.add(person_key);
                    deliveryPerson.add(donation_key);
                    deliveryPerson.add(dsp.getKey());

                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}