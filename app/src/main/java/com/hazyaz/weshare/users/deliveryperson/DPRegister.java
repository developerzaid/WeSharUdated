package com.hazyaz.weshare.users.deliveryperson;

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

public class DPRegister extends AppCompatActivity {

    String area[] = {"Andhra Pradesh",
            "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh", "West Bengal", "Andaman and Nicobar Islands", "Chandigarh",
            "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Lakshadweep", "Puducherry"};

    EditText dp_name, dp_password, dp_email, dp_city, dp_phone;
    Spinner dpStateSpinner;
    Button register_dp_button;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dp_register);


        dp_name = findViewById(R.id.dp_reg_name);
        dp_password = findViewById(R.id.dp_reg_password);
        dp_email = findViewById(R.id.dp_reg_email);
        dp_phone = findViewById(R.id.dp_reg_PhoneNo);
        dp_city = findViewById(R.id.dp_city);

        Toolbar toolbar = findViewById(R.id.toolbar54);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DELIVERY REGISTER");


        register_dp_button = findViewById(R.id.dp_reg_submit);

        dpStateSpinner = findViewById(R.id.state_spinner_dp_register);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        area); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        dpStateSpinner.setAdapter(spinnerArrayAdapter);


        register_dp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name, pass, email, phone, city, state;

                name = dp_name.getText().toString();
                pass = dp_password.getText().toString();
                email = dp_email.getText().toString();
                phone = dp_phone.getText().toString();
                city = dp_city.getText().toString();
                state = dpStateSpinner.getSelectedItem().toString();


                if (email.equals("") || name.equals("") || phone.equals("") ||
                        city.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "fill all", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    dp_email.setError("Invalided Email");
                    dp_email.setFocusable(true);

                } else if (pass.length() < 6) {
                    dp_password.setError("Password length at least 6 characters");
                    dp_password.setFocusable(true);
                }

                registerDeliveryPerson(name, pass, email, phone, state, city);
            }
        });
    }

    void registerDeliveryPerson(String name, String pass, String email, String phone, String state, String city) {
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


                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference("delivery_person");
                            reference.child(uid).setValue(hashMap);
                            //sucess
                            startActivity(new Intent(DPRegister.this, DPHome.class));
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


}