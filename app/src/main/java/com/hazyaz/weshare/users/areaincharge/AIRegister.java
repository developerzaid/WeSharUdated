package com.hazyaz.weshare.users.areaincharge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

public class AIRegister extends AppCompatActivity {

    String area[] = {"Andhra Pradesh",
            "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh", "West Bengal", "Andaman and Nicobar Islands", "Chandigarh",
            "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Lakshadweep", "Puducherry"};
    EditText ai_name, ai_password, ai_email,ai_city, ai_phone, ai_;
    Spinner StateSpinner;
    Button register_ai_button;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_register);


        ai_name = findViewById(R.id.ai_reg_name);
        ai_password = findViewById(R.id.ai_reg_password);
        ai_email = findViewById(R.id.ai_reg_email);
        ai_phone = findViewById(R.id.ai_reg_PhoneNo);
        ai_city = findViewById(R.id.ai_reg_city);
        register_ai_button = findViewById(R.id.ai_reg_submit);

        StateSpinner = findViewById(R.id.states_spinner_ai_register);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        area); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        StateSpinner.setAdapter(spinnerArrayAdapter);
        progressDialog=new ProgressDialog(this,4);


        register_ai_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name, pass, email, phone, city, state;

                name = ai_name.getText().toString();
                pass = ai_password.getText().toString();
                email = ai_email.getText().toString();
                phone = ai_phone.getText().toString();
                city = ai_city.getText().toString();
                state = StateSpinner.getSelectedItem().toString();

                if(email.equals("")||name.equals("")||phone.equals("")||
                        city.equals("")||pass.equals("") ||area.equals("")     ){
                    Toast.makeText(getApplicationContext(), "fill all the data ", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    ai_email.setError("Invalided Email");
                    ai_email.setFocusable(true);

                }
                else if(pass.length()<6){
                    ai_password.setError("Password length at least 6 characters");
                    ai_password.setFocusable(true);
                }
                else{
                    SharedPreferences.Editor editor;
                    editor= PreferenceManager.getDefaultSharedPreferences(AIRegister.this).edit();
                    editor.putString("AreaIncharge", "LoggedIn");
                    editor.apply();
                }
            // Sending data to firebase
                RegisterAreaIncharge(name,pass,email,phone,city,state);
            }
        });

    }

    void RegisterAreaIncharge(String name, String pass, String email, String phone, String city,String state){

        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Registering User");
        progressDialog.setMessage("Please wait, registration in progress");
        progressDialog.show();

        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            String zero="0";
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid=user.getUid();

                            HashMap<Object,String> hashMap=new HashMap<>();

                            hashMap.put("name",name);
                            hashMap.put("email",email);
                            hashMap.put("phone",phone);
                            hashMap.put("state",state);
                            hashMap.put("city",city);

                            FirebaseDatabase database=FirebaseDatabase.getInstance();

                            DatabaseReference reference=database.getReference("area_incharge");
                            reference.child(uid).setValue(hashMap);
                            //sucess
                            startActivity(new Intent(AIRegister.this, AIHome.class));
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }





}
