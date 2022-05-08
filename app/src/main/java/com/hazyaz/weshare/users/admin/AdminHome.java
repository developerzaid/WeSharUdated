package com.hazyaz.weshare.users.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {
    String area[] = {"Mumbai-Central", "Mahim", "Nallasopara", "Andheri", "Byculla", "Jogeshwari"};
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<ArrayList<String>> AreaIncharge = new ArrayList<ArrayList<String>>();
    MyAIAdapter adapter;
    RecyclerView recyclerView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        mAuth = FirebaseAuth.getInstance();

        Spinner spinner = (Spinner) findViewById(R.id.area_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        area); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("AreaIncharge");
        showAllAreaIncharge();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewadmin);
        adapter = new MyAIAdapter(AreaIncharge, "Admin Home", getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);


    }


    void showAllAreaIncharge() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {

                    ArrayList<String> data = new ArrayList<>();
                    String name = dsp.child("Name").getValue().toString();
                    String email = dsp.child("Email").getValue().toString();
                    String phone = dsp.child("Phone").getValue().toString();
                    String area = dsp.child("area").getValue().toString();
                    Log.d("sdfsd2333f4e34", "" + name);
                    data.add(name);
                    data.add(email);
                    data.add(phone);
                    data.add(area);
                    AreaIncharge.add(data);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            startActivity(new Intent(AdminHome.this, AdminLogin.class));

        }
    }


}