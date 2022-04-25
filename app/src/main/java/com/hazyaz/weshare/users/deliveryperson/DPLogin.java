package com.hazyaz.weshare.users.deliveryperson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.donater.DonaterHome;

import static android.content.ContentValues.TAG;

public class DPLogin extends AppCompatActivity {

    EditText username, email , pass;
    Button dp_login, dp_register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dp_login);


        username=findViewById(R.id.dp_username);
        pass=findViewById(R.id.dp_password);
        email=findViewById(R.id.dp_email);
        dp_login=findViewById(R.id.dp_loginBtn);
        dp_register = findViewById(R.id.dp_register);

        dp_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iEmail, iUsername, iPassword;

                iEmail=username.getText().toString().trim();
                iPassword=pass.getText().toString().trim();

                loginUser(iEmail,iPassword);
            }
        });
        dp_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DPLogin.this,DPRegister.class));
            }
        });

    }

    void loginUser(String email, String pass){

        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait, Loggin in progress");
        progressDialog.show();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), DPHome.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Authentication failed."+task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }









}