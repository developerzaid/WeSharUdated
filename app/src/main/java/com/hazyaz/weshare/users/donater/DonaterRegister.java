package com.hazyaz.weshare.users.donater;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hazyaz.weshare.R;

import java.util.HashMap;

public class DonaterRegister extends AppCompatActivity {

    String area[] = {"Andhra Pradesh",
            "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh", "West Bengal", "Andaman and Nicobar Islands", "Chandigarh",
            "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Lakshadweep", "Puducherry"};

    EditText don_name, don_password, don_email, don_city, don_phone, don_area;
    Spinner donStateSpinner;
    Button register_don_button;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donater_register);


        don_name = findViewById(R.id.don_reg_name);
        don_password = findViewById(R.id.don_reg_password);
        don_email = findViewById(R.id.don_reg_email);
        don_phone = findViewById(R.id.don_reg_PhoneNo);
        don_city = findViewById(R.id.don_city);
        don_area = findViewById(R.id.don_area);
        register_don_button = findViewById(R.id.don_reg_submit);

        donStateSpinner = findViewById(R.id.state_spinner_don_register);

        Toolbar toolbar = findViewById(R.id.toolbar54);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DONATER REGISTRATION");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        area); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        donStateSpinner.setAdapter(spinnerArrayAdapter);
        progressDialog = new ProgressDialog(this, 4);

        register_don_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name, pass, email, phone, city, state, area;

                name = don_name.getText().toString();
                pass = don_password.getText().toString();
                email = don_email.getText().toString();
                phone = don_phone.getText().toString();
                city = don_city.getText().toString();
                state = donStateSpinner.getSelectedItem().toString();
                area = don_area.getText().toString();


                if (email.equals("") || name.equals("") || phone.equals("") ||
                        city.equals("") || pass.equals("") || area.equals("")) {
                    Toast.makeText(getApplicationContext(), "fill all", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    don_email.setError("Invalided Email");
                    don_email.setFocusable(true);

                } else if (pass.length() < 6) {
                    don_password.setError("Password length at least 6 characters");
                    don_password.setFocusable(true);
                }

                registerDonater(name, pass, email, phone, state, city, area);
            }
        });

    }


    void registerDonater(String name, String pass, String email, String phone, String state, String city, String area) {
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Registering User");
        progressDialog.setMessage("Please wait, registration in progress");
        progressDialog.show();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {


                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();

                            hashMap.put("name", name);
                            hashMap.put("email", email);
                            hashMap.put("phone", phone);
                            hashMap.put("state", state);
                            hashMap.put("city", city);
                            hashMap.put("area", area);


                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference("donater");
                            reference.child(uid).setValue(hashMap);
                            //sucess
                            startActivity(new Intent(DonaterRegister.this, DonaterHome.class));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
