package com.hazyaz.weshare.users.areaincharge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.donater.MyListAdapter;

import java.util.ArrayList;

public class AssignDP extends AppCompatActivity {

    MyListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<String> deliveryPerson;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, updatedp;
    FirebaseAuth mAuth;
    String donation_key,person_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_assign_dp);


        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("delivery_person");

        updatedp = firebaseDatabase.getReference("donater");

        Intent intent = getIntent();
        donation_key = intent.getStringExtra("donation_key");
        person_key = intent.getStringExtra("person_key");

        recyclerView = findViewById(R.id.recyclerViewdeliveryperson);

        adapter = new MyListAdapter(new ArrayList<ArrayList<String>>(),"AssignDP",getApplicationContext(),deliveryPerson);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

         getallDeliveryPerson();

    }

    void getallDeliveryPerson(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name, city, area,email , phone, state, Image,key_donation;
                name = snapshot.child("name").getValue().toString();
                email = snapshot.child("email").getValue().toString();
                phone = snapshot.child("phone").getValue().toString();

//              area = snapshot.child("area").getValue().toString();
                city = snapshot.child("city").getValue().toString();
                state = snapshot.child("state").getValue().toString();
//              Image = snapshot.child("image").getValue().toString();



                deliveryPerson.add(name);
                deliveryPerson.add(email);
                deliveryPerson.add(phone);

                deliveryPerson.add(" ");
                deliveryPerson.add(city);
                deliveryPerson.add(state);
//              deliveryPerson.add(Image);
                deliveryPerson.add(snapshot.getKey());
                deliveryPerson.add(person_key);
                deliveryPerson.add(donation_key);
                deliveryPerson.add(snapshot.getKey());




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}